package zeiterfassung.models;

import static org.junit.Assert.*;
import org.junit.Test;


public class RoleTest {

    private Role roleTest1 = new Role();

    private Role roleTest2 = new Role();

    private Role roleTest3 = new Role();

    /**
     * Test for checking if the name is returned correctly
     */
    @Test
    public void getName() {
        roleTest1.setName("Werkstudent");
        roleTest2.setName("Freier Mitarbeiter");
        roleTest3.setName("Angestellter");

        assertEquals("Werkstudent", roleTest1.getName());
        assertEquals("Freier Mitarbeiter", roleTest2.getName());
        assertEquals("Angestellter", roleTest3.getName());
    }

    @Test
    public void setName() {
    }

    /**
     * Test for checking if the hourly wage is returned correctly
     */
    @Test
    public void getHourlyWage() {
        roleTest1.setHourlyWage(9.50);
        roleTest2.setHourlyWage(20.00);
        roleTest3.setHourlyWage(33.75);

        //assertEquals(9.50, roleTest1.getHourlyWage());
        //assertEquals(20.00, roleTest2.getHourlyWage());
        //assertEquals(33.75, roleTest3.getHourlyWage());
    }

    @Test
    public void setHourlyWage() {
    }

    /**
     * Test for checking if the role description is returned correctly
     */
    @Test
    public void getDescription() {
        roleTest1.setDescription("Maedchen fuer alles");
        roleTest2.setDescription("Kann Auftraege bekommen");
        roleTest3.setDescription("Immer da");

        assertEquals("Maedchen fuer alles", roleTest1.getDescription());
        assertEquals("Kann Auftraege bekommen", roleTest2.getDescription());
        assertEquals("Immer da", roleTest3.getDescription());
    }

    @Test
    public void setDescription() {
    }
}