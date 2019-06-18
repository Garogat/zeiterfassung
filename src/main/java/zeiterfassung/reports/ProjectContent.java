package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import htmlProducer.HtmlValuePair;
import zeiterfassung.Utils;
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

        HtmlTagElement root = SPAN.build().addProperty("style", "color: green");

        // Caption
        root.addElement(H4.build().addText(project.getName()), BR.build());


        HtmlTagElement ul = UL.build();

        // All Tasks
        project.getTasks(list -> {
            for (Task iter: list) {
                ul.addElement(LI.build().addElement(new TaskContent(iter, start, stop).getHtmlNode()));
            }
        });

        // All Subprojects
        project.getSubProjects(list ->{
            for (SubProject iter: list){
                ul.addElement(LI.build().addElement(new SubProjectContent(iter, start, stop).getHtmlNode()));
            }
        });

        root.addElement(ul).addElement(BR.build());

        root.addText("Projekt Kosten Gesamt: "+ Utils.formatCosts(project.getCosts(start, stop)))
            .addElement(BR.build())
            .addText("Projekt Zeit Gesamt: "+Utils.formatDuration(project.getDuration(start, stop)));

        return root;
    }


    public ProjectContent(Project project, LocalDateTime start, LocalDateTime stop){
        this.project = project;
        this.start = start;
        this.stop = stop;
    }

}
