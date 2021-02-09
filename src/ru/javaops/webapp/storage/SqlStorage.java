package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.ContactType;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.model.Section;
import ru.javaops.webapp.model.SectionType;
import ru.javaops.webapp.sql.SqlHelper;
import ru.javaops.webapp.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContacts(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            String uuid = resume.getUuid();
            try (PreparedStatement ps = connection.prepareStatement("UPDATE resume SET full_name=? WHERE uuid=?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteContacts(resume, connection);
            insertContacts(resume, connection);
            deleteSections(resume, connection);
            insertSections(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume = null;
            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    setContact(resume, rs);
                }
            }

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    setSection(resume, rs);
                }
            }
            return resume;
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
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement psResumes = connection.prepareStatement("" +
                    "   SELECT * FROM resume r " +
                    " ORDER BY full_name, uuid;")) {
                ResultSet rsResumes = psResumes.executeQuery();
                while (rsResumes.next()) {
                    String uuid = rsResumes.getString("uuid");
                    resumes.put(uuid, new Resume(uuid, rsResumes.getString("full_name")));
                }
            }

            try (PreparedStatement psContacts = connection.prepareStatement("SELECT * FROM contact;")) {
                ResultSet rsContacts = psContacts.executeQuery();
                while (rsContacts.next()) {
                    setContact(resumes.get(rsContacts.getString("resume_uuid")), rsContacts);
                }
            }

            try (PreparedStatement psSections = connection.prepareStatement("SELECT * FROM section;")) {
                ResultSet rsSections = psSections.executeQuery();
                while (rsSections.next()) {
                    setSection(resumes.get(rsSections.getString("resume_uuid")), rsSections);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    private void setContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void setSection(Resume resume, ResultSet rs) throws SQLException {
        String content = rs.getString("content");
        if (content != null) {
            resume.addSection(SectionType.valueOf(rs.getString("type")), JsonParser.read(content, Section.class));
        }
    }

    private void insertContacts(Resume resume, Connection connection) throws SQLException {
        if (resume.getContacts().size() != 0) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    ps.setString(3, e.getValue());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void insertSections(Resume resume, Connection connection) throws SQLException {
        if (resume.getSections().size() != 0) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?, ?, ?)")) {
                for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                    ps.setString(1, resume.getUuid());
                    ps.setString(2, e.getKey().name());
                    Section section = e.getValue();
                    ps.setString(3, JsonParser.write(section, Section.class));
                    ps.addBatch();
                }
                ps.executeBatch();
            }
        }
    }

    private void deleteContacts(Resume resume, Connection connection) throws SQLException {
        deleteAttributes(resume.getUuid(), connection, "DELETE FROM contact WHERE resume_uuid=?");
    }

    private void deleteSections(Resume resume, Connection connection) throws SQLException {
        deleteAttributes(resume.getUuid(), connection, "DELETE FROM section WHERE resume_uuid=?");
    }

    private void deleteAttributes(String uuid, Connection connection, String sql) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, uuid);
            ps.execute();
        }
    }
}
