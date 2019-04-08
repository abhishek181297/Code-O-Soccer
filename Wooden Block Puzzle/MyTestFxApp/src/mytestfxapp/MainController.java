/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytestfxapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author abhishek
 */
public class MainController implements Initializable {
    
    @FXML private AnchorPane anchorPane;
    @FXML private Tile tile;
    @FXML private GridPane gridPane;
    @FXML private GridPane gridPane2;
    @FXML private GridPane gridPane11;
    @FXML private GridPane gridPane22,gridPane12,gridPane21,gridPane33,gridPane31;
    @FXML private GridPane gridPane13;
    @FXML private ChoiceBox languageChoice;
    @FXML private TextArea textArea1,textArea2;
    @FXML private Button reset,play,stop,clear,compile;
    @FXML private Label scoreValue;
    @FXML Tile1 tile1;
    @FXML private Button btnRun;
    @FXML TileBlack tileblack;
    
    int[][][] gp;
    
    Group group;
    Node node;
    private int score;
    private String compile_command;
    private String run_command;
    private String extension;
    private final String FILENAME = "Player";
    private long waitTimeout;
    private String out;
    Game game = new Game();
    int retVal = -1;
    
    
    
    @FXML
    public void pressCompile(){
        try
        {
            saveFile(FILENAME + extension, textArea1.getText().getBytes());
            if(btnRun.isDisable() && runProcess(compile_command, null, waitTimeout * 20) == 0)
            {
                //System.out.println(out);
                printOutput("Compile Successfull");
                btnRun.setDisable(false);
                play.setDisable(false);
                
                
            }
        }
        catch(Exception ex)
        {
            printOutput(ex.getMessage());
        }
    }
    @FXML
    public void pressRun(){
        try{
        if(!btnRun.isDisable())
        {
            if(game.isMovePossible())
            {
                if (extension.equals(".py"))
                        saveFile(FILENAME + extension, textArea1.getText().getBytes());
                String inputData = "";
                for(int i=0;i<10;i++)
                {
                    for(int j=0;j<10;j++)
                    {
                        inputData = inputData + Integer.toString(game.board[i][j])+" ";
                    }
                    inputData = inputData + "\n";
                }
                for(int i=0;i<3;i++)
                {
                    if(game.randomArray[i]==-1)
                    {
                        game.randomArray[i] = i;
                    }
                    inputData = inputData + Integer.toString(game.randomArray[i])+" ";
                }
                runProcess(run_command, inputData, waitTimeout);
                
                game.makeMove(out);
                updateGrid();
                removeFilledRowCol();
                updateGrid();
                scoreValue.setText(Long.toString(game.score));
                
            }
            else
            {
                if (stop.isDisable()) {
                        Alert alert = new Alert(Alert.AlertType.NONE, "Your Score: " + Long.toString(game.score+CalculateRowColScore()), ButtonType.OK);
                        alert.show();
                    } else
                        stop.fire();
            }
        }
        else {
                printOutput("First Compile the file");
            }
        } catch (IOException ex) {
            System.out.println("this io ");
            printOutput("Error while saving file");
           //System.out.println(ex);
           ex.printStackTrace();
        } catch (IllegalArgumentException e) {
            stop.fire();
            long finalscore = game.score+CalculateRowColScore();
            scoreValue.setText(Long.toString(finalscore));
            Alert alert = new Alert(Alert.AlertType.NONE, e.getMessage() + " Your Score: "+ Long.toString(finalscore), ButtonType.OK);
            alert.show();
        } catch (InterruptedException ex) {
            printOutput("TimeLimit exceeded");
            stop.fire();
            Alert alert = new Alert(Alert.AlertType.NONE, "TimeLimit exceeded\nYou are disqualified", ButtonType.OK);
            alert.show();
        } catch (TimeoutException ex) {
             stop.fire();
            Alert alert = new Alert(Alert.AlertType.NONE, "TimeLimit exceeded\nYou are disqualified", ButtonType.OK);
            alert.show();
        } catch(Exception ex)
        {
            stop.fire();
            ex.printStackTrace();
        }
        
    }
   
