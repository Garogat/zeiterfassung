package zeiterfassung.reports;

import zeiterfassung.models.Role;
import zeiterfassung.models.SubProject;
import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ReportDocumentTest {

    Task myTask;

    SubProject mySubproject;

    @org.junit.Before
    public void setUp() throws Exception {

        Role myRole = new Role();
        myRole.setHourlyWage(9.50);

        myTask = new Task();
        myTask.setName("The Task 1");
        myTask.setRole(myRole);

        myTask.getWorkList(list -> {
            list.add(new WorkChunk(LocalDateTime.now(),
                    LocalDateTime.now(),
                    "Den ganzen Tag gearbeitet!")
            );
            list.add(new WorkChunk(LocalDateTime.now(),
                    LocalDateTime.now(),
                    "Den ganzen Tag nochmal gearbeitet!")
            );
        });

        mySubproject = new SubProject();
        mySubproject.setName("My SubProject");

        mySubproject.addTask(myTask);
        mySubproject.addTask(myTask);



    }

    @org.junit.Test
    public void testTask() {
        ReportDocument test = new ReportDocument("Test Task", new TaskContent(myTask), new TaskContent(myTask));

        String html = test.getHtmlNode().getHTMLCode();
        assertNotNull(html.charAt(0) == '<');
    }

    @org.junit.Test
    public void testSubProject() {


        ReportDocument test = new ReportDocument("Test Subproject", new SubProjectContent(mySubproject), new SubProjectContent(mySubproject));



        String html = test.getHtmlNode().getHTMLCode();
        assertNotNull(html.charAt(0) == '<');
    }


}