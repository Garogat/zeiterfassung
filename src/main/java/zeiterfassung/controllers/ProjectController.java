package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import zeiterfassung.Utils;
import zeiterfassung.models.Project;
import zeiterfassung.models.Role;
import zeiterfassung.reports.ProjectContent;
import zeiterfassung.reports.ReportDocument;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides the interface between project View and {@link Project} model
 */
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
    private ProgressBar time;

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

    @FXML
    private DatePicker reportMonth;

    /**
     * Initializes the Controller with a Project
     *
     * @param project This Project is handled by the Controller
     */
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

        roleTable.setItems(project.roleListProperty());
        roleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        reportMonth.setShowWeekNumbers(false);
        reportMonth.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        reportMonth.setValue(LocalDate.now());

        updateCosts();
    }

    /**
     * Add a new role
     */
    @FXML
    private void onAddRole() {
        if ((newRoleName.getText() != null) && (newRoleDescription.getText() != null) && (newRoleWage.getText() != null)) {
            Role role = new Role(newRoleName.getText(), newRoleDescription.getText(), Double.parseDouble(newRoleWage.getText()));
            if (!(project.hasRole(role))) {
                this.project.addRole(role);
                roleTable.setItems(project.roleListProperty());
            }
        }
    }

    /**
     * Remove the currently selected role
     */
    @FXML
    private void onRemoveRole() {
        // prevent removing our first "default" role
        if (project.getRolesSize() == 1) {
            Utils.alertWarning("Es muss mindestens eine Rolle existieren.");
            return;
        }

        project.removeRole(roleTable.getSelectionModel().getSelectedItem());
        roleTable.setItems(project.roleListProperty());

        updateCosts();
    }

    /**
     * Create a report
     */
    @FXML
    private void createReport() {
        LocalDateTime date = reportMonth.getValue().atStartOfDay();
        date = date.minusDays(date.getDayOfMonth()-1);

        String title = "Stundenzettel " + this.project.getName() + " " + date.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        ReportDocument report = new ReportDocument(title, new ProjectContent(this.project, date, date.plusMonths(1)));
        Utils.createReport(report, nameTextField.getScene().getWindow());
    }

    /**
     * Create an invoice
     */
    @FXML
    private void createInvoice() {
        Utils.createInvoice(this.project, nameTextField.getScene().getWindow());
    }

    /**
     * update coast label
     */
    private void updateCosts() {
        costLabel.setText(Utils.formatCosts(project.getCosts(LocalDateTime.MIN, LocalDateTime.MAX)));
    }
}
