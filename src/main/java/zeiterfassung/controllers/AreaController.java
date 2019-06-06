package zeiterfassung.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import zeiterfassung.models.Area;
import zeiterfassung.reports.AreaContent;
import zeiterfassung.reports.ReportDocument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Provides the interface between area View and {@link Area} model
 */
public class AreaController {

    private Area area;

    @FXML
    private TextField name;

    @FXML
    private TextArea description;

    @FXML
    private DatePicker reportMonth;

    /**
     * Initializes the Controller with an Area
     *
     * @param area This Area is handled by the Controller
     */
    public void setArea(Area area) {
        this.area = area;

        // update gui elements
        name.textProperty().bindBidirectional(area.nameProperty());
        description.textProperty().bindBidirectional(area.descriptionProperty());

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
    }


    /**
     * Create a new report
     */
    @FXML
    private void createReport() {
        LocalDateTime date = reportMonth.getValue().atStartOfDay();

        String title = "Stundenzettel " + this.area.getName() + " " + date.format(DateTimeFormatter.ofPattern("MMMM yyyy"));

        ReportDocument report = new ReportDocument(title, new AreaContent(this.area, date.minusMonths(1), date));

    }
}
