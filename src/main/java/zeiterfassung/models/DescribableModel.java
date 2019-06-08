package zeiterfassung.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A base interface for all objects which must have a name and a description
 */
@XmlType(propOrder = {"name", "description"})
@XmlAccessorType(XmlAccessType.NONE)
public abstract class DescribableModel extends BaseModel {
    private StringProperty name = new SimpleStringProperty();
    private StringProperty description = new SimpleStringProperty();

    public DescribableModel() {
        super();
    }

    public DescribableModel(String name, String description) {
        super();
        setName(name);
        setDescription(description);
    }

    @XmlElement(name = "name")
    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    @XmlElement(name = "description")
    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }
}
