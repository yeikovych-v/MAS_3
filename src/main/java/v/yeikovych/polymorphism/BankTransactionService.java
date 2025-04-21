package v.yeikovych.polymorphism;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public final class BankTransactionService {

    private static Map<String, String> recognizedBanks = new HashMap<>();
    private static Map<String, Map<String, BigDecimal>> swiftFeesPercent = new HashMap<>();

    static {
        recognizedBanks.put("MONO", "UA");
        recognizedBanks.put("PKO", "PL");
        recognizedBanks.put("CITI", "US");

        var plFees = new HashMap<String, BigDecimal>();
        plFees.put("UA", BigDecimal.valueOf(.02));
        plFees.put("US", BigDecimal.valueOf(.15));
        plFees.put("PL", BigDecimal.ZERO);

        swiftFeesPercent.put("PL", plFees);

        var uaFees = new HashMap<String, BigDecimal>();
        plFees.put("UA", BigDecimal.valueOf(0.));
        plFees.put("US", BigDecimal.valueOf(.20));
        plFees.put("PL", BigDecimal.valueOf(.02));

        swiftFeesPercent.put("UA", uaFees);

        var usFees = new HashMap<String, BigDecimal>();
        plFees.put("UA", BigDecimal.valueOf(.32));
        plFees.put("US", BigDecimal.valueOf(0.));
        plFees.put("PL", BigDecimal.valueOf(.30));

        swiftFeesPercent.put("US", usFees);
    }

    public static BigDecimal getSwiftFee(String senderBank, String receiverBank, BigDecimal amount) {
        if (recognizedBanks.containsKey(senderBank) && recognizedBanks.containsKey(receiverBank)) {
            var swiftFee = swiftFeesPercent.get(senderBank).get(receiverBank);
            var fee = BigDecimal.ZERO;
            if (swiftFee.compareTo(BigDecimal.ZERO) != 0) {
                fee = amount.multiply(swiftFee);
            }
            return amount.subtract(fee);
        } else {
            throw new BankTransactionException("Unknown Bank");
        }
    }

    public static void verifyBankName(String bankName, String accountBankCode) {
        if (!recognizedBanks.containsKey(bankName)) {
            throw new BankTransactionException("Unknown Bank");
        }
        if (!recognizedBanks.get(bankName).equals(accountBankCode)) {
            throw new BankTransactionException("Bank name does not match account bank code");
        }
        if (!recognizedBanks.containsValue(accountBankCode)) {
            throw new BankTransactionException("Bank Code is not recognized");
        }
    }
}
