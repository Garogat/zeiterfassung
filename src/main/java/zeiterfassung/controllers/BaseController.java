package zeiterfassung.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import zeiterfassung.components.ActiveWorkChunk;
import zeiterfassung.components.Tree;
import zeiterfassung.components.TreeContextItem;
import zeiterfassung.models.*;
import zeiterfassung.xml.DataStore;

import java.io.IOException;

public class BaseController {

    @FXML
    private MenuBar menu;

    @FXML
    private TreeView<TreeContextItem> projectTree;

    @FXML
    private AnchorPane content;

    @FXML
    private SplitPane splitPane;

    private DataStore store;
    private ContextMenu contextMenu;
    private Tree tree;
    private TreeContextItem.Listener contextMenuListener;
    private ActiveWorkChunk activeWorkChunk;

    @FXML
    public void initialize() {
        setContent("Start");

        // TODO: add resize listener to correctly set divider position
        splitPane.setDividerPositions(0.3);

        contextMenu = new ContextMenu();
        projectTree.setContextMenu(contextMenu);

        contextMenuListener = new TreeContextItem.Listener() {
            @Override
            public void onDelete(Area area) {
                TimeRegistrationRoot root = (TimeRegistrationRoot) area.getParent();
                root.removeArea(area);
            }

            @Override
            public void onDelete(Project project) {
                Area area = (Area) project.getParent();
                area.removeProject(project);
            }

            @Override
            public void onDelete(SubProject subProject) {
                Project project = (Project) subProject.getParent();
                project.removeSubProject(subProject);
            }

            @Override
            public void onDelete(Task task) {
                SubProject subProject = (SubProject) task.getParent();
                subProject.removeTask(task);
            }

            @Override
            public void onAddArea() {
                Area area = new Area();
                area.setName("Neuer Bereich");
                store.getRoot().addArea(area);
            }

            @Override
            public void onAddProject(Area area) {
                Project project = new Project();
                project.setName("Neues Projekt");
                area.addProject(project);
            }

            @Override
            public void onAddSubProject(Project project) {
                SubProject subProject = new SubProject();
                subProject.setName("Neues Unterprojekt");
                project.addSubProject(subProject);
            }

            @Override
            public void onAddTask(SubProject subProject) {
                Task task = new Task();
                task.setName("Neuer Task");
                subProject.addTask(task);
            }
        };
    }

    public Object setContent(String view) {
        Node node = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/zeiterfassung/views/" + view + ".fxml"));
        try {
            node = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (node != null) {
            content.getChildren().setAll(node);
            return loader.getController();
        }

        return null;
    }

    public void setDataStore(DataStore store) {
        this.store = store;

        tree = new Tree(store.getRoot());
        activeWorkChunk = new ActiveWorkChunk(store.getRoot());
        projectTree.setRoot(tree.getTree());

        projectTree.setCellFactory(t -> new TreeCell<TreeContextItem>() {
            @Override
            public void updateItem(TreeContextItem item, boolean empty) {
                super.updateItem(item, empty);

                textProperty().unbind();
                graphicProperty().unbind();

                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // bind text value to model
                    textProperty().bind(item.textProperty());
                    setGraphic(item.getIcon());
                }
            }
        });

        // Hide the root Item.
        // projectTree.setShowRoot(false);
        projectTree.refresh();
    }

    private void openView(TreeContextItem item) {
        // TODO: open depending content view
        System.out.println("open " + item.getType().name() + " view");

        switch (item.getType()) {
            case AREA:
                openView((Area) item.getItem());
                break;
            case PROJECT:
                openView((Project) item.getItem());
                break;
            case SUBPROJECT:
                openView((SubProject) item.getItem());
                break;
            case TASK:
                openView((Task) item.getItem());
                break;
            default:
                setContent("Start");
                break;
        }
    }

    private AreaController openView(Area area) {
        AreaController areaController = (AreaController) setContent("Area");
        areaController.setArea(area);
        return areaController;
    }

    private ProjectController openView(Project project) {
        ProjectController projectController = (ProjectController) setContent("Project");
        projectController.setProject(project);
        return projectController;
    }

    private SubProjectController openView(SubProject subProject) {
        SubProjectController subProjectController = (SubProjectController) setContent("SubProject");
        subProjectController.setSubProject(subProject);
        return subProjectController;
    }

    private TaskController openView(Task task) {
        TaskController taskController = (TaskController) setContent("Task");
        taskController.setTask(task,  activeWorkChunk);
        return taskController;
    }

    @FXML
    private void onTreeViewPressed(MouseEvent event) {
        TreeItem<TreeContextItem> selected = projectTree.getSelectionModel().getSelectedItem();

        // is any item selected - this prevents fail when clicking on empty space
        if (selected == null) {
            return;
        }

        TreeContextItem item = selected.getValue();
        item.setListener(contextMenuListener);

        contextMenu.hide();

        if (event.getButton() == MouseButton.PRIMARY) {
            openView(item);
        } else if (event.getButton() == MouseButton.SECONDARY) {
            // load menu items
            contextMenu.getItems().setAll(item.getContextMenu());

            // show menu
            contextMenu.show(projectTree, event.getScreenX(), event.getScreenY());
        }
    }
}



