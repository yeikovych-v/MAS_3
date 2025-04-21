package v.yeikovych.dynamic;

import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public class Customer {
    // Customer fields
    private String name;
    private LocalDate registrationDate;
    private CustomerType customerType;
    private boolean isSuspended;

    // Trial Customer Fields
    private LocalDate trialStartDate;
    private int totalTrialDays;
    private boolean wasTrialActivated;

    // Premium Customer Fields
    private int subscriptionPriceUSD;
    private String billingInfo;
    private LocalDate activeSince;

    // Inactive customer Fields
    private LocalDate inactiveSince;
    private String reasonForInactivity = "New Customer";

    public Customer(String name) {
        this.name = name;
        this.registrationDate = LocalDate.now();
    }

    public void startTrial() {
        if (this.customerType == CustomerType.TRIAL || this.wasTrialActivated) {
            throw new IllegalStateException("Customer is already in Trial or has activated a trial before.");
        }

        if (this.isSuspended) {
            throw new IllegalStateException("Customer is suspended.");
        }

        this.customerType = CustomerType.TRIAL;
        this.trialStartDate = LocalDate.now();
        this.totalTrialDays = 30;
        this.wasTrialActivated = true;
    }

    public void activatePremium(String billingInfo) {
        if (this.customerType == CustomerType.PREMIUM) {
            throw new IllegalStateException("Customer is already a Premium member.");
        }

        if (this.isSuspended) {
            throw new IllegalStateException("Customer is suspended.");
        }

        this.customerType = CustomerType.PREMIUM;
        this.activeSince = LocalDate.now();
        this.billingInfo = billingInfo;
        this.subscriptionPriceUSD = 10;
    }

    public void deactivate(String reasonForInactivity) {
        if (this.customerType == CustomerType.INACTIVE) {
            throw new IllegalStateException("Customer is already inactive.");
        }

        this.customerType = CustomerType.INACTIVE;
        this.inactiveSince = LocalDate.now();
        this.reasonForInactivity = reasonForInactivity;
    }

    public void bill() {
        if (this.customerType != CustomerType.PREMIUM) {
            throw new UnsupportedOperationException("Customer is not a Premium member.");
        }

        if (this.isSuspended) {
            throw new IllegalStateException("Customer is suspended.");
        }

        System.out.println("Billing " + this.name + " for " + this.subscriptionPriceUSD + " USD.");
    }

    public void showAdds() {
        if (this.customerType != CustomerType.TRIAL) {
            throw new UnsupportedOperationException("Customer is not a Trial member.");
        }

        if (this.trialStartDate.plusDays(this.totalTrialDays).isBefore(LocalDate.now())) {
            throw new UnsupportedOperationException("Customer trial had ended.");
        }

        if (this.isSuspended) {
            throw new IllegalStateException("Customer is suspended.");
        }

        System.out.println("Showing ads to " + this.name + ".");
    }

    public void setSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        throwIfFalse(() -> isValidName(name));
        this.name = name;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public LocalDate getTrialStartDate() {
        return trialStartDate;
    }

    public int getTotalTrialDays() {
        return totalTrialDays;
    }

    public boolean isWasTrialActivated() {
        return wasTrialActivated;
    }

    public int getSubscriptionPriceUSD() {
        return subscriptionPriceUSD;
    }

    public String getBillingInfo() {
        return billingInfo;
    }

    public void setBillingInfo(String billingInfo) {
        throwIfFalse(() -> isValidString(billingInfo));
        this.billingInfo = billingInfo;
    }

    public LocalDate getActiveSince() {
        return activeSince;
    }

    public LocalDate getInactiveSince() {
        return inactiveSince;
    }

    public String getReasonForInactivity() {
        return reasonForInactivity;
    }
}
