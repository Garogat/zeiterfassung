package zeiterfassung.models;

import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.MAX;
import static junit.framework.TestCase.assertEquals;

public class ProjectTest {
    Project project;
    SubProject subProject;
    Task task;
    Role role;

    @Before
    public void setUp(){
        project = new Project("BeispielP", "");
        subProject = new SubProject("BeispielSP","");
        task = new Task();
        role = new Role("Bsp", "bsp", 12.0);
    }

    @Test
    public void testGetSubProjects() {
        //@Todo
    }

    @Test
    public void testRoleListProperty(){
        project.addRole(role);
        assertEquals(role, project.roleListProperty().get(project.roleListProperty().indexOf(role)));
    }

    @Test
    public void testAddSubProject(){
        project.addSubProject(subProject);
        assertEquals(true, project.hasSubProject(subProject));
    }

    @Test
    public void testRemoveSubProject(){
        project.removeSubProject(subProject);
        assertEquals(false, project.hasSubProject(subProject));
    }

    @Test
    public void testHasSubProject(){
        project.addSubProject(subProject);
        assertEquals(true, project.hasSubProject(subProject));
    }

    @Test
    public void testGetRoleByIdx(){
        project.addRole(role);
        assertEquals(role, project.getRoleByIdx(1));
    }

    @Test
    public void testGetEstimatedDuration(){
        Duration duration = Duration.ofHours(2);
        task.setEstimatedDuration(duration);
        subProject.addTask(task);
        project.addSubProject(subProject);
        assertEquals(duration.toHours(), project.getEstimatedDuration().toHours());
    }

    @Test
    public void testGetDuration(){
        int duration = 20;
        task.addWorkChunk(new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(duration), "Test"));
        subProject.addTask(task);
        project.addSubProject(subProject);
        assertEquals(duration, project.getDuration(LocalDateTime.MIN, MAX).toHours());
    }

    @Test
    public void testGetCosts(){
        int duration = 20;
        task.addWorkChunk(new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(duration), "Test"));
        subProject.addTask(task);
        project.addSubProject(subProject);
        assertEquals(duration*role.getHourlyWage(), project.getCosts(LocalDateTime.MIN, MAX));
    }
}
