package v.yeikovych.multiacpect.payment;


import java.math.BigDecimal;
import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public class AnnualPaymentStructure implements PaymentStructure {

    private BigDecimal annualPremium;
    private final LocalDate renewalDate;
    private int discountPercentage;

    public AnnualPaymentStructure(BigDecimal annualPremium, int discountPercentage) {
        setAnnualPremium(annualPremium);
        this.renewalDate = LocalDate.now();
        setDiscountPercentage(discountPercentage);
    }

    private void setDiscountPercentage(int discountPercentage) {
        throwIfFalse(() -> isValidDiscountPercentage(discountPercentage));
        this.discountPercentage = discountPercentage;
    }

    private boolean isValidDiscountPercentage(int discountPercentage) {
        return discountPercentage >= 0 && discountPercentage <= 35;
    }

    private void setAnnualPremium(BigDecimal annualPremium) {
        throwIfFalse(() -> isPositive(annualPremium));
        this.annualPremium = annualPremium;
    }

    @Override
    public BigDecimal calculatePremium() {
        var discount = annualPremium.multiply(BigDecimal.valueOf(discountPercentage / 100));
        return annualPremium.subtract(discount);
    }

    public BigDecimal getAnnualPremium() {
        return annualPremium;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }
}
