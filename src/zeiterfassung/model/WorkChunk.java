package zeiterfassung.model;

import java.time.LocalDateTime;
import java.time.Duration;
import java.lang.IllegalStateException;

public class WorkChunk {
    private LocalDateTime start;
    private LocalDateTime end;
    private String description;


    /**
     * Constructor
     */
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

    /**
     * @return start time of the work chunk
     */
    public LocalDateTime getStartTime() { return start; }

    /**
     * @param time set start time of the work chunk
     */
    public void setStartTime(LocalDateTime time) {
	    start = time;
    }

    /**
     * @return end time of the work chunk
     */
    public LocalDateTime getEndTime() {
	    return end;
    }

    /**
     * @param time set end time of the work chunk
     */
    public void setEndTime(LocalDateTime time) {
	    end = time;
    }

    /**
     * @return description of the work chunk
     */
    public String getDescription() {
	    return description;
    }

    /**
     * @param description set description of the work chunk
     */
    public void setDescription(String description) {
	    this.description = description;
    }

    /**
     * @return total duration of the work chunk
     */
    public Duration getDuration() {
        return Duration.between(start, end);
    }

    /**
     * Method that tests if the input description is valid
     */
    public boolean isValidDescription(String description) {
        if (description.isEmpty()) {
            return false;
        }
        if (description.equals(null)) {
            return false;
        }
        else {
            return true;
        }

    }
}
