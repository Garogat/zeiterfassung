package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
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


        // TODO: set valued time
        Duration duration = subProject.getDuration(LocalDateTime.MIN, LocalDateTime.MAX);
        workValued.setText("X" + " Stunden");

        if (duration.toHours() > 0) {
            int percentage = (int) (10 / duration.toHours());
            workDone.setText(duration.toHours() + " Stunden (" + percentage + " %)");
        } else {
            workDone.setText("noch nicht begonnen");
        }
    }
}
