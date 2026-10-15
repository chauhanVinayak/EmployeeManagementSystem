
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

public class EmployeeService {

    private final ArrayList<Employee> employees = new ArrayList<>();
    private final HashMap<Integer, Employee> employeeMap = new HashMap<>();
    private final HashSet<String> departments = new HashSet<>();

    public synchronized void addEmployee(Employee employee)
            throws AppException {

        if (employeeMap.containsKey(employee.getId())) {
            throw new AppException(
                "Employee ID already exists: " + employee.getId()
            );
        }

        employees.add(employee);
        employeeMap.put(employee.getId(), employee);
        departments.add(employee.getDepartment());

        System.out.println("Employee added successfully.");
    }

    public synchronized void displayEmployees() {

        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        for (Employee employee : employees) {
            employee.display();
        }

        System.out.println("Departments: " + departments);
    }

    public synchronized Employee searchEmployee(int id)
            throws AppException {

        Employee employee = employeeMap.get(id);

        if (employee == null) {
            throw new AppException(
                "Employee not found with ID: " + id
            );
        }

        return employee;
    }

    public synchronized void removeEmployee(int id)
            throws AppException {

        Employee employee = employeeMap.remove(id);

        if (employee == null) {
            throw new AppException(
                "Employee not found with ID: " + id
            );
        }

        employees.remove(employee);

        departments.clear();

        for (Employee e : employees) {
            departments.add(e.getDepartment());
        }

        System.out.println("Employee removed successfully.");
    }

    public synchronized void sortBySalary() {

        employees.sort(
            Comparator.comparingDouble(Employee::calculateSalary)
        );

        displayEmployees();
    }

    public synchronized void sortByName() {

        employees.sort(
            Comparator.comparing(
                Employee::getName,
                String.CASE_INSENSITIVE_ORDER
            )
        );

        displayEmployees();
    }

    public synchronized void findBySalary(double amount) {

        boolean found = false;

        for (Employee employee : employees) {
            if (employee.calculateSalary() > amount) {
                employee.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                "No employees found above this salary."
            );
        }
    }

    public synchronized double getTotalSalary() {

        double total = 0;

        for (Employee employee : employees) {
            total += employee.calculateSalary();
        }

        return total;
    }

    public synchronized ArrayList<Employee> getEmployeeSnapshot() {
        return new ArrayList<>(employees);
    }
}
