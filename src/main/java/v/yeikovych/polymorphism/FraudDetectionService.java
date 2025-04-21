package v.yeikovych.polymorphism;

import java.util.HashSet;
import java.util.Set;

import static v.yeikovych.util.ValidationUtils.throwIfFalse;

public final class FraudDetectionService {

    private static Set<String> hijackedCC = new HashSet<>();

    public static void addCreditCard(String ccNumber) {
        throwIfFalse(() -> ccNumber != null && !ccNumber.isEmpty(), "Credit card number cannot be null or empty");
        hijackedCC.add(ccNumber);
    }

    public static void removeCreditCard(String ccNumber) {
        throwIfFalse(() -> ccNumber != null && !ccNumber.isEmpty(), "Credit card number cannot be null or empty");
        hijackedCC.remove(ccNumber);
    }

    public static boolean isCreditCardHijacked(String ccNumber) {
        throwIfFalse(() -> ccNumber != null && !ccNumber.isEmpty(), "Credit card number cannot be null or empty");
        return hijackedCC.contains(ccNumber);
    }

    public static boolean checkForFraud(String cardNumber, String cvv, String expiryDate) {
        if (isCreditCardHijacked(cardNumber)) {
            return true;
        }
        if (cvv.equals("123")) {
            return true;
        }
        if (expiryDate.equals("12/23")) {
            return true;
        }
        return false;
    }
}
