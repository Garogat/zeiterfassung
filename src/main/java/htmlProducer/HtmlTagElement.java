package htmlProducer;

import java.util.*;

public class HtmlTagElement implements HtmlElement {

    private List<HtmlValuePair> properties;

    private List<HtmlElement> content;

    private String tag;

    private String getTag(){
        return tag;
    }

    private String getStartTag(){

        StringBuilder result = new StringBuilder("<"+getTag());

        for (HtmlValuePair iter: properties){
            result.append(" " + iter.getKey() + " =\"" + iter.getValue() + "\"");
        }

        // Close Tag with "/>" if there is no content
        if (content.size() == 0){
            result.append(" />\n");
        }else {
            result.append(">\n");
        }
        return result.toString();
    }

    private String getEndTag(){
        return "</"+getTag()+">\n";
    }

    private String getContentString() {
        StringBuilder result = new StringBuilder();
        for (HtmlElement item: content)
            result.append(item.getHTMLCode());
        result.append("\n");
        return result.toString();
    }


    public HtmlTagElement addProperty(String key, String value){
        properties.add(new HtmlValuePair(key, value));
        return this;
    }

    public HtmlTagElement addProperty(HtmlValuePair pair) {
        properties.add(pair);
        return this;

    }
    public HtmlTagElement addElement(HtmlElement... elements){
        Collections.addAll(content, elements);
        return this;
    }

    public HtmlTagElement addText(String text){
        content.add(new HtmlText(text));
        return this;
    }

    @Override
    public String getHTMLCode() {
        {
            if (content.size() == 0){
                return getStartTag();
            }else {
                return getStartTag() + getContentString() + getEndTag();
            }
        }
    }

    private HtmlTagElement(){
        properties = new ArrayList<>();
        content = new ArrayList<>();
    }

    protected HtmlTagElement(String tag, HtmlElement... elements){
        this();
        this.tag = tag;
        Collections.addAll(content,elements);
    }
}
