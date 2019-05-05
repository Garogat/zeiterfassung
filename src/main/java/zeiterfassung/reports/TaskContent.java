package zeiterfassung.reports;


import static htmlProducer.HtmlFactory.*;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;

public class TaskContent implements Reportable {


    Task task;

    @Override
    public HtmlTagElement getHtmlNode() {

        HtmlTagElement root = SPAN.build().addElement(
                        H1.build().addText(task.getName())
        );

        HtmlTagElement rootTable = TABLE.build().addElement(
                TR.build().addElement(
                        TH.build().addText("Start"),
                        TH.build().addText("Ende"),
                        TH.build().addText("Beschreibung")
                )
        );

        task.getWorkList(list -> {
            for (WorkChunk workChunk: list){
                rootTable.addElement(
                        TR.build().addElement(
                                TD.build().addText(workChunk.getStartTime().toString()),
                                TD.build().addText(workChunk.getEndTime().toString()),
                                TD.build().addText(workChunk.getDescription())
                        )
                );
            }
        });

        root.addElement(rootTable);

        root.addText("Kosten Gesamt: "+task.getCosts());
        root.addText("Zeit Gesamt: "+task.getDuration().toString());

        return root;
    }

    TaskContent(Task task){
        this.task = task;
    }

}
