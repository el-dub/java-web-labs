package com.kpi.payments.domain;

import java.math.BigDecimal;

public class Account extends AbstractEntity {

    private String name;
    private BigDecimal moneyAmount;
    private boolean isLocked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(BigDecimal moneyAmount) {
        this.moneyAmount = moneyAmount;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    public void setLocked(Boolean locked) {
        isLocked = locked;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", moneyAmount=" + moneyAmount +
                ", isLocked=" + isLocked +
                '}';
    }

    public static Builder builder() {
        return new Account().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder id(Long id) {
            Account.this.id = id;
            return this;
        }

        public Builder name(String name) {
            Account.this.name = name;
            return this;
        }

        public Builder moneyAmount(BigDecimal moneyAmount) {
            Account.this.moneyAmount = moneyAmount;
            return this;
        }

        public Builder isLocked(boolean isLocked) {
            Account.this.isLocked = isLocked;
            return this;
        }

        public Account build() {
            return Account.this;
        }
    }
}
