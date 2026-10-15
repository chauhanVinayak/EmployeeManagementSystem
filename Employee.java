
public abstract class Employee implements Payable {

    private final int id;
    private final String name;
    private final String department;
    private final double salary;

    public Employee(int id, String name, String department, double salary)
            throws AppException {

        if (id <= 0) {
            throw new AppException("ID must be a positive number.");
        }

        if (name == null ||
                !name.trim().matches("[A-Za-z]+(?: [A-Za-z]+)?")) {
            throw new AppException(
                "Name must contain English letters only and at most one space."
            );
        }

        if (department == null ||
                !department.trim().matches("[A-Za-z]+")) {
            throw new AppException(
                "Department must contain English characters only."
            );
        }

        if (!Double.isFinite(salary) || salary < 0) {
            throw new AppException(
                "Salary cannot be negative or invalid."
            );
        }

        this.id = id;
        this.name = name.trim();
        this.department = department.trim();
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public abstract String getRole();

    public void display() {
        System.out.printf(
            "ID: %d | Name: %s | Department: %s | Role: %s | Salary: %.2f%n",
            id, name, department, getRole(), calculateSalary()
        );
    }
}
