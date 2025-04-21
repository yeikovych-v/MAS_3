package v.yeikovych.multiacpect.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public class MonthlyPaymentStructure implements PaymentStructure {

    private BigDecimal monthlyPremium;
    private int dueDay;
    private int percentagePremiumIfLate = 1;

    public MonthlyPaymentStructure(BigDecimal monthlyPremium, int dueDay, int percentagePremiumIfLate) {
        setMonthlyPremium(monthlyPremium);
        setDueDay(dueDay);
        setPercentagePremiumIfLate(percentagePremiumIfLate);
    }

    @Override
    public BigDecimal calculatePremium() {
        int daysLate = getDaysLate();
        int percentage = percentagePremiumIfLate * daysLate;
        if (percentage == 0) return monthlyPremium;
        return monthlyPremium.multiply(BigDecimal.valueOf(percentage));
    }

    public BigDecimal getMonthlyPremium() {
        return monthlyPremium;
    }

    public void setMonthlyPremium(BigDecimal monthlyPremium) {
        throwIfFalse(() -> isPositive(monthlyPremium));
        this.monthlyPremium = monthlyPremium;
    }

    public int getDueDay() {
        return dueDay;
    }

    public void setDueDay(int dueDay) {
        throwIfFalse(() -> isValidDayOfMonth(dueDay));
        this.dueDay = dueDay;
    }

    public int getPercentagePremiumIfLate() {
        return percentagePremiumIfLate;
    }

    public void setPercentagePremiumIfLate(int percentagePremiumIfLate) {
        throwIfFalse(() -> percentagePremiumIfLate >= 0);
        this.percentagePremiumIfLate = percentagePremiumIfLate;
    }

    public int getDaysLate() {
        return Math.max(LocalDate.now().getDayOfMonth() - dueDay, 0);
    }
}
