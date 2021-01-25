package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.sql.ConnectionFactory;
import ru.javaops.webapp.util.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        SqlHelper.executeCodeWith(connectionFactory, "DELETE FROM resume", PreparedStatement::executeUpdate);
    }

    @Override
    public void save(Resume resume) {
        checkIfExists(resume);
        SqlHelper.executeCodeWith(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
        });
    }

    @Override
    public void update(Resume resume) {
        checkIfNotExists(resume);
        SqlHelper.executeCodeWith(connectionFactory, "UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            ps.execute();
        });
    }

    @Override
    public Resume get(String uuid) {
        checkIfNotExists(new Resume(uuid, ""));
        AtomicReference<Resume> resume = new AtomicReference<>();
        SqlHelper.executeCodeWith(connectionFactory, "SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            resume.set(new Resume(uuid, rs.getString("full_name")));
        });
        return resume.get();
    }

    @Override
    public void delete(Resume resume) {
        checkIfNotExists(resume);
        SqlHelper.executeCodeWith(connectionFactory, "DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
        });
    }

    @Override
    public int size() {
        AtomicInteger size = new AtomicInteger();
        SqlHelper.executeCodeWith(connectionFactory, "SELECT COUNT(*) AS size FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            size.set(rs.getInt("size"));
        });
        return size.get();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        SqlHelper.executeCodeWith(connectionFactory, "SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
        });
        Collections.sort(resumes);
        return resumes;
    }

    private boolean isExists(String uuid) {
        AtomicBoolean isExist = new AtomicBoolean(false);
        SqlHelper.executeCodeWith(connectionFactory, "SELECT * FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            isExist.set(rs.next());
        });
        return isExist.get();
    }

    private void checkIfExists(Resume resume) {
        String resumeUuid = resume.getUuid();
        if (isExists(resumeUuid)) {
            throw new ExistStorageException(resumeUuid);
        }
    }

    private void checkIfNotExists(Resume resume) {
        String resumeUuid = resume.getUuid();
        if (!isExists(resumeUuid)) {
            throw new NotExistStorageException(resumeUuid);
        }
    }
}
