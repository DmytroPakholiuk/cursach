package com.cursach.dmytropakholiuk.export;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.cells.*;
import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.organs.OrganType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LoadAsk {

    public LoadAsk(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Save to:");

        Label labelName = new Label("There is no way to implement file choice in both\n " +
                "(virgin) Windows\n " +
                "and\n " +
                "(chad) Linux\n" +
                "so all files are saved in [saves] directory.\n" +
                "Here they are: " );
        labelName.setFont(new Font(20));
//        TextField textFieldName = new TextField();
//        textFieldName.setFont(new Font(20));

        List<File> fileList = Stream.of(new File("saves").listFiles())
                .filter(file -> !file.isDirectory())
                .collect(Collectors.toList());

        ToggleGroup selectedFile = new ToggleGroup();
        Group buttonGroup = new Group();

        int i = 0;
        for (File file: fileList){
            RadioButton button = new RadioButton(file.getName());
            button.setToggleGroup(selectedFile);
            buttonGroup.getChildren().add(button);
            button.relocate(0,i * 20);
            i++;
        }

        Button submit = new Button("Submit");
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                boolean answer = true;
                String name = ((RadioButton)selectedFile.getSelectedToggle()).getText();
                System.out.println(name);
                Application.jsonExporter.load(name);
                Application.logger.log("User loaded from " + name + ".json");
                window.close();
            }
        });

        submit.setPrefSize(100,50);
        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                labelName, buttonGroup,
                submit
        );
        Scene scene = new Scene(layout, 600, 400);
        window.setScene(scene);
        window.showAndWait();


    }
}