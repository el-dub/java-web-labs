package com.kpi.payments.domain;

public class Card extends AbstractEntity {

    private User user;
    private Account account;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", user=" + user +
                ", account=" + account +
                '}';
    }

    public static Builder builder() {
        return new Card().new Builder();
    }

    public class Builder {

        private Builder() {
        }

        public Builder id(Long id) {
            Card.this.id = id;
            return this;
        }

        public Builder user(User user) {
            Card.this.user = user;
            return this;
        }

        public Builder account(Account account) {
            Card.this.account = account;
            return this;
        }

        public Card build() {
            return Card.this;
        }
    }
}
