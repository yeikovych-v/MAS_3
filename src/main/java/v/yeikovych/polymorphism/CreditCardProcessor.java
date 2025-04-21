package v.yeikovych.polymorphism;

import static v.yeikovych.util.ValidationUtils.isValidName;
import static v.yeikovych.util.ValidationUtils.throwIfFalse;

public class CreditCardProcessor extends AbstractPaymentProcessor{
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private String cardHolderName;

    @Override
    public void processPayment() {
        this.status = PaymentStatus.FAILED;
        validateTransaction();
        performFraudCheck();
        this.status = PaymentStatus.PROCESSING;
        // Send Money
        TransactionBook.add(transactionId, amount);
        this.status = PaymentStatus.COMPLETED;
    }

    private void performFraudCheck() {
        boolean isHijacked = FraudDetectionService.isCreditCardHijacked(cardNumber);
        boolean isFraud = FraudDetectionService.checkForFraud(cardNumber, cvv, expiryDate);
        if (isHijacked || isFraud) {
            throw new FraudDetectionException("Fraud detected for card number: " + cardNumber);
        }
    }

    @Override
    protected void validateTransaction() {
        super.validateTransaction();
        validateInitialDetails();
        throwIfFalse(() -> cardNumber.length() == 16, "Invalid Card Number");
        throwIfFalse(() -> cvv.length() == 3, "Invalid CVV");
        throwIfFalse(() -> expiryDate.length() == 5 && expiryDate.charAt(3) == '/', "Invalid Expiry Date");
        throwIfFalse(() -> isValidName(cardHolderName), "Card holder name is required");
    }

    public void addPaymentDetails(String cardNumber, String expiryDate, String cvv, String cardHolderName) {
        validateInitialDetails();

        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.cardHolderName = cardHolderName;
        this.status = PaymentStatus.PENDING;
    }

    private void validateInitialDetails() {
        throwIfFalse(() -> cardNumber != null && !cardNumber.isEmpty(), "Card number is required");
        throwIfFalse(() -> cvv != null && !cvv.isEmpty(), "CVV is required");
        throwIfFalse(() -> expiryDate != null && !expiryDate.isEmpty(), "Expiry date is required");
        throwIfFalse(() -> cardHolderName != null && !cardHolderName.isEmpty(), "Card holder name is required");
    }
}
