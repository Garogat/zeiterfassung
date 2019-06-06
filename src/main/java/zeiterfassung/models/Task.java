package zeiterfassung.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zeiterfassung.xml.DurationAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Task extends DescribableModel implements TimeableWork {
    @XmlElement(name = "WorkChunk")
    private ObservableList<WorkChunk> workList = FXCollections.observableArrayList();

    @XmlTransient
    private BooleanProperty workActive = new SimpleBooleanProperty(false);

    @XmlIDREF
    private Role role;

    @XmlTransient
    private ObjectProperty<Duration> estimatedDuration = new SimpleObjectProperty<>(Duration.ZERO);

    public Task() {
        super();
        setName("Neue Aufgabe");
        setDescription("Dies ist eine Aufgabe");
    }

    public Task(String name, String description) {
        super(name, description);
    }

    public Task(String name, String description, Role role) {
        super();
        setName(name);
        setDescription(description);
        setRole(role);
    }

    public ObservableList<WorkChunk> workListProperty() {
        return workList;
    }

    public ObjectProperty<Duration> estimatedDurationProperty() {
        return estimatedDuration;

    }

    public void addWorkChunk(WorkChunk workChunk) {
        workList.add(workChunk);
    }

    public void getWorkList(Listable<WorkChunk> workList) {
        workList.getList(this.workList);
    }

    @Override
    public double getCosts(LocalDateTime start, LocalDateTime stop) {
        if (getRole() != null) {
            return getRole().getHourlyWage() * ((double) getDuration(start, stop).toMinutes() / 60);
        }
        return 0;
    }

    @Override
    public Duration getDuration(LocalDateTime start, LocalDateTime stop) {
        Duration duration = Duration.ofSeconds(0);
        for (WorkChunk w : workList) {
            if (w.getStartTime().compareTo(start) >= 0 && w.getStartTime().compareTo(stop) < 0) {
                duration = duration.plus(w.getDuration());
            }
        }
        return duration;
    }

    public Role getRole() {
        // return default role if none is set
        if (this.role == null) {
            Project project = (Project) this.getParentByType(Project.class);
            return project.getDefaultRole();
        }

        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * throws IllegalStateException
     */
    public void start() {
        if (getActiveWorkChunk() != null) {
            throw new IllegalStateException("Task already started");
        }
        workActive.set(true);
        WorkChunk chunk = new WorkChunk();
        chunk.setStartTime(LocalDateTime.now());
        chunk.setParent(this);
        addWorkChunk(chunk);
    }

    /**
     * throws IllegalStateException
     */
    public void stop() {
        if (getActiveWorkChunk() == null) {
            throw new IllegalStateException("Task can't be stopped, without being started");
        }
        workActive.set(false);
        getActiveWorkChunk().setEndTime(LocalDateTime.now());
    }

    @XmlJavaTypeAdapter(DurationAdapter.class)
    public Duration getEstimatedDuration() {
        return estimatedDuration.get();
    }

    public void setEstimatedDuration(Duration estimatedTime) {
        this.estimatedDuration.setValue(estimatedTime);
    }

    @XmlElement(name = "active")
    public boolean isWorkActive() {
        return workActive.get();
    }

    public BooleanProperty workActiveProperty() {
        return workActive;
    }

    public WorkChunk getActiveWorkChunk() {
        for (WorkChunk chunk : this.workList) {
            if (chunk.isRunning()) {
                return chunk;
            }
        }
        return null;
    }
}
