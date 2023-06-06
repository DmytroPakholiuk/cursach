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

public class MiniMap extends Group {
    final static private double SCALE = 0.1;
//    private Pane pane;
    private HashMap<Cell, Rectangle> shoppersMap;
    private HashMap<Organ, Rectangle> buildingsMap;
    private Rectangle mainArea;
    private Rectangle iSee;

    public MiniMap() {
//        this.pane = new Pane();
//        this.pane.setMinWidth(Application.scrollPane.getMinWidth() * MiniMap.SCALE); ;
//        this.pane.setMinHeight(Application.scrollPane.getMinHeight() * MiniMap.SCALE); ;
        shoppersMap = new HashMap<>();
        buildingsMap = new HashMap<>();


//        iSee = new Rectangle(0, 0, Application.scene.getWidth()*SCALE, Application.scene.getHeight()*SCALE);
        iSee = new Rectangle();
        iSee.setFill(Color.LIGHTGREEN);
        iSee.setOpacity(0.2);
        iSee.setStroke(Color.BLACK);
        iSee.setStrokeWidth(2);
        Label label = new Label("Map");
        label.setFont(new Font("Segoe UI Black Italic", 16));
//        label.setLayoutX(pane.getMinWidth() / 2.1);

//        mainArea = new Rectangle(0, 0, Application.scrollPane.getMaxWidth() * MiniMap.SCALE, Application.scrollPane.getMaxHeight() * MiniMap.SCALE);
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
        }
    }
}
