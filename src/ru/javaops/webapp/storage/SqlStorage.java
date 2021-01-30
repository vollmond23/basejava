package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void save(Resume resume) {
        upsertResume(resume, UpsertType.INSERT);
    }

    @Override
    public void update(Resume resume) {
        upsertResume(resume, UpsertType.UPDATE);
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE r.uuid =?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return fillUpResume(uuid, rs);
                });
    }

    @Override
    public void delete(Resume resume) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            String uuid = resume.getUuid();
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) AS size FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                "   SELECT * FROM resume r " +
                "LEFT JOIN contact c " +
                "       ON r.uuid = c.resume_uuid " +
                " ORDER BY r.full_name, r.uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            rs.next();
            do {
                resumes.add(fillUpResume(rs.getString("uuid"), rs));
            } while (rs.next());
            return resumes;
        });
    }

    private Resume fillUpResume(String uuid, ResultSet rs) throws SQLException {
        Resume resume = new Resume(uuid, rs.getString("full_name"));
        do {
            String value = rs.getString("value");
            if (!rs.getString("uuid").equals(uuid) || value == null) {
                rs.previous();
                break;
            }
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        } while (rs.next());
        return resume;
    }

    private void upsertResume(Resume resume, UpsertType upsertType) {
        sqlHelper.transactionalExecute(connection -> {
            String resumeSqlRequest = null;
            String contactSqlRequest = null;
            switch (upsertType) {
                case INSERT:
                    resumeSqlRequest = "INSERT INTO resume (full_name, uuid) VALUES (?, ?);";
                    contactSqlRequest = "INSERT INTO contact (value, resume_uuid, type) VALUES (?, ?, ?);";
                    break;
                case UPDATE:
                    resumeSqlRequest = "UPDATE resume SET full_name=? WHERE uuid=?;";
                    contactSqlRequest = "UPDATE contact SET value=? WHERE resume_uuid=? AND type=?;";
                    break;
            }
            String uuid = resume.getUuid();
            try (PreparedStatement ps = connection.prepareStatement(resumeSqlRequest)) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try (PreparedStatement ps = connection.prepareStatement(contactSqlRequest)) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, e.getValue());
                    ps.setString(2, uuid);
                    ps.setString(3, e.getKey().name());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            return null;
        });
    }

    private enum UpsertType {
        INSERT,
        UPDATE
    }
}
