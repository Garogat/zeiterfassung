package zeiterfassung;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String formatCosts(double costs){
        return costs+"€";
    }

    public static String formatDuration(Duration dur){
        return dur.getSeconds()/3600 + " Std " + dur.getSeconds()%60 + " Min";
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime){
        return String.format(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
