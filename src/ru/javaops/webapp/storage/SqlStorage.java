package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
            insertSection(resume, connection);
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
            clearContactsSections(uuid, connection);
            insertContacts(resume, connection);
            insertSection(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                "   SELECT r.uuid as uuid, r.full_name as full_name, c.type as type, c.value as value, s.type as s_type, s.content as content FROM resume r " +
                "LEFT JOIN contact c " +
                "       ON r.uuid = c.resume_uuid " +
                "LEFT JOIN section s " +
                "       ON r.uuid = s.resume_uuid " +
                "    WHERE r.uuid =?", ps -> {
            Resume resume = null;
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            resume = getResumeFrom(rs);
            do {
                setContact(resume, rs);
                setSection(resume, rs);
            } while (rs.next());
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
            try (PreparedStatement psResumes = connection.prepareStatement("" +
                    "   SELECT * FROM resume r " +
                    " ORDER BY full_name, uuid;");
                 PreparedStatement psContacts = connection.prepareStatement("" +
                         "SELECT * FROM contact;");
                 PreparedStatement psSections = connection.prepareStatement("" +
                         "SELECT resume_uuid, type as s_type, content FROM section;")) {

                ResultSet rsResumes = psResumes.executeQuery();
                ResultSet rsContacts = psContacts.executeQuery();
                ResultSet rsSections = psSections.executeQuery();
                Map<String, Resume> map = new LinkedHashMap<>();
                while (rsResumes.next()) {
                    String uuid = rsResumes.getString("uuid");
                    map.put(uuid, new Resume(uuid, rsResumes.getString("full_name")));
                }
                while (rsContacts.next()) {
                    setContact(map.get(rsContacts.getString("resume_uuid")), rsContacts);
                }
                while (rsSections.next()) {
                    setSection(map.get(rsSections.getString("resume_uuid")), rsSections);
                }
                return new ArrayList<>(map.values());
            }
        });
    }

    private Resume getResumeFrom(ResultSet rs) throws SQLException {
        return new Resume(rs.getString("uuid"), rs.getString("full_name"));
    }

    private void setContact(Resume resume, ResultSet rs) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void setSection(Resume resume, ResultSet rs) throws SQLException {
        String type = rs.getString("s_type");
        if (type != null) {
            SectionType sectionType = SectionType.valueOf(type);
            String content = rs.getString("content");
            Section section = null;
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    section = new TextSection(content);
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    section = new ListSection(Arrays.asList(content.split("\n")));
                    break;
            }
            resume.addSection(sectionType, section);
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

    private void insertSection(Resume resume, Connection connection) throws SQLException {
        if (resume.getSections().size() != 0) {
            try (PreparedStatement ps = connection.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?, ?, ?)")) {
                for (Map.Entry<SectionType, Section> e : resume.getSections().entrySet()) {
                    SectionType key = e.getKey();
                    if (key != SectionType.EDUCATION && key != SectionType.EXPERIENCE) {
                        String value = getSectionContent(key, e.getValue());
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, key.name());
                        ps.setString(3, value);
                        ps.addBatch();
                    }
                }
                ps.executeBatch();
            }
        }
    }

    private String getSectionContent(SectionType key, Section section) {
        String content = null;
        switch (key) {
            case PERSONAL:
            case OBJECTIVE:
                content = ((TextSection) section).getContent();
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                content = String.join("\n", ((ListSection) section).getContent());
                break;
        }
        return content;
    }

    private void clearContactsSections(String uuid, Connection connection) throws SQLException {
        try (PreparedStatement psContact = connection.prepareStatement("DELETE FROM contact WHERE resume_uuid=?");
             PreparedStatement psTextSection = connection.prepareStatement("DELETE FROM section WHERE resume_uuid=?")) {
            psContact.setString(1, uuid);
            psTextSection.setString(1, uuid);
            psContact.execute();
            psTextSection.execute();
        }
    }
}
