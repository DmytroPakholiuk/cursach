package com.cursach.dmytropakholiuk;

import com.cursach.dmytropakholiuk.cells.*;
import com.cursach.dmytropakholiuk.cells.Cell;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

import java.nio.file.Watchable;

public class ModificationDialogue {

    public com.cursach.dmytropakholiuk.cells.Cell modifiedCell;
    public ModificationDialogue(Cell cell){
        this.modifiedCell = cell;

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Modify a cell");

        Label labelName = new Label("Name:");
        labelName.setFont(new Font(20));
        TextField textFieldName = new TextField();
        textFieldName.setFont(new Font(20));
        textFieldName.setText(cell.getName());


        Label labelX = new Label("X:");
        labelX.setFont(new Font(20));
        TextField textFieldX = new TextField();
        textFieldX.setFont(new Font(20));
        textFieldX.setText(Double.toString(cell.getX()));

        Label labelY = new Label("Y:");
        labelY.setFont(new Font(20));
        TextField textFieldY = new TextField();
        textFieldY.setFont(new Font(20));
        textFieldY.setText(Double.toString(cell.getY()));


        CheckBox active = new CheckBox("Active");

        Label labelDigest = new Label("[WBC only] Digest time: ");
        labelDigest.setFont(new Font(20));
        TextField textFieldDigest = new TextField();
        textFieldDigest.setFont(new Font(20));

        Label labelPstage = new Label("[Plasmodium only] Plasmodium stage: ");
        labelDigest.setFont(new Font(20));
        ComboBox pStage = new ComboBox<>();
        pStage.setMinWidth(200);
        pStage.getItems().addAll(
                "Schizont",
                "Gametocyte",
                "Sporozoit"
        );
        pStage.setPromptText("Select Stage: ");


        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                boolean answer = true;
                String name = textFieldName.getText();
                double x = modifiedCell.getX();
                try {
                    x = Integer.parseInt(textFieldX.getText());
                } catch (Exception e){

                }
                double y = modifiedCell.getY();
                try {
                    y = Double.parseDouble(textFieldY.getText());
                } catch (Exception e){

                }
                boolean _active = active.isSelected();

                modifiedCell.setName(name);
                modifiedCell.setActive(_active);
                modifiedCell.setX(x);
                modifiedCell.setY(y);

                if (cell instanceof WhiteBloodCell){
                    double digestTime = ((WhiteBloodCell)modifiedCell).getDigestTime();
                    try {
                        digestTime = Double.parseDouble(textFieldDigest.getText());
                    } catch (Exception e){

                    }
                    ((WhiteBloodCell) modifiedCell).setDigestTime(digestTime);
                }
                if (cell instanceof InactivePlasmodium){
                    PStage stage = ((InactivePlasmodium)cell).getStage();
                    switch (pStage.getValue().toString()){
                        case "Schizont":
                            stage = new SchizontPStage(); break;
                        case "Gametocyte":
                            stage = new GametocytePStage(); break;
                        case "Sporozoit":
                            stage = new SporozoitPStage(); break;
                    }

                    ((InactivePlasmodium)cell).bindStage(stage);
                }

                window.close();
            }
        });

        submit.setPrefSize(100,50);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                labelName, textFieldName,
                labelX, textFieldX,
                labelY, textFieldY,
                active
        );
        if (cell instanceof WhiteBloodCell){
            layout.getChildren().addAll(labelDigest, textFieldDigest);
        }
        if (cell instanceof InactivePlasmodium){
            layout.getChildren().addAll(labelPstage, pStage);
        }
        layout.getChildren().add(submit);

        Scene scene = new Scene(layout, 400, 600);
        window.setScene(scene);
        window.showAndWait();


    }
}
