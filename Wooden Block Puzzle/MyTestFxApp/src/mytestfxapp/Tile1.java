/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytestfxapp;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 *
 * @author abhishek
 */
public class Tile1 extends Label{
    Tile1() {
        final int squareWidth = 45;
        final int squareHeight = 40;
        setMinSize(squareWidth, squareHeight);
        setMaxSize(squareWidth, squareHeight);
        setPrefSize(squareWidth, squareHeight);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color:brown;-fx-border-width: 1px;-fx-border-color: black;");
        //this.getParent().toFront();
        
        
    }
}
