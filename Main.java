
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final EmployeeService service = new EmployeeService();

    private static void handleException(Exception e) {
        System.out.println("Error: " + e.getMessage());
    }

    private static int readInt(String message) {

        while (true) {
            System.out.print(message);

            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                handleException(
                    new Exception("Enter a valid whole number.")
                );
            }
        }
    }

    private static double readDouble(String message) {

        while (true) {
            System.out.print(message);

            try {
                double value =
                    Double.parseDouble(sc.nextLine().trim());

                if (!Double.isFinite(value)) {
                    handleException(
                        new Exception("Enter a valid finite number.")
                    );
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                handleException(
                    new Exception("Enter a valid numeric value.")
                );
            }
        }
    }

    private static String readName() {

        while (true) {
            System.out.print(
                "Enter employee name (e.g. Rahul or Rahul Kumar): "
            );

            String name = sc.nextLine().trim();

            if (name.matches("[A-Za-z]+(?: [A-Za-z]+)?")) {
                return name;
            }

            handleException(
                new Exception(
                    "Name must contain English letters only, "
                    + "with at most one space between two words."
                )
            );
        }
    }

    private static String readDepartment() {

        while (true) {
            System.out.print(
                "Enter department (e.g. HR, IT, Finance): "
            );

            String department = sc.nextLine().trim();

            if (department.matches("[A-Za-z]+")) {
                return department;
            }

            handleException(
                new Exception(
                    "Department must contain English characters only."
                )
            );
        }
    }

    private static void addEmployee() {

        try {
            int id = readInt("Enter employee ID: ");
            String name = readName();
            String department = readDepartment();
            double salary = readDouble("Enter base salary: ");

            System.out.println("1. Regular Employee");
            System.out.println("2. Manager");

            int type = readInt("Choose employee type: ");

            Employee employee;

            if (type == 1) {
                employee = new RegularEmployee(
                    id, name, department, salary
                );

            } else if (type == 2) {
                double bonus = readDouble("Enter manager bonus: ");

                employee = new Manager(
                    id, name, department, salary, bonus
                );

            } else {
                throw new AppException(
                    "Invalid employee type. Choose 1 or 2."
                );
            }

            service.addEmployee(employee);

        } catch (Exception e) {
            handleException(e);
        }
    }

    public static void main(String[] args) {

        boolean running = true;

        while (running) {

            System.out.println(
                "\n===== EMPLOYEE MANAGEMENT SYSTEM ====="
            );
            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Remove Employee");
            System.out.println("5. Sort by Salary");
            System.out.println("6. Sort by Name");
            System.out.println("7. Find by Minimum Salary");
            System.out.println("8. Total Salary");
            System.out.println("9. Start Employee Processing");
            System.out.println("10. Exit");

            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {

                    case 1:
                        addEmployee();
                        break;

                    case 2:
                        service.displayEmployees();
                        break;

                    case 3:
                        int id = readInt("Enter employee ID: ");
                        service.searchEmployee(id).display();
                        break;

                    case 4:
                        int removeId =
                            readInt("Enter employee ID to remove: ");

                        service.removeEmployee(removeId);
                        break;

                    case 5:
                        service.sortBySalary();
                        break;

                    case 6:
                        service.sortByName();
                        break;

                    case 7:
                        double amount =
                            readDouble("Enter minimum salary: ");

                        if (amount < 0) {
                            throw new AppException(
                                "Salary cannot be negative."
                            );
                        }

                        service.findBySalary(amount);
                        break;

                    case 8:
                        System.out.printf(
                            "Total salary: %.2f%n",
                            service.getTotalSalary()
                        );
                        break;

                    case 9:
                        EmployeeProcessor.process(service);
                        break;

                    case 10:
                        running = false;
                        System.out.println("Application closed.");
                        break;

                    default:
                        System.out.println(
                            "Invalid choice. Select 1 to 10."
                        );
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

                handleException(
                    new Exception("Employee processing was interrupted.")
                );

            } catch (Exception e) {
                handleException(e);
            }
        }

        sc.close();
    }
}
