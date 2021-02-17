<%@ page import="ru.javaops.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<section>
    <h2>List of all resumes in DB</h2>
    <table>
        <thead>
        <tr>
            <th>Имя</th>
            <th>Email</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {
        %>
        <tr>
            <td><a href="?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%></a></td>
            <td><%=resume.getContact(ContactType.EMAIL)%></td>
        </tr>
        <%
            }
        %>
        </tbody>

    </table>
</section>
</body>
</html>
