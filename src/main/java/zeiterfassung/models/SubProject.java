package zeiterfassung.models;

import java.util.ArrayList;
import java.util.List;
import java.lang.IllegalArgumentException;
import java.time.Duration;
import java.math.BigDecimal;

public class SubProject implements DescribableContainer, TimeableWork {
    private List<Task> taskList;
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Duration getDuration() {
        Duration duration = Duration.ofSeconds(0);
        for (Task task : taskList) {
            duration.plus(task.getDuration());
        }
        return duration;
    }

    public Money getCosts() {
        BigDecimal costs = new BigDecimal(0);
        for (Task task : taskList) {
            costs = costs.add(task.getCosts().getAmount());
        }
        return new Money(costs);
    }

    /**
     * @throws IllegalArgumentException
     */
    public void addTask(Task newTask) {
        if (hasTask(newTask.getName())) {
            String exceptionExplanation;
            exceptionExplanation = "Project " + getName()
                    + "already has a Task named " + newTask.getName();
            throw new IllegalArgumentException(exceptionExplanation);
        }
        taskList.add(newTask);
    }

    public boolean hasTask(String taskToFind) {
        for (Task task : taskList) {
            if (taskToFind.equals(task.getName())) {
                return true;
            }
        }
        return false;
    }

    public Task getTask(String taskToFind) {
        for (Task task : taskList) {
            if (taskToFind.equals(task.getName())) {
                return task;
            }
        }
        return null;
    }

    public boolean removeTask(String taskName) {
        Task task = getTask(taskName);
        if (task == null) return false;
        return taskList.remove(task);
    }

}
