package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import zeiterfassung.models.SubProject;
import zeiterfassung.models.Task;

import java.time.LocalDateTime;

import static htmlProducer.HtmlFactory.*;

public class SubProjectContent implements Reportable{

    private SubProject subProject;
    LocalDateTime start;
    LocalDateTime stop;


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
                .addText("Kosten Gesamt: "+subProject.getCosts(start, stop))
                .addElement(BR.build())
                .addText("Zeit Gesamt: "+subProject.getDuration(start, stop).getSeconds()/(double)3600);

        return root;
    }

    public SubProjectContent(SubProject subProject, LocalDateTime start, LocalDateTime stop){
        this.subProject = subProject;
        this.start = start;
        this.stop = stop;
    }


}