package zeiterfassung.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class SubProjectTest {
    private SubProject subProject;
    private Task task;


    @Before
    public void setUp() throws Exception {
        subProject = new SubProject();
        task = new Task();
    }

    @Test
    public void testGetDuration() throws InterruptedException {
        int duration = 42;
        WorkChunk workChunk = new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(duration), "Testing");
        task.addWorkChunk(workChunk);
        subProject.addTask(task);
        assertEquals(duration, subProject.getDuration(LocalDateTime.MIN, LocalDateTime.MAX).toHours());
    }

    @Test
    public void testGetEstimatedDuration() {
        int durationLength = 42;
        task.setEstimatedDuration(Duration.ofHours(durationLength));
        subProject.addTask(task);
        assertEquals(durationLength, subProject.getEstimatedDuration().toHours());
    }

    @Test
    public void testGetCosts() {
        int duration = 42;
        WorkChunk workChunk = new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(duration), "Testing");
        long wage = 15;
        Role role = new Role("Name", "Beschreibung", wage);
        task.setRole(role);
        task.addWorkChunk(workChunk);
        long hours = 10;
        task.setEstimatedDuration(Duration.ofHours(hours));
        subProject.addTask(task);
        assertEquals((double)duration*wage, subProject.getCosts(LocalDateTime.MIN, LocalDateTime.MAX));
    }

    @Test
    public void testGetTasks() {
        //@Todo
        Assert.fail();
    }

    @Test
    public void testAddTask() {
        subProject.addTask(task);
        assertTrue(subProject.hasTask(task));
    }

    @Test
    public void testRemoveTask() {
        subProject.removeTask(task);
        assertFalse(subProject.hasTask(task));
    }

    @Test
    public void testHasTask() {
        subProject.addTask(task);
        assertTrue(subProject.hasTask(task));
    }

    @Test
    public void testTaskListProperty() {
        subProject.addTask(task);
        assertTrue(subProject.taskListProperty().contains(task));
    }
}
