package zeiterfassung.models;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SubProject extends DescribableModel implements TimeableWork {
    private List<Task> taskList = new ArrayList<>();

    public SubProject() {
        super();
        setName("Neues Unterprojekt");
        setDescription("Dies ist ein Unterprojekt");
    }

    public SubProject(String name, String description) {
        super(name, description);
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

    public void getTasks(Listable<Task> tasks) {
        tasks.getList(taskList);
    }

    /**
     * @throws IllegalArgumentException
     */
    public boolean addTask(Task newTask) {
        return taskList.add(newTask);
    }

    public boolean removeTask(Task task) {
        return taskList.remove(task);
    }

    public boolean hasTask(Task taskToFind) {
        return taskList.contains(taskToFind);
    }
}
