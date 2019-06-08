package zeiterfassung.models;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Base Interface for all models which have duration, costs and estimated costs
 */
public interface TimeableWork {
    Duration getDuration(LocalDateTime start, LocalDateTime stop);

    Duration getEstimatedDuration();

    double getCosts(LocalDateTime start, LocalDateTime stop);
}
