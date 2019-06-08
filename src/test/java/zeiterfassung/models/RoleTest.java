package zeiterfassung.models;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RoleTest {

    private Role role;


    @Before
    public void setUp() {
        role = new Role();
    }

    /**
     * Test for checking if the name is returned correctly
     */
    @Test
    public void getName() {
        role.setName("Werkstudent");

        assertEquals("Werkstudent", role.getName());
    }

    @Test
    public void setName() {
    }

    @Test
    public void getHourlyWage() {
        double wage = 20;
        role.setHourlyWage(wage);
        assertEquals(wage, role.getHourlyWage());
    }

    @Test
    public void setHourlyWage() {
        double wage = 42;
        role.setHourlyWage(wage);
        assertEquals(wage, role.getHourlyWage());
    }


    /**
     * Test for checking if the role description is returned correctly
     */
    @Test
    public void getDescription() {
        role.setDescription("Maedchen fuer alles");

        assertEquals("Maedchen fuer alles", role.getDescription());
    }

    @Test
    public void setDescription() {
    }
}
