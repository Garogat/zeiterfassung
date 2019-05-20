package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import zeiterfassung.models.Task;

import javax.xml.soap.Text;

public class TaskController {
    private Task task;

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
    TableView workchunkTable;
    @FXML
    Button startBtn;
    @FXML
    TextField workchuncDescription;
    @FXML
    Button stopBtn;


    public void setTask(Task task) {
        this.task = task;
        nameTextField.textProperty().bindBidirectional(task.nameProperty());
        descriptionTextArea.textProperty().bindBidirectional(task.descriptionProperty());


        /*

            description.textProperty().bindBidirectional(area.descriptionProperty());
        */
    }

    public Task getTask() {
        return task;
    }
}
