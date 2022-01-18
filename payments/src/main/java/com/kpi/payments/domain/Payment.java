package com.kpi.payments.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Payment extends AbstractEntity {

    private Account sender;
    private Account recipient;
    private Timestamp time;
    private BigDecimal amount;
    private PaymentStatus status;

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getRecipient() {
        return recipient;
    }

    public void setRecipient(Account recipient) {
        this.recipient = recipient;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", time=" + time +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }

    public static Builder builder() {
        return new Payment().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder id(Long id) {
            Payment.this.id = id;
            return this;
        }

        public Builder sender(Account sender) {
            Payment.this.sender = sender;
            return this;
        }

        public Builder recipient(Account recipient) {
            Payment.this.recipient = recipient;
            return this;
        }

        public Builder time(Timestamp time) {
            Payment.this.time = time;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            Payment.this.amount = amount;
            return this;
        }

        public Builder status(PaymentStatus status) {
            Payment.this.status = status;
            return this;
        }

        public Payment build() {
            return Payment.this;
        }
    }
}
