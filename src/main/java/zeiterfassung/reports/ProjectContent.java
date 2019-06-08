package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import zeiterfassung.models.Project;
import zeiterfassung.models.SubProject;
import zeiterfassung.models.Task;

import java.time.LocalDateTime;

import static htmlProducer.HtmlFactory.*;

/**
 * A html report for a project
 */
public class ProjectContent implements Reportable {

    Project project;
    LocalDateTime start;
    LocalDateTime stop;

    @Override
    public HtmlElement getHtmlNode() {

        HtmlTagElement root = SPAN.build();

        // Caption
        root.addElement(H3.build().addText(project.getName()), BR.build());


        // All Tasks
        HtmlTagElement ul = UL.build();
        project.getTasks(list -> {
            for (Task iter: list) {
                ul.addElement(LI.build().addElement(new TaskContent(iter, start, stop).getHtmlNode()));
            }
        });

        project.getSubProjects(list ->{
            for (SubProject iter: list){
                ul.addElement(LI.build().addElement(new SubProjectContent(iter, start, stop).getHtmlNode()));
            }
        });

        root.addElement(ul);

        return root;
    }


    public ProjectContent(Project project, LocalDateTime start, LocalDateTime stop){
        this.project = project;
        this.start = start;
        this.stop = stop;
    }

}
