package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import zeiterfassung.DataStore;
import zeiterfassung.components.Tree;
import zeiterfassung.components.TreeContextItem;
import zeiterfassung.models.Area;
import zeiterfassung.models.Project;
import zeiterfassung.models.SubProject;
import zeiterfassung.models.Task;

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

    @FXML
    public void initialize() {
        setContent("Start");

        // TODO: add resize listener to correctly set divider position
        splitPane.setDividerPositions(0.3);

        contextMenu = new ContextMenu();
        projectTree.setContextMenu(contextMenu);
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

        // TODO: observe datastore and update tree view
        updateTreeView();
    }

    private void updateTreeView() {
        Tree tree = new Tree(store.getRoot());
        projectTree.setRoot(tree.getTree());

        // Hide the root Item.
        projectTree.setShowRoot(false);
        projectTree.refresh();
    }

    private void openView(TreeContextItem item) {
        // TODO: open depending content view
        System.out.println("open " + item.getType().name() + " view");

        switch (item.getType()) {
            case AREA:
                AreaController areaController = (AreaController) setContent("Area");
                areaController.setArea((Area) item.getItem());
                break;
            case PROJECT:
                ProjectController projectController = (ProjectController) setContent("Project");
                projectController.setProject((Project) item.getItem());
                break;
            case SUBPROJECT:
                SubProjectController subProjectController = (SubProjectController) setContent("SubProject");
                subProjectController.setSubProject((SubProject) item.getItem());
                break;
            case TASK:
                TaskController taskController = (TaskController) setContent("Task");
                taskController.setTask((Task) item.getItem());
                break;
            default:
                setContent("Start");
                break;
        }
    }

    @FXML
    private void onTreeViewPressed(MouseEvent event) {
        TreeItem<TreeContextItem> selected = projectTree.getSelectionModel().getSelectedItem();

        // is any item selected - this prevents fail when clicking on empty space
        if (selected == null) {
            return;
        }

        TreeContextItem item = selected.getValue();

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



