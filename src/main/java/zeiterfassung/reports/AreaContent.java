package zeiterfassung.reports;

import htmlProducer.HtmlElement;
import htmlProducer.HtmlTagElement;
import static htmlProducer.HtmlFactory.*;

import zeiterfassung.models.Area;
import zeiterfassung.models.Project;


public class AreaContent implements Reportable  {

    Area area;

    @Override
    public HtmlElement getHtmlNode() {
        HtmlTagElement root = SPAN.build();

        // Caption
        root.addElement(H2.build().addText(area.getName()), BR.build());


        // All Tasks
        HtmlTagElement ul = UL.build();
        area.getProjectList(list -> {
            for (Project iter: list) {
                ul.addElement(LI.build().addElement(new ProjectContent(iter).getHtmlNode()));
            }
        });

        root.addElement(ul);

        return root;
    }


    public AreaContent(Area area){
        this.area = area;

    }


}
