package zeiterfassung.models;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.lang.IllegalStateException;
import java.util.Observable;


public class Task extends Observable implements TimeableWork, DescribableContainer {
    private List<WorkChunk> workList;

    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private String workDescription;
    private Role role;
    private String name;
    private String description;

    public void getWorkList(Listable<WorkChunk> workList){
        workList.getList(this.workList);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
        setChanged();
        notifyObservers();
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
        setChanged();
        notifyObservers();
    }

    @Override
    public double getCosts(LocalDateTime start, LocalDateTime stop) {
        return getRole().getHourlyWage() *  ((double)getDuration(start, stop).toMinutes()/60);
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
        setChanged();
        notifyObservers();
    }


    public boolean hasWorkStarted() {
        return workStartTime != null;
    }

    public boolean hasWorkEnded() {
        return workEndTime != null;
    }

    public void setWorkDescription(String description) {
        this.workDescription = description;
        setChanged();
        notifyObservers();
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
        setChanged();
        notifyObservers();
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
        setChanged();
        notifyObservers();
    }

    public Task(){
        workList = new ArrayList<>();
    }


}
