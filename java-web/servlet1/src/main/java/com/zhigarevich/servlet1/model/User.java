package com.zhigarevich.servlet1.model;

public class User extends AbstractEntity {
    private int id;
    private String username;
    private String password;
    private String email;
    private String verificationToken;
    private boolean verified;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User other = (User) obj;
            return id == other.id &&
                    (username != null ? username.equals(other.username) : other.username == null) &&
                    (email != null ? email.equals(other.email) : other.email == null);
        }
        return false;
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(username).append(email);
        return sb.toString().hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("User{id=").append(id)
                .append(", username='").append(username).append('\'')
                .append(", email='").append(email).append('\'')
                .append(", verified=").append(verified)
                .append('}');
        return sb.toString();
    }
}