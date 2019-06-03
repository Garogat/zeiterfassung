package zeiterfassung.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import zeiterfassung.Utils;
import zeiterfassung.models.Project;
import zeiterfassung.models.Role;

import java.time.Duration;
import java.time.LocalDateTime;

public class ProjectController {
    private Project project;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label costLabel;

    @FXML
    private TableView<Role> roleTable;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private Text timeSpent;

    @FXML
    private Text timeEstimated;

    @FXML
    ProgressBar time;

    @FXML
    private TableColumn<Role, String> nameCol;

    @FXML
    private TableColumn<Role, String> descCol;

    @FXML
    private TableColumn<Role, String> wageCol;

    @FXML
    private TextField newRoleName;

    @FXML
    private TextField newRoleDescription;

    @FXML
    private TextField newRoleWage;

    @FXML
    private TextArea customer;

    public void setProject(Project project) {
        this.project = project;
        nameTextField.textProperty().bindBidirectional(project.nameProperty());
        descriptionTextArea.textProperty().bindBidirectional(project.descriptionProperty());
        customer.textProperty().bindBidirectional(project.customerProperty());

        // Setting timeframe within which durations of WorkChunks are added up
        Duration estimatedDuration = project.getEstimatedDuration();
        timeEstimated.setText(Utils.formatDuration(estimatedDuration));

        Duration duration = project.getDuration(LocalDateTime.MIN, LocalDateTime.MAX);
        int percentage = 0;
        if (estimatedDuration.toMinutes() == 0) {
            percentage = 100;
        } else if (duration.toMinutes() > 0) {
            percentage = (int) (duration.toMinutes() * 100.0 / estimatedDuration.toMinutes());
        }
        timeSpent.setText(Utils.formatDuration(duration) + " (" + percentage + "%)");
        time.setProgress(percentage / 100.0);
        time.setMaxWidth(Double.MAX_VALUE);

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        wageCol.setCellValueFactory(new PropertyValueFactory<>("hourlyWage"));

        costLabel.setText("" + project.getCosts(LocalDateTime.MIN, LocalDateTime.MAX) + "€");

        roleTable.setItems(project.roleListProperty());
        roleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public Project getProject() {
        return project;
    }

    public void onAddRole(ActionEvent event) {
        if ((newRoleName.getText() != null) && (newRoleDescription.getText() != null) && (newRoleWage.getText() != null)) {
            Role role = new Role(newRoleName.getText(), newRoleDescription.getText(), Double.parseDouble(newRoleWage.getText()));
            if (!(project.hasRole(role))) {
                this.project.addRole(role);
                roleTable.setItems(project.roleListProperty());
            }
        }
    }

    public void onRemoveRole(ActionEvent event) {
        // prevent removing our first "default" role
        if (project.getRolesSize() == 1) {
            Utils.alertWarning("Es muss mindestens eine Rolle existieren.");
            return;
        }

        project.removeRole(roleTable.getSelectionModel().getSelectedItem());
        roleTable.setItems(project.roleListProperty());
    }
}