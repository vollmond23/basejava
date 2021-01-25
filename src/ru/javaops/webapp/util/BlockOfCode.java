package ru.javaops.webapp.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface BlockOfCode {
    void execute(PreparedStatement preparedStatement) throws SQLException;
}
