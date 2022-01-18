package com.kpi.payments.domain;

import java.io.Serializable;
import java.util.Date;

public class User extends AbstractEntity implements Serializable {

    private String firstName;
    private String middleName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String phone;
    private Role role;
    private String password;
    private boolean isLocked;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", locked=" + isLocked +
                '}';
    }

    public static Builder builder() {
        return new User().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder id(Long id) {
            User.this.id = id;
            return this;
        }

        public Builder firstName(String firstName) {
            User.this.firstName = firstName;
            return this;
        }

        public Builder middleName(String middleName) {
            User.this.middleName = middleName;
            return this;
        }

        public Builder lastName(String lastName) {
            User.this.lastName = lastName;
            return this;
        }

        public Builder dateOfBirth(Date dateOfBirth) {
            User.this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder email(String email) {
            User.this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            User.this.phone = phone;
            return this;
        }

        public Builder role(Role role) {
            User.this.role = role;
            return this;
        }

        public Builder password(String password) {
            User.this.password = password;
            return this;
        }

        public Builder isLocked(boolean isLocked) {
            User.this.isLocked = isLocked;
            return this;
        }

        public User build() {
            return User.this;
        }
    }
}
