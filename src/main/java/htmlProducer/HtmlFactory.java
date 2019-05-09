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
    H2(()->{return new HtmlTagElement("h2");}),
    H3(()->{return new HtmlTagElement("h3");}),
    H4(()->{return new HtmlTagElement("h4");}),
    UL(()->{return new HtmlTagElement("ul");}),
    LI(()->{return new HtmlTagElement("li");}),
    BR(()->{return new HtmlTagElement("br");});

    private HtmlBuilder attribute;

    HtmlFactory(HtmlBuilder hb) {
        attribute = hb;
    }

    @Override
    public HtmlTagElement build() {
        return attribute.build();
    }

}

