package zeiterfassung.models;

import org.junit.*;

import static junit.framework.TestCase.assertEquals;

public class AreaTest {
    private Area area;
    private Project project;

    @Before
    public void setUp(){
        area = new Area();
        project = new Project("TestProjekt", "Descr");
    }

    @Test
    public void testAddProject(){
        for(int i=0; i<5; i++){
            area.addProject(new Project("Project"+i,""));
        }
        assertEquals(5, area.projectsListProperty().size());
    }

    @Test
    public void testProjectListProperty(){
        area.addProject(project);
        assertEquals(project, area.projectsListProperty().get(area.projectsListProperty().indexOf(project)));
    }

    @Test
    public void testHasProject(){
        area.addProject(project);
        assertEquals(true, area.hasProject(project));
    }

    @Test
    public void testRemoveProject(){
        area.removeProject(project);
        assertEquals(false, area.hasProject(project));
    }
}
