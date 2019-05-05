package zeiterfassung.models;

public class Role {
    private String name;
    private String description;
    private Money hourlyWage;

    public String getName() {
        return name;
    }

    public Money getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(Money hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
