package htmlProducer;

import java.util.*;

/**
 * Represents a HTML Element
 */
public class HtmlTagElement implements HtmlElement {

    /**
     * Key Value Pairs for the open tag
     */
    private List<HtmlValuePair> properties;

    /**
     * HTML Elements inside this Element
     */
    private List<HtmlElement> content;

    /**
     * The Name of the tag
     */
    private String tag;

    private String getTag(){
        return tag;
    }

    /**
     * surrounds the tagname with  "<" and ">"
     * if the content is empty it ends with "/>"
     * @return the opening tag
     */
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

    /**
     * surrounds the tagname with  "</" and ">"
     * @return the closing tag
     */
    private String getEndTag(){
        return "</"+getTag()+">\n";
    }

    /**
     * returns the content inside the tags
     * @return content inside the tags
     */
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

    /**
     * adds a HTML Element to the content
     * @param elements Element to add
     * @return this for connected statements
     */
    public HtmlTagElement addElement(HtmlElement... elements){
        Collections.addAll(content, elements);
        return this;
    }

    /**
     * Adds a Text to the content
     * @param text Test to add
     * @return this for connected statements
     */
    public HtmlTagElement addText(String text){
        content.add(new HtmlText(text));
        return this;
    }

    /**
     * @see HtmlElement
     */
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
