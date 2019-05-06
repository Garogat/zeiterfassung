package zeiterfassung.reports;

import zeiterfassung.models.Role;
import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class ReportDocumentTest {


    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.Test
    public void getContentString() {

        Role myRole = new Role();
        myRole.setHourlyWage(9.50);

        Task myTask = new Task();
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

        myTask.setName("The Task 1");
        myTask.setRole(new Role());

        ReportDocument test = new ReportDocument("Test Task", new TaskContent(myTask), new TaskContent(myTask)
                );

        String html = test.getHtmlNode().getHTMLCode();
        assertNotNull(html.charAt(0) == '<');
    }

}