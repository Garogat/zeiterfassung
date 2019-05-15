package zeiterfassung.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Task extends DescribableModel implements TimeableWork {
    private List<WorkChunk> workList = new ArrayList<>();

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

    public void getWorkList(Listable<WorkChunk> workList) {
        workList.getList(this.workList);
    }

    @Override
    public double getCosts(LocalDateTime start, LocalDateTime stop) {
        return getRole().getHourlyWage() * ((double) getDuration(start, stop).toMinutes() / 60);
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
