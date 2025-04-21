package v.yeikovych.polymorphism;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class TransactionBook {

    private static Map<UUID, BigDecimal> processedTransactions = new HashMap<>();

    public static void add(UUID transactionId, BigDecimal amount) {
        if (transactionId == null || amount == null) {
            throw new IllegalArgumentException("Transaction ID and amount cannot be null");
        }

        processedTransactions.put(transactionId, amount);
    }

    public static BigDecimal getAmount(UUID transactionId) {
        return processedTransactions.get(transactionId);
    }
}
