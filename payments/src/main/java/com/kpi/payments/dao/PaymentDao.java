package com.kpi.payments.dao;

import com.kpi.payments.domain.Account;
import com.kpi.payments.domain.Payment;
import com.kpi.payments.domain.PaymentStatus;
import com.kpi.payments.utils.ConnectionProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class PaymentDao implements Dao<Payment> {

    private static final String GET_PAYMENT_BY_ID = "SELECT * FROM payments WHERE id = ?";
    private static final String GET_ALL_PAYMENTS = "SELECT * FROM payments";
    private static final String CREATE_PAYMENT = "INSERT INTO payments VALUES (DEFAULT, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PAYMENT = "UPDATE payments SET " +
            "sender_account_id = ?, " +
            "recipient_account_id = ?, " +
            "time = ?, " +
            "amount = ?, " +
            "status = ? " +
            "WHERE id = ?";
    private static final String DELETE_PAYMENT = "DELETE FROM payments WHERE id = ?";
    private static final String GET_ALL_PAYMENTS_BY_USER_ID =
            "SELECT " +
            "       payments.id, " +
            "       payments.sender_account_id, " +
            "       payments.recipient_account_id, " +
            "       payments.time, " +
            "       payments.amount, " +
            "       payments.status " +
            "FROM payments " +
            "INNER JOIN accounts a on a.id = payments.recipient_account_id " +
            "INNER JOIN cards c on a.id = c.account_id " +
            "INNER JOIN users u on u.id = c.user_id " +
            "WHERE user_id = ?";

    @Override
    public Optional<Payment> getById(long id) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(GET_PAYMENT_BY_ID)) {
            statement.setLong(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToPayment(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Payment> getAll() {
        List<Payment> payments = new ArrayList<>();
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(GET_ALL_PAYMENTS)) {
            while (resultSet.next()) {
                payments.add(mapResultSetToPayment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    @Override
    public void save(Payment payment) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(CREATE_PAYMENT, RETURN_GENERATED_KEYS)) {
            statement.setLong(1, payment.getSender().getId());
            statement.setLong(2, payment.getRecipient().getId());
            statement.setTimestamp(3, payment.getTime());
            statement.setBigDecimal(4, payment.getAmount());
            statement.setString(5, payment.getStatus().toString());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    payment.setId(generatedKeys.getLong("id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Payment payment) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(UPDATE_PAYMENT)) {
            statement.setLong(1, payment.getSender().getId());
            statement.setLong(2, payment.getRecipient().getId());
            statement.setTimestamp(3, payment.getTime());
            statement.setBigDecimal(4, payment.getAmount());
            statement.setString(5, payment.getStatus().toString());
            statement.setLong(6, payment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Payment payment) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(DELETE_PAYMENT)) {
            statement.setLong(1, payment.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Payment> getAllByUserId(long id) {
        List<Payment> payments = new ArrayList<>();
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(GET_ALL_PAYMENTS_BY_USER_ID)) {
            statement.setLong(1, id);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    payments.add(mapResultSetToPayment(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    private Payment mapResultSetToPayment(ResultSet resultSet) throws SQLException {
        var sender = new Account();
        var recipient = new Account();
        sender.setId(resultSet.getLong("sender_account_id"));
        recipient.setId(resultSet.getLong("recipient_account_id"));
        return Payment.builder()
                .id(resultSet.getLong("id"))
                .sender(sender)
                .recipient(recipient)
                .time(resultSet.getTimestamp("time"))
                .amount(resultSet.getBigDecimal("amount"))
                .status(PaymentStatus.valueOf(resultSet.getString("status")))
                .build();
    }
}
