package htmlProducer;

/**
 * HtmlValuePair represents a Key Value Param inside an opening tag
 */
public class HtmlValuePair {

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private String key;
    private String value;

    /**
     * Constuctor with Key Value Pair parameter
     * @param key The Key
     * @param value The Value
     */
    public HtmlValuePair(String key, String value){
        this.key = key;
        this.value = value;
    }
}
