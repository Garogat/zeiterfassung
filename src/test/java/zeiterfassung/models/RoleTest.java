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
    public void testGetName() {
        role.setName("Werkstudent");
        assertEquals("Werkstudent", role.getName());
    }

    @Test
    public void testSetName() {
        String expectedResult = "Student";
        role.setName("Student");
        assertEquals(expectedResult, role.getName());
    }

    @Test
    public void testGetHourlyWage() {
        double wage = 20;
        role.setHourlyWage(wage);
        assertEquals(wage, role.getHourlyWage());
    }

    @Test
    public void testSetHourlyWage() {
        double wage = 42;
        role.setHourlyWage(wage);
        assertEquals(wage, role.getHourlyWage());
    }


    /**
     * Test for checking if the role description is returned correctly
     */
    @Test
    public void testGetDescription() {
        String expectedResult = "Maedchen fuer alles";
        role.setDescription("Maedchen fuer alles");
        assertEquals(expectedResult, role.getDescription());
    }

    @Test
    public void testSetDescription() {
        String expectedResult = "Maedchen fuer alles";
        role.setDescription("Maedchen fuer alles");
        assertEquals(expectedResult, role.getDescription());
    }
}
