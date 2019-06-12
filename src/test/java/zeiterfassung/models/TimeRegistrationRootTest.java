package zeiterfassung.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.*;

public class TimeRegistrationRootTest {
    private TimeRegistrationRoot timeRegistrationRoot;
    private Area area;

    @Before
    public void setUp(){
        timeRegistrationRoot = new TimeRegistrationRoot();
        area = new Area();
    }


    @Test
    public void testAddArea() {
        assertTrue(timeRegistrationRoot.areaListProperty().isEmpty());
        timeRegistrationRoot.addArea(area);
        assertFalse(timeRegistrationRoot.areaListProperty().isEmpty());
    }

    @Test
    public void testRemoveArea() {
        timeRegistrationRoot.addArea(area);
        assertTrue(timeRegistrationRoot.areaListProperty().contains(area));
        timeRegistrationRoot.removeArea(area);
        assertFalse(timeRegistrationRoot.areaListProperty().contains(area));
    }

    @Test
    public void testHasArea() {
        timeRegistrationRoot.addArea(area);
        assertTrue(timeRegistrationRoot.hasArea(area));
    }


    @Test
    public void testGetAreas() {
        assertFalse(timeRegistrationRoot.hasArea(area));
        timeRegistrationRoot.getAreas(areas -> {
            timeRegistrationRoot.addArea(area);
        });
        assertTrue(timeRegistrationRoot.hasArea(area));
    }

    @Test
    public void testAreaListProperty() {
        assertTrue(timeRegistrationRoot.areaListProperty().isEmpty());
        timeRegistrationRoot.addArea(area);
        assertNotNull(timeRegistrationRoot.areaListProperty());
        assertFalse(timeRegistrationRoot.areaListProperty().isEmpty());

    }

    @Test
    public void testGetActiveTask() {
        Task task = new Task();
        timeRegistrationRoot.setActiveTask(task);
        assertEquals(task, timeRegistrationRoot.getActiveTask());
    }

    @Test
    public void testSetActiveTask() {
        Task task = new Task();
        timeRegistrationRoot.setActiveTask(task);
        assertEquals(task, timeRegistrationRoot.getActiveTask());
    }

    @Test
    public void testIsTaskActive() {
        Task task = new Task();
        timeRegistrationRoot.setActiveTask(task);
        task.setWorkActive(true);
        assertTrue(timeRegistrationRoot.isTaskActive());
        task.setWorkActive(false);
        assertFalse(timeRegistrationRoot.isTaskActive());
    }
}
