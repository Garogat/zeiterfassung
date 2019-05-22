package zeiterfassung.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Task extends DescribableModel implements TimeableWork {
    @XmlElement(name = "WorkChunk")
    //private List<WorkChunk> workList = new ArrayList<>();
    private ObservableList<WorkChunk> workList = FXCollections.observableArrayList();

    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private String workDescription;
    private Role role;



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
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
}
