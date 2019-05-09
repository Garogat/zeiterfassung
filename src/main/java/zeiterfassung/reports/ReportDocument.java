package zeiterfassung.reports;


import static htmlProducer.HtmlFactory.*;
import htmlProducer.HtmlTagElement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportDocument implements Reportable {

    String title;
    List<Reportable> items;


    @Override
    public HtmlTagElement getHtmlNode() {
        HtmlTagElement body = BODY.build();

        body.addElement(H1.build().addText(title));

        for (Reportable item: items){
            body.addElement(item.getHtmlNode());
        }

        return HTML.build().addElement(
                HEAD.build().addElement(
                        TITLE.build().addText(title)
                ),
                body);
    }

    ReportDocument(String title, Reportable... items){
        this.title = title;
        this.items = new ArrayList<>();
        Collections.addAll(this.items, items);


    }


}
