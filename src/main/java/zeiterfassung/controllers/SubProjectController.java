package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import zeiterfassung.models.SubProject;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubProjectController {
    private SubProject subProject;

    @FXML
    TextField name;

    @FXML
    TextArea description;

    /* @Todo Replace
     */
    @FXML
    TextArea work;

    public void setSubProject(SubProject subProject) {
        this.subProject = subProject;
        name.textProperty().bindBidirectional(subProject.nameProperty());
        description.textProperty().bindBidirectional(subProject.descriptionProperty());

        //Setting timeframe within which durations of WorkChunks are added up
        LocalDateTime start = LocalDateTime.parse("2019-05-01T10:20:40.577");
        LocalDateTime stop = LocalDateTime.parse("2019-06-30T10:20:40.577");
        Duration duration = subProject.getDuration(start, stop);
        work.setText("GeschÃ¤tzte Arbeit: "+"X" + " Stunden\n" +
                "Bisher geleistete Arbeit: "+duration.toHours()+" Stunden");
    }

    public SubProject getSubProject() {
        return subProject;
    }
}
