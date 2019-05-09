package zeiterfassung.models;

import java.time.Duration;
import java.time.LocalDateTime;

public interface TimeableWork {
    Duration getDuration(LocalDateTime start, LocalDateTime stop);

    double getCosts(LocalDateTime start, LocalDateTime stop);
}
