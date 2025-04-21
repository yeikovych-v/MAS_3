package v.yeikovych.multiacpect;

import v.yeikovych.multiacpect.payment.PaymentStructure;
import v.yeikovych.util.ValidationUtils;

import java.math.BigDecimal;

import static v.yeikovych.util.ValidationUtils.*;

public class HealthInsurance extends InsuranceProduct {

    private final String coverageNetwork;
    private final PaymentStructure paymentStructure;

    public HealthInsurance(String name, String description, String termsAndConditions, String coverageNetwork, PaymentStructure paymentStructure) {
        super(name, description, termsAndConditions);
        throwIfFalse(() -> isValidString(coverageNetwork), "Coverage network must not be null or empty");
        throwIfFalse(() -> paymentStructure != null, "Payment structure must not be null");

        this.coverageNetwork = coverageNetwork;
        this.paymentStructure = paymentStructure;
    }

    public String getCoverageNetwork() {
        return coverageNetwork;
    }

    public PaymentStructure getPaymentStructure() {
        return paymentStructure;
    }

    public BigDecimal calculatePremium() {
        return paymentStructure.calculatePremium();
    }
}
