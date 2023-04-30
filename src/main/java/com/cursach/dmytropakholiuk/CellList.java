package com.cursach.dmytropakholiuk;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.Cell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays a list of cells that it receives. Each cell can be selected with its checkbox
 *
 * //Незначні зміни з 2 ЛР
 */
public class CellList {

    public List<Cell> cells = Application.cells;
    public List<CellBox> selectCells = new ArrayList<>();

    public CellList(){
        this(Application.cells);
    }
    public CellList(List<Cell> cellList){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Cell list");

        VBox layout = new VBox(10);

//        for (Cell cell: cellList){
//            CellBox select = new CellBox(cell.getPrettyString(), cell);
//            this.selectCells.add(select);
//            layout.getChildren().add(select);
//        }

        cellList.stream().forEach(cell -> {
            CellBox select = new CellBox(cell.getPrettyString(), cell);
            this.selectCells.add(select);
            layout.getChildren().add(select);
        });

        Label countLabel = new Label(cellList.toArray().length +" cells that match the request were found");
        countLabel.setFont(new Font(20));


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
                    Application.logger.log("User selected " + cellBox.relatedCell);
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
                        Application.logger.log("User deleted " + cellBox.relatedCell);
                        cellBox.relatedCell.delete();
                    }
                }

                window.close();
            }
        });
        extract.setOnAction(actionEvent -> {
            for (CellBox cellBox: selectCells){
                if (cellBox.isSelected()){
                    Application.logger.log("User extracted " + cellBox.relatedCell);
                    cellBox.relatedCell.quitOrgan();
                }
            }
            System.out.println(Application.anopheles.tenants);

            window.close();
        });

        layout.getChildren().addAll(
                countLabel,
                submit,
                delete,
                extract
        );
        Scene scene = new Scene(layout, 700, 600);
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


