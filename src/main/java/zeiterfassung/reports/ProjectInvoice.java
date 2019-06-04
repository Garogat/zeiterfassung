package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import zeiterfassung.Utils;
import zeiterfassung.models.Project;

import java.time.LocalDateTime;
import java.util.Scanner;

import static htmlProducer.HtmlFactory.*;

public class ProjectInvoice implements Reportable {

    private Project project;

    @Override
    public HtmlElement getHtmlNode() {
        HtmlTagElement root = SPAN.build();

        // Caption
        root.addElement(H4.build().addText(project.getName()), BR.build());

        // Customer Line by Line
        root.addElement(H4.build().addText("Auftraggeber")).addElement(BR.build());
        if (project.getCustomer() != null) {
            Scanner scanner = new Scanner(project.getCustomer());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                root.addText(line).addElement(BR.build());
            }
            scanner.close();
        }


        root.addElement(BR.build(), BR.build(), BR.build(), BR.build());

        root.addElement(TABLE.build().addElement(

                TR.build().addElement(
                        TD.build().addText("Projekt:"),
                        TD.build().addText(project.getName())
                ),

                TR.build().addElement(
                        TD.build().addText("Dauer:"),
                        TD.build().addText(Utils.formatDuration(project.getDuration(LocalDateTime.MIN, LocalDateTime.MAX)))
                ),
                TR.build().addElement(
                        TD.build().addText("Kosten:"),
                        TD.build().addText(Utils.formatCosts(project.getCosts(LocalDateTime.MIN, LocalDateTime.MAX)))
                )
        ));

        return root;
    }

    public ProjectInvoice(Project project) {
        this.project = project;
    }
}
