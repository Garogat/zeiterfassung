package zeiterfassung.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Role extends DescribableModel {
    public static final Role DEFAULT_ROLE = new Role("Standard", "Die ist die standard Rolle", 0);

    private double hourlyWage;

    private String id;

    public Role() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }

    public Role(String name, String description, double hourlyWage) {
        this();
        setName(name);
        setDescription(description);
        setHourlyWage(hourlyWage);
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
