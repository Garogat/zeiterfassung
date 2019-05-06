package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import zeiterfassung.models.SubProject;
import zeiterfassung.models.Task;

import static htmlProducer.HtmlFactory.*;

public class SubProjectContent implements Reportable{

    private SubProject subProject;

    @Override
    public HtmlElement getHtmlNode() {
        HtmlTagElement root = SPAN.build().addElement(
                H3.build().addText(subProject.getName())
        );
        HtmlTagElement list = UL.build();

        subProject.getTasks(tasks -> {
            for (Task iter: tasks){
                list.addElement(LI.build().addElement(new TaskContent(iter).getHtmlNode()));
            }
        });


        root.addElement(list)
                .addText("Kosten Gesamt: "+subProject.getCosts())
                .addElement(BR.build())
                .addText("Zeit Gesamt: "+subProject.getDuration().toString());

        return root;
    }

    public SubProjectContent(SubProject subProject){
        this.subProject = subProject;
    }


}
