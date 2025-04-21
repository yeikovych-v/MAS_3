package v.yeikovych.multi;


import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public class Student extends Person implements IStudent {

    private long scholarship;

    public Student(String name, LocalDate birthdate, long scholarship) {
        super(name, birthdate);
        setScholarship(scholarship);
    }

    @Override
    public long getScholarship() {
        return scholarship;
    }

    @Override
    public long getIncome() {
        return scholarship;
    }

    public void setScholarship(long scholarship) {
        throwIfFalse(() -> isPositiveOrZero(scholarship));
        this.scholarship = scholarship;
    }
}
