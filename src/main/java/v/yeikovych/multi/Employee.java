package v.yeikovych.multi;

import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public class Employee extends Person {

    private long salary;

    public Employee(String name, LocalDate birthdate, long salary) {
        super(name, birthdate);
        setSalary(salary);
    }

    @Override
    public long getIncome() {
        return salary;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        throwIfFalse(() -> isPositive(salary));
        this.salary = salary;
    }
}
