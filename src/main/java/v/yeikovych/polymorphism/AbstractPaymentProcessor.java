package v.yeikovych.polymorphism;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static v.yeikovych.util.ValidationUtils.*;

public abstract class AbstractPaymentProcessor {

    protected UUID transactionId;
    protected BigDecimal amount;
    protected LocalDateTime timeStamp;
    protected PaymentStatus status;

    public abstract void processPayment();

    protected void validateAmount() {
        throwIfFalse(() -> isPositiveOrZero(amount));
    }

    protected void validateTransaction() {
        throwIfFalse(() -> transactionId != null);
        validateAmount();
    }

    public String getTransactionDetails() {
        return String.format("Transaction ID: %s, Amount: %s, Timestamp: %s, Status: %s",
                transactionId, amount, timeStamp, status);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPaymentProcessor that = (AbstractPaymentProcessor) o;
        return Objects.equals(transactionId, that.transactionId) && Objects.equals(amount, that.amount) && Objects.equals(timeStamp, that.timeStamp) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, amount, timeStamp, status);
    }

    @Override
    public String toString() {
        return getTransactionDetails();
    }
}
