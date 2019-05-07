package zeiterfassung.model;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class WorkChunkTest {
    private WorkChunk chunkTest1  = new WorkChunk(LocalDateTime.now(), LocalDateTime.now() , "Hecke schneiden");
    private WorkChunk chunkTest2 = new WorkChunk(2019-04-03T10:15:30, 12:11:45, "Rasen maehen");

    @Test
    public void getStartTime() {

        assertEquals(LocalDateTime.now(), chunkTest1.getStartTime());
        assertEquals(2019-04-03T10:15:30, chunkTest2.getStartTime());
    }

    @Test
    public void setStartTime() {
    }

    @Test
    public void getEndTime() {
       // assertEquals("...", chunk1.getEndTime());
        assertEquals(2019-04-03T12:11:45, chunkTest2.getEndTime());
    }

    @Test
    public void setEndTime() {
    }

    @Test
    public void getDescription() {

        assertEquals("Hecke schneiden", chunkTest1.getDescription());
        assertEquals("Rasen maehen", chunkTest2.getDescription());
    }

    @Test
    public void setDescription() {
    }

    @Test
    public void getDuration() {
    }

    @Test
    public void testIsValidStartTime() {
        assertEquals(yyyy-MM-ddT00:00:00, chunkTest1.isValidStartTime(chunkTest1.getStartTime());

    }

    @Test
    public void testIsValidEndTime() {


    }

    @Test
    public void testIsValidDescription() {

        assertTrue(chunkTest1.isValidDescription(chunkTest1.getDescription()));
        assertTrue(chunkTest2.isValidDescription(chunkTest2.getDescription()));
    }
}