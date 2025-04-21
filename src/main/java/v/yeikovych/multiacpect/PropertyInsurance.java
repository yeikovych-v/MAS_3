package v.yeikovych.multiacpect;

import v.yeikovych.multiacpect.payment.PaymentStructure;

import java.math.BigDecimal;

import static v.yeikovych.util.ValidationUtils.*;

public class PropertyInsurance extends InsuranceProduct {
    private BigDecimal propertyValue;
    private final BigDecimal coverageValue;
    private final PaymentStructure paymentStructure;

    public PropertyInsurance(
            String name,
            String description,
            String termsAndConditions,
            BigDecimal propertyValue,
            BigDecimal coverageValue,
            PaymentStructure paymentStructure
    ) {
        super(name, description, termsAndConditions);
        throwIfFalse(() -> isPositive(coverageValue), "Coverage value must be positive");
        throwIfFalse(() -> paymentStructure != null, "Payment structure must not be null");

        setPropertyValue(propertyValue);
        this.coverageValue = coverageValue;
        this.paymentStructure = paymentStructure;
    }

    public BigDecimal getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(BigDecimal propertyValue) {
        throwIfFalse(() -> isPositive(propertyValue), "Property value must be positive");
        this.propertyValue = propertyValue;
    }

    public BigDecimal getCoverageValue() {
        return coverageValue;
    }

    public PaymentStructure getPaymentStructure() {
        return paymentStructure;
    }
}
