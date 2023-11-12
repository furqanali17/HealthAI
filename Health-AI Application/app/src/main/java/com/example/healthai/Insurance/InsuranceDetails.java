package com.example.healthai.Insurance;

public class InsuranceDetails {
    String insuranceCompany, insuranceYear, policyNumber, typeOfInsurance, subscriberID, groupNumber, insurancePhone;

    public InsuranceDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(InsuranceDetails.class)
    }

    public InsuranceDetails(String insuranceCompany, String insuranceYear, String policyNumber, String typeOfInsurance,
                            String subscriberID, String groupNumber, String insurancePhone) {
        this.insuranceCompany = insuranceCompany;
        this.insuranceYear = insuranceYear;
        this.policyNumber = policyNumber;
        this.typeOfInsurance = typeOfInsurance;
        this.subscriberID = subscriberID;
        this.groupNumber = groupNumber;
        this.insurancePhone = insurancePhone;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public String getInsuranceYear() {
        return insuranceYear;
    }

    public void setInsuranceYear(String insuranceYear) {
        this.insuranceYear = insuranceYear;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getTypeOfInsurance() {
        return typeOfInsurance;
    }

    public void setTypeOfInsurance(String typeOfInsurance) {
        this.typeOfInsurance = typeOfInsurance;
    }

    public String getSubscriberID() {
        return subscriberID;
    }

    public void setSubscriberID(String subscriberID) {
        this.subscriberID = subscriberID;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getInsurancePhone() {
        return insurancePhone;
    }

    public void setInsurancePhone(String insurancePhone) {
        this.insurancePhone = insurancePhone;
    }

}
