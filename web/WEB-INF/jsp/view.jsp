<%@ page import="ru.javaops.webapp.storage.ListStorage" %>
<%@ page import="ru.javaops.webapp.model.*" %>
<%@ page import="ru.javaops.webapp.util.DateUtil" %>
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
    </c:if>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javaops.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <c:forEach var="sectionType" items="<%=SectionType.values()%>">
        <c:if test="${resume.getSection(sectionType) != null}">
            <h3>${sectionType.title}</h3>
        </c:if>
        <c:choose>
            <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}">
                <c:set var="textSection" value="${resume.getSection(sectionType)}"/>
                <jsp:useBean id="textSection" class="ru.javaops.webapp.model.TextSection"/>
                <p>${textSection.content}</p>
            </c:when>
            <c:when test="${sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS}">
                <c:set var="listSection" value="${resume.getSection(sectionType)}"/>
                <jsp:useBean id="listSection" class="ru.javaops.webapp.model.ListSection"/>
                <ul>
                    <c:forEach var="str" items="<%=listSection.getContent()%>">
                        <li>${fn:escapeXml(str)}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${sectionType == SectionType.EDUCATION || sectionType == SectionType.EXPERIENCE}">
                <ul>
                    <c:set var="orgSection" value="${resume.getSection(sectionType)}"/>
                    <jsp:useBean id="orgSection" class="ru.javaops.webapp.model.OrganizationSection"/>
                    <c:forEach var="organization" items="${orgSection.content}">
                        <strong>
                            <li>${fn:escapeXml(organization.homePage.name)}
                                <c:if test="${organization.homePage.url != null}">
                                    &nbsp;(<a href="${organization.homePage.url}">${organization.homePage.url}</a>)
                                </c:if>
                            </li>
                        </strong>
                        <p>Периоды
                            <c:choose>
                            <c:when test="${sectionType == SectionType.EDUCATION}">
                            обучения:
                            </c:when>
                            <c:otherwise>
                            работы:
                            </c:otherwise>
                            </c:choose>
                        <div class="positions">
                            <c:forEach var="position" items="${organization.positions}">
                                ${DateUtil.format(position.dateBegin)}&nbsp;-
                                <c:choose>
                                    <c:when test="${position.dateEnd == DateUtil.NOW}">
                                        настоящее время
                                    </c:when>
                                    <c:otherwise>
                                        ${DateUtil.format(position.dateEnd)}
                                    </c:otherwise>
                                </c:choose>
                                :&nbsp;<strong>${fn:escapeXml(position.title)}</strong>
                                <br/>
                                <c:if test="${position.description != null}">
                                    Обязанности: ${fn:escapeXml(position.description)}<br/>
                                </c:if>
                            </c:forEach>
                        </div>
                    </p>
                    </c:forEach>
                </ul>
            </c:when>
        </c:choose>
    </c:forEach>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>