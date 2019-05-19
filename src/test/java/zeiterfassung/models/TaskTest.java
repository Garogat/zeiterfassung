package zeiterfassung.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class TaskTest {

    private Task taskTest1 = new Task();

    private Task taskTest2 = new Task();

    /**
     * Test for checking if the name is returned correctly
     */
    @Test
    public void getName() {
        taskTest1.setName("Hausaufgaben");
        taskTest2.setName("Putzen");

        assertEquals("Hausaufgaben", taskTest1.getName());
        assertEquals("Putzen", taskTest2.getName());
    }

    @Test
    public void setName() {
    }

    /**
     * Test for checking if the role is returned correctly
     */
    @Test
    public void getRole() {
    }

    @Test
    public void setRole() {
    }

    /**
     * Test for checking if the description is returned correctly
     */
    @Test
    public void getDescription() {
        taskTest1.setDescription("Zu erledigen fuer FH");
        taskTest2.setDescription("");

        assertEquals("Zu erledigen fuer FH", taskTest1.getDescription());
        assertEquals("", taskTest2.getDescription());
    }

    @Test
    public void setDescription() {
    }

    @Test
    public void hasWorkStarted() {
    }

    @Test
    public void hasWorkEnded() {
    }
}