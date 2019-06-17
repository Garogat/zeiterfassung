package zeiterfassung.reports;


import htmlProducer.HtmlTagElement;
import htmlProducer.HtmlValuePair;
import zeiterfassung.Utils;
import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static htmlProducer.HtmlFactory.*;


/**
 * A html report for a task
 */
public class TaskContent implements Reportable {

    Task task;
    LocalDateTime start;
    LocalDateTime stop;

    HtmlValuePair borderStyle = new HtmlValuePair("style", "border:1px solid black;");
    HtmlValuePair backgroundColor = new HtmlValuePair("style", "background-color: lightblue");
    HtmlValuePair fontColor = new HtmlValuePair("style", "color: blue");

    @Override
    public HtmlTagElement getHtmlNode() {

        HtmlTagElement root = SPAN.build().addElement(
            H3.build().addText(task.getName())
        );

        HtmlTagElement rootTable = TABLE.build().addProperty(borderStyle).addElement(
            TR.build().addElement(
                TH.build().addProperty(backgroundColor).addText("Start"),
                TH.build().addProperty(backgroundColor).addText("Ende"),
                TH.build().addProperty(backgroundColor).addText("Beschreibung")
            )
        );

        DateTimeFormatter frm = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        task.getWorkList(list -> {
            for (WorkChunk workChunk : list) {
                rootTable.addElement(
                    TR.build().addElement(
                        TD.build().addProperty(borderStyle).addText(workChunk.getStartTime().format(frm)),
                        TD.build().addProperty(borderStyle).addText(workChunk.getEndTime() == null ? "---" : workChunk.getEndTime().format(frm)),
                        TD.build().addProperty(borderStyle).addText(workChunk.getDescription())
                    )
                );
            }
        });

        root.addElement(rootTable)
            .addProperty(fontColor).addText("Kosten Gesamt: " + Utils.formatCosts(task.getCosts(start, stop)))
            .addElement(BR.build())
            .addProperty(fontColor).addText("Zeit Gesamt: " + Utils.formatDuration(task.getDuration(start, stop)));

        return root;
    }

    TaskContent(Task task, LocalDateTime start, LocalDateTime stop) {
        this.task = task;
        this.start = start;
        this.stop = stop;
    }

}
