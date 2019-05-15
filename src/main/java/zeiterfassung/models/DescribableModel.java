package zeiterfassung.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class DescribableModel {
    private StringProperty name;
    private StringProperty description;

    public DescribableModel() {
        name = new SimpleStringProperty();
        description = new SimpleStringProperty();
    }

    public DescribableModel(String name, String description) {
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();

        setName(name);
        setDescription(description);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

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
