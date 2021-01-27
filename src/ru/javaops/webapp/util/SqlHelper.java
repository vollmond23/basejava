package ru.javaops.webapp.util;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public void executeCodeWith(String queryString, BlockOfCode code) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(queryString)) {
            code.execute(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                String eMessage = e.getMessage();
                String uuid = eMessage.substring(eMessage.lastIndexOf("(") + 1, eMessage.lastIndexOf(")"));
                throw new ExistStorageException(uuid);
            }
            throw new StorageException(e);
        }
    }
}
