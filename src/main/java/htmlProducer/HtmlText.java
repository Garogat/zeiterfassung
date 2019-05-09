package htmlProducer;


public class HtmlText implements HtmlElement {

    private String text;

    @Override
    public String getHTMLCode() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    protected HtmlText(String value){
        text = value;
    }

}
