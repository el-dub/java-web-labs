package com.kpi.payments.dao;

import com.kpi.payments.domain.Account;
import com.kpi.payments.utils.ConnectionProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class AccountDao implements Dao<Account> {

    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE id = ?";
    private static final String GET_ALL_ACCOUNTS = "SELECT * FROM accounts";
    private static final String CREATE_ACCOUNT = "INSERT INTO accounts VALUES (DEFAULT, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT = "UPDATE accounts SET " +
            "name = ?, " +
            "money_amount = ?, " +
            "locked = ? " +
            "WHERE id = ?";
    private static final String DELETE_ACCOUNT = "DELETE FROM accounts WHERE id = ?";
    private static final String GET_ALL_ACCOUNTS_BY_USER_ID =
            "SELECT accounts.id, accounts.name, accounts.money_amount, accounts.locked FROM accounts " +
            "INNER JOIN cards c on accounts.id = c.account_id " +
            "INNER JOIN users u on u.id = c.user_id " +
            "WHERE u.id = ?";

    @Override
    public Optional<Account> getById(long id) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(GET_ACCOUNT_BY_ID)) {
            statement.setLong(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToAccount(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = new ArrayList<>();
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(GET_ALL_ACCOUNTS)) {
            while (resultSet.next()) {
                accounts.add(mapResultSetToAccount(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;

    }

    @Override
    public void save(Account account) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(CREATE_ACCOUNT, RETURN_GENERATED_KEYS)) {
            statement.setString(1, account.getName());
            statement.setBigDecimal(2, account.getMoneyAmount());
            statement.setBoolean(3, account.getLocked());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getLong("id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Account account) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(UPDATE_ACCOUNT)) {
            statement.setString(1, account.getName());
            statement.setBigDecimal(2, account.getMoneyAmount());
            statement.setBoolean(3, account.getLocked());
            statement.setLong(4, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Account account) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(DELETE_ACCOUNT)) {
            statement.setLong(1, account.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Account> getAllByUserId(long userId) {
        List<Account> accounts = new ArrayList<>();
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(GET_ALL_ACCOUNTS_BY_USER_ID)) {
            statement.setLong(1, userId);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    accounts.add(mapResultSetToAccount(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private Account mapResultSetToAccount(ResultSet resultSet) throws SQLException {
        return Account.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .moneyAmount(resultSet.getBigDecimal("money_amount"))
                .isLocked(resultSet.getBoolean("locked"))
                .build();
    }
}
