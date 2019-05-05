package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Base {

    @FXML
    private MenuBar menu;

    @FXML
    private TreeView<?> projectTree;

    @FXML
    private AnchorPane content;

    @FXML
    private SplitPane splitPane;

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

        // TODO: load projectTree
    }

    public void setContent(String view) throws IOException {
        Node node = FXMLLoader.load(getClass().getResource("/zeiterfassung/views/" + view + ".fxml"));
        content.getChildren().setAll(node);
    }
}
