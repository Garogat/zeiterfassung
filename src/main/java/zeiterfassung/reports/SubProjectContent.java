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
        HtmlTagElement root = SPAN.build().addProperty(fontColor);
        root.addElement(H4.build().addText(subProject.getName()));

        HtmlTagElement list = UL.build();

        subProject.getTasks(tasks -> {
            for (Task iter: tasks){
                list.addElement(LI.build().addElement(new TaskContent(iter, start, stop).getHtmlNode()));
            }
        });

        root.addElement(list).addElement(BR.build());

        root.addText("Unterprojekt Kosten Gesamt: "+ Utils.formatCosts(subProject.getCosts(start, stop)))
                .addElement(BR.build())
                .addText("Unterprojekt Zeit Gesamt: "+Utils.formatDuration(subProject.getDuration(start, stop)));

        return root;
    }

    public SubProjectContent(SubProject subProject, LocalDateTime start, LocalDateTime stop){
        this.subProject = subProject;
        this.start = start;
        this.stop = stop;
    }


}

