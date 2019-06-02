package zeiterfassung.models;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RoleTest {
    private Role role;

    @Before
    public void setUp(){
        role = new Role();
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

    @Test
    public void getId() {
        String id = "100132";
        role.setId(id);
        assertEquals(id, role.getId());
    }

    @Test
    public void setId() {
        String id = "431014";
        role.setId(id);
        assertEquals(id, role.getId());
    }
}
