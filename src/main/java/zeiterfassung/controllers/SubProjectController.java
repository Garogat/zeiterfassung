package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
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
    private TextField name;

    @FXML
    private TextArea description;

    @FXML
    private Text timeEstimated;

    @FXML
    private Text timeSpent;

    @FXML
    private Text costs;

    @FXML
    private ProgressBar time;

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
        name.textProperty().bindBidirectional(subProject.nameProperty());
        description.textProperty().bindBidirectional(subProject.descriptionProperty());

        Duration estimatedDuration = subProject.getEstimatedDuration();
        timeEstimated.setText(Utils.formatDuration(estimatedDuration));

        Duration duration = subProject.getDuration(LocalDateTime.MIN, LocalDateTime.MAX);
        int percentage = 0;
        if (estimatedDuration.toMinutes() == 0) {
            percentage = 100;
        } else if (duration.toMinutes() > 0) {
            percentage = (int) (duration.toMinutes() * 100.0 / estimatedDuration.toMinutes());
        }
        timeSpent.setText(Utils.formatDuration(duration) + " (" + percentage + "%)");
        time.setProgress(percentage / 100.0);
        time.setMaxWidth(Double.MAX_VALUE);

        costs.setText(Utils.formatCosts(subProject.getCosts(LocalDateTime.MIN, LocalDateTime.MAX)));
    }
}
