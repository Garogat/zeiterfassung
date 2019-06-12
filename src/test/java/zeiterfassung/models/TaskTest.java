package zeiterfassung.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class TaskTest {
    private TimeRegistrationRoot root = new TimeRegistrationRoot();
    private Area area = new Area();
    private Project project = new Project();
    private SubProject subProject = new SubProject();
    private Task task = new Task();
    private Role role = new Role();
    private WorkChunk workChunk;

    @Before
    public void setUp() {
        root.addArea(area);
        area.addProject(project);
        project.addSubProject(subProject);
        project.addRole(role);
        subProject.addTask(task);
        int duration = 42;
        workChunk = new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(duration), "Testing");

    }

    @Test
    public void testWorkListProperty() {
        assertTrue(task.workListProperty().isEmpty());
        task.addWorkChunk(workChunk);
        assertFalse(task.workListProperty().isEmpty());
    }

    @Test
    public void testEstimatedDurationProperty() {
        Duration duration = Duration.ofHours(12);
        task.setEstimatedDuration(duration);
        assertEquals(duration.toHours(), task.estimatedDurationProperty().get().toHours());
    }

    @Test
    public void testAddWorkChunk() {
        task.addWorkChunk(workChunk);
        assertTrue(task.workListProperty().contains(workChunk));
    }

    @Test
    public void testGetWorkList() {
        assertFalse(task.workListProperty().contains(workChunk));
        task.getWorkList(workChunks -> {
            task.addWorkChunk(workChunk);
        });
        assertTrue(task.workListProperty().contains(workChunk));
    }

    @Test
    public void testGetCosts() {
        int duration = 42;
        WorkChunk workChunk = new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(duration), "Testing");
        task.addWorkChunk(workChunk);
        task.setRole(role);
        assertEquals(duration*role.getHourlyWage(), task.getCosts(LocalDateTime.MIN, LocalDateTime.MAX));
    }

    @Test
    public void testGetDuration() {
        int duration = 42;
        WorkChunk workChunk = new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(duration), "Testing");
        task.addWorkChunk(workChunk);
        assertEquals(duration, task.getDuration(LocalDateTime.MIN, LocalDateTime.MAX).toHours());
    }

    @Test
    public void testGetRole() {
        assertNotSame(task.getRole(), role);
        task.setRole(role);
        assertSame(task.getRole(), role);
    }

    @Test
    public void testSetRole() {
        assertNotSame(task.getRole(), role);
        task.setRole(role);
        assertNotNull(task.getRole());
    }

    @Test
    public void testStart() {
        task.start();
        assertTrue(task.isWorkActive());
    }

    @Test
    public void testStop() {
        assertFalse(task.isWorkActive());
        task.start();
        assertTrue(task.isWorkActive());
        task.stop();
        assertFalse(task.isWorkActive());
    }

    @Test
    public void testGetEstimatedDuration() {
        Duration duration = Duration.ofHours(42);
        task.setEstimatedDuration(duration);
        assertEquals(duration.toHours(), task.getEstimatedDuration().toHours());
    }

    @Test
    public void testSetEstimatedDuration() {
        Duration duration = Duration.ofHours(42);
        task.setEstimatedDuration(duration);
        assertEquals(duration.toHours(), task.getEstimatedDuration().toHours());
    }

    @Test
    public void testIsWorkActive() {
        assertFalse(task.isWorkActive());
        task.setWorkActive(true);
        assertTrue(task.isWorkActive());
    }

    @Test
    public void testSetWorkActive() {
        assertFalse(task.isWorkActive());
        task.setWorkActive(true);
        assertTrue(task.isWorkActive());
        task.setWorkActive(false);
        assertFalse(task.isWorkActive());
    }

    @Test
    public void testWorkActiveProperty() {
        assertFalse(task.workActiveProperty().get());
        task.setWorkActive(true);
        assertTrue(task.workActiveProperty().get());
    }

    @Test
    public void testGetActiveWorkChunk() {
        assertNull(task.getActiveWorkChunk());
        workChunk.setEndTime(null);
        task.addWorkChunk(workChunk);
        assertEquals(workChunk, task.getActiveWorkChunk());
    }
}
