package zeiterfassung.models;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.*;
import java.lang.IllegalArgumentException;
import java.time.Duration;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SubProject implements DescribableContainer, TimeableWork {
    private List<Task> taskList = new ArrayList<>();
    private String name;
    private String description;

    public SubProject(String name, String description){
        setName(name);
        setDescription(description);
    }

    public void getTasks(Listable<Task> tasks){
        tasks.getList(taskList);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Duration getDuration(LocalDateTime start, LocalDateTime stop) {
        Duration duration = Duration.ofSeconds(0);
        for (Task task : taskList) {
            duration = duration.plus(task.getDuration(start, stop));
        }
        return duration;
    }

    @Override
    public double getCosts(LocalDateTime start, LocalDateTime stop) {
        double costs = 0;
        for (Task task : taskList) {
            costs += task.getCosts(start, stop);
        }
        return costs;
    }

    /**
     * @throws IllegalArgumentException
     */
    public void addTask(Task newTask) {
            taskList.add(newTask);
    }

    public boolean hasTask(Task taskToFind) {
        return taskList.contains(taskToFind);
    }

    public boolean removeTask(Task task) {
        return taskList.remove(task);
    }

    public SubProject(){
        taskList = new ArrayList<>();
    }


}
