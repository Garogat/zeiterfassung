package zeiterfassung.models;

public class Role extends DescribableModel {
    private double hourlyWage;

    public Role() {
        super();
        setName("Neues Rolle");
        setDescription("Dies ist eine Rolle");
    }

    public Role(String name, String description) {
        super(name, description);
    }

    public double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }
}
