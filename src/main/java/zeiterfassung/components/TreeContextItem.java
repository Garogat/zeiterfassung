package zeiterfassung.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
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


    private String stringified;
    private List<MenuItem> contextMenu = new ArrayList<MenuItem>();
    private Type type;
    private Object item;
    private Listener listener;

    private MenuItem addArea(){
        MenuItem result = new MenuItem("Bereich anlegen");
        result.setOnAction(event -> listener.onAddArea());
        return result;
    }

    private MenuItem addProject(){
        MenuItem result = new MenuItem("Projekt anlegen");
        result.setOnAction(event -> listener.onAddProject((Area)item));
        return result;
    }

    private MenuItem addSubProject(){
        MenuItem result = new MenuItem("Unterprojekt anlegen");
        result.setOnAction(event -> listener.onAddSubProject((Project)item));
        return result;
    }

    private MenuItem addTask(){
        MenuItem result = new MenuItem("Aufgabe anlegen");
        result.setOnAction(event -> listener.onAddTask((SubProject) item));
        return result;
    }

    private MenuItem deleteObject(String caption){
        MenuItem result = new MenuItem(caption + "löschen");
        result.setOnAction(event -> listener.onDelete(item));
        return result;
    }

    public TreeContextItem setListener(Listener listener){
        this.listener = listener;
        return this;
    }

    public TreeContextItem(TimeRegistrationRoot root) {
        type = Type.ROOT;
        item = root;
        stringified = "";

        contextMenu.add(addArea());
    }

    public TreeContextItem(Area area) {
        type = Type.AREA;
        item = area;
        stringified = area.getName();

        contextMenu.add(addProject());
        contextMenu.add(deleteObject("Bereich"));
    }

    public TreeContextItem(Project project) {
        type = Type.PROJECT;
        item = project;
        stringified = project.getName();
        contextMenu.add(addTask());
        contextMenu.add(addSubProject());
        contextMenu.add(deleteObject("Projekt"));
    }

    public TreeContextItem(SubProject subProject) {
        type = Type.SUBPROJECT;
        item = subProject;
        stringified = subProject.getName();
        contextMenu.add(addTask());
        contextMenu.add(deleteObject("Unterprojekt"));
    }

    public TreeContextItem(Task task) {
        type = Type.TASK;
        item = task;
        stringified = task.getName();
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




    @Override
    public String toString() {
        return this.stringified;
    }
}
