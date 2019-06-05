package zeiterfassung.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import zeiterfassung.Utils;
import zeiterfassung.components.ActiveWorkChunk;
import zeiterfassung.models.Project;
import zeiterfassung.models.Role;
import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Provides the interface between task View and Task model
 */
public class TaskController {
    private Task task;
    private ActiveWorkChunk activeWorkChunk;
    private WorkChunk editWorkChunk;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField nameTextField;

    @FXML
    private Text durationLabel;

    @FXML
    private Text costsLabel;

    @FXML
    private ChoiceBox<Role> roleChoiceBox;

    @FXML
    private TableView<WorkChunk> workchunkTable;

    @FXML
    private TableColumn<WorkChunk, LocalDateTime> startCol;

    @FXML
    private TableColumn<WorkChunk, LocalDateTime> endCol;

    @FXML
    private TableColumn<WorkChunk, Duration> durCol;

    @FXML
    private TableColumn<WorkChunk, Double> descCol;

    @FXML
    private Button startBtn;

    @FXML
    private TextField workchuncDescription;

    @FXML
    private TextField estimatedDurationTextField;

    @FXML
    private Button stopBtn;

    /**
     * Determines if a WorkChunk is open and if the WorkChunk belongs to this Task.
     * It shows and enables the buttons so that you can not start a second WorkChunk
     */
    private void setEditWorkChunk() {
        boolean editable = ActiveWorkChunk.isWorkChunkInTask(this.activeWorkChunk.getActiveWorkChunk(), task) || this.activeWorkChunk.getActiveWorkChunk() == null;
        if (editable) {
            editWorkChunk = this.activeWorkChunk.getActiveWorkChunk();
        } else {
            editWorkChunk = null;
        }

        startBtn.setVisible(editable);
        stopBtn.setVisible(editable);
        descriptionTextArea.setVisible(editable);
        if (editWorkChunk != null) {
            workchuncDescription.textProperty().bindBidirectional(editWorkChunk.descriptionProperty());
        } else {
            workchuncDescription.setText("");
        }
        startBtn.setDisable(!editable || editWorkChunk != null);
        stopBtn.setDisable(editWorkChunk == null);
        workchuncDescription.setDisable(editWorkChunk == null);
    }

    /**
     * Initializes the Controller with a Task
     *
     * @param task            This Task is handled by the Controller
     * @param activeWorkChunk Is needed to determine, if a Task is running
     */
    public void setTask(Task task, ActiveWorkChunk activeWorkChunk) {
        this.task = task;
        this.activeWorkChunk = activeWorkChunk;

        /* Bindings */

        // Name
        nameTextField.textProperty().bindBidirectional(task.nameProperty());
        // Description
        descriptionTextArea.textProperty().bindBidirectional(task.descriptionProperty());
        // Estimated Time
        estimatedDurationTextField.textProperty().bindBidirectional(task.estimatedDurationProperty(), new StringConverter<Duration>() {
                    @Override
                    public String toString(Duration object) {
                        return String.valueOf(object.getSeconds() / 3600);
                    }

                    @Override
                    public Duration fromString(String string) {
                        try {
                            Duration res = Duration.ofHours(Integer.valueOf(string));
                            return res;
                        } catch (NumberFormatException e) {
                            return Duration.ZERO;
                        }
                    }
                }
        );

        // Enable/ Disable/ Hide Start and Stop Task
        setEditWorkChunk();

        // Roles
        Project project = (Project) task.getParentByType(Project.class);
        roleChoiceBox.setItems(project.roleListProperty());
        roleChoiceBox.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role role) {
                if (role == null) {
                    return null;
                } else {
                    return role.getName();
                }
            }

            @Override
            public Role fromString(String id) {
                return null;
            }
        });
        roleChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Role>() {
            @Override
            public void changed(ObservableValue<? extends Role> observable, Role oldValue, Role newValue) {
                task.setRole(newValue);
                updateCostDuration();
            }
        });
        roleChoiceBox.getSelectionModel().select(task.getRole());

        // WorkChunks
        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startCol.setCellFactory(col -> new TableCell<WorkChunk, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty)
                    setText(null);
                else
                    setText(Utils.formatLocalDateTime(item));
            }
        });
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endCol.setCellFactory(col -> new TableCell<WorkChunk, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else {
                    setText(Utils.formatLocalDateTime(item));
                }
            }
        });
        durCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        durCol.setCellFactory(col -> new TableCell<WorkChunk, Duration>() {
            @Override
            protected void updateItem(Duration item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null)
                    setText(null);
                else {
                    setText(Utils.formatDuration(item));
                }
            }
        });

        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        workchunkTable.setItems(task.workListProperty());
        workchunkTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        updateCostDuration();
    }

    /**
     * recalculates the duration and the costs
     */
    private void updateCostDuration() {
        costsLabel.setText(Utils.formatCosts(task.getCosts(LocalDateTime.MIN, LocalDateTime.MAX)));
        durationLabel.setText(Utils.formatDuration(task.getDuration(LocalDateTime.MIN, LocalDateTime.MAX)));
    }

    public Task getTask() {
        return task;
    }

    /**
     * Starts a Task and generates a new WorkChunk
     *
     * @param actionEvent
     */
    public void onStartBtn(ActionEvent actionEvent) {
        WorkChunk newWc = new WorkChunk();
        newWc.setStartTime(LocalDateTime.now());
        task.addWorkChunk(newWc);
        setEditWorkChunk();
    }

    /**
     * Stops a Task
     *
     * @param actionEvent
     */
    public void onStopBtn(ActionEvent actionEvent) {
        workchuncDescription.textProperty().unbindBidirectional(editWorkChunk.descriptionProperty());
        editWorkChunk.setEndTime(LocalDateTime.now());
        setEditWorkChunk();
        updateCostDuration();
    }
}
