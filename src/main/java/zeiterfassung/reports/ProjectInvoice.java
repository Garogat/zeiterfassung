package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import htmlProducer.HtmlValuePair;
import zeiterfassung.Utils;
import zeiterfassung.models.Project;

import java.time.LocalDateTime;
import java.util.Scanner;

import static htmlProducer.HtmlFactory.*;

/**
 * A html report for a project invoice
 */
public class ProjectInvoice implements Reportable {

    private Project project;

    HtmlValuePair border = new HtmlValuePair("style", "border: 1px solid black");
    HtmlValuePair backgroundColor = new HtmlValuePair("style", "color: light-grey");
    HtmlValuePair fontFamily = new HtmlValuePair("style", "font-family: Arial Black");
    HtmlValuePair fontColor = new HtmlValuePair("style", "color: red");
    HtmlValuePair fontColor2 = new HtmlValuePair("style", "color: green");


    @Override
    public HtmlElement getHtmlNode() {
        HtmlTagElement root = SPAN.build();

        // Caption
        root.addElement(H4.build().addProperty(fontFamily).addText(project.getName()), BR.build());

        // Customer Line by Line
        root.addElement(H4.build().addProperty(fontFamily).addText("Auftraggeber")).addElement(BR.build());
        if (project.getCustomer() != null) {
            Scanner scanner = new Scanner(project.getCustomer());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                root.addText(line).addElement(BR.build());
            }
            scanner.close();
        }


        root.addElement(BR.build(), BR.build());

        root.addElement(TABLE.build().addProperty(border).addElement(

                TR.build().addProperty(border).addElement(
                        TD.build().addProperty(fontFamily).addText("Projekt:"),
                        TD.build().addProperty(fontColor2).addText(project.getName())
                ),

                TR.build().addElement(
                        TD.build().addProperty(fontFamily).addText("Dauer:"),
                        TD.build().addProperty(fontColor2).addText(Utils.formatDuration(project.getDuration(LocalDateTime.MIN, LocalDateTime.MAX)))
                ),
                TR.build().addElement(
                        TD.build().addProperty(fontFamily).addText("Kosten:"),
                        TD.build().addProperty(fontColor).addText(Utils.formatCosts(project.getCosts(LocalDateTime.MIN, LocalDateTime.MAX)))
                )
        ));

        return root;
    }

    public ProjectInvoice(Project project) {
        this.project = project;
    }
}
