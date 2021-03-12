<%@ page import="ru.javaops.webapp.storage.ListStorage" %>
<%@ page import="ru.javaops.webapp.model.*" %>
<%@ page import="ru.javaops.webapp.util.DateUtil" %>
<%@ page import="javax.print.attribute.standard.OrientationRequested" %>
<%@ page import="ru.javaops.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javaops.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="?uuid=${resume.uuid}&action=edit"><img width="20" src="img/edit-icon.png"></a>
    </h2>
    <c:if test="${resume.contacts.size() != 0}">
        <h3>Контакты:</h3>
        <p>
            <c:forEach var="contactEntry" items="${resume.contacts}">
                <jsp:useBean id="contactEntry"
                             type="java.util.Map.Entry<ru.javaops.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
            </c:forEach>
        </p>
    </c:if>
    <hr/>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.SectionType, ru.javaops.webapp.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="ru.javaops.webapp.model.Section"/>
            <tr>
                <td><h3><a name="type.name">${type.title}</a></h3></td>
                <c:if test="${type=='OBJECTIVE'}">
                    <td><h3><%=((TextSection) section).getContent()%>
                    </h3></td>
                </c:if>
            </tr>
            <c:if test="${type!='OBJECTIVE'}">
                <c:choose>
                    <c:when test="${type=='PERSONAL'}">
                        <tr>
                            <td>
                                <%=((TextSection) section).getContent()%>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='ACHIEVEMENT' || type=='QUALIFICATIONS'}">
                        <tr>
                            <td>
                                <ul>
                                    <c:forEach var="item" items="<%=((ListSection) section).getContent()%>">
                                        <li>${item}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="org" items="<%=((OrganizationSection) section).getContent()%>">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty org.homePage.url}">
                                            <h3>${org.homePage.name}</h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3><a href="${org.homePage.url}">${org.homePage.name}</a></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="position" items="${org.positions}">
                                <jsp:useBean id="position" type="ru.javaops.webapp.model.Organization.Position"/>
                                <tr>
                                    <td><%=HtmlUtil.formatDates(position)%>
                                    </td>
                                    <td><strong>${position.title}</strong><br/>${position.description}</td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
    </table>
    <button onclick="window.history.back()">OK</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>