    @FXML 
    public void pressClear(){
        textArea2.setText(null);
    }
    private void saveFile(String fileName, byte[] data) throws IOException {
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(data);
        out.close();
    }
    private void printOutput(String output)
    {
        this.textArea2.appendText(output + "\n");
        out = output;
    }
    private void printLines(InputStream ins) throws IOException {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            printOutput(line);
        }
    }
    
     private int runProcess(String command, String argument, long waitTimeout) throws InterruptedException, IOException, TimeoutException {
        Process pro = Runtime.getRuntime().exec(command);

        if (argument != null) {
            OutputStream outStream = pro.getOutputStream();
            outStream.write(argument.getBytes());
            outStream.flush();
            outStream.close();
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<?> future = executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    printLines(pro.getInputStream());
                    printLines(pro.getErrorStream());
                } catch (IOException e) {
                    printOutput(e.getStackTrace().toString());
                }
            }
        });

        try {
            future.get(waitTimeout, TimeUnit.SECONDS);
        } catch (ExecutionException e) {
            printOutput(e.getStackTrace().toString());
        }

        pro.waitFor();

        retVal = pro.exitValue();
        executorService.shutdown();
        return retVal;
    }
     
     public void updateGrid(){
         gridPane.getChildren().clear();
         gridPane2.getChildren().clear();
         gridPane11.getChildren().clear();
         gridPane12.getChildren().clear();
         gridPane21.getChildren().clear();
         gridPane22.getChildren().clear();
         gridPane31.getChildren().clear();
         gridPane13.getChildren().clear();
         gridPane33.getChildren().clear();
         
         
          // Creating the 10 * 10 Board        
        for(int i=0;i<10;i++)
        {
            for(int j=0;j<10;j++)
            {
                if(game.board[i][j] == -1)
                {
                    tileblack = new TileBlack();
                    gridPane.add(tileblack, j, i);
                }
                else if(game.board[i][j]==0)
                {
                tile = new Tile();
                gridPane.add(tile,j,i);
                }
                else
                {
                    tile1 = new Tile1();
                    gridPane.add(tile1,j,i);
                }
                
            }
        }
        
         //Creating the 5*13 Board
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<13;j++)
            {
                tile = new Tile();
                gridPane2.add(tile,j,i);
            }
        }
        
         //Setting the 3*3 GridPanes invisible by default. 
        gridPane11.setVisible(false);
        gridPane12.setVisible(false);
        gridPane21.setVisible(false);
        gridPane22.setVisible(false);
        gridPane31.setVisible(false);
        gridPane13.setVisible(false);
        gridPane33.setVisible(false);
       /* 
        // Setting the size of 3*3 GridPanes 
        gridPane11.setPrefSize(180,150);
        gridPane12.setPrefSize(180,150);
        gridPane21.setPrefSize(180,150);
        gridPane22.setPrefSize(180,150);
        gridPane13.setPrefSize(180,150);
        gridPane31.setPrefSize(180,150);
        gridPane33.setPrefSize(180,150);
        
        // Creating the Tiles for 3*3 gridPanes
        // Here since duplicate children can't be added to parent so we have 
        // to create the instances of Tile1 multiple times
        
        tile1 = new Tile1();
        
        gridPane11.add(tile1,1,1);  // GridPane 1*1 is Painted
        
        tile1 = new Tile1();
        gridPane12.add(tile1, 1, 1);
        tile1 = new Tile1();
        gridPane12.add(tile1,2,1);
        
        
        tile1 = new Tile1();
        gridPane21.add(tile1, 1, 1);
        tile1 = new Tile1();
        gridPane21.add(tile1,1,2);
        
        
        tile1 = new Tile1();
        gridPane22.add(tile1, 1, 1);
        tile1 = new Tile1();
        gridPane22.add(tile1,2,1);
        tile1 = new Tile1();
        gridPane22.add(tile1, 1, 2);
        tile1 = new Tile1();
        gridPane22.add(tile1,2,2);
        
        
        
        tile1 = new Tile1();
        gridPane13.add(tile1, 0, 1);
        tile1 = new Tile1();
        gridPane13.add(tile1,1,1);
        tile1 = new Tile1();
        gridPane13.add(tile1,2,1);
        
        
        
        tile1 = new Tile1();
        gridPane31.add(tile1,1,0);
        tile1 = new Tile1();
        gridPane31.add(tile1,1,1);
        tile1 = new Tile1();
        gridPane31.add(tile1,1,2);
        
        
        
        tile1 = new Tile1();
        gridPane33.add(tile1,0,0);
        tile1 = new Tile1();
        gridPane33.add(tile1,0,1);
        tile1 = new Tile1();
        gridPane33.add(tile1,0,2);
        tile1 = new Tile1();
        gridPane33.add(tile1,1,0);
        tile1 = new Tile1();
        gridPane33.add(tile1,1,1);
        tile1 = new Tile1();
        gridPane33.add(tile1,1,2);
        tile1 = new Tile1();
        gridPane33.add(tile1,2,0);
        tile1 = new Tile1();
        gridPane33.add(tile1,2,1);
        tile1 = new Tile1();
        gridPane33.add(tile1,2,2);
        */
         gp = new int[7][3][3];
    
        /*
        gp11 = 0
        gp12 = 1
        gp21 = 2
        gp22 = 3
        gp13 = 4
        gp31 = 5
        gp33 = 6
        */
        
        gp[0][1][1] = 1;
        gp[1][1][1] = gp[1][1][2] = 1;
        gp[2][1][1] = gp[2][2][1] = 1;
        gp[3][1][1] = gp[3][1][2] = gp[3][2][1] = gp[3][2][2] = 1;
        gp[4][1][0] = gp[4][1][1] = gp[4][1][2] = 1;
        gp[5][0][1] = gp[5][1][1] = gp[5][2][1] = 1;
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
                gp[6][i][j] = 1;
        }
        for(int i=0;i<3;i++)
        {
            if(game.randomArray[i]==-1)
            {
                game.randomArray[i] = i;
            }
            int num = game.randomArray[i];
            if(i==0)
            {
                for(int k=0;k<3;k++)
                {
                    for(int j=0;j<3;j++)
                    {
                        if(gp[num][k][j]==1)
                        {
                            gridPane2.add(new Tile1(),j+1,k+1);
                        }
                    }
                }

               /* switch(game.randomArray[i])
                {
                    case 0:gridPane11.setVisible(true);
                            gridPane11.setLayoutX(87);
                            gridPane11.setLayoutY(656);
                            break;
                    case 1:gridPane12.setVisible(true);
                            gridPane12.setLayoutX(87);
                            gridPane12.setLayoutY(656);
                            break;
                    case 2:gridPane21.setVisible(true);
                            gridPane21.setLayoutX(87);
                            gridPane21.setLayoutY(656);
                            break;
                    case 3:gridPane22.setVisible(true);
                            gridPane22.setLayoutX(87);
                            gridPane22.setLayoutY(656);
                            break;

                    case 4:gridPane13.setVisible(true);
                            gridPane13.setLayoutX(87);
                            gridPane13.setLayoutY(656);
                            break;
                    case 5:gridPane31.setVisible(true);
                            gridPane31.setLayoutX(87);
                            gridPane31.setLayoutY(656);
                            break;
                    case 6:gridPane33.setVisible(true);
                            gridPane33.setLayoutX(87);
                            gridPane33.setLayoutY(656);
                            break;
                }*/
            }
            else if(i==1)
            {
                for(int k=0;k<3;k++)
                {
                    for(int j=0;j<3;j++)
                    {
                        if(gp[num][k][j]==1)
                        {
                            gridPane2.add(new Tile1(),j+5,k+1);
                        }
                    }
                }
                /* switch(game.randomArray[i])
                {
                    case 0:gridPane11.setVisible(true);
                            gridPane11.setLayoutX(327);
                            gridPane11.setLayoutY(656);
                            break;
                    case 1:gridPane12.setVisible(true);
                            gridPane12.setLayoutX(327);
                            gridPane12.setLayoutY(656);
                            break;
                    case 2:gridPane21.setVisible(true);
                            gridPane21.setLayoutX(327);
                            gridPane21.setLayoutY(656);
                            break;
                    case 3:gridPane22.setVisible(true);
                            gridPane22.setLayoutX(327);
                            gridPane22.setLayoutY(656);
                            break;

                    case 4:gridPane13.setVisible(true);
                            gridPane13.setLayoutX(327);
                            gridPane13.setLayoutY(656);
                            break;

                    case 5:gridPane31.setVisible(true);
                            gridPane31.setLayoutX(327);
                            gridPane31.setLayoutY(656);
                            break;
                    case 6:gridPane33.setVisible(true);
                            gridPane33.setLayoutX(327);
                            gridPane33.setLayoutY(656);
                            break;
                }*/
            }
            else
            {
                for(int k=0;k<3;k++)
                {
                    for(int j=0;j<3;j++)
                    {
                        if(gp[num][k][j]==1)
                        {
                            gridPane2.add(new Tile1(),j+9,k+1);
                        }
                    }
                }
                 /*switch(game.randomArray[i])
                {
                    case 0:gridPane11.setVisible(true);
                            gridPane11.setLayoutX(567);
                            gridPane11.setLayoutY(656);
                            break;
                    case 1:gridPane12.setVisible(true);
                            gridPane12.setLayoutX(567);
                            gridPane12.setLayoutY(656);
                            break;
                    case 2:gridPane21.setVisible(true);
                            gridPane21.setLayoutX(567);
                            gridPane21.setLayoutY(656);
                            break;
                    case 3:gridPane22.setVisible(true);
                            gridPane22.setLayoutX(567);
                            gridPane22.setLayoutY(656);
                            break;

                    case 4:gridPane13.setVisible(true);
                            gridPane13.setLayoutX(567);
                            gridPane13.setLayoutY(656);
                            break;
                    case 5:gridPane31.setVisible(true);
                            gridPane31.setLayoutX(567);
                            gridPane31.setLayoutY(656);
                            break;
                    case 6:gridPane33.setVisible(true);
                            gridPane33.setLayoutX(567);
                            gridPane33.setLayoutY(656);
                            break;
                }*/
            }
        }
     }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println(System.getProperty("os.name"));
        languageChoice.getItems().add("C++");
        languageChoice.getItems().add("Java");
        anchorPane.setStyle("-fx-background-color: #ffff80;");
        
        languageChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                switch(newValue.intValue())
                {
                    case 0:
                        extension = ".cpp";
                        compile_command = "g++ " + FILENAME + extension + " -o " + FILENAME + ".exe";
                        run_command =FILENAME + ".exe";
                        if(System.getProperty("os.name").equals("Linux"))
                        {
                            run_command = "./"+run_command;
                        }
                        compile.setDisable(false);
                        btnRun.setDisable(true);
                        play.setDisable(true);
                        waitTimeout = 1;
                        break;
                    case 1:
                        extension = ".java";
                        compile_command = "javac " + FILENAME + extension;
                        run_command = "java " + FILENAME;
                        compile.setDisable(false);
                        play.setDisable(true);
                        btnRun.setDisable(true);
                        waitTimeout = 2;
                        break;
                    case 2:
                        extension = ".py";
                        run_command = "python " + FILENAME + extension;
                        compile.setDisable(false);
                        play.setDisable(true);
                        btnRun.setDisable(true);
                        waitTimeout = 2;
                        break;
                    default:
                        printOutput("unknown");
                        break;
                        
                }
                try {
                    File file = new File(FILENAME + extension);
                    FileInputStream fis = new FileInputStream(file);
                    byte[] data = new byte[(int) file.length()];
                    fis.read(data);
                    fis.close();

                    String str = new String(data, "UTF-8");

                    textArea1.setText(str);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        languageChoice.getSelectionModel().selectFirst();
        textArea1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(!extension.equals(".py")){
                    play.setDisable(true);
                }
                if(event.getCode() == KeyCode.S && event.isControlDown())
                {
                    try {
                        saveFile(FILENAME + extension, textArea1.getText().getBytes());
                        btnRun.setDisable(true);
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        for(int i=0;i<3;i++)
                {
                    if(game.randomArray[i]==-1)
                    {
                        game.randomArray[i] = i;
                    }
                    //inputData = inputData + Integer.toString(game.randomArray[i])+" ";
                }
        //addBlackTiles();
        
        
        updateGrid();
       score=0;
        AnimationTimer animation = new AnimationTimer() {
            @Override
            public void handle(long now) {
                 try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                 pressRun();
            }
        };
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           stop.setDisable(false);
           play.setDisable(true);
           animation.start();
            }
        });
        stop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           animation.stop();
           if (!game.isMovePossible()) {
               scoreValue.setText(Long.toString(game.score+CalculateRowColScore()));
                    Alert alert = new Alert(Alert.AlertType.NONE, "Your Score: " + Long.toString(game.score+CalculateRowColScore()), ButtonType.OK);
                    alert.show();
                }
                stop.setDisable(true);
                
                play.setDisable(false);
            }
        });
        
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game = null;
                game = new Game();
                
                animation.stop();
                /*
               for(int i=0;i<10;i++)
               {
                   for(int j=0;j<10;j++)
                       game.board[i][j]=0;
               }
               game.score = 0;
                for(int i=0;i<3;i++)
                    game.randomArray[i] = -1;*/
                //addBlackTiles();
                updateGrid();
                stop.setDisable(true);
                if(!btnRun.isDisable())
                {
                    play.setDisable(false);
                }
                textArea2.setText("");
                scoreValue.setText("");
                System.out.println(game.randomArray[0] +" "+game.randomArray[1] + " "+game.randomArray[2]);
            }
        });
        
      
        //gridPane11.setVisible(true);
        //gridPane33.setVisible(true);
        //gridPane12.setVisible(true);
        //gridPane21.setVisible(true);
        //gridPane22.setVisible(true);
        //gridPane13.setVisible(true);
        //gridPane31.setVisible(true);
        
        //gridPane11.getParent().toFront();
       
       
        Node nd = getNodeFromGridPane(gridPane, 0, 0);
        if(nd instanceof Tile)
        {
            tile = (Tile) nd;
            if(tile.getText().equals("."))
            {
                System.out.println("helo");
            }
        }
       
    }    
   private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
    for (Node node : gridPane.getChildren()) {
        if (GridPane.getColumnIndex(node)!=null && GridPane.getRowIndex(node)!=null && GridPane.getColumnIndex(node) == col &&
                GridPane.getRowIndex(node) == row) {
            return node;
        }
    }
    return null;
}

    private long CalculateRowColScore() {
        long sc=0;
        for(int i=0;i<10;i++)
        {
            int cnt=0,cnt1=0;
            for(int j=0;j<10;j++)
            {
                if(game.board[i][j]==1 || game.board[i][j]==-1)
                    cnt++;
                if(game.board[j][i]==1 || game.board[i][j]==-1)
                    cnt1++;
            }
            if(cnt==10)
                sc+=10;
            if(cnt1==10)
                sc+=10;
        }
        return sc;
    }

    private void addBlackTiles() {
        int rx = (int)(Math.random()*3);
        int ry = (int)(Math.random()*9);
        game.board[rx][ry] = -1;
        game.board[rx][ry+1] = -1;
        rx = 3+ (int)(Math.random()*3);
        ry = (int)(Math.random()*9);
        game.board[rx][ry] = -1;
        game.board[rx][ry+1] = -1;
        rx = 6+ (int)(Math.random()*3);
        ry = (int)(Math.random()*9);
        game.board[rx][ry] = -1;
        game.board[rx][ry+1] = -1;
    }

    private void removeFilledRowCol() {
        for(int i=0;i<10;i++)
        {
            int cnt=0,cnt1=0;
            for(int j=0;j<10;j++)
            {
                if(game.board[i][j]==1 || game.board[i][j]==-1)
                    cnt++;
                if(game.board[j][i]==1 || game.board[j][i]==-1)
                    cnt1++;
            }
            if(cnt==10)
            {
                for(int j=0;j<10;j++)
                {
                    if(game.board[i][j]==1)
                        game.board[i][j]=0;
                }
                game.score+=10;
                scoreValue.setText(Long.toString(game.score));
            }
            if(cnt1==10)
            {
                for(int j=0;j<10;j++)
                {
                    if(game.board[j][i]==1)
                        game.board[j][i]=0;
                }
                game.score+=10;
                scoreValue.setText(Long.toString(game.score));
            }
        }
    }
    
}
