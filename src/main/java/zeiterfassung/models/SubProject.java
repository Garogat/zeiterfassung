package zeiterfassung.models;


import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Represents a subproject. A subproject is part of a project and contains tasks.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SubProject extends DescribableModel implements TimeableWork {

    /**
     * A List of different tasks
     */
    @XmlElement(name = "Task")
    private ListProperty<Task> taskList = new SimpleListProperty(FXCollections.observableArrayList());

    public SubProject() {
        super();
        setName("Neues Unterprojekt");
        setDescription("Dies ist ein Unterprojekt");
    }

    public SubProject(String name, String description) {
        super(name, description);
    }

    /**
     * Returns the overall duration from all tasks in an interval
     * @param start Start time of the interval
     * @param stop End time of the interval
     * @return the overall duration
     */
    @Override
    public Duration getDuration(LocalDateTime start, LocalDateTime stop) {
        Duration duration = Duration.ofSeconds(0);
        for (Task task : taskList) {
            duration = duration.plus(task.getDuration(start, stop));
        }
        return duration;
    }

    /**
     * Returns the overall estimated duration from all tasks
     * @return the overall estimated duration
     */
    @Override
    public Duration getEstimatedDuration() {
        Duration duration = Duration.ofSeconds(0);
        for (Task task : taskList) {
            duration = duration.plus(task.getEstimatedDuration());
        }
        return duration;
    }

    /**
     * Returns the overall cost from all tasks in an interval
     * @param start Start time of the interval
     * @param stop End time of the interval
     * @return the overall costs
     */
    @Override
    public double getCosts(LocalDateTime start, LocalDateTime stop) {
        double costs = 0;
        for (Task task : taskList) {
            costs += task.getCosts(start, stop);
        }
        return costs;
    }

    public void getTasks(Listable<Task> tasks) {
        tasks.getList(taskList);
    }

    public boolean addTask(Task newTask) {
        newTask.setParent(this);
        return taskList.add(newTask);
    }

    public boolean removeTask(Task task) {
        return taskList.remove(task);
    }

    public boolean hasTask(Task taskToFind) {
        return taskList.contains(taskToFind);
    }

    public ListProperty<Task> taskListProperty() {
        return taskList;
    }
}
