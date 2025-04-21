package v.yeikovych.multi;

import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public class Pensioner extends Person {

    private long pension;

    public Pensioner(String name, LocalDate birthdate, long pension) {
        super(name, birthdate);
        setPension(pension);
    }

    @Override
    public long getIncome() {
        return pension;
    }

    public long getPension() {
        return pension;
    }

    public void setPension(long pension) {
        throwIfFalse(() -> isPositive(pension));
        this.pension = pension;
    }
}
