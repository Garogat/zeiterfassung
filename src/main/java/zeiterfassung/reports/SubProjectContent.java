package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import htmlProducer.HtmlValuePair;
import zeiterfassung.Utils;
import zeiterfassung.models.SubProject;
import zeiterfassung.models.Task;

import java.time.LocalDateTime;

import static htmlProducer.HtmlFactory.*;

/**
 * A html report for a subproject
 */
public class SubProjectContent implements Reportable{

    private SubProject subProject;
    LocalDateTime start;
    LocalDateTime stop;

    HtmlValuePair fontColor = new HtmlValuePair("style", "color: red");

    @Override
    public HtmlElement getHtmlNode() {
        HtmlTagElement root = SPAN.build().addElement(
                H3.build().addText(subProject.getName())
        );
        HtmlTagElement list = UL.build();

        subProject.getTasks(tasks -> {
            for (Task iter: tasks){
                list.addElement(LI.build().addElement(new TaskContent(iter, start, stop).getHtmlNode()));
            }
        });

        root.addElement(list)
                .addProperty(fontColor).addText("Kosten Gesamt: "+ Utils.formatCosts(subProject.getCosts(start, stop)))
                .addElement(BR.build())
                .addProperty(fontColor).addText("Zeit Gesamt: "+Utils.formatDuration(subProject.getDuration(start, stop)));

        return root;
    }

    public SubProjectContent(SubProject subProject, LocalDateTime start, LocalDateTime stop){
        this.subProject = subProject;
        this.start = start;
        this.stop = stop;
    }


}

