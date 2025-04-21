package v.yeikovych.polymorphism;

import static v.yeikovych.util.ValidationUtils.throwIfFalse;

public class BankTransferProcessor extends AbstractPaymentProcessor{
    private String accountNumber;
    private String accountBankCode;
    private String routingNumber;
    private String routingBankCode;
    private String bankName;


    @Override
    public void processPayment() {
        this.status = PaymentStatus.FAILED;
        validateTransaction();
        this.status = PaymentStatus.PROCESSING;
        // Send Money
        var balanceAfterFee = BankTransactionService.getSwiftFee(accountBankCode, routingBankCode, amount);
        TransactionBook.add(transactionId, balanceAfterFee);
        this.status = PaymentStatus.COMPLETED;
    }


    @Override
    protected void validateTransaction() {
        super.validateTransaction();
        validateInitialDetails();
        validateIBANs();
        BankTransactionService.verifyBankName(bankName, accountBankCode);
    }

    private void validateIBANs() {
        throwIfFalse(() -> accountNumber.length() == 34, "Invalid Account Number");
        throwIfFalse(() -> routingNumber.length() == 34, "Invalid Account Number");
        throwIfFalse(() -> routingNumber.substring(0, 2).equals(routingBankCode), "Routing number does not match routing bank");
        throwIfFalse(() -> accountNumber.substring(0, 2).equals(accountBankCode), "Account number does not match account bank");
    }

    public void addPaymentDetails(String accountBank, String accountNumber, String routingBank, String routingNumber, String bankName) {
        validateInitialDetails();

        this.accountNumber = accountNumber;
        this.accountBankCode = accountBank;
        this.routingNumber = routingNumber;
        this.routingBankCode = routingBank;
        this.bankName = bankName;
    }

    private void validateInitialDetails() {
        throwIfFalse(() -> accountNumber != null && !accountNumber.isEmpty(), "Account Number is required");
        throwIfFalse(() -> accountBankCode != null && !accountBankCode.isEmpty(), "Account Bank is required");
        throwIfFalse(() -> routingNumber != null && !routingNumber.isEmpty(), "Routing Number is required");
        throwIfFalse(() -> routingBankCode != null && !routingBankCode.isEmpty(), "Routing Bank is required");
        throwIfFalse(() -> bankName != null && !bankName.isEmpty(), "Bank Name is required");
    }
}
