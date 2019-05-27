package zeiterfassung.reports;


import htmlProducer.HtmlTagElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static htmlProducer.HtmlFactory.*;

public class ReportDocument implements Reportable {

    String title;
    List<Reportable> items;


    @Override
    public HtmlTagElement getHtmlNode() {
        HtmlTagElement body = BODY.build();

        body.addElement(H1.build().addText(title));

        for (Reportable item : items) {
            body.addElement(item.getHtmlNode());
        }

        return HTML.build().addElement(
                HEAD.build().addElement(
                        TITLE.build().addText(title)
                ),
                body);
    }

    public ReportDocument(String title, Reportable... items) {
        this.title = title;
        this.items = new ArrayList<>();
        Collections.addAll(this.items, items);


    }


}
