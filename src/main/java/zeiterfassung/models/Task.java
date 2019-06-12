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

/**
 * Represents a task. A task belongs to a project or a subproject and contains workchunks
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement
public class Task extends DescribableModel implements TimeableWork {
    /**
     * A list of workchunks to count the time the user is working on a task
     */
    @XmlElement(name = "WorkChunk")
    private ObservableList<WorkChunk> workList = FXCollections.observableArrayList();

    private BooleanProperty workActive = new SimpleBooleanProperty(false);

    @XmlElement(name = "Role")
    @XmlIDREF
    private Role role;

    /**
     * The user can set a estimated time for the task to control his progress
     */
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
            if (w.getStartTime().isAfter(start) && w.getStartTime().isBefore(stop)) {
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
     * Starts a workchunk to measure the wroking hours
     * throws IllegalStateException
     */
    public void start() {
        if (getActiveWorkChunk() != null) {
            throw new IllegalStateException("Task already started");
        }

        TimeRegistrationRoot root = (TimeRegistrationRoot) getParentByType(TimeRegistrationRoot.class);
        if (root.isTaskActive()) {
            throw new IllegalStateException("An other task is already running");
        }

        root.setActiveTask(this);
        workActive.setValue(true);
        WorkChunk chunk = new WorkChunk();
        chunk.setStartTime(LocalDateTime.now());
        chunk.setParent(this);
        addWorkChunk(chunk);
    }

    /**
     * Stops measuring the wroking hours
     * throws IllegalStateException
     */
    public void stop() {
        if (getActiveWorkChunk() == null) {
            throw new IllegalStateException("Task can't be stopped, without being started");
        }

        TimeRegistrationRoot root = (TimeRegistrationRoot) getParentByType(TimeRegistrationRoot.class);
        root.setActiveTask(null);

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

    /**
     * Checks, if this task contains the open workchunk
     * @return
     */
    @XmlElement(name = "active")
    public boolean isWorkActive() {
        return workActive.get();
    }

    public void setWorkActive(boolean active) {
        workActiveProperty().set(active);
    }

    public BooleanProperty workActiveProperty() {
        return workActive;
    }

    /**
     * Returns the running work chunk
     * @return the running work chunk or null if no work chunk is running
     */
    public WorkChunk getActiveWorkChunk() {
        for (WorkChunk chunk : this.workList) {
            if (chunk.isRunning()) {
                return chunk;
            }
        }
        return null;
    }
}
