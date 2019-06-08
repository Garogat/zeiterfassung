package zeiterfassung.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import zeiterfassung.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A special {@link TreeItem} content wrapper including a special contextmenu, icon, ...
 */
public class TreeContextItem {

    /**
     * Listener for contextmenu actions
     */
    public interface Listener {
        void onDelete(Area area);

        void onDelete(Project project);

        void onDelete(SubProject subProject);

        void onDelete(Task task);

        void onAddArea();

        void onAddProject(Area area);

        void onAddSubProject(Project project);

        void onAddTask(SubProject subProject);

        void onImportDatabase();

        void onExportDatabase();

        void onOpenActiveTask();
    }

    /**
     * Enum for type of the wrappers content
     */
    public enum Type {ROOT, AREA, PROJECT, SUBPROJECT, TASK}

    ;

    /**
     * Icon for {@link TimeRegistrationRoot}
     */
    private final Node iconRoot = new ImageView(new Image(getClass().getResourceAsStream("/zeiterfassung/icons/database-25.png")));

    /**
     * Icon for {@link Area}
     */
    private final Node iconArea = new ImageView(new Image(getClass().getResourceAsStream("/zeiterfassung/icons/folder-25.png")));

    /**
     * Icon for {@link Project} and {@link SubProject}
     */
    private final Node iconProject = new ImageView(new Image(getClass().getResourceAsStream("/zeiterfassung/icons/clipboard-25.png")));

    /**
     * Icon for {@link Task}
     */
    private final Node iconTask = new ImageView(new Image(getClass().getResourceAsStream("/zeiterfassung/icons/worker-with-shovel-25.png")));

    /**
     * String property for {@link TreeItem} text
     */
    private StringProperty text = new SimpleStringProperty();

    /**
     * contextmenu
     */
    private List<MenuItem> contextMenu = new ArrayList<>();

    /**
     * Type of {@code item}
     */
    private Type type;

    /**
     * Model represented by {@link TreeItem}
     */
    private BaseModel item;

    /**
     * Contextmenu action listener
     */
    private Listener listener;

    /**
     * Icon of this instance
     */
    private Node icon;

    /**
     * Add area menu item and set it's listener
     *
     * @return "Add area" menu item
     */
    private MenuItem addArea() {
        MenuItem result = new MenuItem("Bereich anlegen");
        result.setOnAction(event -> listener.onAddArea());
        return result;
    }

    /**
     * Add porject menu item and set it's listener
     *
     * @return "Add project" menu item
     */
    private MenuItem addProject() {
        MenuItem result = new MenuItem("Projekt anlegen");
        result.setOnAction(event -> listener.onAddProject((Area) item));
        return result;
    }

    /**
     * "Add subproject" menu item and set it's listener
     *
     * @return "Add subproject" menu item
     */
    private MenuItem addSubProject() {
        MenuItem result = new MenuItem("Unterprojekt anlegen");
        result.setOnAction(event -> listener.onAddSubProject((Project) item));
        return result;
    }

    /**
     * "Add task" menu item and set it's listener
     *
     * @return "Add task" menu item
     */
    private MenuItem addTask() {
        MenuItem result = new MenuItem("Aufgabe anlegen");
        result.setOnAction(event -> listener.onAddTask((SubProject) item));
        return result;
    }

    /**
     * "Delete area" menu item and set it's listener
     *
     * @return "Delete area" menu item
     */
    private MenuItem deleteArea() {
        MenuItem result = new MenuItem("Bereich löschen");
        result.setOnAction(event -> listener.onDelete((Area) item));
        return result;
    }

    private MenuItem deleteProject() {
        MenuItem result = new MenuItem("Projekt löschen");
        result.setOnAction(event -> listener.onDelete((Project) item));
        return result;
    }

    private MenuItem deleteSubProject() {
        MenuItem result = new MenuItem("Unterprojekt löschen");
        result.setOnAction(event -> listener.onDelete((SubProject) item));
        return result;
    }

    private MenuItem deleteTask() {
        MenuItem result = new MenuItem("Aufgabe löschen");
        result.setOnAction(event -> listener.onDelete((Task) item));
        return result;
    }

    /**
     * Set the contextmenu action listener
     *
     * @param listener action listener
     * @return {@link TreeContextItem} instance
     */
    public TreeContextItem setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    /**
     * Create {@link TreeContextItem} for a {@link TimeRegistrationRoot}
     *
     * @param root {@link TimeRegistrationRoot} model
     */
    public TreeContextItem(TimeRegistrationRoot root) {
        type = Type.ROOT;
        item = root;
        text.set("ZeitErfassung.xml");
        icon = iconRoot;

        MenuItem importDatabase = new MenuItem("Datenbank importieren");
        importDatabase.setOnAction(event -> listener.onImportDatabase());
        contextMenu.add(importDatabase);

        MenuItem exportDatabase = new MenuItem("Datenbank exportieren");
        exportDatabase.setOnAction(event -> listener.onExportDatabase());
        contextMenu.add(exportDatabase);

        MenuItem openActiveTask = new MenuItem("Aktive Aufgabe öffnen");
        openActiveTask.setOnAction(event -> listener.onOpenActiveTask());
        contextMenu.add(openActiveTask);

        contextMenu.add(addArea());
    }

    /**
     * Create {@link TreeContextItem} for a {@link Area}
     *
     * @param area {@link Area} model
     */
    public TreeContextItem(Area area) {
        type = Type.AREA;
        item = area;
        text.bind(area.nameProperty());
        icon = iconArea;

        contextMenu.add(addProject());
        contextMenu.add(deleteArea());
    }

    /**
     * Create {@link TreeContextItem} for a {@link Project}
     *
     * @param project {@link Project} model
     */
    public TreeContextItem(Project project) {
        type = Type.PROJECT;
        item = project;
        text.bind(project.nameProperty());
        icon = iconProject;

        contextMenu.add(addTask());
        contextMenu.add(addSubProject());
        contextMenu.add(deleteProject());
    }

    /**
     * Create {@link TreeContextItem} for {@link SubProject}
     *
     * @param subProject {@link SubProject} model
     */
    public TreeContextItem(SubProject subProject) {
        type = Type.SUBPROJECT;
        item = subProject;
        text.bind(subProject.nameProperty());
        icon = iconProject;

        contextMenu.add(addTask());
        contextMenu.add(deleteSubProject());
    }

    /**
     * Create {@link TreeContextItem} for {@link Task}
     *
     * @param task {@link Task} model
     */
    public TreeContextItem(Task task) {
        type = Type.TASK;
        item = task;
        text.bind(task.nameProperty());
        icon = iconTask;

        contextMenu.add(deleteTask());
    }

    public Type getType() {
        return this.type;
    }

    public BaseModel getItem() {
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
