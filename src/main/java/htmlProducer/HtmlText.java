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

    public HtmlText(String value){
        text = value;
    }

    public HtmlText(){
        text = "";
    }

}
