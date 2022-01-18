package com.kpi.payments.service;

import com.kpi.payments.dao.PaymentDao;
import com.kpi.payments.domain.Payment;

import java.util.List;
import java.util.Optional;

public class PaymentService {

    private final PaymentDao dao = new PaymentDao();

    public void save(Payment payment) {
        dao.save(payment);
    }

    public Optional<Payment> getById(long id) {
        return dao.getById(id);
    }

    public List<Payment> getAllByUserId(long id) {
        return dao.getAllByUserId(id);
    }

    public void update(Payment payment) {
        dao.update(payment);
    }
}
