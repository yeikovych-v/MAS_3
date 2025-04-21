package v.yeikovych.multi;

import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public class WorkingStudent extends Employee implements IStudent {

    private long scholarship;

    public WorkingStudent(String name, LocalDate birthdate, long salary, long scholarship) {
        super(name, birthdate, salary);
        setScholarship(scholarship);
    }

    @Override
    public long getIncome() {
        return getSalary() + scholarship;
    }

    @Override
    public long getScholarship() {
        return scholarship;
    }

    public void setScholarship(long scholarship) {
        throwIfFalse(() -> isPositiveOrZero(scholarship));
        this.scholarship = scholarship;
    }
}
