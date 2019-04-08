
package dotsandboxes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;


public class SampleController implements Initializable {
    
    @FXML
    private GridPane gridPane;
    @FXML
    private Button btnReset,btnPlay,btnStop,btnRun,btnSwap,btnCompile,btnClear;
    @FXML
    private TextArea textArea,output;
    @FXML
    private TextField player1,player2;
    @FXML
    private ChoiceBox languageChoice1,languageChoice2;
    @FXML
    private Pane myPane;
    @FXML
    private Label scoreValue;
    
    
    @FXML
    private AnchorPane anchorPane;
    
    private Board game = new Board();
    private int result;
    private Line line;
    private char currentPlayer;
    private Circle cir;

    private String compile_command1;
    private String run_command1;
    private String extension1;

    private String compile_command2;
    private String run_command2;
    private String extension2;

    private String player1Filename;
    private String player2Filename;

    private long waitTimeout1;
    private long waitTimeout2;

    private String out;

    int retVal = -1;
    private HashMap<Integer,Pair<Integer,Integer> > hm;
    private HashMap<Pair<Integer,Integer> ,Integer> hm1;
    private ArrayList<Pair<Integer,Integer> > ar;
    
    @FXML
    private void pressRun()
    {
        try{
            if(!btnRun.isDisable()){
                if(isMovePossible()){
                if (extension1.equals(".py"))
                        saveFile(player1Filename + extension1, textArea.getText().getBytes());
                
                String inputData = "";
                for(int i=0;i<36;i++)
                {
                    for(int j=0;j<36;j++)
                    {
                        inputData = inputData + game.board[i][j] + " ";
                        
                    }
                    inputData = inputData + "\n";   
                }
                /*for(int i=0;i<36;i++)
                {
                    for(int j=0;j<36;j++)
                    {
                        inputData = inputData + game.mat[i][j]+" ";
                    }
                    inputData = inputData + "\n";
                }*/
                if (currentPlayer == 'A')
                        runProcess(run_command1, inputData, waitTimeout1);
                    else
                        runProcess(run_command2, inputData, waitTimeout2);
                if (makeMove(out)) {
                        scoreValue.setText("Player 1: " + game.p1score + "   Player 2: " + game.p2score);
                        updateGrid();
                    } else {
                        btnStop.fire();
                        showPlayerDisqualifiedAlert("invalid output");
                    }
              }
                else{
                    if (btnStop.isDisable()) {
                        showResultAlertMessage();
                    } else
                        btnStop.fire();
                }
            }
            else
            {
                printOutput("First Compile the file");
            }
        }catch (InterruptedException ex) {
            printOutput("TimeLimit exceeded");
            showPlayerDisqualifiedAlert("TimeLimit Exceeded");
        } catch (IOException ex) {
            printOutput("Error while saving file");
        } catch (TimeoutException e) {
            btnStop.fire();
            showPlayerDisqualifiedAlert("TimeLimit Exceeded");
        }
        
    } 
    @FXML
    private void pressCompile()
    {
        try {
            player1Filename = player1.getText();
            player2Filename = player2.getText();
            //printOutput(compile_command2);
            saveFile(player1Filename + extension1, textArea.getText().getBytes());

            if (runProcess(compile_command1, null, waitTimeout1 * 20) == 0 &&
                    runProcess(compile_command2, null, waitTimeout2 * 20) == 0) {
                printOutput("Compile Successful");
                btnRun.setDisable(false);
                btnPlay.setDisable(false);
            }

        } catch (Exception ex) {
            printOutput(ex.getMessage());
        }
        
    }
    @FXML
    private void pressClear()
    {
        output.setText(null);
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hm = new HashMap<Integer, Pair<Integer,Integer> >();
        hm1 = new HashMap<Pair<Integer,Integer> ,Integer>();
        ar = new ArrayList<Pair<Integer,Integer> >(); 
        int cnt=0;
        int layx = 0,layy = 0;
        for(int i=0;i<6;i++)
        {
            for(int j=0;j<6;j++)
            {
                Pair<Integer,Integer> pr = new Pair<Integer,Integer>(i,j);
                hm1.put(pr, cnt);
                hm.put(cnt,pr);
                ar.add(new Pair<Integer,Integer>(layx,layy));
                layx+=100;
                cnt++;
            }
            layx = 0;
            layy+=90;
            
        }
        for(int i=0;i<36;i++)
        {
            for(int j=0;j<36;j++)
                System.out.print(game.mat[i][j]+" ");
            System.out.println();
        }
        
        //line = new Line(0,0,100,90);
        //myPane.getChildren().add(line);
        //anchorPane.getChildren().add(line);
        //for(int i=0;i<36;i++)
          //  System.out.println((ar.get(i)).getKey() + " "+(ar.get(i)).getValue());
        
       languageChoice1.getItems().add("C++");
        languageChoice1.getItems().add("Java");

        languageChoice2.getItems().add("C++");
        languageChoice2.getItems().add("Java");
        
        currentPlayer = 'A';
        
        languageChoice1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player1Filename = player1.getText();
                player2Filename = player2.getText();
                 //printOutput(player1Filename);
               // printOutput(player2Filename);
                switch (newValue.intValue()) {
                    case 0:
                        extension1 = ".cpp";
                        compile_command1 = "g++ " + player1Filename + extension1 + " -o " + player1Filename + ".exe";
                        run_command1 = player1Filename + ".exe";
                        if(System.getProperty("os.name").equals("Linux"))
                        {
                            run_command1 = "./"+run_command1;
                        }
                        btnCompile.setDisable(false);
                        btnPlay.setDisable(true);
                        btnRun.setDisable(true);
                        waitTimeout1 = 1;
                        break;
                    case 1:
                        extension1 = ".java";
                        compile_command1 = "javac " + player1Filename + extension1;
                        run_command1 = "java " + player1Filename;
                        btnCompile.setDisable(false);
                        btnPlay.setDisable(true);
                        btnRun.setDisable(true);
                        waitTimeout1 = 2;
                        break;
                    case 2:
                        extension1 = ".py";
                        run_command1 = "python " + player1Filename + extension1;
                        btnCompile.setDisable(true);
                        btnRun.setDisable(false);
                        waitTimeout1 = 2;
                        break;
                    default:
                        printOutput("Unknown");
                        break;
                }
                try {
                    File file = new File(player1Filename + extension1);
                    FileInputStream fis = new FileInputStream(file);
                    byte[] data = new byte[(int) file.length()];
                    fis.read(data);
                    fis.close();

                    String str = new String(data, "UTF-8");

                    textArea.setText(str);
                   /* file = new File(player2Filename + extension1);
                    fis = new FileInputStream(file);
                    data = new byte[(int) file.length()];
                    fis.read(data);
                    fis.close();
                    str = new String(data, "UTF-8");
                    printOutput(str);*/
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        languageChoice1.getSelectionModel().selectFirst();
        
        languageChoice2.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player1Filename = player1.getText();
                player2Filename = player2.getText();
               // printOutput(player1Filename);
              //  printOutput(player2Filename);
                switch (newValue.intValue()) {
                    case 0:
                        extension2 = ".cpp";
                        compile_command2 = "g++ " + player2Filename + extension2 + " -o " + player2Filename + ".exe";
                        run_command2 = player2Filename + ".exe";
                        if(System.getProperty("os.name").equals("Linux"))
                        {
                            run_command2 = "./"+run_command2;
                        }
                        waitTimeout2 = 1;
                        btnPlay.setDisable(true);
                        btnRun.setDisable(true);
                        break;
                    case 1:
                        extension2 = ".java";
                        compile_command2 = "javac " + player2Filename + extension2;
                        run_command2 = "java " + player2Filename;
                        waitTimeout2 = 2;
                        btnPlay.setDisable(true);
                        btnRun.setDisable(true);
                        break;
                    case 2:
                        extension2 = ".py";
                        run_command2 = "python " + player2Filename + extension2;
                        waitTimeout2 = 2;
                        break;
                    default:
                        printOutput("Unknown");
                        break;
                }
            }
        });

        languageChoice2.getSelectionModel().selectFirst();
        
        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!extension1.equals(".py")) {
                    btnRun.setDisable(true);
                    btnPlay.setDisable(true);
                }
                if (event.getCode() == KeyCode.S && event.isControlDown()) {
                    try {
                        saveFile(player1Filename + extension1, textArea.getText().getBytes());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        updateGrid();
        
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
        
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnStop.setDisable(false);
                btnPlay.setDisable(true);
                animation.start();
            }
        });
        
        btnStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                animation.stop();
                if (!isMovePossible()) {
                    showResultAlertMessage();
                }
                btnStop.setDisable(true);
                btnPlay.setDisable(false);
            }

           
        });
        
        btnReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game = null;
                game = new Board();
                updateGrid();
                scoreValue.setText("");
                animation.stop();
                btnStop.setDisable(true);
                if (!btnRun.isDisable())
                    btnPlay.setDisable(false);
                currentPlayer = 'A';
                output.setText("");
            }
        });
        
        btnSwap.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                player1Filename = player2.getText();
                player2Filename = player1.getText();
                player1.setText(player1Filename);
                player2.setText(player2Filename);
                int index = languageChoice1.getSelectionModel().getSelectedIndex();
                
                languageChoice1.getSelectionModel().clearSelection();
                languageChoice1.getSelectionModel().select(languageChoice2.getSelectionModel().getSelectedIndex());
                languageChoice2.getSelectionModel().clearSelection();
                languageChoice2.getSelectionModel().select(index);
            }
        });
        
    }   
    
    private void changePlayer() {
        currentPlayer = (currentPlayer == 'B') ? 'A' : 'B';
    }
    private void printOutput(String output) {
        this.output.appendText(output + '\n');
        out = output;
    }
    private void saveFile(String fileName, byte[] data) throws IOException {
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(data);
        out.close();
    }

     private boolean isMovePossible() {
         if(game.lineCount<60)
             return true;
         else
             return false;
     }

    private void showResultAlertMessage() {
        String message = null;
        if(game.p1score>game.p2score)
        {
            message = "Player1 Wins\n"+"Player1: "+game.p1score + " Player2: "+game.p2score;    
        }
        else
        {
            message = "Player2 Wins\n"+"Player1: "+game.p1score + " Player2: "+game.p2score;
        }
        Alert alert = new Alert(Alert.AlertType.NONE,  message, ButtonType.OK);
        alert.show();
    }
            
    private void updateGrid() {
        myPane.getChildren().clear();
         //cir = new Circle(0,0,5,Color.BLACK);
        
        //myPane.getChildren().add(cir);
       // Tile tile = new Tile();
        //tile.setLayoutX(0);
        //tile.setLayoutY(0);
        //myPane.getChildren().add(tile);
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<5;j++)
            {
                int index = i*5+j;
                Tile tile = new Tile();
                if(game.colorArray.get(index)=='B')
                {
                    tile.setStyle("-fx-background-color: blue;");
                    
                }
                else if(game.colorArray.get(index)=='A')
                    tile.setStyle("-fx-background-color: red;");
                tile.setLayoutX(j*100);
                tile.setLayoutY(i*90);
                myPane.getChildren().add(tile);
            }
        }
        for(int i=0;i<36;i++)
        {
            for(int j=i;j<36;j++)
            {
                if(game.board[i][j]==1)
                {
                    int startX,startY,endX,endY;
                    startX = ar.get(i).getKey();
                    startY = ar.get(i).getValue();
                    endX = ar.get(j).getKey();
                    endY = ar.get(j).getValue();
                    System.out.println(startX+" "+startY+" "+endX+" "+endY);
                    line = new Line(startX,startY,endX,endY);
                    line.setStrokeWidth(5);
                   // if(currentPlayer=='B')
                    //line.setStroke(Color.BLUE);
                    //else
                     // line.setStroke(Color.RED);
                    myPane.getChildren().add(line);
                }
            }
        }
        for(int i=0;i<36;i++)
        {
            cir = new Circle(ar.get(i).getKey(),ar.get(i).getValue(),5,Color.BLACK);
            myPane.getChildren().add(cir);
        }
        
    }
    private int runProcess(String command, String argument, long waitTimeout) throws InterruptedException, IOException, TimeoutException {
        retVal = -1;
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
    
    private void printLines(InputStream ins) throws IOException {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            printOutput(line);
        }
    }

    private boolean isCurrentPlayerMoveNotPossible() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean makeMove(String out) {
        try{
            
        out = out.trim();
        System.out.println(out);
        //if(out.charAt(0)!=currentPlayer)
          //  return false;
        int pos1,pos2;
        String splitStr[] = out.split("\\s+");
        if(splitStr.length!=2)
         {
             throw new IllegalArgumentException("Invalid Output Format.\nGame Ends.\n");
         }
        pos1 = Integer.parseInt(splitStr[0]);
        pos2 = Integer.parseInt(splitStr[1]);
       /* if(out.length()==3)
        {
             pos1= Integer.parseInt(out.charAt(0)+"");
            pos2 = Integer.parseInt(out.charAt(2)+"");
            
        }
        else if(out.length()==4)
        {
            if(out.charAt(1)==' ')
            {
                pos1 = Integer.parseInt(out.charAt(0)+"");
                String str1 = out.substring(2, 4);
                pos2 = Integer.parseInt(str1);
                System.out.println(str1);
                
            }
            else
            {
                String str1 = out.substring(0,2);
                pos1 = Integer.parseInt(str1);
                pos2 = Integer.parseInt(out.charAt(3)+"");
                System.out.println(str1);
            }
        }
        else
        {
            String str1 = out.substring(0,2);
            String str2 = out.substring(3,5);
            pos1 = Integer.parseInt(str1);
                pos2 = Integer.parseInt(str2);
        }*/
       
        if(pos2<pos1)
        {
            int tmp = pos2;
            pos2 = pos1;
            pos1 = tmp;
        }
        if(isThisMovePossible(pos1,pos2))
        {
            makeThisMove(pos1,pos2);
            return true;
        }
        else
            return false;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        
        
    }

    private void showPlayerDisqualifiedAlert(String mainMessage) {
        String message;
        if (currentPlayer == 'A')
            message = "Player 1 is disqualified because of " + mainMessage +"\nPlayer 2 Wins";
        else
            message = "Player 2 is disqualified because of " + mainMessage + "\nPlayer 1 Wins";
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.show();
    }

    private boolean isThisMovePossible(int pos1, int pos2) {
        if(pos1<0 || pos1>35 || pos2<0 || pos2>35)
            return false;
        if(game.board[pos1][pos2]==1)
            return false;
        int pos1LayX = ar.get(pos1).getKey();
        int pos1LayY = ar.get(pos1).getValue();
        int pos2LayX = ar.get(pos2).getKey();
        int pos2LayY = ar.get(pos2).getValue();
        if(Math.abs((pos2LayX-pos1LayX) + (pos2LayY-pos1LayY))==100 || 
                Math.abs((pos2LayX-pos1LayX) + (pos2LayY-pos1LayY))==90)  
          return true;
        else
            return false;
            
        
    }

    private void makeThisMove(int pos1, int pos2) {
        try{
            game.board[pos1][pos2] = 1;
            game.board[pos2][pos1] = 1;
            game.lineCount++;
            int flag=0;
            if(pos2-pos1==1) // Horizontal Line Drawn
            {
                if(pos2<=5) // If line belongs to first row then it only contributes to one sq
                {
                    if(game.board[pos1][pos2]==1 &&
                       game.board[pos1+6][pos2+6]==1 &&
                       game.board[pos1][pos1+6]==1 &&
                       game.board[pos2][pos2+6]==1)
                    {
                        if(currentPlayer=='A')
                        {
                            game.p1score++;
                        
                        game.colorArray.set(pos1, 'A');
                        }
                        else
                        {
                         game.p2score++;
                        
                        game.colorArray.set(pos1,'B');
                        }
                           
                        flag=1;
                    }
                        
                }
                else if(pos1>=30)// if line belongs to last row it contributes 1 square
                {
                    if(game.board[pos1][pos2]==1 &&
                       game.board[pos1-6][pos2-6]==1 &&
                       game.board[pos1][pos1-6]==1 &&
                       game.board[pos2][pos2-6]==1)
                    {
                        if(currentPlayer=='A')
                        {
                            game.p1score++;
                        
                        game.colorArray.set(pos1-10, 'A');
                        }
                        else
                        {
                         game.p2score++;
                        
                        game.colorArray.set(pos1-10,'B');
                        }
                        flag=1;
                    }
                }
                else // if line belongs to other row it may contibute to two square
                {
                    if(game.board[pos1][pos2]==1 &&
                       game.board[pos1+6][pos2+6]==1 &&
                       game.board[pos1][pos1+6]==1 &&
                       game.board[pos2][pos2+6]==1)
                    {
                        if(currentPlayer=='A')
                            game.p1score++;
                        else
                            game.p2score++;
                        if(pos1>=6 && pos1<=10)
                        {
                            game.colorArray.set(pos1-1, currentPlayer);
                        }
                        else if(pos1>=12 && pos1<=16)
                        {
                            game.colorArray.set(pos1-2,currentPlayer);
                        }
                        else if(pos1>=18 && pos1<=22)
                        {
                            game.colorArray.set(pos1-3,currentPlayer);
                        }
                        else if(pos1>=24 && pos1<=28)
                        {
                            game.colorArray.set(pos1-4,currentPlayer);
                        }
                        flag=1;
                    }
                    if(game.board[pos1][pos2]==1 &&
                       game.board[pos1-6][pos2-6]==1 &&
                       game.board[pos1][pos1-6]==1 &&
                       game.board[pos2][pos2-6]==1)
                    {
                        if(currentPlayer=='A')
                            game.p1score++;
                        else
                            game.p2score++;
                        if(pos1>=6 && pos1<=10)
                        {
                            game.colorArray.set(pos1-6, currentPlayer);
                        }
                        else if(pos1>=12 && pos1<=16)
                        {
                            game.colorArray.set(pos1-7,currentPlayer);
                        }
                        else if(pos1>=18 && pos1<=22)
                        {
                            game.colorArray.set(pos1-8,currentPlayer);
                        }
                        else if(pos1>=24 && pos1<=28)
                        {
                            game.colorArray.set(pos1-9,currentPlayer);
                        }
                        flag=1;
                    }
                }
            }
            else    // Vertival Line Drawn
            {
                if(pos1%6==0 && pos2%6==0) // if line drawn is from first column
                {
                    if(game.board[pos1][pos2]==1 && 
                       game.board[pos1][pos1+1]==1 && 
                       game.board[pos2][pos2+1]==1 && 
                       game.board[pos1+1][pos2+1]==1)
                    {
                        if(currentPlayer=='A')
                            game.p1score++;
                        else
                            game.p2score++;
                        switch(pos1)
                        {
                            case 0:game.colorArray.set(pos1, currentPlayer);
                                break;
                            case 6:game.colorArray.set(pos1-1, currentPlayer);
                                break;
                            case 12:game.colorArray.set(pos1-2, currentPlayer);
                                break;
                            case 18:game.colorArray.set(pos1-3, currentPlayer);
                                break;
                            case 24:game.colorArray.set(pos1-4, currentPlayer);
                                break;
                        }
                        flag=1;
                    }
                }
                else if(pos1%6==5 && pos2%6==5) // if line drawn is from last column
                {
                    if(game.board[pos1][pos2]==1 && 
                       game.board[pos1][pos1-1]==1 && 
                       game.board[pos2][pos2-1]==1 && 
                       game.board[pos1-1][pos2-1]==1)
                    {
                        if(currentPlayer=='A')
                            game.p1score++;
                        else
                            game.p2score++;
                         switch(pos1)
                        {
                            case 5:game.colorArray.set(pos1-1, currentPlayer);
                                break;
                            case 11:game.colorArray.set(pos1-2, currentPlayer);
                                break;
                            case 17:game.colorArray.set(pos1-3, currentPlayer);
                                break;
                            case 23:game.colorArray.set(pos1-4, currentPlayer);
                                break;
                            case 29:game.colorArray.set(pos1-5, currentPlayer);
                                break;
                        }
                        flag=1;
                    }
                }
                else
                {
                    if(game.board[pos1][pos2]==1 && 
                       game.board[pos1][pos1+1]==1 && 
                       game.board[pos2][pos2+1]==1 && 
                       game.board[pos1+1][pos2+1]==1)
                    {
                        if(currentPlayer=='A')
                            game.p1score++;
                        else
                            game.p2score++;
                        if(pos1>=1 && pos1<=4)
                        {
                            game.colorArray.set(pos1,currentPlayer);
                        }
                        else if(pos1>=7 && pos1<=10)
                        {
                            game.colorArray.set(pos1-1,currentPlayer);
                        }
                        else if(pos1>=13 && pos1<=16)
                        {
                            game.colorArray.set(pos1-2,currentPlayer);
                        }
                        else if(pos1>=19 && pos1<=22)
                        {
                            game.colorArray.set(pos1-3,currentPlayer);
                        }
                        else if(pos1>=25 && pos1<=28)
                        {
                            game.colorArray.set(pos1-4,currentPlayer);
                        }
                        flag=1;
                    }
                     if(game.board[pos1][pos2]==1 && 
                       game.board[pos1][pos1-1]==1 && 
                       game.board[pos2][pos2-1]==1 && 
                       game.board[pos1-1][pos2-1]==1)
                    {
                        if(currentPlayer=='A')
                            game.p1score++;
                        else
                            game.p2score++;
                        if(pos1>=1 && pos1<=4)
                        {
                            game.colorArray.set(pos1-1,currentPlayer);
                        }
                        else if(pos1>=7 && pos1<=10)
                        {
                            game.colorArray.set(pos1-2,currentPlayer);
                        }
                        else if(pos1>=13 && pos1<=16)
                        {
                            game.colorArray.set(pos1-3,currentPlayer);
                        }
                        else if(pos1>=19 && pos1<=22)
                        {
                            game.colorArray.set(pos1-4,currentPlayer);
                        }
                        else if(pos1>=25 && pos1<=28)
                        {
                            game.colorArray.set(pos1-5,currentPlayer);
                        }
                        flag=1;
                    }
                }
                
            }
            if(flag==0)
            {
                changePlayer();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
    }

    
    
}
