package zeiterfassung.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class SubProjectTest {

    private List<String> taskList1 = new ArrayList<>();
    private SubProject subProjectTest1 = new SubProject();

    private SubProject subProjectTest2 = new SubProject();

    /**
     * Test for checking if the name is returned correctly
     */
    @Test
    public void getName() {
        subProjectTest1.setName("Gruenpflege");
        subProjectTest2.setName("Aufraeumen");

        assertEquals("Gruenpflege", subProjectTest1.getName());
        assertEquals("Aufraeumen", subProjectTest2.getName());
    }

    @Test
    public void setName() {
    }

    /**
     * Test for checking if the description is returned correctly
     */
    @Test
    public void getDescription() {
        subProjectTest1.setDescription("Um Gruenkram im Garten kuemmern");
        subProjectTest2.setDescription("nicht toll");

        assertEquals("Um Gruenkram im Garten kuemmern", subProjectTest1.getDescription());
        assertEquals("nicht toll", subProjectTest2.getDescription());
    }

    @Test
    public void setDescription() {
    }

    @Test
    public void getDuration() {
    }

    @Test
    public void getCosts() {
    }

    @Test
    public void addTask() {
    }

    @Test
    public void hasTask() {
    }
}