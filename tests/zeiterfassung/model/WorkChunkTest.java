package zeiterfassung.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class WorkChunkTest {
    private WorkChunk chunk1  = new WorkChunk(LocalDateTime.now(), LocalDateTime.now() , "Hecke schneiden");

    @Test
    public void getStartTime() {
        assertEquals(LocalDateTime.now(), chunk1.getStartTime());
    }

    @Test
    public void setStartTime() {
    }

    @Test
    public void getEndTime() {
       // assertEquals("...", chunk1.getEndTime());
    }

    @Test
    public void setEndTime() {
    }

    @Test
    public void getDescription() {
        assertEquals("Hecke schneiden", chunk1.getDescription());
    }

    @Test
    public void setDescription() {
    }

    @Test
    public void getDuration() {
    }

    @Test
    public void testIsValidStartTime() {
    }

    @Test
    public void testIsValidEndTime() {

    }

    @Test
    public void testIsValidDescription() {
        assertTrue(chunk1.isValidDescription(chunk1.getDescription()));
    }
}