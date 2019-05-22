package zeiterfassung.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import zeiterfassung.models.*;

import java.util.ArrayList;
import java.util.List;

public class TreeContextItem {

    public interface Listener {
        void onDelete(Object obj);

        void onAddArea();

        void onAddProject(Area area);

        void onAddSubProject(Project project);

        void onAddTask(SubProject subProject);
    }

    public enum Type {ROOT, AREA, PROJECT, SUBPROJECT, TASK}

    ;

    private final Node iconRoot =  new ImageView(new Image(getClass().getResourceAsStream("/zeiterfassung/icons/database-25.png")));
    private final Node iconArea =  new ImageView(new Image(getClass().getResourceAsStream("/zeiterfassung/icons/folder-25.png")));
    private final Node iconProject =  new ImageView(new Image(getClass().getResourceAsStream("/zeiterfassung/icons/clipboard-25.png")));
    private final Node iconTask =  new ImageView(new Image(getClass().getResourceAsStream("/zeiterfassung/icons/worker-with-shovel-25.png")));

    private StringProperty text = new SimpleStringProperty();
    private List<MenuItem> contextMenu = new ArrayList<>();
    private Type type;
    private Object item;
    private Listener listener;
    private Node icon;

    private MenuItem addArea() {
        MenuItem result = new MenuItem("Bereich anlegen");
        result.setOnAction(event -> listener.onAddArea());
        return result;
    }

    private MenuItem addProject() {
        MenuItem result = new MenuItem("Projekt anlegen");
        result.setOnAction(event -> listener.onAddProject((Area) item));
        return result;
    }

    private MenuItem addSubProject() {
        MenuItem result = new MenuItem("Unterprojekt anlegen");
        result.setOnAction(event -> listener.onAddSubProject((Project) item));
        return result;
    }

    private MenuItem addTask() {
        MenuItem result = new MenuItem("Aufgabe anlegen");
        result.setOnAction(event -> listener.onAddTask((SubProject) item));
        return result;
    }

    private MenuItem deleteObject(String caption) {
        MenuItem result = new MenuItem(caption + " löschen");
        result.setOnAction(event -> listener.onDelete(item));
        return result;
    }

    public TreeContextItem setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public TreeContextItem(TimeRegistrationRoot root) {
        type = Type.ROOT;
        item = root;
        text.set("Zeiterfassung.xml");
        icon = iconRoot;

        contextMenu.add(addArea());
    }

    public TreeContextItem(Area area) {
        type = Type.AREA;
        item = area;
        text.bind(area.nameProperty());
        icon = iconArea;

        contextMenu.add(addProject());
        contextMenu.add(deleteObject("Bereich"));
    }

    public TreeContextItem(Project project) {
        type = Type.PROJECT;
        item = project;
        text.bind(project.nameProperty());
        icon = iconProject;

        contextMenu.add(addTask());
        contextMenu.add(addSubProject());
        contextMenu.add(deleteObject("Projekt"));
    }

    public TreeContextItem(SubProject subProject) {
        type = Type.SUBPROJECT;
        item = subProject;
        text.bind(subProject.nameProperty());
        icon = iconProject;

        contextMenu.add(addTask());
        contextMenu.add(deleteObject("Unterprojekt"));
    }

    public TreeContextItem(Task task) {
        type = Type.TASK;
        item = task;
        text.bind(task.nameProperty());
        icon = iconTask;

        contextMenu.add(deleteObject("Aufgabe"));
    }

    public Type getType() {
        return this.type;
    }

    public Object getItem() {
        return item;
    }

    public List<MenuItem> getContextMenu() {
        return this.contextMenu;
    }

    public Node getIcon() {
        return this.icon;
    }

    public StringProperty textProperty() {
        return text;
    }

    @Override
    public String toString() {
        return text.get();
    }
}
