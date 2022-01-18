package com.kpi.payments.service;

import com.kpi.payments.dao.UnlockRequestDao;
import com.kpi.payments.domain.UnlockRequest;

import java.util.List;

public class UnlockRequestService {

    private final UnlockRequestDao unlockRequestDao = new UnlockRequestDao();

    public void save(UnlockRequest unlockRequest) {
        unlockRequestDao.save(unlockRequest);
    }

    public List<UnlockRequest> getAll() {
        return unlockRequestDao.getAll();
    }
}
