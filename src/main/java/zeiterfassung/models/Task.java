package zeiterfassung.models;

import java.time.Duration;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.lang.IllegalStateException;

public class Task implements TimeableWork, DescribableContainer {
    private List<WorkChunk> workList;

    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private String workDescription;
    private Role role;
    private String name;
    private String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Money getCosts() {
        BigDecimal payment = getRole().getHourlyWage().getAmount();
        BigDecimal hours = new BigDecimal(getDuration().toHours());
        BigDecimal costs = payment.multiply(hours);
        return new Money(costs);

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

    public Duration getDuration() {
        Duration duration = Duration.ofSeconds(0);
        for (WorkChunk w : workList) {
            duration.plus(w.getDuration());
        }
        return duration;
    }
}
