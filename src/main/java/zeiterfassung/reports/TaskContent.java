package zeiterfassung.reports;


import static htmlProducer.HtmlFactory.*;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import htmlProducer.HtmlValuePair;
import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * A html report for a task
 */
public class TaskContent implements Reportable {

    Task task;
    LocalDateTime start;
    LocalDateTime stop;

    HtmlValuePair borderStyle = new HtmlValuePair("style", "border:1px solid black;");

    @Override
    public HtmlTagElement getHtmlNode() {

        HtmlTagElement root = SPAN.build().addElement(
                H3.build().addText(task.getName())
        );

        HtmlTagElement rootTable = TABLE.build().addProperty(borderStyle).addElement(
                TR.build().addElement(
                        TH.build().addText("Start"),
                        TH.build().addText("Ende"),
                        TH.build().addText("Beschreibung")
                )
        );

        DateTimeFormatter frm = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        task.getWorkList(list -> {
            for (WorkChunk workChunk: list){
                rootTable.addElement(
                        TR.build().addElement(
                                TD.build().addText(workChunk.getStartTime().format(frm)),
                                TD.build().addText(workChunk.getEndTime().format(frm)),
                                TD.build().addText(workChunk.getDescription())
                        )
                );
            }
        });

        root.addElement(rootTable)
                .addText("Kosten Gesamt: "+task.getCosts(start, stop))
                .addElement(BR.build())
                .addText("Zeit Gesamt: "+task.getDuration(start, stop).getSeconds()/(double)3600);

        return root;
    }

    TaskContent(Task task, LocalDateTime start, LocalDateTime stop){
        this.task = task;
        this.start = start;
        this.stop = stop;
    }

}
