package com.cursach.dmytropakholiuk;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.Cell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class CellList {

    public List<Cell> cells = Application.cells;
    public List<CellBox> selectCells = new ArrayList<>();

    public CellList(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Cell list");

        VBox layout = new VBox(10);

        for (Cell cell: cells){
            CellBox select = new CellBox(cell.getPrettyString(), cell);
            this.selectCells.add(select);
            layout.getChildren().add(select);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.fitToWidthProperty().set(true);
        scrollPane.setContent(layout);

        Button submit = new Button("Select");
        Button delete = new Button("Delete");
        Button extract = new Button("Extract");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (CellBox cellBox: selectCells){
                    cellBox.relatedCell.setActive(cellBox.isSelected());
                }

                window.close();
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (CellBox cellBox: selectCells){
                    if (cellBox.isSelected()){
                        cellBox.relatedCell.delete();
                    }
                }

                window.close();
            }
        });
        extract.setOnAction(actionEvent -> {
            for (CellBox cellBox: selectCells){
                if (cellBox.isSelected()){
                    cellBox.relatedCell.quitOrgan();
                }
            }

            window.close();
        });

        layout.getChildren().addAll(
                submit,
                delete,
                extract
        );
        Scene scene = new Scene(layout, 400, 350);
        window.setScene(scene);
        window.showAndWait();


    }
    private static class CellBox extends CheckBox{
        public Cell relatedCell;
        public CellBox(String show, Cell cell){
            super(show);
            this.relatedCell = cell;
        }
    }

}


