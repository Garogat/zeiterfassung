package htmlProducer;

public class HtmlValuePair {
    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    private String key;
    private String value;

    public HtmlValuePair(String key, String value){
        this.key = key;
        this.value = value;
    }
}
