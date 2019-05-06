package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import zeiterfassung.models.Project;
import zeiterfassung.models.SubProject;
import zeiterfassung.models.Task;

import static htmlProducer.HtmlFactory.*;

public class ProjectContent implements Reportable {

    Project project;

    @Override
    public HtmlElement getHtmlNode() {

        HtmlTagElement root = SPAN.build();

        // Caption
        root.addElement(H3.build().addText(project.getName()), BR.build());


        // All Tasks
        HtmlTagElement ul = UL.build();
        project.getTasks(list -> {
            for (Task iter: list) {
                ul.addElement(LI.build().addElement(new TaskContent(iter).getHtmlNode()));
            }
        });

        project.getSubProjects(list ->{
            for (SubProject iter: list){
                ul.addElement(LI.build().addElement(new SubProjectContent(iter).getHtmlNode()));
            }
        });

        root.addElement(ul);

        return root;
    }


    public ProjectContent(Project project){
        this.project = project;
    }

}
