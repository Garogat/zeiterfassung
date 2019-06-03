package zeiterfassung.models;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;

public class SubProjectTest {
    private SubProject subProject;
    private Task task;


    @Before
    public void setUp() throws Exception {
        subProject = new SubProject();
        task = new Task();
    }

    @Test
    public void getDuration() throws InterruptedException {
        task.start();
        task.stop();
        subProject.addTask(task);
        assertEquals(true, subProject.getDuration(LocalDateTime.MIN, LocalDateTime.MAX).toNanos()>=0);
    }

    @Test
    public void getEstimatedDuration() {
        task.setEstimatedDuration(Duration.ofHours(22));
        subProject.addTask(task);
        assertEquals(22, subProject.getEstimatedDuration().toHours());
    }

    @Test
    public void getCosts() {
        long wage = 1000000000;
        Role role = new Role("Name", "Beschreibung", wage);
        task.setRole(role);
        task.start();
        long hours = 10;
        task.setEstimatedDuration(Duration.ofHours(hours));
        subProject.addTask(task);
        task.stop();
        assertEquals(true, subProject.getCosts(LocalDateTime.MIN, LocalDateTime.MAX)>=0);
    }

    @Test
    public void getTasks() {

    }

    @Test
    public void addTask() {
        subProject.addTask(task);
        assertEquals(true, subProject.hasTask(task));
    }

    @Test
    public void removeTask() {
        subProject.removeTask(task);
        assertEquals(false, subProject.hasTask(task));
    }

    @Test
    public void hasTask() {
        subProject.addTask(task);
        assertEquals(true, subProject.hasTask(task));
    }

    @Test
    public void taskListProperty() {
        subProject.addTask(task);
        assertEquals(true, subProject.taskListProperty().contains(task));
    }
}
