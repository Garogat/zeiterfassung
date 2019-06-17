package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import static htmlProducer.HtmlFactory.*;

import htmlProducer.HtmlValuePair;
import zeiterfassung.Utils;
import zeiterfassung.models.Area;
import zeiterfassung.models.Project;

import java.time.LocalDateTime;

/**
 * A html report for an area
 */
public class AreaContent implements Reportable  {

    Area area;
    LocalDateTime start;
    LocalDateTime stop;
    @Override
    public HtmlElement getHtmlNode() {
        HtmlTagElement root = SPAN.build().addProperty("style", "color: black");

        // Caption
        root.addElement(H4.build().addText(area.getName()), BR.build());


        // All Tasks
        HtmlTagElement ul = UL.build();
        area.getProjectList(list -> {
            for (Project iter: list) {
                ul.addElement(LI.build().addElement(new ProjectContent(iter, start, stop).getHtmlNode()));
            }
        });

        root.addElement(ul);



        return root;
    }


    public AreaContent(Area area, LocalDateTime start, LocalDateTime stop){
        this.area = area;
        this.start = start;
        this.stop = stop;

    }


}
