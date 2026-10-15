
public class Manager extends Employee {

    private final double bonus;

    public Manager(int id, String name, String department,
                   double salary, double bonus) throws AppException {

        super(id, name, department, salary);

        if (!Double.isFinite(bonus) || bonus < 0) {
            throw new AppException(
                "Bonus cannot be negative or invalid."
            );
        }

        this.bonus = bonus;
    }

    @Override
    public String getRole() {
        return "Manager";
    }

    @Override
    public double calculateSalary() {
        return getSalary() + bonus;
    }
}

