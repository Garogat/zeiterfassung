package zeiterfassung;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import zeiterfassung.models.Project;
import zeiterfassung.reports.ProjectInvoice;
import zeiterfassung.reports.ReportDocument;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility collection of static helpers
 */
public class Utils {

    /**
     * Formatting helper for prices
     *
     * @param costs cost of price to format
     * @return formatted price string
     */
    public static String formatCosts(double costs) {
        return costs + "€";
    }

    /**
     * Formatting helper for {@link Duration}
     *
     * @param dur duration to format
     * @return formatted duration string
     */
    public static String formatDuration(Duration dur) {
        String res = "";
        if (dur.toMinutes() == 0) {
            return "0h 0m";
        }

        int hours = (int) dur.getSeconds() / 3600;
        if (hours > 0) {
            res += hours + "h";
        }

        int minutes = (int) (dur.getSeconds() % 3600) / 60;
        if (minutes > 0) {
            res += " " + minutes + "m";
        }

        return res;
    }

    /**
     * Formatting helper for {@link LocalDateTime} dates
     *
     * @param localDateTime date to format
     * @return formatted date string
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return String.format(localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
    }


    /**
     * Open alert popup for information
     *
     * @param content information text
     * @return alert instance
     */
    public static Alert alertInfo(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ZeitErfassung");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
        return alert;
    }

    /**
     * Open alert popup for a warning
     *
     * @param content warning message
     * @return alert instance
     */
    public static Alert alertWarning(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ZeitErfassung");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        return alert;
    }

    /**
     * Open alert popup for an error / exception
     *
     * @param content error message
     * @return alert instance
     */
    public static Alert alertDanger(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ZeitErfassung");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
        return alert;
    }

    /**
     * Save HTML report to file
     *
     * @param report Report to be saved
     * @param window Window to show file saving dialog on
     */
    public static void createReport(ReportDocument report, Window window) {
        String html = report.getHtmlNode().getHTMLCode();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Studenzettel speichern");
        fileChooser.setInitialFileName(report.getTitle().replace(" ", "-") + ".html");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML Stundenzettel (*.html)", "*.html");
        File file = fileChooser.showSaveDialog(window);

        if (file == null) {
            return;
        }

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(html);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Utils.alertInfo("Der Stundenzettel wurde erfolgreich erstellt");
    }

    /**
     * Create HTML Invoice from project and save to file
     *
     * @param project Project to get times and
     * @param window  Window to show file saving dialog on
     */
    public static void createInvoice(Project project, Window window) {
        ProjectInvoice invoice = new ProjectInvoice(project);
        String html = invoice.getHtmlNode().getHTMLCode();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Rechnung speichern");
        String filename = "Rechnung-" + project.getName().replace(" ", "-") + ".html";
        fileChooser.setInitialFileName(filename);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HTML Rechnung (*.html)", "*.html");
        File file = fileChooser.showSaveDialog(window);

        if (file == null) {
            return;
        }

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(html);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Utils.alertInfo("Die Rechnung wurde erfolgreich erstellt");
    }
}
