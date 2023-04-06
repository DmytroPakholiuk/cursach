package com.cursach.dmytropakholiuk;
import com.cursach.dmytropakholiuk.cells.CellFactory;
import com.cursach.dmytropakholiuk.cells.RedBloodCell;
import com.cursach.dmytropakholiuk.cells.WhiteBloodCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean answer = true;
                String name = textFieldName.getText();
                int x = 0;
                try {
                    x = Integer.parseInt(textFieldX.getText());
                } catch (Exception e){
                }

                int y = 0;
                try {
                    y = Integer.parseInt(textFieldY.getText());
                }catch (Exception e){
                }

                boolean _active = active.isSelected();
                try {
                    switch (cellType.getValue().toString()){
                        case "White blood cell":

                            WhiteBloodCell whiteBloodCell = new WhiteBloodCell(name, _active, x, y, 30, 7.5);
                            break;
                        case "Red blood cell":
                            RedBloodCell redBloodCell = new RedBloodCell(name, _active, x, y, 30);
                            break;
                        case "Inactive plasmodium":
                            CellFactory.createCell(CellFactory.CELLTYPE_INACTIVEPLAS);break;
                        case "Plasmodium vivax":
                            CellFactory.createCell(CellFactory.CELLTYPE_PLASVIVAX);break;
                        case "HIV-plasmodium":
                            CellFactory.createCell(CellFactory.CELLTYPE_HIVPLAS);break;
                    }
                } catch (Exception e){
                    System.out.println("could not create a cell");
                }
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
                submit
        );
        Scene scene = new Scene(layout, 400, 350);
        window.setScene(scene);
        window.showAndWait();

    }
}
