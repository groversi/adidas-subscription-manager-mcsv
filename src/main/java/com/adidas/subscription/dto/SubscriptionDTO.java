package com.adidas.subscription.dto;

public class SubscriptionDTO {

    private String email;
    private String firstName;
    private String gender;
    private String dateOfBirth;
    private Boolean subscriptionConsent;
    private Integer campaignId;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Boolean getSubscriptionConsent() {
        return subscriptionConsent;
    }

    public Integer getCampaignId() {
        return campaignId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setSubscriptionConsent(Boolean subscriptionConsent) {
        this.subscriptionConsent = subscriptionConsent;
    }

    public void setCampaignId(Integer campaignId) {
        this.campaignId = campaignId;
    }
}
