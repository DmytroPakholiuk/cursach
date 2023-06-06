package com.cursach.dmytropakholiuk;
import com.cursach.dmytropakholiuk.cells.*;
import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.organs.OrganType;
import com.cursach.dmytropakholiuk.strategy.Strategy;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Renders a form of cell params and creates a cell with those params
 *
 * // Реалізовано в 2ЛР
 */
public class CreationDialogue {
    public CreationDialogue(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a cell");

        Label labelName = new Label("Name:");
        labelName.setFont(new Font(20));
        TextField textFieldName = new TextField();
        textFieldName.setFont(new Font(20));


        Label labelX = new Label("X:");
        labelX.setFont(new Font(20));
        TextField textFieldX = new TextField();
        textFieldX.setFont(new Font(20));

        Label labelY = new Label("Y:");
        labelY.setFont(new Font(20));
        TextField textFieldY = new TextField();
        textFieldY.setFont(new Font(20));

        CheckBox active = new CheckBox("Active");

        ComboBox cellType = new ComboBox<>();
        cellType.setMinWidth(200);
        cellType.getItems().addAll(
                "White blood cell",
                "Red blood cell",
                "Inactive plasmodium",
                "Plasmodium vivax",
                "HIV-plasmodium"
        );
        cellType.setPromptText("Cell type: ");

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
                int x = 50;
                try {
                    x = Integer.parseInt(textFieldX.getText());
                } catch (Exception e){
                }

                int y = 50;
                try {
                    y = Integer.parseInt(textFieldY.getText());
                }catch (Exception e){
                }

                Cell cell = null;

                boolean _active = active.isSelected();
                try {
                    switch (cellType.getValue().toString()){
                        case "White blood cell":
                            double digestTime = 7.5;
                            try {
                                digestTime = Double.parseDouble(textFieldDigest.getText());
                            }catch (Exception e){
                            }

                            cell = new WhiteBloodCell(name, _active, x, y, 30, digestTime);
                            break;
                        case "Red blood cell":
                            cell = new RedBloodCell(name, _active, x, y, 30);
                            break;
                        case "Inactive plasmodium":
                            PStage stage = null;
                            switch (pStage.getValue().toString()){
                                case "Schizont":
                                    stage = new SchizontPStage(); break;
                                case "Gametocyte":
                                    stage = new GametocytePStage(); break;
                                case "Sporozoit":
                                    stage = new SporozoitPStage(); break;
                            }

                            cell = new InactivePlasmodium(name, _active, x, y, 30, OrganType.ORGANTYPE_NULLORGAN);
                            ((InactivePlasmodium)cell).bindStage(stage);
                            break;
                        case "Plasmodium vivax":
                            PStage stage1 = null;
                            switch (pStage.getValue().toString()){
                                case "Schizont":
                                    stage1 = new SchizontPStage(); break;
                                case "Gametocyte":
                                    stage1 = new GametocytePStage(); break;
                                case "Sporozoit":
                                    stage1 = new SporozoitPStage(); break;
                            }

                            cell = new PlasmodiumVivax(name, _active, x, y, 30, OrganType.ORGANTYPE_NULLORGAN);
                            ((InactivePlasmodium)cell).bindStage(stage1);
                            break;
                        case "HIV-plasmodium":
                            PStage stage2 = null;
                            switch (pStage.getValue().toString()){
                                case "Schizont":
                                    stage2 = new SchizontPStage(); break;
                                case "Gametocyte":
                                    stage2 = new GametocytePStage(); break;
                                case "Sporozoit":
                                    stage2 = new SporozoitPStage(); break;
                            }

                            cell = new HIVPlasmodium(name, _active, x, y, 30, OrganType.ORGANTYPE_NULLORGAN);
                            ((InactivePlasmodium)cell).bindStage(stage2);
                            break;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("could not create a cell");
                }
                Application.logger.log("User created a cell " + cell.toString());
                window.close();
            }
        });

        cellType.setPrefSize(100,50);
        submit.setPrefSize(100,50);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                labelName, textFieldName,
                labelX, textFieldX,
                labelY, textFieldY,
                cellType,
                active,
                labelDigest, textFieldDigest,
                labelPstage, pStage,
                submit
        );
        Scene scene = new Scene(layout, 400, 600);
        window.setScene(scene);
        window.showAndWait();

    }
}
