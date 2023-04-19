package com.cursach.dmytropakholiuk.cells;

import com.cursach.dmytropakholiuk.Application;
import com.cursach.dmytropakholiuk.export.*;
import com.cursach.dmytropakholiuk.strategy.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


public abstract class Cell implements Exportable, StrategyManageable, Deployable, Cloneable {
    static int newx;
    static int newy;
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

    protected boolean active = false;
    public void setActive(boolean a) {
        this.active = a;
        if (this.active)
            this.r.setFill(Color.RED);
        else
            this.r.setFill(getrRColour());
    }
    public void switchActivation(){
        setActive(!this.isActive());
    }

    public boolean isActive()
    {
        return active;
    }
    protected void configureGroup()
    {
        ImageView imageView = new ImageView(getImage());
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        this.imageView = imageView;

        Circle aura = new Circle();
        aura.setRadius(40.0f);
        aura.setFill(getrRColour());
        this.r = aura;

        this.group = new Group(r, imageView, shownName);
        imageView.relocate(15, 15);
        shownName.relocate(0, 0);
        r.relocate(0, 0);

        this.group.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setActive(!active);
            }
        });
    }
    public String getPrettyString(){
        String _x = Double.toString(getX());
        String _y = Double.toString(getY());
        String _active = active ? "active" : "inactive";

        return "Cell "+this.name+", x="+_x+", y="+_y+", "+_active;
    }

    public void delete(){
        Application.strategyTimer.stop();

        Application.cellGroup.getChildren().remove(this.group);
        Application.cells.remove(this);
        this.group.setVisible(false);
        this.unbindExporter();
//        this.strategy.unbindManageable();
        this.setStrategy(null);

        Application.strategyTimer.start();

    }
    public Cell clone() throws CloneNotSupportedException{
        Cell cloned = (Cell)super.clone();
        cloned.setDefaultStrategy();

        return cloned;
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
        setX(x);
    }
    public void moveRight() {
        if (!active) return;
        int x = (int) (group.getLayoutX() + step);
        setX(x);
    }
    public void moveUp() {
        if (!active) return;
        int y = (int) (group.getLayoutY() - step);
        setY(y);
    }
    public void moveDown() {
        if (!active) return;
        int y = (int) (group.getLayoutY() + step);
        setY(y);
    }

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
    public void bindDefaultExporter(){
        this.bindExporter(Application.jsonExporter);
    }
    @JsonIgnore
    protected List<UsableStrategies> allowedStrategies;
//            = setAllowedStrategies();
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
