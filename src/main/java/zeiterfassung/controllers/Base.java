package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import zeiterfassung.DataStore;
import zeiterfassung.models.TimeRegistrationRoot;

import java.io.IOException;

public class Base {

    @FXML
    private MenuBar menu;

    @FXML
    private TreeView<String> projectTree;

    @FXML
    private AnchorPane content;

    @FXML
    private SplitPane splitPane;

    private DataStore store;

    @FXML
    public void initialize() {
        System.out.println("running");
        try {
            setContent("Start");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: add resize listener to correctly set divider position
        splitPane.setDividerPositions(0.3);
    }

    public void setContent(String view) throws IOException {
        Node node = FXMLLoader.load(getClass().getResource("/zeiterfassung/views/" + view + ".fxml"));
        content.getChildren().setAll(node);
    }

    public void setDataStore(DataStore store) {
        this.store = store;

        // TODO: load projectTree
        TimeRegistrationRoot root = this.store.getRoot();
        root.toString();
        updateTreeView();
    }

    public void updateTreeView() {
        Tree tree = new Tree(this.store.getRoot());
        TreeItem<String> root = tree.getTree();
        this.projectTree.setRoot(root);

        // Hide the root Item.
        this.projectTree.setShowRoot(false);
    }
}
