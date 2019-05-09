package zeiterfassung.models;

import org.junit.Test;
import java.time.LocalDateTime;


import static org.junit.Assert.*;

public class WorkChunkTest {
    private WorkChunk chunkTest1  = new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(8) , "Hecke schneiden");
    private WorkChunk chunkTest2 = new WorkChunk(LocalDateTime.now().plusDays(1),LocalDateTime.now().plusDays(1).plusHours(7), "Rasen maehen");

    @Test
    public void getStartTime() {

        assertNotNull(chunkTest1.getStartTime());
//        assertEquals("2019-04-03T10:15:30", chunkTest2.getStartTime());
    }

    @Test
    public void setStartTime() {
        LocalDateTime tmp = LocalDateTime.now();
        chunkTest1.setStartTime(tmp);
        assertTrue(chunkTest1.getStartTime().toString().equals(tmp.toString()));
    }

    @Test
    public void getEndTime() {
        assertNotNull(chunkTest1.getEndTime());
       // assertEquals("...", chunk1.getEndTime());
//        assertEquals("2019-04-03T12:11:45", chunkTest2.getEndTime());
    }

    @Test
    public void setEndTime() {
        LocalDateTime tmp = LocalDateTime.now().plusHours(9);
        chunkTest1.setEndTime(tmp);
        assertTrue(chunkTest1.getEndTime().toString().equals(tmp.toString()));
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
  //      assertEquals("yyyy-MM-ddT00:00:00", chunkTest1.isValidStartTime(chunkTest1.getStartTime());

    }

    @Test
    public void testIsValidEndTime() {


    }

}