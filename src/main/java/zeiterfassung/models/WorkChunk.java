package zeiterfassung.models;

import java.time.LocalDateTime;
import java.time.Duration;
import java.lang.IllegalStateException;

public class WorkChunk {
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;

    public WorkChunk() {
        setStartTime(null);
        setEndTime(null);
        setDescription(null);
    }

    public WorkChunk(LocalDateTime start, LocalDateTime end, String description) {
        setStartTime(start);
        setEndTime(end);
        setDescription(description);
    }

    public LocalDateTime getStartTime() {
        return start;
    }

    public void setStartTime(LocalDateTime time) {
        start = time;
    }

    public LocalDateTime getEndTime() {
        return end;
    }

    public void setEndTime(LocalDateTime time) {
        end = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }
}
