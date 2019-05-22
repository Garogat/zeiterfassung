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
import java.time.LocalDateTime;
import java.time.Duration;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class WorkChunk {

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private ObjectProperty<LocalDateTime> startTime = new SimpleObjectProperty<>();

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private ObjectProperty<LocalDateTime> endTime = new SimpleObjectProperty<>();

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
        return startTime.getValue();
    }

    public void setStartTime(LocalDateTime time) {
        startTime.set(time);
    }

    public LocalDateTime getEndTime() {
        return endTime.get();
    }

    public void setEndTime(LocalDateTime time) {
        endTime.set(time);
    }

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

    public StringProperty descriptionProperty(){
        return description;
    }

    public ObjectProperty<LocalDateTime> startTimeProperty() {
        return startTime;
    }

    public ObjectProperty<LocalDateTime> endTimeProperty() {
        return endTime;
    }
}
