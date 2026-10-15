
public class RegularEmployee extends Employee {

    public RegularEmployee(int id, String name, String department,
                           double salary) throws AppException {
        super(id, name, department, salary);
    }

    @Override
    public String getRole() {
        return "Regular Employee";
    }

    @Override
    public double calculateSalary() {
        return getSalary();
    }
}

