package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.export.*;
import com.cursach.dmytropakholiuk.organs.Organ;
import com.cursach.dmytropakholiuk.organs.OrganType;
import com.cursach.dmytropakholiuk.strategy.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public abstract class Cell implements Exportable, StrategyManageable, Deployable, Cloneable, Comparable<Cell> {
    static int newx;
    static int newy;
    protected static int cellQnt = 0;
    public static CellType getType(Deployable cell){

        if (cell instanceof RedBloodCell){
            return CellType.RED_BLOOD_CELL;
        }
        if (cell instanceof WhiteBloodCell){
            return CellType.WHITE_BLOOD_CELL;
        }
        if (cell instanceof HIVPlasmodium){
            return CellType.HIV_PLASMODIUM;
        }
        if (cell instanceof PlasmodiumVivax){
            return CellType.PLASMODIUM_VIVAX;
        }
        if (cell instanceof InactivePlasmodium){
            return CellType.INACTIVE_PLASMODIUM;
        }
        return null;
    }
    protected double x, y;
    public double getX(){return x;}
    public void setX(double _x)
    {
        this.x = _x;
        this.group.setLayoutX(_x);
    }
    public double getY(){return y;}
    public void setY(double _y)
    {
        this.y = _y;
        this.group.setLayoutY(_y);
    }

    /**
     * a unique id to affect the hashcode of the object
     */
    protected int id;

    public int getId() {
        return id;
    }
    public void setId(int id){
        if (cellQnt < id){
            cellQnt = id;
            this.id = id;
        }
    }

    protected double speed = 1;

    public double getSpeed() {
        return speed;
    }
    public void setSpeed(double _speed){
        this.speed = _speed;
    }

    protected int step = 30;

    public double getStep() {
        return step;
    }
    public void setStep(int _step){
        this.step = _step;
    }
    @JsonIgnore

    

    protected Circle r;
    @JsonIgnore
    protected Color rColour;
    @JsonIgnore
    public Color getrRColour(){
        return rColour;
    }
    @JsonIgnore
    protected Image image;
    @JsonIgnore
    public Image getImage(){
        return image;
    }
    @JsonIgnore
    protected ImageView imageView;
    @JsonIgnore
    protected Text shownName = new Text();
    protected String name = new String();
    public void setName(String _name) {
        if (_name.equals("")){
            this.setName(Integer.toString(hashCode())); return;
        }
        this.name = (_name);
        this.shownName.setText(_name);
    }
    public String getName(){
        return name;
    }
    @JsonIgnore
    protected Group group;
    @JsonIgnore
    public Group getGroup() {
        return group;
    }

    protected boolean active = false;
    public void setActive(boolean a) {
        if (!this.isVisible()){
            return;
        }
        this.active = a;
        if (this.active){
            this.r.setFill(Color.RED);
            Application.infoPanel.addCell(this);
        } else {
            this.r.setFill(getrRColour());
            try {
                Application.infoPanel.removeCell(this);
            } catch (Exception e) {

            }
        }
    }
    public void switchActivation(){
        setActive(!this.isActive());
    }

    public boolean isActive()
    {
        return active;
    }

    protected boolean visible = true;
    public boolean isVisible(){
        return visible;
    }
    public void setVisible(boolean visible){
        this.visible = visible;
        this.group.setVisible(visible);
    }

    /**
     * Organ it is inside right now
     */
    @JsonIgnore
    public Organ organ = Application.nullOrgan;
    /**
     * Checking organType is easier than checking type manually. Also helps to export/import objects correctly
     */
    protected OrganType organType = OrganType.ORGANTYPE_NULLORGAN;
    public void setOrganType(OrganType organType){
        this.organType = organType;
        this.enterOrgan(Organ.getOrganByType(organType));
    }
    public OrganType getOrganType() {
        return organType;
    }

    public void enterOrgan(){
        Organ organ1 = Application.nullOrgan;
        if (this.group.getBoundsInParent().intersects(Application.anopheles.getGroup().getBoundsInParent())){
            organ1 = Application.anopheles;
        }
        if (this.group.getBoundsInParent().intersects(Application.liver.getGroup().getBoundsInParent())){
            organ1 = Application.liver;
        }
        if (this.group.getBoundsInParent().intersects(Application.marrow.getGroup().getBoundsInParent())){
            organ1 = Application.marrow;
        }
//        System.out.println(this.toString() + " ENTERED ORGAN " + organ1.toString());
        organ1.acceptCell(this);
        Application.refreshScreen();
        this.organ = organ1;
        this.organType = Organ.getOrganType(this.organ);
    }
    public void enterOrgan(Organ organ1) {
        organ1.acceptCell(this);
        Application.refreshScreen();
        this.organ = organ1;
        this.organType = Organ.getOrganType(this.organ);
    }
    public void quitOrgan(){

        if (!(this.organ instanceof Organ.NullOrgan)){
            this.setVisible(true);
            organ.moveOutside(this);
            Application.refreshScreen();
            this.organ = Application.nullOrgan;
            this.organType = Organ.getOrganType(this.organ);
        }

    }

    /**
     * Checks if this cell is inside an organ
     * @param organ1
     * @return
     */
    public boolean inOrgan(Organ organ1){
        return organ1.equals(this.organ);
    }

    /**
     * Basic group configuration for graphics
     */
    protected void configureGroup()
    {
        try{
            ImageView imageView = new ImageView(getImage());
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            imageView.setPreserveRatio(false);
            this.imageView = imageView;

            Circle aura = new Circle();
            aura.setRadius(40.0f);
            aura.setFill(getrRColour());
            this.r = aura;

            this.group = new Group(r, imageView, shownName);
            imageView.relocate(15, 15);
            shownName.relocate(0, 0);
            r.relocate(0, 0);

            group.toFront();
//        group.setManaged(false);

            this.group.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    setActive(!active);
                    Application.logger.log("User selected cell in X = " + event.getX() + " Y = " + event.getY());
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * returns a "presentable" string for this object
     * @return
     */
    public String getPrettyString(){
        String _x = Double.toString(getX());
        String _y = Double.toString(getY());
        String _active = active ? "active" : "inactive";

        return "Cell "+this.name+", INSIDE "+this.organType+", x="+_x+", y="+_y+", "+_active;
    }

    /**
     * removes all possible links to this object and makes its group invisible
     */
    public void delete(){
        Application.strategyTimer.stop();

        this.quitOrgan();

        System.out.println(this.group.getChildren());

        for (Node node: this.group.getChildren()){
            node.setVisible(false);
        }
        this.imageView.setVisible(false);
        this.r.setVisible(false);
        this.shownName.setVisible(false);

        Application.cellGroup.getChildren().remove(this.group);
        Application.cells.remove(this);
//        this.group.setVisible(false);

        this.unbindExporter();
//        this.strategy.unbindManageable();
        this.setStrategy(null);
        Application.miniMap.deleteCell(this);
        Application.refreshScreen();

        Application.strategyTimer.start();

    }
    public Cell clone() throws CloneNotSupportedException{
        Cell cloned = (Cell)super.clone();
        this.setId(cellQnt + 1);
        cloned.setDefaultStrategy();

        return cloned;
    }

    public static Comparator<Cell> coordinateComparator
            = new Comparator<Cell>() {
        @Override
        public int compare(Cell c1, Cell c2) {
            if (c1.getX() + c1.getY() < c2.getY() + c2.getX()) return -1;
            if (c1.getX() + c1.getY() > c2.getY() + c2.getX()) return 1;
            return 0;
        }
    };
    public static Comparator<Cell> nameComparator
            = new Comparator<Cell>() {
        @Override
        public int compare(Cell e1, Cell e2) {
            return e1.name.compareTo(e2.name);
        }
    };
    public static Comparator<Cell> organComparator
            = new Comparator<Cell>() {
        @Override
        public int compare(Cell e1, Cell e2) {
            return e1.organType.compareTo(e2.organType);
        }
    };

    public int compareTo(Cell cell){
        return this.name.compareTo(cell.name);
    }


    protected void configureClone(Cell cloned){
        cloned.shownName = new Text(cloned.name);

        cloned.configureGroup();
        Application.cells.add(cloned);
        Application.cellGroup.getChildren().add(cloned.group);

        cloned.setX(cloned.getX() - 100);
        cloned.setY(cloned.getY() - 100);

        cloned.bindDefaultExporter();
        cloned.allowedStrategies = setAllowedStrategies();
        cloned.setDefaultStrategy();

        cloned.bindDefaultExporter();
        cloned.exporter.bindExportable(cloned);
    }

    public void moveLeft() {
        if (!active) return;
        int x = (int) (group.getLayoutX() - step);
        if (x < 0){
            x = 0;
        }
        System.out.println(Application.scrollPane.getMaxHeight());
        if (x > Application.scrollPane.getMaxHeight() - 80){
            x = (int) Application.scrollPane.getMaxHeight() - 80;
        }
        setX(x);
    }
    public void moveRight() {
        if (!active) return;
        int x = (int) (group.getLayoutX() + step);
        if (x < 0){
            x = 0;
        }
        if (x > Application.scrollPane.getMaxHeight() - 80){
            x = (int) Application.scrollPane.getMaxHeight() - 80;
        }
        setX(x);
    }
    public void moveUp() {
        if (!active) return;
        int y = (int) (group.getLayoutY() - step);
        if (y < 0){
            y = 0;
        }
        if (y > Application.scrollPane.getMaxHeight() - 80){
            y = (int) Application.scrollPane.getMaxHeight() - 80;
        }
//        System.out.println("up");
        setY(y);
    }
    public void moveDown() {
        if (!active) return;
        int y = (int) (group.getLayoutY() + step);
        if (y < 0){
            y = 0;
        }
        if (y > Application.scrollPane.getMaxHeight() - 80){
            y = (int) Application.scrollPane.getMaxHeight() - 80;
        }
        setY(y);
    }

    /**
     * Usually JSONExporter, but you can change that
     */
    @JsonIgnore
    public Exporter exporter;
    public void bindExporter(Exporter _exporter){
        if (_exporter != null){
            if (_exporter.equals(this.exporter)) {
                return;
            }
        }
        this.exporter = _exporter;
        _exporter.bindExportable(this);
    };
    public void unbindExporter(){
        if (exporter != null) {
            exporter.unbindExportable(this);
        }
        this.exporter = null;

    };

    /**
     * Change this method if you wish to bind a different exporter as default
     */
    public void bindDefaultExporter(){
        this.bindExporter(Application.jsonExporter);
    }
    @JsonIgnore
    protected List<UsableStrategies> allowedStrategies;
//            = setAllowedStrategies();

    /**
     * a method that sets which strategies are allowed for the object. Has to be overwritten in subclasses
     * @return
     */
    protected List<UsableStrategies> setAllowedStrategies(){
        List<UsableStrategies> strategies = new ArrayList<>();
        strategies.add(UsableStrategies.INACTIVE);
        strategies.add(UsableStrategies.RANDOM);

        return strategies;
    }

    @JsonIgnore
    public List<UsableStrategies> getAllowedStrategies(){
        return this.allowedStrategies;
    }
    @JsonIgnore
    protected Strategy strategy;
    public void setStrategy(Strategy strategy){
        if (this.getAllowedStrategies().contains(Strategy.getType(strategy)) || strategy == null){
            this.strategy = strategy;
            if (strategy != null){
                strategy.bindManageable(this);
            }
            return;
        }
        System.out.println("could not assign strategy "+ strategy.toString() +" to "+this.toString()+": strategy type not allowed");
    }
    @JsonIgnore
    public Strategy getStrategy(){
        return strategy;
    }

    public void setDefaultStrategy(){
        this.setStrategy(new InactiveStrategy());
    }
}
