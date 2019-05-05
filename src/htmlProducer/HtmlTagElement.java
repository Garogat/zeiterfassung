package htmlProducer;

import java.util.*;

public class HtmlTagElement implements HtmlElement {

    private Map<String, String> properties;

    private List<HtmlElement> content;

    private String tag;

    private String getTag(){
        return tag;
    }

    private String getStartTag(){

        StringBuilder result = new StringBuilder("<"+getTag());

        Iterator it = properties.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            result.append(" " + pair.getKey() + " =\"" + pair.getValue() + "\"");
            it.remove();
        }
        result.append(">\n");
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
        properties.put(key, value);
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
            return getStartTag() + getContentString() + getEndTag();
        }
    }

    private HtmlTagElement(){
        properties = new HashMap<>();
        content = new ArrayList<>();
    }

    protected HtmlTagElement(String tag, HtmlElement... elements){
        this();
        this.tag = tag;
        Collections.addAll(content,elements);
    }
}
