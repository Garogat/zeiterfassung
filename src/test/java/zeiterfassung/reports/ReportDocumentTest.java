package zeiterfassung.reports;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DayDV;
import zeiterfassung.models.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ReportDocumentTest {

    Task myTask;

    SubProject mySubproject;

    Project myProject;

    Area myArea;

    @org.junit.Before
    public void setUp() throws Exception {

        Role myRole = new Role();
        myRole.setHourlyWage(9.50);

        myTask = new Task();
        myTask.setName("My Task");
        myTask.setRole(myRole);

        myTask.getWorkList(list -> {
            list.add(new WorkChunk(LocalDateTime.now(),
                    LocalDateTime.now().plusHours(8),
                    "Den ganzen Tag gearbeitet!")
            );
            list.add(new WorkChunk(LocalDateTime.now().plusDays(2),
                    LocalDateTime.now().plusDays(2).plusHours(8),
                    "Den ganzen Tag nochmal gearbeitet!")
            );
        });

        mySubproject = new SubProject();
        mySubproject.setName("My SubProject");

        mySubproject.addTask(myTask);
        mySubproject.addTask(myTask);

        myProject = new Project();
        myProject.setName("my Project");
        myProject.addTask(myTask);
        myProject.addTask(myTask);
        myProject.addSubProject(mySubproject);
        myProject.addSubProject(mySubproject);

        myArea = new Area();
        myArea.setName("My Area");
        myArea.addProject(myProject);
        myArea.addProject(myProject);


    }

    @org.junit.Test
    public void testTask() {
        ReportDocument test = new ReportDocument("Test Task", new TaskContent(myTask), new TaskContent(myTask));

        String html = test.getHtmlNode().getHTMLCode();
        assertTrue(html.charAt(0) == '<');
    }

    @org.junit.Test
    public void testSubProject() {
        ReportDocument test = new ReportDocument("Test Subproject", new SubProjectContent(mySubproject), new SubProjectContent(mySubproject));

        String html = test.getHtmlNode().getHTMLCode();
        assertTrue(html.charAt(0) == '<');
    }

    @org.junit.Test
    public void testProject() {
        ReportDocument test = new ReportDocument("Test Project", new ProjectContent(myProject));

        String html = test.getHtmlNode().getHTMLCode();
        assertTrue(html.charAt(0) == '<');
    }


    @org.junit.Test
    public void testArea() {
        ReportDocument test = new ReportDocument("Test Area", new AreaContent(myArea));

        String html = test.getHtmlNode().getHTMLCode();
        assertTrue(html.charAt(0) == '<');
    }
}