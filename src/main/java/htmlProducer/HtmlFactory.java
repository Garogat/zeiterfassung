package htmlProducer;

public enum HtmlFactory implements HtmlBuilder{
    TD(()->{return new HtmlTagElement("td");}),
    TR(()->{return new HtmlTagElement("tr");}),
    TH(()->{return new HtmlTagElement("th");}),
    TABLE(()->{return new HtmlTagElement("table");}),
    SPAN(()->{return new HtmlTagElement("span");}),
    TITLE(()->{return new HtmlTagElement("title");}),
    HTML(()->{return new HtmlTagElement("html");}),
    BODY(()->{return new HtmlTagElement("body");}),
    HEAD(()->{return new HtmlTagElement("head");}),
    H1(()->{return new HtmlTagElement("h1");}),
    H3(()->{return new HtmlTagElement("h3");});

    private HtmlBuilder attribute;

    HtmlFactory(HtmlBuilder hb) {
        attribute = hb;
    }

    @Override
    public HtmlTagElement build() {
        return attribute.build();
    }

}


