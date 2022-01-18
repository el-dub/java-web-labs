package com.kpi.payments.dao;

import com.kpi.payments.domain.Account;
import com.kpi.payments.domain.Card;
import com.kpi.payments.domain.User;
import com.kpi.payments.utils.ConnectionProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class CardDao implements Dao<Card> {

    private static final String GET_CARD_BY_ID = "SELECT * FROM cards WHERE id = ?";
    private static final String GET_ALL_CARDS = "SELECT * FROM cards";
    private static final String CREATE_CARD = "INSERT INTO cards VALUES (DEFAULT, ?, ?)";
    private static final String UPDATE_CARD = "UPDATE cards SET " +
            "user_id = ?, " +
            "account_id = ? " +
            "WHERE id = ?";
    private static final String DELETE_CARD = "DELETE FROM cards WHERE id = ?";
    
    @Override
    public Optional<Card> getById(long id) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(GET_CARD_BY_ID)) {
            statement.setLong(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToCard(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Card> getAll() {
        List<Card> cards = new ArrayList<>();
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(GET_ALL_CARDS)) {
            while (resultSet.next()) {
                cards.add(mapResultSetToCard(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public void save(Card card) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(CREATE_CARD, RETURN_GENERATED_KEYS)) {
            statement.setLong(1, card.getUser().getId());
            statement.setLong(2, card.getAccount().getId());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    card.setId(generatedKeys.getLong("id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Card card) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(UPDATE_CARD)) {
            statement.setLong(1, card.getUser().getId());
            statement.setLong(2, card.getAccount().getId());
            statement.setLong(3, card.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Card card) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(DELETE_CARD)) {
            statement.setLong(1, card.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Card mapResultSetToCard(ResultSet resultSet) throws SQLException {
        var user = new User();
        var account = new Account();
        user.setId(resultSet.getLong("user_id"));
        account.setId(resultSet.getLong("account_id"));
        return Card.builder()
                .id(resultSet.getLong("id"))
                .user(user)
                .account(account)
                .build();
    }
}
