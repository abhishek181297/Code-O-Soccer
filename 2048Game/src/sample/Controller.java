package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.URL;
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
    private ChoiceBox languageChoice;
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

    private Game game = new Game();

    private String compile_command;
    private String run_command;
    private String extension;

    private final String FILENAME = "Player";

    private long waitTimeout;

    private String out;

    int retVal = -1;

    @FXML
    private void pressCompile() {
        try {
            saveFile(FILENAME + extension, textArea.getText().getBytes());

            if (btnRun.isDisable() && runProcess(compile_command, null, waitTimeout * 20) == 0) {
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
                if (game.isMovePossible()) {
                    if (extension.equals(".py"))
                        saveFile(FILENAME + extension, textArea.getText().getBytes());
                    //String inputData = input.getText();
                    String inputData = Integer.toString(game.SIZE) + " " + Integer.toString(game.SIZE) + "\n";
                    for (int i = 0; i < game.SIZE; i++) {
                        for (int j = 0; j < game.SIZE; j++) {
                            inputData = inputData +
                                    Integer.toString(game.board[j][i]) + " ";
                        }
                        inputData = inputData + "\n";
                    }

                    runProcess(run_command, inputData, waitTimeout);
                    game.makeMove(out);
                    updateGrid();
                    score.setText(Long.toString(game.score));
                } else {
                    if (stop.isDisable()) {
                        Alert alert = new Alert(Alert.AlertType.NONE, "Your Score: " + Long.toString(game.score), ButtonType.OK);
                        alert.show();
                    } else
                        stop.fire();
                }
            } else {
                printOutput("First Compile the file");
            }
        } catch (InterruptedException ex) {
            printOutput("TimeLimit exceeded");
        } catch (IOException ex) {
            printOutput("Error while saving file");
        } catch (IllegalArgumentException e) {
            stop.fire();
            Alert alert = new Alert(Alert.AlertType.NONE, e.getMessage() + " You are disqualified", ButtonType.OK);
            alert.show();
        } catch (TimeoutException e) {
            stop.fire();
            Alert alert = new Alert(Alert.AlertType.NONE, "TimeLimit exceeded\nYou are disqualified", ButtonType.OK);
            alert.show();
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
        languageChoice.getItems().add("c++");
        languageChoice.getItems().add("java");

        languageChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                switch (newValue.intValue()) {
                    case 0:
                        extension = ".cpp";
                        compile_command = "g++ " + FILENAME + extension + " -o " + FILENAME + ".exe";
                        run_command = FILENAME + ".exe";
                        btnCompile.setDisable(false);
                        btnRun.setDisable(true);
                        play.setDisable(true);
                        waitTimeout = 1;
                        break;
                    case 1:
                        extension = ".java";
                        compile_command = "javac " + FILENAME + extension;
                        run_command = "java " + FILENAME;
                        btnCompile.setDisable(false);
                        btnRun.setDisable(true);
                        play.setDisable(true);
                        waitTimeout = 2;
                        break;
                    case 2:
                        extension = ".py";
                        run_command = "python " + FILENAME + extension;
                        btnCompile.setDisable(true);
                        btnRun.setDisable(false);
                        waitTimeout = 2;
                        break;
                    default:
                        printOutput("Unknown");
                        break;
                }
                try {
                    File file = new File(FILENAME + extension);
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

        languageChoice.getSelectionModel().selectFirst();

        textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!extension.equals(".py")) {
                    btnRun.setDisable(true);
                    play.setDisable(true);
                }
                if (event.getCode() == KeyCode.S && event.isControlDown()) {
                    try {
                        saveFile(FILENAME + extension, textArea.getText().getBytes());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        gridPane.getStyleClass().add("game-grid");

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
                if (!game.isMovePossible()) {
                    Alert alert = new Alert(Alert.AlertType.NONE, "Your Score: " + Long.toString(game.score), ButtonType.OK);
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
                updateGrid();
                animation.stop();
                stop.setDisable(true);
                if (!btnRun.isDisable())
                    play.setDisable(false);
                score.setText("");
            }
        });

    }

    private void updateGrid() {
        gridPane.getChildren().clear();
        for (int i = 0; i < game.SIZE; i++)
            for (int j = 0; j < game.SIZE; j++) {
                if (game.board[i][j] != 0)
                    gridPane.add(Tile.newTile(game.board[i][j]), i, j);
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


