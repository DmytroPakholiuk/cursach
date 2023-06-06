package com.cursach.dmytropakholiuk;

import com.cursach.dmytropakholiuk.cells.Cell;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;

public class InfoPanel extends Group {
    private HashMap<Cell, Label> shoppersMap = new HashMap<>();
    private Rectangle mainArea = new Rectangle();
    private double width = 600;
    private double height = 0;
    public InfoPanel()
    {
        mainArea.setWidth(width);
        this.getChildren().add(mainArea);
        mainArea.setFill(Color.LIGHTYELLOW);
    }

    public void addCell(Cell cell)
    {
        Label cellLabel = new Label(cell.toString());
        cellLabel.setFont(new Font(10));
        shoppersMap.put(cell, cellLabel);
        this.getChildren().add(cellLabel);

        int count = this.getChildren().size() - 2;
        this.setLabelsToHeight();
        mainArea.setHeight(count * 12 + 12);
    }

    public void removeCell(Cell cell)
    {
        shoppersMap.get(cell).setText("");
        this.getChildren().remove(shoppersMap.get(cell));
        shoppersMap.remove(cell);

        int count = this.getChildren().size() - 2;
        this.setLabelsToHeight();
        mainArea.setHeight(count * 12 + 12);
    }

    public void setLabelsToHeight()
    {
        int size = shoppersMap.size();
        int i = 0;
        for(Map.Entry<Cell, Label> entry : shoppersMap.entrySet()) {
            Cell key = entry.getKey();
            Label value = entry.getValue();

            value.relocate(0, i * 12);
            i++;
            // do what you have to do here
            // In your case, another loop.
        }

    }

    public void update()
    {
        this.setOpacity(0.6);
//        this.setLayoutX(-Application.group.getLayoutX() + Application.scene.getWidth() - this.width);
//        this.setLayoutY(-Application.group.getLayoutY());

        this.setLayoutX(-Application.group.getLayoutX() + Application.scene.getWidth() - this.width);
        this.setLayoutY(-Application.group.getLayoutY());


    }

}
