package v.yeikovych.multiacpect;


import java.time.LocalDate;

import static v.yeikovych.util.ValidationUtils.*;

public abstract class InsuranceProduct {

    private String name;
    private String description;
    private LocalDate issueDate;
    private String termsAndConditions;

    public InsuranceProduct(String name, String description, String termsAndConditions) {
        setTermsAndConditions(termsAndConditions);
        this.name = name;
        this.description = description;
        this.issueDate = LocalDate.now();
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        throwIfFalse(() -> isValidString(termsAndConditions));
        this.termsAndConditions = termsAndConditions;
    }

    public String getName() {
        return name;
    }
}
