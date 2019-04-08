package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Tile extends Label {
    private Integer value;

    public static Tile newTile(int value) {
        return new Tile(value);
    }

    private Tile(Integer value) {
        final int squareSize = 115;
        setMinSize(squareSize, squareSize);
        setMaxSize(squareSize, squareSize);
        setPrefSize(squareSize, squareSize);
        setAlignment(Pos.CENTER);

        this.value = value;
        setText(value.toString());
        getStyleClass().addAll("game-label", "game-tile-" + value);
    }
}
