package v.yeikovych.multi;

import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public abstract class Person {

    private String name;
    private LocalDate birthdate;
    private int age;

    public Person(String name, LocalDate birthdate) {
        setName(name);
        setBirthdate(birthdate);
        this.age = calculateAge(birthdate);
    }

    private int calculateAge(LocalDate birthdate) {
        LocalDate now = LocalDate.now();
        return now.getYear() - birthdate.getYear() - (now.getDayOfYear() < birthdate.getDayOfYear() ? 1 : 0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        throwIfFalse(() -> isValidName(name));
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        throwIfFalse(() -> birthdate != null && birthdate.isBefore(LocalDate.now()));
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public abstract long getIncome();
}
