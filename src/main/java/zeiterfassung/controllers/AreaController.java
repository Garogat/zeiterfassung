package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import zeiterfassung.models.Area;
import zeiterfassung.reports.AreaContent;
import zeiterfassung.reports.ReportDocument;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

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

    @FXML
    private void createReport() {
        // TODO: get time
        LocalDateTime date = LocalDateTime.now();

        ReportDocument report = new ReportDocument("Stundenzettel", new AreaContent(this.area, date.minusMonths(1), date));
        String html = report.getHtmlNode().getHTMLCode();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Studenzettel speichern");
        File file = fileChooser.showSaveDialog(name.getScene().getWindow());

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(html);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
