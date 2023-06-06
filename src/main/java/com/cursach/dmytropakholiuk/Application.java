package com.cursach.dmytropakholiuk;

import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.cells.WhiteBloodCell;
import com.cursach.dmytropakholiuk.export.JSONExporter;
import com.cursach.dmytropakholiuk.export.LoadAsk;
import com.cursach.dmytropakholiuk.export.SaveAsk;
import com.cursach.dmytropakholiuk.organs.Anopheles;
import com.cursach.dmytropakholiuk.organs.Liver;
import com.cursach.dmytropakholiuk.organs.Marrow;
import com.cursach.dmytropakholiuk.organs.Organ;
import com.cursach.dmytropakholiuk.strategy.InactiveStrategy;
import com.cursach.dmytropakholiuk.strategy.RandomStrategy;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
//import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Application extends javafx.application.Application {
    public static JSONExporter jsonExporter = JSONExporter.getInstance();
    static AnimationTimer timer ;
    static Scene scene;
    static Stage stage;
    public static ScrollPane scrollPane = new ScrollPane(new Pane());
    public static void configureScrollPane(){
        scrollPane.setMaxHeight(3000);
        scrollPane.setMaxWidth(3000);
    }
    public static MiniMap miniMap = new MiniMap();
    public static InfoPanel infoPanel = new InfoPanel();

    public static Group group = new Group();
    public static Group cellGroup = new Group();
    public static Group organGroup = new Group();
