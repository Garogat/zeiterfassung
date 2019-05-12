package zeiterfassung.components;

import javafx.scene.control.MenuItem;
import zeiterfassung.models.*;

import java.util.ArrayList;
import java.util.List;

public class TreeContextItem {

    public enum Type {ROOT, AREA, PROJECT, SUBPROJECT, TASK}

    ;

    private String stringified;
    private List<MenuItem> contextMenu = new ArrayList<MenuItem>();
    private Type type;
    private Object item;

    public TreeContextItem(TimeRegistrationRoot root) {
        type = Type.ROOT;
        item = root;
        stringified = "";
        contextMenu.add(new MenuItem("Bereich anlegen"));
    }

    public TreeContextItem(Area area) {
        type = Type.AREA;
        item = area;
        stringified = area.getName();
        contextMenu.add(new MenuItem("Projekt anlegen"));
        contextMenu.add(new MenuItem("Bereich löschen"));
    }

    public TreeContextItem(Project project) {
        type = Type.PROJECT;
        item = project;
        stringified = project.getName();
        contextMenu.add(new MenuItem("Aufgabe anlegen"));
        contextMenu.add(new MenuItem("Unterprojekt anlegen"));
        contextMenu.add(new MenuItem("Projekt löschen"));
    }

    public TreeContextItem(SubProject subProject) {
        type = Type.SUBPROJECT;
        item = subProject;
        stringified = subProject.getName();
        contextMenu.add(new MenuItem("Aufgabe anlegen"));
        contextMenu.add(new MenuItem("Unterprojekt löschen"));
    }

    public TreeContextItem(Task task) {
        type = Type.TASK;
        item = task;
        stringified = task.getName();
        contextMenu.add(new MenuItem("Aufgabe löschen"));
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
