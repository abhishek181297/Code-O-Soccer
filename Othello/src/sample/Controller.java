package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

public class Controller implements Initializable {

    @FXML
    private TextArea textArea;
    @FXML
    private TextArea output;

    @FXML
    private ChoiceBox languageChoice1;
    @FXML
    private ChoiceBox languageChoice2;
    @FXML
    private Button btnRun;
    @FXML
    private Button btnCompile;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label score;

    @FXML
    private Button play;

    @FXML
    private Button stop;

    @FXML
    private Button reset;

    @FXML
    private TextField player1;
    @FXML
    private TextField player2;

    @FXML
    private Button swap;

    private Board game = new Board();

    private HashSet<Board.Point> blackPlaceableLocations;
    private HashSet<Board.Point> whitePlaceableLocations;

    private Board.Point move = game.new Point(0, 0);

    private int result;

    private char currentPlayer;

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

    @FXML
    private void pressCompile() {
        try {
            player1Filename = player1.getText();
            player2Filename = player2.getText();
            saveFile(player1Filename + extension1, textArea.getText().getBytes());

            if (runProcess(compile_command1, null, waitTimeout1 * 20) == 0 &&
                    runProcess(compile_command2, null, waitTimeout2 * 20) == 0) {
                printOutput("Compile Successful");
                btnRun.setDisable(false);
                play.setDisable(false);
            }

        } catch (Exception ex) {
            printOutput(ex.getMessage());
        }
    }

    @FXML
    private void pressRun() {
        try {
            if (!btnRun.isDisable()) {
                if (isMovePossible()) {
                    if (extension1.equals(".py"))
                        saveFile(player1Filename + extension1, textArea.getText().getBytes());

                    if (isCurrentPlayerMoveNotPossible())
                        changePlayer();
                    String inputData = Integer.toString(game.SIZE) + " " + Integer.toString(game.SIZE) + " " + currentPlayer + "\n";
                    for (int i = 0; i < game.SIZE; i++) {
                        for (int j = 0; j < game.SIZE; j++) {
                            inputData = inputData +
                                    game.board[j][i] + " ";
                        }
                        inputData = inputData + "\n";
                    }
                    if (currentPlayer == 'B')
                        runProcess(run_command1, inputData, waitTimeout1);
                    else
                        runProcess(run_command2, inputData, waitTimeout2);
                    if (makeMove(out)) {
                        score.setText("Player 1: " + game.BScore + " Player 2: " + game.WScore);
                        updateGrid();
                    } else {
                        stop.fire();
                        showPlayerDisqualifiedAlert("invalid output");
                    }
                } else {
                    if (stop.isDisable()) {
                        showResultAlertMessage();
                    } else
                        stop.fire();
                }
            } else {
                printOutput("First Compile the file");
            }
        } catch (InterruptedException ex) {
            printOutput("TimeLimit exceeded");
            showPlayerDisqualifiedAlert("TimeLimit Exceeded");
        } catch (IOException ex) {
            printOutput("Error while saving file");
        } catch (TimeoutException e) {
            stop.fire();
            showPlayerDisqualifiedAlert("TimeLimit Exceeded");
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageChoice1.getItems().add("c++");
        languageChoice1.getItems().add("java");

        languageChoice2.getItems().add("c++");
        languageChoice2.getItems().add("java");

        currentPlayer = 'B';

        languageChoice1.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player1Filename = player1.getText();
                player2Filename = player2.getText();
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
                        play.setDisable(true);
                        btnRun.setDisable(true);
                        waitTimeout1 = 1;
                        break;
                    case 1:
                        extension1 = ".java";
                        compile_command1 = "javac " + player1Filename + extension1;
                        run_command1 = "java " + player1Filename;
                        btnCompile.setDisable(false);
                        play.setDisable(true);
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
                        play.setDisable(true);
                        btnRun.setDisable(true);
                        break;
                    case 1:
                        extension2 = ".java";
                        compile_command2 = "javac " + player2Filename + extension2;
                        run_command2 = "java " + player2Filename;
                        waitTimeout2 = 2;
                        play.setDisable(true);
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
                    play.setDisable(true);
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
                if (!isMovePossible()) {
                    showResultAlertMessage();
                }
                stop.setDisable(true);
                play.setDisable(false);
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game = null;
                game = new Board();
                updateGrid();
                score.setText("");
                animation.stop();
                stop.setDisable(true);
                if (!btnRun.isDisable())
                    play.setDisable(false);
                currentPlayer = 'B';
            }
        });

        swap.setOnAction(new EventHandler<ActionEvent>() {
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
        currentPlayer = (currentPlayer == 'B') ? 'W' : 'B';
    }

    private boolean isMovePossible() {
        blackPlaceableLocations = game.getPlaceableLocations('B', 'W');
        whitePlaceableLocations = game.getPlaceableLocations('W', 'B');
        result = game.gameResult(whitePlaceableLocations, blackPlaceableLocations);
        return result == -2;
    }

    private boolean makeMove(String out) {
        try {
            move.x = game.coordinateX(out.charAt(0));
            move.y = Integer.parseInt(out.charAt(1) + "") - 1;
            if (currentPlayer == 'B') {
                if (!blackPlaceableLocations.contains(move))
                    return false;
                else {
                    game.placeMove(move, 'B', 'W');
                    changePlayer();
                }
            } else if (currentPlayer == 'W') {
                if (!whitePlaceableLocations.contains(move))
                    return false;
                else {
                    game.placeMove(move, 'W', 'B');
                    changePlayer();
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isCurrentPlayerMoveNotPossible() {
        if (currentPlayer == 'B')
            return blackPlaceableLocations.isEmpty();
        else
            return whitePlaceableLocations.isEmpty();
    }

    private void showResultAlertMessage() {
        String message = null;
        if (result == 0)
            message = "It is a draw.";
        else if (result == 1)
            message = "Player 2(White) wins: " + game.WScore + ":" + game.BScore;
        else if (result == -1)
            message = "Player 1(Black) wins: " + game.BScore + ":" + game.WScore;
        Alert alert = new Alert(Alert.AlertType.NONE, "Your Score: " + message, ButtonType.OK);
        alert.show();
    }

    private void showPlayerDisqualifiedAlert(String mainMessage) {
        String message;
        if (currentPlayer == 'B')
            message = "Player 1 is disqualified because of " + mainMessage +"\nPlayer 2 Wins";
        else
            message = "Player 2 is disqualified because of " + mainMessage + "\nPlayer 1 Wins";
        Alert alert = new Alert(Alert.AlertType.NONE, message, ButtonType.OK);
        alert.show();
    }

    private void updateGrid() {
        gridPane.getChildren().clear();
        for (int i = 0; i < game.SIZE; i++)
            for (int j = 0; j < game.SIZE; j++) {
                Image image;
                if (game.board[i][j] == 'B')
                    image = new Image(getClass().getResourceAsStream("/res/black.png"));
                else if (game.board[i][j] == 'W')
                    image = new Image(getClass().getResourceAsStream("/res/white.png"));
                else
                    image = new Image(getClass().getResourceAsStream("/res/empty.png"));
                gridPane.add(new ImageView(image), i, j);
            }
    }

    private void saveFile(String fileName, byte[] data) throws IOException {
        FileOutputStream out = new FileOutputStream(fileName);
        out.write(data);
        out.close();
    }

    private void printOutput(String output) {
        this.output.appendText(output + '\n');
        out = output;
    }

    @FXML
    private void clear() {
        output.setText(null);
    }
}


