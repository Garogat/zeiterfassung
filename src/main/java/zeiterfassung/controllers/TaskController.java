package zeiterfassung.controllers;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;
import zeiterfassung.Utils;
import zeiterfassung.components.ActiveWorkChunk;
import zeiterfassung.models.*;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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


    private void setEditWorkChunk(){
        boolean editable = ActiveWorkChunk.isWorkChunkInTask(this.activeWorkChunk.getActiveWorkChunk(), task)  || this.activeWorkChunk.getActiveWorkChunk() == null;
        if (editable) {
            editWorkChunk = this.activeWorkChunk.getActiveWorkChunk();
        }else {
            editWorkChunk = null;
        }

        startBtn.setVisible(editable);
        stopBtn.setVisible(editable);
        descriptionTextArea.setVisible(editable);
        if (editWorkChunk != null) {
            workchuncDescription.textProperty().bindBidirectional(editWorkChunk.descriptionProperty());
        }else{
            workchuncDescription.setText("");
        }
        startBtn.setDisable(!editable || editWorkChunk != null);
        stopBtn.setDisable(editWorkChunk == null);
        workchuncDescription.setDisable(editWorkChunk == null);
    }


    public void setTask(Task task, ActiveWorkChunk activeWorkChunk) {
        this.task = task;
        this.activeWorkChunk = activeWorkChunk;


        nameTextField.textProperty().bindBidirectional(task.nameProperty());
        descriptionTextArea.textProperty().bindBidirectional(task.descriptionProperty());

        estimatedDurationTextField.textProperty().bindBidirectional(task.estimatedDurationProperty(), new StringConverter<Duration>(){
                    @Override
                    public String toString(Duration object) {
                        return String.valueOf(object.getSeconds()/3600);
                    }

                    @Override
                    public Duration fromString(String string) {
                        try {
                            Duration res = Duration.ofHours(Integer.valueOf(string));
                            return res;
                        }catch (NumberFormatException e){
                            return Duration.ZERO;
                        }
                    }
                }
        );

        startBtn.setVisible(this.activeWorkChunk.getActiveWorkChunk() == null || ActiveWorkChunk.isWorkChunkInTask(this.activeWorkChunk.getActiveWorkChunk(), task));
        stopBtn.setVisible(startBtn.isVisible());
        workchuncDescription.setVisible(startBtn.isVisible());

        setEditWorkChunk();

        roleChoiceBox.setItems(((Project)task.getParentByType(Project.class)).roleListProperty());
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

        updateCostDuration();
    }


    private void updateCostDuration(){
        costsLabel.setText(Utils.formatCosts( task.getCosts(LocalDateTime.MIN, LocalDateTime.MAX)));
        durationLabel.setText( Utils.formatDuration(task.getDuration(LocalDateTime.MIN, LocalDateTime.MAX)));
    }

    public Task getTask() {
        return task;
    }

    public void onStartBtn(ActionEvent actionEvent) {
        WorkChunk newWc = new WorkChunk();
        newWc.setStartTime(LocalDateTime.now());
        task.addWorkChunk(newWc);
        setEditWorkChunk();
    }

    public void onStopBtn(ActionEvent actionEvent) {
        workchuncDescription.textProperty().unbindBidirectional(editWorkChunk.descriptionProperty());
        editWorkChunk.setEndTime(LocalDateTime.now());
        setEditWorkChunk();
        updateCostDuration();
    }
}
