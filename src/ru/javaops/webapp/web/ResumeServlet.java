package ru.javaops.webapp.web;

import ru.javaops.webapp.Config;
import ru.javaops.webapp.model.*;
import ru.javaops.webapp.storage.Storage;
import ru.javaops.webapp.util.DateUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ResumeServlet extends HttpServlet {
    private static Storage RESUME_STORAGE;

    @Override
    public void init() throws ServletException {
        RESUME_STORAGE = Config.get().getStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = RESUME_STORAGE.get(uuid);
        resume.setFullName(fullName);
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            switch (type) {
                case PERSONAL:
                case OBJECTIVE:
                    String value = request.getParameter(type.name());
                    if (value != null && value.trim().length() != 0) {
                        resume.addSection(type, new TextSection(value));
                    } else {
                        resume.getSections().remove(type);
                    }
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    String[] values = request.getParameterMap().get(type.name());
                    if (values.length > 0) {
                        List<String> list = new ArrayList<>();
                        for (String str : values) {
                            if (str.trim().length() > 0) {
                                list.add(str);
                            }
                        }
                        resume.addSection(type, new ListSection(list));
                    } else {
                        resume.getSections().remove(type);
                    }
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    List<Organization> organizations = new ArrayList<>();
                    List<String> inputsByType = filterInputsByType(request, type);
                    for (String orgName : getOrgsNames(inputsByType)) {
                        String name = request.getParameter(orgName + "_" + type.name() + "_orgName");
                        if (name.trim().equals("")) {
                            continue;
                        }
                        String url = request.getParameter(orgName + "_" + type.name() + "_orgUrl");
                        Link homePage = new Link(
                                name,
                                (url.trim().equals("") ? null : url));
                        List<String> inputsByOrg = getInputsByOrg(inputsByType, orgName);
                        List<Organization.Position> positions = new ArrayList<>();
                        for (String positionTitle : getPositionTitles(inputsByOrg)) {
                            String title = request.getParameter(orgName + "_" + positionTitle + "_" + type.name() + "_title");
                            if (title.trim().equals("")) {
                                continue;
                            }
                            String dateBegin = request.getParameter(orgName + "_" + positionTitle + "_"
                                    + type.name() + "_dateBegin");
                            String dateEnd = request.getParameter(orgName + "_" + positionTitle + "_"
                                    + type.name() + "_dateEnd");
                            String description = request.getParameter(orgName + "_" + positionTitle + "_" + type.name() + "_description");
                            positions.add(new Organization.Position(
                                    DateUtil.parse(dateBegin),
                                    dateEnd.equals("") ? DateUtil.NOW : DateUtil.parse(dateEnd),
                                    title,
                                    description));
                        }
                        organizations.add(new Organization(homePage, positions));
                    }
                    if (organizations.size() > 0) {
                        resume.addSection(type, new OrganizationSection(organizations));
                    } else {
                        resume.getSections().remove(type);
                    }
                    break;
            }
        }
        RESUME_STORAGE.update(resume);
        response.sendRedirect("resume");
    }

    private List<String> getPositionTitles(List<String> inputsByOrg) {
        return inputsByOrg.stream()
                .filter(s -> s.contains("title"))
                .map(s -> {
                    String[] splitted = s.split("_");
                    return splitted[1];
                })
                .collect(Collectors.toList());
    }

    private List<String> getInputsByOrg(List<String> inputsByType, String orgName) {
        return inputsByType.stream()
                .filter(s -> s.contains(orgName))
                .collect(Collectors.toList());
    }

    private List<String> getOrgsNames(List<String> inputsByType) {
        return inputsByType.stream()
                .filter(s -> s.contains("orgName"))
                .filter(s -> !s.startsWith("newOrg"))
                .map(s -> s.substring(0, s.indexOf("_")))
                .collect(Collectors.toList());
    }

    private List<String> filterInputsByType(HttpServletRequest request, SectionType type) {
        return request.getParameterMap().keySet().stream()
                .filter(s -> s.contains(type.name()))
                .collect(Collectors.toList());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", RESUME_STORAGE.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume = RESUME_STORAGE.get(uuid);
        switch (action) {
            case "delete":
                RESUME_STORAGE.delete(resume);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = RESUME_STORAGE.get(uuid);
                break;
            default:
                throw new IllegalStateException("Action " + action + " is illegal");
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}