//    public static Cell[] cells = new Cell[0];
    public static List<Cell> cells = new ArrayList<>();
    public static Logger logger = Logger.getInstance();
    public static double appWidth = 1920, appHeight = 1080;

    public static Anopheles anopheles;
    public static Liver liver;
    public static Marrow marrow;
    public static Organ.NullOrgan nullOrgan = new Organ.NullOrgan();
    public static void truncateCells(){
        for (int i = cells.toArray().length - 1; i >= 0; i--){
            cells.get(i).delete();
        }
    }
    public static void refreshScreen(){
        stage.setHeight(stage.getHeight() + 0.000000001);
    }
    public static AnimationTimer strategyTimer = new AnimationTimer() {
        @Override
        public void handle(long l) {
//            for (Cell cell: cells){
            for (int i = cells.toArray().length - 1; i >= 0; i--){
                Cell cell = cells.get(i);
//                System.out.println(cell.toString()
//                        .getStrategy()
//                );
                cell.getStrategy().execute();
            }
            miniMap.updateMap();
            infoPanel.update();
        }
    };

    @Override
    public void start(Stage stage) throws IOException {

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        configureScrollPane();
        //        layout = new BorderPane();
//        layout.setCenter(scrollPane);
        Application.stage = stage;

        group.getChildren().add(organGroup);
        group.getChildren().add(cellGroup);
//        group.getChildren().add(scrollPane);
        group.getChildren().add(miniMap);
        group.getChildren().add(infoPanel);


        Application.anopheles = new Anopheles(300, 300);
        Application.liver = new Liver(0,0);
        Application.marrow = new Marrow(0,450);
        Cell example =  new WhiteBloodCell("example", false, 300, 400, 30, 7.5);
        Cell example1 =  new WhiteBloodCell("example1", false, 400, 400, 30, 7.5);

        cells.sort(Cell.coordinateComparator);
        System.out.println(cells);

        scene = new Scene(group, 1080,780);

        scene.setOnKeyPressed(new KeyPressedHandler());
        stage.setTitle("Some infected nigger");
        stage.setScene(scene);
        strategyTimer.start();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * This class handles key inputs
     * - N or INSERT - create new cell
     * - DELETE - delete selected cell
     * - ESCAPE - unselect all cells
     * - M - modify 1 selected cell
     * - K - clone 1 selected cell
     * - J - find cells
     * - L - all cells
     * - E - try to enter organs with selected cells
     * - CTRL+A - select all cells
     * - F6 - quicksave
     * - F7 - quickload
     */
    private class KeyPressedHandler implements EventHandler<KeyEvent> {
        public void handle(KeyEvent event) {

            if (event.getCode().equals(KeyCode.N) || event.getCode().equals(KeyCode.INSERT)) {
                CreationDialogue dialogue = new CreationDialogue();
            }

            if (event.getCode().equals(KeyCode.DELETE)){
                for (int i = cells.toArray().length - 1; i >= 0; i--){
                    if (cells.get(i).isActive()){
                        logger.log("USER KILLED CELL " + cells.get(i) + ". YOU BASTARD!");
                        cells.get(i).delete();
                    }
                }

            }

            if (event.getCode().equals(KeyCode.ESCAPE)) {
                for (Cell cell: cells){
                    cell.setActive(false);
                }
                logger.log("User unselected all cells");
            }
            if (event.getCode().equals(KeyCode.M)){
                int count = 0;
                Cell selected = null;
                for (Cell cell: cells){
                    if (cell.isActive()){
                        selected = cell;
                        count++;
                    }
                }
                if (count == 1){
                    logger.log("User tried to modify cell " + selected.toString() + " ...");
                    ModificationDialogue dialogue = new ModificationDialogue(selected);
                } else {

                }
            }
            if (event.getCode().equals(KeyCode.K)){
                int count = 0;
                Cell selected = null;
                for (Cell cell: cells){
                    if (cell.isActive()){
                        selected = cell;
                        count++;
                    }
                }
                if (count == 1){
//                    ModificationDialogue dialogue = new ModificationDialogue(selected, true);
                    try {
                        selected.clone();
                        logger.log("User cloned cell " + selected.toString());
                    } catch (CloneNotSupportedException e) {
                        throw new RuntimeException(e);
                    }
                } else {

                }
            }
            if (event.getCode().equals(KeyCode.L)){
                logger.log("User requested full cell list");
                CellList cellList = new CellList();
            }
            if (event.getCode().equals(KeyCode.J)){
                logger.log("User requested specific cell list");
                CellFilter filter = new CellFilter();
            }
            if (event.getCode().equals(KeyCode.E)){
                for (Cell cell: cells){
                    if (cell.isActive()){
                        cell.enterOrgan();
                    }
                }
                logger.log("User tried to enter organs with cells");
            }
            if (event.getCode().equals(KeyCode.A)){
                if (event.isControlDown()){
                    cells.forEach(Cell::switchActivation);
                }
                logger.log("User selected all cells");
            }
            if (event.getCode().equals(KeyCode.F6)){
                jsonExporter.quickSave();
                logger.log("User quicksaved");
            }
            if (event.getCode().equals(KeyCode.F7)){
                Application.strategyTimer.stop();
                jsonExporter.quickLoad();
                for (Cell cell: cells){
                    System.out.println(Application.jsonExporter.exportObjectAsString(cell));
                }

                Application.strategyTimer.start();
                logger.log("User quickloaded");
            }
            if (event.getCode().equals(KeyCode.DIGIT6)){
                new SaveAsk();
            }
            if (event.getCode().equals(KeyCode.DIGIT7)){
                new LoadAsk();
            }
            if (event.getCode().equals(KeyCode.UP)) {
                for (Cell cell : cells) {
                    if (cell.isActive()){
                        cell.moveUp();
                    }
                }
                logger.log("User moved selected cells UP");
            }
            if (event.getCode().equals(KeyCode.DOWN)) {
                for (Cell cell : cells) {
                    if (cell.isActive()){
                        cell.moveDown();
                    }
                }
                logger.log("User moved selected cells DOWN");
            }
            if (event.getCode().equals(KeyCode.LEFT)) {
                for (Cell cell : cells) {
                    if (cell.isActive()){
                        cell.moveLeft();
                    }
                }
                logger.log("User moved selected cells LEFT");

            }
            if (event.getCode().equals(KeyCode.RIGHT)) {
                for (Cell cell : cells) {
                    if (cell.isActive()){
                        cell.moveRight();
                    }
                }
                logger.log("User moved selected cells RIGHT");

            }


            if (event.getCode().equals(KeyCode.DIGIT1)){
                for (Cell cell: cells){
                    cell.setStrategy(new InactiveStrategy());
                }
                logger.log("User enabled inactive mode");
            }
            if (event.getCode().equals(KeyCode.DIGIT2)){
                for (Cell cell: cells){
                    cell.setStrategy(new RandomStrategy());
                }
                logger.log("User enabled random mode");
            }
            if (event.getCode().equals(KeyCode.DIGIT3)){
                for (Cell cell: cells){
                    cell.setDefaultStrategy();
                }
                logger.log("User enabled standard mode");
            }

            if(event.isControlDown()) {

                double xloc= Application.group.getLayoutX();
                double yloc = Application.group.getLayoutY();
                System.out.println(Application.group.getLayoutX());

                switch(event.getCode()) {
                    case UP:
                        yloc += 50;
                        break;
                    case DOWN:
                        yloc -= 50;
                        break;
                    case LEFT:
                        xloc += 50;
                        break;
                    case RIGHT:
                        xloc -= 50;
                        break;
                }

                if(xloc>0)xloc=0.0;

                if( !( -(xloc - stage.getWidth()) < Application.scrollPane.getMaxWidth() ) ){
                    xloc = (-(Application.scrollPane.getMaxWidth() - stage.getWidth()));
                }

                if(yloc>0)yloc=0.0;

                if( !( -(yloc - stage.getHeight() ) < Application.scrollPane.getMaxHeight( ) )){
                    yloc = (-(Application.scrollPane.getMaxHeight() - stage.getHeight()));
                }


                Application.group.setLayoutX(xloc);
                Application.group.setLayoutY(yloc);


            }
        }

    }
}