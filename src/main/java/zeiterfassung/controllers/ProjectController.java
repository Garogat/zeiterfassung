package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import zeiterfassung.models.Project;
import zeiterfassung.models.Role;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.Duration;
import java.time.LocalDateTime;

public class ProjectController {
    private Project project;
    /*
    @Todo add roles (copy from TaskController), add Auftraggeber
     */
    @FXML
    private TextField nameTextField;

    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Text actualLabel;

    @FXML
    private Text estimateLabel;

    @FXML
    private TableColumn <Role, String> nameCol;

    @FXML
    private TableColumn <Role, String> descCol;

    @FXML
    private TableColumn<Role, String> wageCol;

    @FXML
    private Button addRole;

    @FXML
    private Button deleteRole;

    public void setProject(Project project) {
        this.project = project;
        nameTextField.textProperty().bindBidirectional(project.nameProperty());
        descriptionTextArea.textProperty().bindBidirectional(project.descriptionProperty());

        //Setting timeframe within which durations of WorkChunks are added up
        LocalDateTime start = LocalDateTime.parse("2019-05-01T10:20:40.577");
        LocalDateTime stop = LocalDateTime.parse("2019-06-30T10:20:40.577");
        Duration duration = project.getDuration(start, stop);
        estimateLabel.setText("Geschätzte Arbeit: " + "X" + " Stunden");
        actualLabel.setText("Bisher geleistete Arbeit: "+duration.toHours()+" Stunden");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        wageCol.setCellValueFactory(new PropertyValueFactory<>("hourlyWage"));

        roleTable.setItems(project.roleListProperty());
    }

    public Project getProject() {
        return project;
    }

}
