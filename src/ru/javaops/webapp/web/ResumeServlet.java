package ru.javaops.webapp.web;

import ru.javaops.webapp.Config;
import ru.javaops.webapp.model.Resume;
import ru.javaops.webapp.storage.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {
    private static final Storage RESUME_STORAGE = Config.get().getStorage();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        Writer writer = response.getWriter();
        writeHeader(writer);
        String uuid = request.getParameter("uuid");
        if (uuid == null) {
            writeTableResumes(writer);
        } else {
            writeResumeInfo(uuid, writer);
        }
        writeFooter(writer);
    }

    private void writeTableResumes(Writer writer) throws IOException {
        writer.write("" +
                "<h2>List of all resumes in DB</h2>" +
                "<table>" +
                "<thead><tr><th>UUID</td><th>Full Name</th></tr></thead>" +
                "<tbody>");
        for (Resume resume : RESUME_STORAGE.getAllSorted()) {
            writer.write("<tr>");
            writer.write("<td><a href=\"?uuid=" + resume.getUuid() + "\">" + resume.getUuid() + "</td>");
            writer.write("<td>" + resume.getFullName() + "</td>");
            writer.write("</tr>");
        }
        writer.write("</tbody></table>");
    }

    private void writeResumeInfo(String uuid, Writer writer) throws IOException {
        Resume resume = RESUME_STORAGE.get(uuid);
        writer.write("<h2>" + resume.getFullName() + "</h2>");
    }

    private void writeHeader(Writer writer) throws IOException {
        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <link rel=\"stylesheet\" href=\"css/style.css\">\n" +
                "    <title>BaseJava + Web</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<header>Coding with TopJava</header>");
    }

    private void writeFooter(Writer writer) throws IOException {
        writer.write("<footer>Coding with TopJava</footer>\n" +
                "</body>\n" +
                "</html>");
    }
}
