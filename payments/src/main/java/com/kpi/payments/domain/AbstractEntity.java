package com.kpi.payments.domain;

public abstract class AbstractEntity {

    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
