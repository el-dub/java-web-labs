package com.kpi.payments.domain;

public class UnlockRequest extends AbstractEntity {

    Account account;
    boolean active;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "UnlockRequest{" +
                "id=" + id +
                ", account=" + account +
                ", active=" + active +
                '}';
    }
}
