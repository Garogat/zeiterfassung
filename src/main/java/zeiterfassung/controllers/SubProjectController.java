package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import zeiterfassung.Utils;
import zeiterfassung.models.SubProject;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubProjectController {
    private SubProject subProject;

    @FXML
    TextField name;

    @FXML
    TextArea description;

    @FXML
    Text workValued;

    @FXML
    Text workDone;

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
        name.textProperty().bindBidirectional(subProject.nameProperty());
        description.textProperty().bindBidirectional(subProject.descriptionProperty());

        Duration duration = subProject.getDuration(LocalDateTime.MIN, LocalDateTime.MAX);
        workValued.setText(subProject.getEstimatedDuration().toHours() + " Stunden");

        if (duration.toNanos() > 0) {
            int percentage = (int) (subProject.getEstimatedDuration().toHours() / duration.toHours());
            workDone.setText(Utils.formatDuration(subProject.getEstimatedDuration()) + " (" + percentage + " %)");
        } else {
            workDone.setText("noch nicht begonnen");
        }
    }
}
