package zeiterfassung.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"name", "description", "hourlyWage"})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Role extends DescribableModel {
    private double hourlyWage;

    public Role(){}

    public Role(String name, String description, double hourlyWage){
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

    public static Role roleFactory(String role){
        switch(role){
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
