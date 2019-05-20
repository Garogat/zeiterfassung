package zeiterfassung.controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import zeiterfassung.components.ActiveWorkChunk;
import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;

import javax.xml.soap.Text;
import java.time.LocalDateTime;

public class TaskController {
    private Task task;
    private ActiveWorkChunk activeWorkChunk;
    private WorkChunk editWorkChunk;

    @FXML
    TextArea descriptionTextArea;
    @FXML
    TextField nameTextField;
    @FXML
    Text durationLabel;
    @FXML
    Text costsLabel;
    @FXML
    ChoiceBox roleChouceBox;

    @FXML
    private TableView<WorkChunk> workchunkTable;

    @FXML
    private TableColumn<WorkChunk, LocalDateTime> startCol;

    @FXML
    private TableColumn<WorkChunk, LocalDateTime> endCol;
    @FXML
    private TableColumn<WorkChunk, Double> durCol;

    @FXML
    private TableColumn<WorkChunk, String> descCol;

    @FXML
    Button startBtn;
    @FXML
    TextField workchuncDescription;
    @FXML
    Button stopBtn;


    private void setEditWorkChunk(WorkChunk workChunk){
        editWorkChunk = workChunk;

        if (editWorkChunk != null) {
            workchuncDescription.textProperty().bindBidirectional(workChunk.descriptionProperty());
        }else{
            workchuncDescription.textProperty().unbind();
            workchuncDescription.setText("");
        }
        startBtn.setDisable(this.activeWorkChunk.getActiveWorkChunk() != null);
        stopBtn.setDisable(workChunk != null);
        workchuncDescription.setDisable(workChunk != null);
    }

    public void setTask(Task task, ActiveWorkChunk activeWorkChunk) {
        this.task = task;
        this.activeWorkChunk = activeWorkChunk;


        nameTextField.textProperty().bindBidirectional(task.nameProperty());
        descriptionTextArea.textProperty().bindBidirectional(task.descriptionProperty());


        startBtn.setVisible(this.activeWorkChunk.getActiveWorkChunk() == null || ActiveWorkChunk.isWorkChunkInTask(this.activeWorkChunk.getActiveWorkChunk(), task));
        stopBtn.setVisible(startBtn.isVisible());
        workchuncDescription.setVisible(startBtn.isVisible());

        if (ActiveWorkChunk.isWorkChunkInTask(this.activeWorkChunk.getActiveWorkChunk(), task)){
            setEditWorkChunk(this.activeWorkChunk.getActiveWorkChunk());
        }else{
            setEditWorkChunk(null);
        }

        startCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        durCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        workchunkTable.setItems(task.workListProperty());

        /*

            description.textProperty().bindBidirectional(area.descriptionProperty());
        */
    }

    public Task getTask() {
        return task;
    }
}
