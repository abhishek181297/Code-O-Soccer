/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dotsandboxes;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 *
 * @author abhishek
 */
public class Tile extends Label{
    private Integer value;

    Tile() {
        final int squareWidth = 100;
        final int squareHeight = 90;
        setMinSize(squareWidth, squareHeight);
        setMaxSize(squareWidth, squareHeight);
        setPrefSize(squareWidth, squareHeight);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: none;" );
        
    }
}
