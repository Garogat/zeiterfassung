package zeiterfassung.models;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class AreaTest {

    private List<String> projects1 = new ArrayList<>();
    private Area areaTest1 = new Area();

    private Area areaTest2 = new Area();

    private Area areaTest3 = new Area();


    /**
     * Test for getting the Area name
     */
    @Test
    public void getName() {
        areaTest1.setName("Garten");
        areaTest2.setName("Fachhochschule");
        areaTest3.setName("Privat");

        assertEquals("Garten", areaTest1.getName());
        assertEquals("Fachhochschule", areaTest2.getName());
        assertEquals("Privat", areaTest3.getName());
    }

    @Test
    public void setName() {
    }


    /**
     * Test for getting the Area description
     */
    @Test
    public void getDescription() {
        areaTest1.setDescription("Alles, was mit dem Garten zu tun hat.");
        areaTest2.setDescription("Bereich Studium");
        areaTest3.setDescription("Was zu Hause zu tun ist");

        assertEquals("Alles, was mit dem Garten zu tun hat.", areaTest1.getDescription());
        assertEquals("Bereich Studium", areaTest2.getDescription());
        assertEquals("Was zu Hause zu tun ist", areaTest3.getDescription());
    }

    @Test
    public void setDescription() {
    }

    @Test
    public void addProject() {
    }

    /**
     * Test for getting a list of projects
     */
    @Test
    public void getProject() {
        fillList(projects1, 5, "Hecke schneiden");
        assertEquals(5, projects1.size());
    }

    //filling the projects list for testing
    private void fillList(List<String> projects1, int howMany, String name) {
        for (int i = 0; i < howMany; i++){
            projects1.add(name + i);
        }
    }


    /**
     * Test for checking if an area has a project
     */
    @Test
    public void hasProject() {
    }
}