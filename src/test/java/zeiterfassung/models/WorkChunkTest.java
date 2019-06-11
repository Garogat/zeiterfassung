package zeiterfassung.models;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;


import static org.junit.Assert.*;

public class WorkChunkTest {
    private String description = "Hecke schneiden";
    private LocalDateTime start = LocalDateTime.now();
    private LocalDateTime stop = LocalDateTime.now().plusHours(13);
    private WorkChunk workChunk  = new WorkChunk(start, stop,description);

    @Test
    public void testGetStartTime() {
        assertEquals(start, workChunk.getStartTime());
    }

    @Test
    public void testSetStartTime() {
        LocalDateTime startTime = LocalDateTime.now().plusHours(91);
        workChunk.setStartTime(startTime);
        assertEquals(startTime, workChunk.getStartTime());
    }

    @Test
    public void testGetEndTime() {
        assertEquals(stop, workChunk.getEndTime());
    }

    @Test
    public void testSetEndTime() {
        LocalDateTime endTime = LocalDateTime.now().plusHours(104);
        workChunk.setEndTime(endTime);
        assertEquals(endTime, workChunk.getEndTime());
    }

    @Test
    public void testGetDescription() {
        assertEquals(description, workChunk.getDescription());
    }

    @Test
    public void testSetDescription() {
        String newDescription = "Pfusch aufm Bau";
        workChunk.setDescription(newDescription);
        assertEquals(newDescription, workChunk.getDescription());
    }

    @Test
    public void testGetDuration() {
        int duration = 7;
        start = LocalDateTime.now();
        stop = start.plusHours(duration);
        workChunk.setStartTime(start);
        workChunk.setEndTime(stop);
        assertEquals(workChunk.getDuration().toHours(), duration);
    }

    @Test
    public void testDescriptionProperty() {
        assertEquals(description, workChunk.descriptionProperty().getValue());
    }

    @Test
    public void testStartTimeProperty() {
        assertEquals(start, workChunk.startTimeProperty().getValue());
    }

    @Test
    public void testEndTimeProperty() {
        assertEquals(stop, workChunk.endTimeProperty().getValue());
    }

    @Test
    public void testIsRunning() {
        assertFalse(workChunk.isRunning());
        workChunk.setEndTime(null);
        assertTrue(workChunk.isRunning());
    }
}
