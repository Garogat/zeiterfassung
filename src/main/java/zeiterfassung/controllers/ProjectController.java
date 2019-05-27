package zeiterfassung.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.lang.String;
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
        customer.setText(project.getCustomer());

        //Setting timeframe within which durations of WorkChunks are added up
        LocalDateTime start = LocalDateTime.parse("2019-05-01T10:20:40.577");
        LocalDateTime stop = LocalDateTime.parse("2019-06-30T10:20:40.577");
        Duration duration = project.getDuration(start, stop);
        estimateLabel.setText("Geschätzte Arbeit: " + "X" + " Stunden");
        actualLabel.setText("Bisher geleistete Arbeit: " + duration.toHours() + " Stunden");

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        wageCol.setCellValueFactory(new PropertyValueFactory<>("hourlyWage"));

        roleTable.setItems(project.roleListProperty());
    }

    public Project getProject() {
        return project;
    }

    public void onAddRole(ActionEvent event) {
        if((newRoleName.getText()!=null)&&(newRoleDescription.getText()!=null)&&(newRoleWage.getText()!=null)){
            Role role = new Role(newRoleName.getText(), newRoleDescription.getText(), Double.parseDouble(newRoleWage.getText()));
            if(!(project.hasRole(role))){
                System.out.println(project.hasRole(role));
                this.project.addRole(role);
                roleTable.setItems(project.roleListProperty());
            }
        }
    }

    public void onRemoveRole(ActionEvent event){
        project.removeRole(roleTable.getSelectionModel().getSelectedItem());
        roleTable.setItems(project.roleListProperty());
    }

    public void setCustomerField(){
        this.project.setCustomer(customer.getText());
    }
}