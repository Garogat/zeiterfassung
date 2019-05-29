package zeiterfassung;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String formatCosts(double costs) {
        return costs + "€";
    }

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

    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return String.format(localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
    }

}
