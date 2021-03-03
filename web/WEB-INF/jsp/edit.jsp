<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javaops.webapp.model.ListSection" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="contactType" items="<%=ContactType.values()%>">
            <dl>
                <dt>${contactType.title}</dt>
                <dd><input type="text" name="${contactType.name()}" value="${resume.getContact(contactType)}"></dd>
            </dl>
        </c:forEach>
        <hr/>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${sectionType == SectionType.PERSONAL || sectionType == SectionType.OBJECTIVE}">
                    <c:set var="textSection" value="${resume.getSection(sectionType)}"/>
                    <jsp:useBean id="textSection" class="ru.javaops.webapp.model.TextSection"/>
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd><textarea name="${sectionType.name()}">${fn:escapeXml(textSection.content)}</textarea></dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.ACHIEVEMENT || sectionType == SectionType.QUALIFICATIONS}">
                    <c:set var="listSection" value="${resume.getSection(sectionType)}"/>
                    <jsp:useBean id="listSection" class="ru.javaops.webapp.model.ListSection"/>
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <dd>
                            <div id="${sectionType.name()}">
                                <c:forEach var="str" items="<%=listSection.getContent()%>">
                                    <input type="text" name="${sectionType.name()}" value="${fn:escapeXml(str)}"><br/>
                                </c:forEach>
                            </div>
                            <a href="#" onclick="addInput('${sectionType.name()}')">Добавить поле</a>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${sectionType == SectionType.EDUCATION || sectionType == SectionType.EXPERIENCE}">
                    <c:set var="orgSection" value="${resume.getSection(sectionType)}"/>
                    <jsp:useBean id="orgSection" class="ru.javaops.webapp.model.OrganizationSection"/>
                    <h3>${sectionType.title}</h3>
                    <div id="${sectionType.name()}">
                        <div id="${sectionType.name()}_newOrgs">
                            <a href="#" onclick="addOrganization('${sectionType.name()}')">Добавить организацию</a>
                        </div>
                        <c:forEach var="organization" items="${orgSection.content}">
                            <div class="org_section">
                                <dl>
                                    <dt>Название:</dt>
                                    <dd><input type="text"
                                               name="${organization.homePage.name}_${sectionType.name()}_orgName"
                                               value="${fn:escapeXml(organization.homePage.name)}">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>URL:</dt>
                                    <dd><input type="text"
                                               name="${organization.homePage.name}_${sectionType.name()}_orgUrl"
                                               value="${organization.homePage.url}"></dd>
                                </dl>
                                <strong>Периоды:</strong>
                                <div class="positions">
                                    <c:forEach var="position" items="${organization.positions}">
                                        <dl>
                                            <dt>Дата начала:</dt>
                                            <dd><input type="date"
                                                       name="${organization.homePage.name}_${fn:replace(position.title, '\"', '')}_${sectionType.name()}_dateBegin"
                                                       value="${position.dateBegin}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Дата окончания:</dt>
                                            <dd><input type="date"
                                                       name="${organization.homePage.name}_${fn:replace(position.title, '\"', '')}_${sectionType.name()}_dateEnd"
                                                       value="${position.dateEnd}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Должность:</dt>
                                            <dd><input type="text"
                                                       name="${organization.homePage.name}_${fn:replace(position.title, '\"', '')}_${sectionType.name()}_title"
                                                       value="${fn:escapeXml(position.title)}">
                                            </dd>
                                        </dl>
                                        <c:if test="${position.description != null}">
                                            <dl>
                                                <dt>Обязанности:</dt>
                                                <dd><textarea
                                                        name="${organization.homePage.name}_${fn:replace(position.title, '\"', '')}_${sectionType.name()}_description">${fn:escapeXml(position.description)}</textarea>
                                                </dd>
                                            </dl>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
    <script type='text/javascript'>
        function addInput(sectionTypeName) {
            var input = document.createElement('input');
            input.type = "text";
            input.name = sectionTypeName;
            document.getElementById(sectionTypeName).appendChild(input);
            document.getElementById(sectionTypeName).appendChild(document.createElement('br'));
        }

        function addOrganization(sectionTypeName) {
            var div = document.createElement('div');
            div.id = sectionTypeName + '_newOrganization';
            div.className = 'org_section';
            div.innerHTML = '' +
                '<dl><dt>Название:</dt><dd>' +
                '<input type="text" name="newOrganization_' + sectionTypeName + '_orgName" value=""></dd></dl>' +
                '<dl><dt>URL:</dt><dd><input type="text" name="newOrganizationName_' + sectionTypeName + '_orgUrl" value=""></dd></dl>' +
                '<strong>Периоды:</strong>' +
                '<div class="positions">' +
                '<dl><dt>Дата начала:</dt><dd><input type="date" name="newOrganizationName_newPosition_' + sectionTypeName + '_dateBegin" value=""></dd></dl>' +
                '<dl><dt>Дата окончания:</dt><dd><input type="date" name="newOrganizationName_newPosition_' + sectionTypeName + '_dateEnd" value=""></dd></dl>' +
                '<dl><dt>Должность:</dt><dd><input type="text" name="newOrganizationName_newPosition_title" value=""></dd></dl>';
            document.getElementById(sectionTypeName + '_newOrgs').appendChild(div);
        }
    </script>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
