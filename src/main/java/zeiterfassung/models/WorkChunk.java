package zeiterfassung.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import zeiterfassung.xml.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Represents a workchunk. A workchunk is a measured timespan the user has worked on a task
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class WorkChunk extends BaseModel {
    /**
     * Start time of timespan
     */
    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();

    /**
     * Stop time of timespan
     */
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();

    /**
     * A description to this workchunk
     */
    private StringProperty description = new SimpleStringProperty();

    public WorkChunk() {
        super();
        setStartTime(LocalDateTime.now());
        setEndTime(null);
        setDescription("");
    }

    public WorkChunk(LocalDateTime start, LocalDateTime end, String description) {
        super();
        setStartTime(start);
        setEndTime(end);
        setDescription(description);
    }

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlElement(name = "start")
    public LocalDateTime getStartTime() {
        return startTime.getValue();
    }

    public void setStartTime(LocalDateTime time) {
        startTime.set(time);
    }

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    @XmlElement(name = "end")
    public LocalDateTime getEndTime() {
        return endTime.get();
    }

    public void setEndTime(LocalDateTime time) {
        endTime.set(time);
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Duration getDuration() {
        if (startTime.get() != null && endTime.get() != null) {
            return Duration.between(startTime.get(), endTime.get());
        }
        return Duration.ZERO;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return startTime;
    }

    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return endTime;
    }

    public boolean isRunning() {
        return startTime.get() != null && endTime.get() == null;
    }
}
