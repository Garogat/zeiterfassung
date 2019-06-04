package zeiterfassung.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Role extends DescribableModel {
    public static final Role DEFAULT_ROLE = new Role("Standard", "Die ist die standard Rolle", 0);

    private double hourlyWage;

    public Role() {
        super();
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
}
