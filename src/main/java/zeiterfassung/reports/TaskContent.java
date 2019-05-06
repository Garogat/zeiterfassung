package zeiterfassung.reports;


import static htmlProducer.HtmlFactory.*;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import zeiterfassung.models.Task;
import zeiterfassung.models.WorkChunk;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskContent implements Reportable {

    Task task;
    LocalDateTime start;
    LocalDateTime stop;

    @Override
    public HtmlTagElement getHtmlNode() {

        HtmlTagElement root = SPAN.build().addElement(
                H3.build().addText(task.getName())
        );

        HtmlTagElement rootTable = TABLE.build().addElement(
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
                .addText("Zeit Gesamt: "+task.getDuration(start, stop).toString());

        return root;
    }

    TaskContent(Task task, LocalDateTime start, LocalDateTime stop){
        this.task = task;
        this.start = start;
        this.stop = stop;
    }

}
