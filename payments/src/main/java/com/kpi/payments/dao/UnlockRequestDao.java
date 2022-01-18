package com.kpi.payments.dao;

import com.kpi.payments.domain.Account;
import com.kpi.payments.domain.UnlockRequest;
import com.kpi.payments.utils.ConnectionProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UnlockRequestDao implements Dao<UnlockRequest> {

    private static final String GET_ALL_UNLOCK_REQUESTS = "SELECT * FROM unlock_requests";
    private static final String CREATE_UNLOCK_REQUEST = "INSERT INTO unlock_requests VALUES (DEFAULT, ?, ?)";


    @Override
    public Optional<UnlockRequest> getById(long id) {
        return Optional.empty();
    }

    @Override
    public List<UnlockRequest> getAll() {
        List<UnlockRequest> unlockRequests = new ArrayList<>();
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(GET_ALL_UNLOCK_REQUESTS)) {
            while (resultSet.next()) {
                unlockRequests.add(mapResultSetToUnlockRequest(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return unlockRequests;
    }

    @Override
    public void save(UnlockRequest unlockRequest) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(CREATE_UNLOCK_REQUEST, RETURN_GENERATED_KEYS)) {
            statement.setLong(1, unlockRequest.getAccount().getId());
            statement.setBoolean(2, unlockRequest.isActive());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    unlockRequest.setId(generatedKeys.getLong("id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(UnlockRequest unlockRequest) {

    }

    @Override
    public void delete(UnlockRequest unlockRequest) {

    }

    private UnlockRequest mapResultSetToUnlockRequest(ResultSet resultSet) throws SQLException {
        var account = new Account();
        account.setId(resultSet.getLong("account_id"));
        UnlockRequest unlockRequest = new UnlockRequest();
        unlockRequest.setAccount(account);
        unlockRequest.setId(resultSet.getLong("id"));
        unlockRequest.setActive(resultSet.getBoolean("active"));
        return unlockRequest;
    }
}
