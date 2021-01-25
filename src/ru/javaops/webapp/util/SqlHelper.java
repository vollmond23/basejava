package ru.javaops.webapp.util;

import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.sql.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private SqlHelper() {
    }

    public static void executeCodeWith(ConnectionFactory connectionFactory, String queryString, BlockOfCode code) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(queryString)) {
            code.execute(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
