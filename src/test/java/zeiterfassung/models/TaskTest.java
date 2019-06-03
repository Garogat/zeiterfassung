package zeiterfassung.models;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static junit.framework.TestCase.assertEquals;

public class TaskTest {
    private Project project = new Project();
    private Task task = new Task();
    private Role role = new Role();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void workListProperty() {
        task.start();
        task.stop();
        assertEquals(false, task.workListProperty().isEmpty());
    }

    @Test
    public void estimatedDurationProperty() {
        Duration duration = Duration.ofHours(12);
        task.setEstimatedDuration(duration);
        assertEquals(duration.toHours(), task.estimatedDurationProperty().get().toHours());
    }

    @Test
    public void addWorkChunk() {
        assertEquals(true, task.workListProperty().isEmpty());
        task.start();
        task.stop();
        assertEquals(false, task.workListProperty().isEmpty());
    }

    @Test
    public void getWorkList() {
    }

    @Test
    public void getCosts() {
    }

    @Test
    public void getDuration() {
    }

    @Test
    public void getRole() {
        project.addRole(role);
        project.addTask(task);
        assertEquals(false, task.getRole()==role);
        task.setRole(role);
        assertEquals(true, task.getRole()==role);
    }

    @Test
    public void setRole() {
        project.addRole(role);
        project.addTask(task);
        assertEquals(false, task.getRole()==role);
        task.setRole(role);
        assertEquals(true, task.getRole()!=null);
    }

    @Test
    public void hasWorkStarted() {
        assertEquals(false, task.hasWorkEnded());
        task.start();
        assertEquals(true, task.hasWorkStarted());
    }

    @Test
    public void hasWorkEnded() {
        task.start();
        assertEquals(false, task.hasWorkEnded());
        task.stop();
        assertEquals(true, task.hasWorkEnded());
    }

    @Test
    public void setWorkDescription() {
        String description = "Beschreibung1";
        task.setWorkDescription(description);
        assertEquals(description, task.getWorkDescription());
    }

    @Test
    public void getWorkDescription() {
        String description = "Beschreibung42";
        task.setWorkDescription(description);
        assertEquals(description, task.getWorkDescription());
    }

    @Test
    public void start() {
        task.start();
        assertEquals(true, task.hasWorkStarted());
    }

    @Test
    public void stop() {
        assertEquals(false, task.hasWorkStarted());
        task.start();
        assertEquals(true, task.hasWorkStarted());
        task.stop();
        assertEquals(true, task.hasWorkEnded());
    }

    @Test
    public void getEstimatedDuration() {
        Duration duration = Duration.ofHours(42);
        task.setEstimatedDuration(duration);
        assertEquals(duration.toHours(), task.getEstimatedDuration().toHours());
    }

    @Test
    public void setEstimatedDuration() {
        Duration duration = Duration.ofHours(42);
        task.setEstimatedDuration(duration);
        assertEquals(duration.toHours(), task.getEstimatedDuration().toHours());
    }
}