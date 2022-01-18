package com.kpi.payments.dao;

import com.kpi.payments.domain.Role;
import com.kpi.payments.domain.User;
import com.kpi.payments.utils.ConnectionProvider;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDao implements Dao<User> {

    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String GET_ALL_USERS = "SELECT * FROM users";
    private static final String CREATE_USER = "INSERT INTO users VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET " +
            "first_name = ?, " +
            "middle_name = ?, " +
            "last_name = ?, " +
            "date_of_birth = ?, " +
            "email = ?, " +
            "phone = ?, " +
            "role = ?, " +
            "password = ?, " +
            "locked = ? " +
            "WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String GET_USER_BY_ID_AND_PASSWORD = "SELECT * FROM users WHERE email = ? AND password = ?";
    
    @Override
    public Optional<User> getById(long id) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(GET_USER_BY_ID)) {
            statement.setLong(1, id);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(GET_ALL_USERS)) {
            while (resultSet.next()) {
                users.add(mapResultSetToUser(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void save(User user) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(CREATE_USER, RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getMiddleName());
            statement.setString(3, user.getLastName());
            statement.setDate(4, new Date(user.getDateOfBirth().getTime()));
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhone());
            statement.setString(7, user.getRole().toString());
            statement.setString(8, user.getPassword());
            statement.setBoolean(9, user.getLocked());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong("id"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(UPDATE_USER)) {
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getMiddleName());
            statement.setString(3, user.getLastName());
            statement.setDate(4, new Date(user.getDateOfBirth().getTime()));
            statement.setString(5, user.getEmail());
            statement.setString(6, user.getPhone());
            statement.setString(7, user.getRole().toString());
            statement.setString(8, user.getPassword());
            statement.setBoolean(9, user.getLocked());
            statement.setLong(10, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User user) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(DELETE_USER)) {
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> getByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionProvider.getConnection();
             var statement = connection.prepareStatement(GET_USER_BY_ID_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, password);
            try (var resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSetToUser(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private User mapResultSetToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong("id"))
                .firstName(resultSet.getString("first_name"))
                .middleName(resultSet.getString("middle_name"))
                .lastName(resultSet.getString("last_name"))
                .dateOfBirth(resultSet.getDate("date_of_birth"))
                .email(resultSet.getString("email"))
                .phone(resultSet.getString("phone"))
                .role(Role.valueOf(resultSet.getString("role")))
                .password(resultSet.getString("password"))
                .isLocked(resultSet.getBoolean("locked"))
                .build();
    }
}
