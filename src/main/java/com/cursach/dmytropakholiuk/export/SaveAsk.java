package com.cursach.dmytropakholiuk.export;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.*;
import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.organs.OrganType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SaveAsk {

    public SaveAsk(){

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Save to:");

        Label labelName = new Label("There is no way to implement file choice in both\n " +
                "(virgin) Windows\n " +
                "and\n " +
                "(chad) Linux\n" +
                "so all files are saved in [saves] directory.\n" +
                "Existing files will be overwritten because kill all niggers");
        labelName.setFont(new Font(20));
        TextField textFieldName = new TextField();
        textFieldName.setFont(new Font(20));

        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean answer = true;
                String name = textFieldName.getText();
                Application.jsonExporter.save(name);
                Application.logger.log("User saved to " + name + ".json");
                window.close();
            }
        });

        submit.setPrefSize(100,50);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                labelName, textFieldName,
                submit
        );
        Scene scene = new Scene(layout, 600, 400);
        window.setScene(scene);
        window.showAndWait();


    }
}
