package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import zeiterfassung.models.Area;

public class AreaController {

    private Area area;

    @FXML
    TextField name;

    @FXML
    TextArea description;

    public void setArea(Area area) {
        this.area = area;

        // update gui elements

        name.textProperty().bindBidirectional(area.nameProperty());
        description.textProperty().bindBidirectional(area.descriptionProperty());
    }
}
