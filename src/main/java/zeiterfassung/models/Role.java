package zeiterfassung.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Role extends DescribableModel {
    private double hourlyWage;


    private String id;

    public Role() {
        if (id == null || id.isEmpty()){
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

    public static Role roleFactory(String role) {
        switch (role) {
            case "Hiwi":
                return new Role("Hiwi", "Wissenschaftliche Hilfskraft", 16);
            case "Professor":
                return new Role("Professor", "Professor mit vielen Jahren tiefgründiger Erfahrung in der Informatik", 30);
            case "Student":
                return new Role("Student", "Einfacher Student mit grundlegenden Programmierkenntnissen", 12);

            default:
                return new Role();
        }
    }
}
