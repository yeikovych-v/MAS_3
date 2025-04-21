package v.yeikovych.multiacpect;

import v.yeikovych.multiacpect.payment.PaymentStructure;

import java.math.BigDecimal;

import static v.yeikovych.util.ValidationUtils.*;

public class LifeInsurance extends InsuranceProduct {

    private String beneficiaryName;
    private final BigDecimal deathBenefit;
    private final PaymentStructure paymentStructure;

    public LifeInsurance(
            String name,
            String description,
            String termsAndConditions,
            String beneficiaryName,
            BigDecimal deathBenefit,
            PaymentStructure paymentStructure
    ) {
        super(name, description, termsAndConditions);
        throwIfFalse(() -> isPositive(deathBenefit), "Death benefit must be positive");
        throwIfFalse(() -> paymentStructure != null, "Payment structure must not be null");

        setBeneficiaryName(beneficiaryName);
        this.deathBenefit = deathBenefit;
        this.paymentStructure = paymentStructure;
    }


    public BigDecimal getDeathBenefit() {
        return deathBenefit;
    }

    public PaymentStructure getPaymentStructure() {
        return paymentStructure;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        throwIfFalse(() -> isValidName(beneficiaryName), "Beneficiary name must not be null or empty");
        this.beneficiaryName = beneficiaryName;
    }
}
