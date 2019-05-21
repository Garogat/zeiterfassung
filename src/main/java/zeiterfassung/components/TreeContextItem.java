package zeiterfassung.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.MenuItem;
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

    private StringProperty text = new SimpleStringProperty();
    private List<MenuItem> contextMenu = new ArrayList<>();
    private Type type;
    private Object item;
    private Listener listener;

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
        text.set("");

        contextMenu.add(addArea());
    }

    public TreeContextItem(Area area) {
        type = Type.AREA;
        item = area;
        text.bind(area.nameProperty());

        contextMenu.add(addProject());
        contextMenu.add(deleteObject("Bereich"));
    }

    public TreeContextItem(Project project) {
        type = Type.PROJECT;
        item = project;
        text.bind(project.nameProperty());

        contextMenu.add(addTask());
        contextMenu.add(addSubProject());
        contextMenu.add(deleteObject("Projekt"));
    }

    public TreeContextItem(SubProject subProject) {
        type = Type.SUBPROJECT;
        item = subProject;
        text.bind(subProject.nameProperty());

        contextMenu.add(addTask());
        contextMenu.add(deleteObject("Unterprojekt"));
    }

    public TreeContextItem(Task task) {
        type = Type.TASK;
        item = task;
        text.bind(task.nameProperty());

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

    public StringProperty textProperty() {
        return text;
    }

    @Override
    public String toString() {
        return text.get();
    }
}
