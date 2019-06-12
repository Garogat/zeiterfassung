package zeiterfassung.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.MAX;
import static junit.framework.TestCase.*;

public class ProjectTest {
    private Project project;
    private SubProject subProject;
    private Task task;
    private Role role;

    @Before
    public void setUp(){
        project = new Project("BeispielP", "");
        subProject = new SubProject("BeispielSP","");
        task = new Task();
        role = new Role("Bsp", "bsp", 12.0);
    }

    @Test
    public void testGetSubProjects() {
        assertFalse(project.hasSubProject(subProject));
        project.getSubProjects(subProjects -> {
            project.addSubProject(subProject);
        });
        assertTrue(project.hasSubProject(subProject));
    }

    @Test
    public void testRoleListProperty(){
        project.addRole(role);
        assertEquals(role, project.roleListProperty().get(project.roleListProperty().indexOf(role)));
    }

    @Test
    public void testAddSubProject(){
        project.addSubProject(subProject);
        assertTrue(project.hasSubProject(subProject));
    }

    @Test
    public void testRemoveSubProject(){
        project.removeSubProject(subProject);
        assertFalse(project.hasSubProject(subProject));
    }

    @Test
    public void testHasSubProject(){
        project.addSubProject(subProject);
        assertTrue(project.hasSubProject(subProject));
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
        WorkChunk workChunk = new WorkChunk(LocalDateTime.now(), LocalDateTime.now().plusHours(duration),"Test");
        task.addWorkChunk(workChunk);
        subProject.addTask(task);
        project.addSubProject(subProject);
        project.addRole(role);
        task.setRole(role);
        assertEquals(duration*role.getHourlyWage(), project.getCosts(LocalDateTime.MIN, MAX));
    }


    @Test
    public void testSubProjectListProperty() {
        project.addSubProject(subProject);
        assertTrue(project.subProjectListProperty().contains(subProject));
    }

    @Test
    public void testGetRoles() {
        assertFalse(project.hasRole(role));
        project.getRoles(roles -> {
            project.addRole(role);
        });
        assertTrue(project.hasRole(role));
    }

    @Test
    public void testGetRolesSize() {
        int times = 4;
        for(int i=0; i<times; i++){
            project.addRole(role);
        }
        assertEquals(project.getRolesSize(), times+1);//Times+1 due to already existing default role
    }

    @Test
    public void testGetDefaultRole() {
        assertNotNull(project.getDefaultRole());
    }

    @Test
    public void testAddRole() {
        assertFalse(project.hasRole(role));
        project.addRole(role);
        assertTrue(project.hasRole(role));
    }

    @Test
    public void testRemoveRole() {
        project.addRole(role);
        assertTrue(project.hasRole(role));
        project.removeRole(role);
        assertFalse(project.hasRole(role));
    }

    @Test
    public void testHasRole() {
        project.addRole(role);
        assertTrue(project.hasRole(role));
    }

    @Test
    public void testGetCustomer() {
        String customer = "FH Lübeck\nSokratesplatz 42\n24500 Lübeck";
        project.setCustomer(customer);
        assertEquals(customer, project.getCustomer());
    }

    @Test
    public void testSetCustomer() {
        String customer = "FH Kiel\nSokratesplatz 1\n24149 Kiel";
        project.setCustomer(customer);
        assertEquals(customer, project.getCustomer());
    }

    @Test
    public void testCustomerProperty() {
        String customer = "FH Kiel\nSokratesplatz 1\n24149 Kiel";
        project.setCustomer(customer);
        assertEquals(customer, project.customerProperty().getValue());
    }

    @Test
    public void testGetAllTasks() {
        ArrayList<Task> myList;
        project.addTask(task);
        myList = project.getAllTasks();
        assertTrue(myList.contains(task));
    }
}
