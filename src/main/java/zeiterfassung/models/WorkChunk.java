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
    private LocalDateTime start;
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime end;

    private StringProperty description = new SimpleStringProperty();;

    public WorkChunk() {
        setStartTime(LocalDateTime.now());
        setEndTime(LocalDateTime.now());
        setDescription("Default description");
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
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }

    public StringProperty descriptionProperty(){
        return description;
    }
}
