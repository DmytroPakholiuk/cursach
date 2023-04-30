package com.cursach.dmytropakholiuk;

import com.cursach.dmytropakholiuk.cells.*;
import com.cursach.dmytropakholiuk.cells.Cell;
import com.cursach.dmytropakholiuk.organs.OrganType;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This class is used to display cell filter form, process request and pass it on to CellList
 */
public class CellFilter {

    public List<Cell> result = new ArrayList<>(Application.cells);

    /**
     * Applies filters to the result. Commented code simply iterates through the collection, removing elements that don't match.
     * Uncommented code does the same but via streams
     * @param request
     */
    public void filter(Request request){
//        for (int i = result.toArray().length - 1; i >= 0; i--){
//            Cell checked = result.get(i);
//
//            if (!(request.name.equals(""))){
//                if (!(request.name.equals(checked.getName()))){
//                    result.remove(i); continue;
//                }
//            }
//
//            if (!(request.x == -1)){
//                if (!(request.x == checked.getX())){
//                    result.remove(i); continue;
//                }
//            }
//
//            if (!(request.y == -1)){
//                if (!(request.y == checked.getY())){
//                    result.remove(i); continue;
//                }
//            }
//
////            try {
////                if (!(request.pstage == null)){
////                    if (!(request.pstage.equals(PStage.getPStageType(((InactivePlasmodium)checked).getStage())))){
////                        result.remove(i); continue;
////                    }
////                }
////            } catch (Exception e) {
////                result.remove(i); continue;
////            }
//            System.out.println(request.pstage);
//            if (!(request.pstage == null)){
//                if (checked instanceof InactivePlasmodium){
//                    if (!(request.pstage.equals(PStage.getPStageType(((InactivePlasmodium)checked).getStage())))){
//                        result.remove(i); continue;
//                    }
//                } else {
//                    result.remove(i); continue;
//                }
//
//            }
//
//            if (!(request.organType == null)){
//                if (!(request.organType.equals(checked.getOrganType()))){
//                    result.remove(i); continue;
//                }
//            }
//
//            if (!(request.digest == -1)){
//                if (checked instanceof WhiteBloodCell){
//                    if (!(request.digest == ((WhiteBloodCell)checked).getDigestTime())){
//                        result.remove(i); continue;
//                    }
//                } else {
//                    result.remove(i); continue;
//                }
//            }
//
//            if (!(request.cellType == null)){
//                if (!(request.cellType.equals(Cell.getType(checked)))){
//                    result.remove(i); continue;
//                }
//            }
//
//        }

        Stream<Cell> cellStream = result.stream();

        if (!(request.name.equals(""))){
                cellStream = cellStream.filter(cell -> cell.getName().equals(request.name));
            }

            if (!(request.x == -1)){
                cellStream = cellStream.filter(cell -> cell.getX() == request.x);
            }

            if (!(request.y == -1)){
                cellStream = cellStream.filter(cell -> cell.getY() == request.y);
            }

//            try {
//                if (!(request.pstage == null)){
//                    if (!(request.pstage.equals(PStage.getPStageType(((InactivePlasmodium)checked).getStage())))){
//                        result.remove(i); continue;
//                    }
//                }
//            } catch (Exception e) {
//                result.remove(i); continue;
//            }
//            System.out.println(request.pstage);
            if (!(request.pstage == null)){
                cellStream = cellStream.filter(cell -> {
                    if (!(cell instanceof InactivePlasmodium)){
                        return false;
                    }
                    return PStage.getPStageType(((InactivePlasmodium)cell).getStage()).equals(request.pstage);

                });

            }

            if (!(request.organType == null)){
                cellStream = cellStream.filter(cell -> cell.getOrganType().equals(request.organType));
            }

            if (!(request.digest == -1)){
                cellStream = cellStream.filter(cell -> {
                    if (!(cell instanceof WhiteBloodCell)){
                        return false;
                    }
                    return ((WhiteBloodCell)cell).getDigestTime() == request.digest;
                });
            }

            if (!(request.cellType == null)){
                cellStream = cellStream.filter(cell -> Cell.getType(cell).equals(request.cellType));
            }

            result = cellStream.collect(Collectors.toList());

    }

    /**
     * It simply gets the strings you can get from form and parses them into a nicer, more usable object
     */
    public static class Request{
        public int x;
        public int y;
        public String name;
        public CellType cellType = null;
        public boolean active;
        public double digest;
        public PStage.PStageType pstage;
        public OrganType organType;

