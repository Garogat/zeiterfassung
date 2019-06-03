package zeiterfassung;

import javafx.scene.control.Alert;

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
}
