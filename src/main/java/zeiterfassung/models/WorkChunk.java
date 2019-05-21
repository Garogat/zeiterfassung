package zeiterfassung.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import zeiterfassung.xml.LocalDateTimeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.time.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class WorkChunk {

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startTime;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endTime;

    private StringProperty description = new SimpleStringProperty();;

    public WorkChunk() {
        setStartTime(LocalDateTime.now());
        setEndTime(null);
        setDescription("");
    }

    public WorkChunk(LocalDateTime start, LocalDateTime end, String description) {
        setStartTime(start);
        setEndTime(end);
        setDescription(description);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime time) {
        startTime = time;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime time) {
        endTime = time;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    public StringProperty descriptionProperty(){
        return description;
    }
}
