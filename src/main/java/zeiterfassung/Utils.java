package zeiterfassung;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String formatCosts(double costs) {
        return costs + "€";
    }

    public static String formatDuration(Duration dur) {
        int hours = (int) dur.getSeconds() / 3600;
        int minutes = (int) dur.getSeconds() / 60;
        return hours + " Std " + minutes + " Min";
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return String.format(localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
    }

}