        public Request(String name, String x, String y, String cellType, boolean active, String digest, String pStage, String organ){
            this.name = name;
            this.active = active;
            try {
                this.x = Integer.parseInt(x);
            } catch (Exception e){
                this.x = -1;
            }

            try {
                this.y = Integer.parseInt(y);
            } catch (Exception e){
                this.y = -1;
            }

            try {
                this.digest = Double.parseDouble(digest);
            } catch (Exception e){
                this.digest = -1;
            }

            switch (cellType){
                case "White blood cell":
                    this.cellType = CellType.WHITE_BLOOD_CELL;
                    break;
                case "Red blood cell":
                    this.cellType = CellType.RED_BLOOD_CELL;
                    break;
                case "Inactive plasmodium":
                    this.cellType = CellType.INACTIVE_PLASMODIUM;
                    break;
                case "Plasmodium vivax":
                    this.cellType = CellType.PLASMODIUM_VIVAX;
                    break;
                case "HIV-plasmodium":
                    this.cellType = CellType.HIV_PLASMODIUM;
                    break;
                default:
                    this.cellType = null;
            }

            switch (pStage){
                case "Schizont":
                    this.pstage = PStage.PStageType.SCHIZONT_PSTAGE; break;
                case "Gametocyte":
                    this.pstage = PStage.PStageType.GAMETOCYTE_PSTAGE; break;
                case "Sporozoit":
                    this.pstage = PStage.PStageType.SPOROZOIT_PSTAGE; break;
                default:
                    this.pstage = null;
            }

            switch (organ){
                case "Outside organs":
                    this.organType = OrganType.ORGANTYPE_NULLORGAN; break;
                case "Liver":
                    this.organType = OrganType.ORGANTYPE_LIVER; break;
                case "Anopheles":
                    this.organType = OrganType.ORGANTYPE_ANOPHELES; break;
                case "Marrow":
                    this.organType = OrganType.ORGANTYPE_MARROW; break;
                default:
                    this.organType = null;
            }


        }
    }
    public Request request;

    /**
     * Constructor displays form and passes the data to filter, parser and CellList
     */
    public CellFilter(){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Find cells");

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
                "[ANY]",
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
                "[ANY]",
                "Schizont",
                "Gametocyte",
                "Sporozoit"
        );
        pStage.setPromptText("Select Stage: ");

        Label labelOrgan = new Label("Organ: ");
        labelOrgan.setFont(new Font(20));
        ComboBox organ = new ComboBox<>();
        organ.setMinWidth(200);
        organ.getItems().addAll(
                "[ANY]",
                "Outside organs",
                "Liver",
                "Anopheles",
                "Marrow"
        );
        organ.setPromptText("Select Organ: ");

        Label sortLabel = new Label("Sort by: ");
        ToggleGroup sort = new ToggleGroup();
        RadioButton sortName = new RadioButton("Name");
        RadioButton sortCoordinate = new RadioButton("Coordinates");
        RadioButton sortOrgan = new RadioButton("Organ");
        sortName.setToggleGroup(sort);
        sortCoordinate.setToggleGroup(sort);
        sortOrgan.setToggleGroup(sort);


        Button submit = new Button("Submit");

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String stage;
                try {
                    stage = pStage.getValue().toString();
                } catch (Exception e){
                    stage = "";
                }
                String organ1;
                try {
                    organ1 = organ.getValue().toString();
                } catch (Exception e){
                    organ1 = "";
                }
                String celltype;
                try {
                    celltype = cellType.getValue().toString();
                } catch (Exception e){
                    celltype = "";
                }

                Request request1 = new Request(
                        textFieldName.getText(),
                        textFieldX.getText(),
                        textFieldY.getText(),
                        celltype,
                        active.isSelected(),
                        textFieldDigest.getText(),
                        stage,
                        organ1
                );
                filter(request1);

                if (sortName.isSelected()){
                    result.sort(Cell.nameComparator);
                }
                if (sortCoordinate.isSelected()){
                    result.sort(Cell.coordinateComparator);
                }
                if (sortOrgan.isSelected()){
                    result.sort(Cell.organComparator);
                }

                new CellList(result);


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
//                active,
                labelDigest, textFieldDigest,
                labelPstage, pStage,
                labelOrgan, organ,
                sortLabel, sortName, sortCoordinate, sortOrgan,
                submit
        );
        Scene scene = new Scene(layout, 400, 800);
        window.setScene(scene);
        window.showAndWait();
    }
}
