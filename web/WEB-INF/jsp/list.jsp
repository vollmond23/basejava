<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>List of all resumes in DB</h2>
    <a href="resume?action=add">Добавить резюме</a><br/>
    <table id="list_table">
        <thead>
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" class="ru.javaops.webapp.model.Resume" />
            <tr>
                <td><a href="?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%></td>
                <td><a href="?uuid=${resume.uuid}&action=delete"><img width="20" src="img/delete-icon.png"></a></td>
                <td><a href="?uuid=${resume.uuid}&action=edit"><img width="20" src="img/edit-icon.png"></a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
