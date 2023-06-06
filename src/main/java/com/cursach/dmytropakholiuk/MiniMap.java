package com.cursach.dmytropakholiuk;

import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.organs.Organ;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.HashMap;

/**
 * Minimap for the app. Extends Group, so we can manipulate it as if it were an actual Group
 */
public class MiniMap extends Group {
    final static private double SCALE = 0.1;
//    private Pane pane;
    /**
     * A hashMap of cell-rectangle
     */
    private HashMap<Cell, Rectangle> shoppersMap;
//    private Pane pane;
    /**
     * A hashMap of organ-rectangle
     */
    private HashMap<Organ, Rectangle> buildingsMap;
    private Rectangle mainArea;
    private Rectangle iSee;

    public MiniMap() {
        shoppersMap = new HashMap<>();
        buildingsMap = new HashMap<>();

        iSee = new Rectangle();
        iSee.setFill(Color.LIGHTGREEN);
        iSee.setOpacity(0.2);
        iSee.setStroke(Color.BLACK);
        iSee.setStrokeWidth(2);
        Label label = new Label("Map");
        label.setFont(new Font("Segoe UI Black Italic", 16));

        mainArea = new Rectangle(0, 0, 300, 300);
        mainArea.setFill(Color.LIGHTPINK);
        mainArea.setOpacity(0.3);
        mainArea.setStrokeWidth(2);
        mainArea.setStroke(Color.YELLOW);
//        this.getChildren().addAll(rectangle, label, mainArea);
        this.getChildren().addAll(label, mainArea, iSee);
        label.relocate(0, 310);

        this.setOnMousePressed(event -> {
            this.moveTo(event.getX(), event.getY());
        });
    }

    /**
     * Moves area of vision to map coordinates. "movement" is implemented by moving around the superglobal app group
     * @param x
     * @param y
     */
    public void moveTo(double x, double y) {

        double width = Application.scrollPane.getMaxWidth();
        double height = Application.scrollPane.getMaxHeight();
        double scrWidth = Application.scene.getWidth();
        double scrHeight = Application.scene.getHeight();

        Application.group.setLayoutY(-(y/SCALE - scrHeight/2));
        Application.group.setLayoutX(-(x/SCALE - scrWidth/2));

        if (x/SCALE < scrWidth/2){
            Application.group.setLayoutX(0);
        }
        if (y/SCALE < scrHeight/2){
            Application.group.setLayoutY(0);
        }
        if (x/SCALE > width - scrWidth/2){
            Application.group.setLayoutX(-(width - scrWidth));
        }
        if (y/SCALE > height - scrHeight/2){
            Application.group.setLayoutY(-(height - scrHeight));
        }

    }

//    public Pane getPane() {
//        return pane;
//    }

    public Rectangle getMainArea() {
        return mainArea;
    }

    public static double getSCALE() {
        return SCALE;
    }

    /**
     * Basically adds a rectangle that represents this cell
     * @param cell
     */
    public void addCell(Cell cell) {
        Rectangle rec = new Rectangle(80 * MiniMap.SCALE, 80 * MiniMap.SCALE);
        rec.setLayoutX(cell.getX() * MiniMap.SCALE);
        rec.setLayoutY(cell.getY() * MiniMap.SCALE);
        rec.setFill(cell.getrRColour());
        shoppersMap.put(cell, rec);
        this.getChildren().add(rec);
    }

    public void deleteCell(Cell shopper) {
        shoppersMap.get(shopper).setVisible(false);
        this.getChildren().remove(shoppersMap.get(shopper));
        shoppersMap.remove(shopper);
    }

    public void addOrgan(Organ organ) {
        Rectangle rec = new Rectangle(250 * MiniMap.SCALE, 250 * MiniMap.SCALE);
        rec.setLayoutX(organ.getX() * MiniMap.SCALE);
        rec.setLayoutY(organ.getY() * MiniMap.SCALE);
        rec.setFill(organ.getrRColour());

        buildingsMap.put(organ, rec);
        this.getChildren().add(rec);
    }

    public void deleteOrgan(Organ building) {
        buildingsMap.get(building).setVisible(false);
        this.getChildren().remove(buildingsMap.get(building));
        buildingsMap.remove(building);
    }

    /**
     * Moves the map in the global group and moves map elements inside map
     */
    public void updateMap() {
        this.setOpacity(0.7);
//        mainArea.setWidth(Application.scene.getWidth()* MiniMap.SCALE);
//        mainArea.setHeight(Application.scene.getHeight()*MiniMap.SCALE);
        this.setLayoutX(-Application.group.getLayoutX());
        this.setLayoutY(-Application.group.getLayoutY());

        iSee.setWidth(Application.scene.getWidth()*SCALE);
        iSee.setHeight(Application.scene.getHeight()*SCALE);
        iSee.relocate(-Application.group.getLayoutX()*SCALE, -Application.group.getLayoutY()*SCALE);

        for (Cell cell : Application.cells) {
            Rectangle rec = shoppersMap.get(cell);
            rec.relocate(cell.getX() * MiniMap.SCALE, cell.getY() * MiniMap.SCALE);
            rec.setVisible(cell.isVisible());
        }
    }
}
