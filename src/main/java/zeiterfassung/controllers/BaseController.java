package zeiterfassung.controllers;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import zeiterfassung.Main;
import zeiterfassung.Utils;
import zeiterfassung.components.Tree;
import zeiterfassung.components.TreeContextItem;
import zeiterfassung.components.VersionInfo;
import zeiterfassung.models.*;
import zeiterfassung.xml.DataStore;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * This is the main controller which manages the tree view, menu and loads the model controllers
 */
public class BaseController {

    @FXML
    private MenuBar menu;

    @FXML
    private TreeView<TreeContextItem> projectTree;

    @FXML
    private VBox content;

    @FXML
    private SplitPane splitPane;
    @FXML
    private Menu aboutMenu;

    private DataStore store;
    private ContextMenu contextMenu;
    private Tree tree;
    private TreeContextItem.Listener contextMenuListener;

    /**
     * Injector for {@link DataStore} and initial tree rendering
     *
     * @param store Currently loaded {@link DataStore}
     */
    public void setDataStore(DataStore store) {
        this.store = store;

        tree = new Tree(store.getRoot());
        projectTree.setRoot(tree.getTree());

        openStart();
    }

    /**
     * Initilaizes the controller
     */
    @FXML
    private void initialize() {
        // TODO: add resize listener to correctly set divider position
        splitPane.setDividerPositions(0.3);

        contextMenu = new ContextMenu();
        projectTree.setContextMenu(contextMenu);

        contextMenuListener = new TreeContextItem.Listener() {
            @Override
            public void onDelete(Area area) {
                TimeRegistrationRoot root = (TimeRegistrationRoot) area.getParent();
                root.removeArea(area);

                // open parent (start)
                openStart();
            }

            @Override
            public void onDelete(Project project) {
                Area area = (Area) project.getParent();
                area.removeProject(project);

                // open parent
                openView(area);
            }

            @Override
            public void onDelete(SubProject subProject) {
                Project project = (Project) subProject.getParent();
                project.removeSubProject(subProject);

                // open parent
                openView(project);
            }

            @Override
            public void onDelete(Task task) {
                SubProject subProject = (SubProject) task.getParent();
                subProject.removeTask(task);

                // unset active task
                if (store.getRoot().getActiveTask().equals(task)) {
                    store.getRoot().setActiveTask(null);
                }

                // open parent
                openView(subProject);
            }

            @Override
            public void onAddArea() {
                Area area = new Area();
                area.setName("Neuer Bereich");
                store.getRoot().addArea(area);
                openView(area);
            }

            @Override
            public void onAddProject(Area area) {
                Project project = new Project();
                project.setName("Neues Projekt");
                area.addProject(project);
                openView(project);
            }

            @Override
            public void onAddSubProject(Project project) {
                SubProject subProject = new SubProject();
                subProject.setName("Neues Unterprojekt");
                project.addSubProject(subProject);
                openView(subProject);
            }

            @Override
            public void onAddTask(SubProject subProject) {
                Task task = new Task();
                task.setName("Neuer Task");
                subProject.addTask(task);
                openView(task);
            }

            @Override
            public void onImportDatabase() {
                importDatabase();
            }

            @Override
            public void onExportDatabase() {
                exportDatabase();
            }

            @Override
            public void onOpenActiveTask() {
                Task task = store.getRoot().getActiveTask();

                if (task == null) {
                    Utils.alertWarning("Zur Zeit ist kein Task in Bearbeitung.");
                } else {
                    openView(task);
                }
            }
        };

        VersionInfo vInfo = new VersionInfo();
        MenuItem versionInfoMenu = new MenuItem(vInfo.info());
        //copy commit hash to the clipboard
        if (vInfo.isActive()) {
            versionInfoMenu.setOnAction(event -> {
                final Clipboard clipboard = Clipboard.getSystemClipboard();
                final ClipboardContent content = new ClipboardContent();
                content.putString(vInfo.getCommitHash());
                clipboard.setContent(content);
                Utils.alertInfo("Der Commit Hash ist nun im Clipboard");
            });
        }
        aboutMenu.getItems().add(versionInfoMenu);

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
                    if (item.getType() == TreeContextItem.Type.TASK) {
                        Task task = (Task) item.getItem();
                        task.workActiveProperty().addListener((ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) -> {
                            if (aBoolean) {
                                getStyleClass().remove("active-task");
                            } else {
                                getStyleClass().add("active-task");
                            }
                        });
                        if (task.isWorkActive()) {
                            getStyleClass().add("active-task");
                        }
                    }
                }
            }
        });
    }

    private Object setContent(String view) {
        Node node = null;

        FXMLLoader loader = new FXMLLoader(Charset.forName("UTF-8"));
        loader.setLocation(getClass().getResource("/zeiterfassung/views/" + view + ".fxml"));

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
        taskController.setTask(task);
        return taskController;
    }

    private StartController openStart() {
        this.projectTree.getSelectionModel().selectFirst();
        return (StartController) setContent("Start");
    }

    private void openView(TreeContextItem item) {
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
                openStart();
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

    @FXML
    private void importDatabase() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Datenbank auswählen");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Datenbank (*.xml)", "*.xml");
        chooser.getExtensionFilters().add(extFilter);

        File newDatabase = chooser.showOpenDialog(content.getScene().getWindow());

        if (newDatabase == null) {
            return;
        }

        File currentDatabase = new File(DataStore.XMLFilePath);
        File backupDatabase = new File("ZeitErfassung_Backup.xml");

        // save current database to backup file
        try {
            store.saveToXML(backupDatabase);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        // save imported database
        try {
            String content = new String(Files.readAllBytes(newDatabase.toPath()));
            PrintWriter writer = new PrintWriter(currentDatabase);
            writer.println(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // restart application
        Main.restart();

        // alert user
        Utils.alertInfo("Die neue Datenbank wurde erfolgreich importiert.");
    }

    @FXML
    private void exportDatabase() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Datenbank exportieren");
        chooser.setInitialFileName(DataStore.XMLFilePath);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Datenbank (*.xml)", "*.xml");
        chooser.getExtensionFilters().add(extFilter);

        File file = chooser.showSaveDialog(content.getScene().getWindow());

        if (file == null) {
            return;
        }

        try {
            this.store.saveToXML(file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        Utils.alertInfo("Die Datenbank wurde erfolgreich exportiert.");
    }
}



