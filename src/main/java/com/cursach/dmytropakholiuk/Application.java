package com.cursach.dmytropakholiuk;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;


//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.ObjectWriter;
//todo implement saving session via json export of microobjects

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application extends javafx.application.Application {
    static JSONExporter jsonExporter = JSONExporter.getInstance();
    static AnimationTimer timer ;
    static Scene scene;
    static BorderPane layout;
    public static ScrollPane scrollPane;
    public static Group group = new Group();
    public static Group cellGroup = new Group();
//    public static Cell[] cells = new Cell[0];
    public static List<Cell> cells = new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {
//        scrollPane = new ScrollPane();
//        scrollPane.setMaxWidth(1000);
//        scrollPane.setMaxHeight(1000);
//        scrollPane.setFitToHeight(true);
//        scrollPane.setFitToWidth(true);
//        layout = new BorderPane();
//        layout.setCenter(scrollPane);
        group.getChildren().add(cellGroup);
        new WhiteBloodCell();
        scene = new Scene(group, 600,700);
        scene.setOnKeyPressed(new KeyPressedHandler());
        stage.setTitle("Some infected nigger");
        stage.setScene(scene);
//        timer.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private class KeyPressedHandler implements EventHandler<KeyEvent> {
        public void handle(KeyEvent event) {

            if (event.getCode().equals(KeyCode.N)) {
                CreationDialogue dialogue = new CreationDialogue();
            }

            if (event.getCode().equals(KeyCode.DELETE)){
                for (Cell cell: cells){
                    if (cell.isActive()){
                        cell.delete();
                    }
                }
            }

            if (event.getCode().equals(KeyCode.ESCAPE)) {
                for (Cell cell: cells){
                    cell.setActive(false);
                }
            }
            if (event.getCode().equals(KeyCode.M)){
                int count = 0;
                Cell selected = null;
                for (Cell cell: cells){
                    if (cell.active){
                        selected = cell;
                        count++;
                    }
                }
                if (count == 1){
                    ModificationDialogue dialogue = new ModificationDialogue(selected);
                } else {

                }
            }
            if (event.getCode().equals(KeyCode.L)){
                CellList cellList = new CellList();
            }
            if (event.getCode().equals(KeyCode.UP)) {
                for (Cell cell : cells) {
                    if (cell.isActive()){
                        cell.moveUp();
                    }
                }
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                for (Cell cell : cells) {
                    if (cell.isActive()){
                        cell.moveDown();
                    }
                }
            }
            if (event.getCode().equals(KeyCode.LEFT)) {
                for (Cell cell : cells) {
                    if (cell.isActive()){
                        cell.moveLeft();
                    }
                }
            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                for (Cell cell : cells) {
                    if (cell.isActive()){
                        cell.moveRight();
                    }
                }
            }
        }

    }
}