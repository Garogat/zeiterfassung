package zeiterfassung.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import zeiterfassung.xml.DurationAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.Duration;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Task extends DescribableModel implements TimeableWork {
    @XmlElement(name = "WorkChunk")
    private ObservableList<WorkChunk> workList = FXCollections.observableArrayList();

    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private String workDescription;
    private String roleId;



    private ObjectProperty<Duration> estimatedDuration = new SimpleObjectProperty<>(Duration.ZERO);


    public Task() {
        super();
        setName("Neue Aufgabe");
        setDescription("Dies ist eine Aufgabe");
    }

    public Task(String name, String description) {
        super(name, description);
    }

    public Task(String name, String description, String workDescription, Role role) {
        setName(name);
        setDescription(description);
        setWorkDescription(workDescription);
        setRole(role);
    }

    public ObservableList<WorkChunk> workListProperty() {
        return workList;
    }

    public ObjectProperty<Duration> estimatedDurationProperty(){
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
        Project tmpProject = (Project)getParentByType(Project.class);
        if (tmpProject != null){
            return tmpProject.getRole(roleId);
        }

        return null;
    }

    public void setRole(Role role) {
        this.roleId = role.getId();
    }

    public boolean hasWorkStarted() {
        return workStartTime != null;
    }

    public boolean hasWorkEnded() {
        return workEndTime != null;
    }

    public void setWorkDescription(String description) {
        this.workDescription = description;
    }

    public String getWorkDescription() {
        return workDescription;
    }

    /**
     * throws IllegalStateException
     */
    public void start() {
        if (hasWorkStarted()) {
            throw new IllegalStateException("Task already started");
        } else {
            workStartTime = LocalDateTime.now();
        }
    }

    /**
     * throws IllegalStateException
     */
    public void stop() {
        if (!hasWorkStarted()) {
            throw new IllegalStateException("Task can't be stopped, without being started");
        }
        if (hasWorkEnded()) {
            throw new IllegalStateException("Task already ended");
        }
        workEndTime = LocalDateTime.now();
        WorkChunk newWork = new WorkChunk(workStartTime, workEndTime, workDescription);
        workList.add(newWork);
    }

    @XmlJavaTypeAdapter(DurationAdapter.class)
    public Duration getEstimatedDuration() {
        return estimatedDuration.get();
    }

    public void setEstimatedDuration(Duration estimatedTime) {
        this.estimatedDuration.setValue(estimatedTime);
    }
}
