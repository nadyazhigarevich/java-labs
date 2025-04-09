package com.zhigarevich.servlet1.model;

public class PhoneBookEntry {
    private int id;
    private int userId;
    private String contactName;
    private String phoneNumber;

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getContactName() {
        return contactName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PhoneBookEntry) {
            PhoneBookEntry other = (PhoneBookEntry) obj;
            return id == other.id &&
                    userId == other.userId &&
                    (contactName != null ? contactName.equals(other.contactName) : other.contactName == null) &&
                    (phoneNumber != null ? phoneNumber.equals(other.phoneNumber) : other.phoneNumber == null);
        }
        return false;
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(userId).append(contactName).append(phoneNumber);
        return sb.toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PhoneBookEntry{id=").append(id)
                .append(", userId=").append(userId)
                .append(", contactName='").append(contactName).append('\'')
                .append(", phoneNumber='").append(phoneNumber).append('\'')
                .append('}');
        return sb.toString();
    }
}
