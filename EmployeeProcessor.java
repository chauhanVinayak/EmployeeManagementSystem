
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class EmployeeProcessor {

    public static void process(EmployeeService service)
            throws InterruptedException {

        ArrayList<Employee> snapshot =
            service.getEmployeeSnapshot();

        AtomicReference<Double> totalSalary =
            new AtomicReference<>(0.0);

        Thread displayThread = new Thread(() -> {

            System.out.println(
                "\n--- Thread 1: Employee Details ---"
            );

            for (Employee employee : snapshot) {
                employee.display();
            }
        });

        Thread salaryThread = new Thread(() -> {

            System.out.println(
                "\n--- Thread 2: Salary Calculation ---"
            );

            double total = 0;

            for (Employee employee : snapshot) {
                total += employee.calculateSalary();
            }

            totalSalary.set(total);

            System.out.printf(
                "Calculated total salary: %.2f%n", total
            );
        });

        displayThread.start();
        salaryThread.start();

        displayThread.join();
        salaryThread.join();

        System.out.printf(
            "Final result after both threads finish: %.2f%n",
            totalSalary.get()
        );
    }
}
