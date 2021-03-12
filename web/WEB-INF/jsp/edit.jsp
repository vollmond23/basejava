<%@ page import="ru.javaops.webapp.model.ContactType" %>
<%@ page import="ru.javaops.webapp.model.SectionType" %>
<%@ page import="ru.javaops.webapp.util.DateUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.javaops.webapp.model.ListSection" %>
<%@ page import="ru.javaops.webapp.model.OrganizationSection" %>
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
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.javaops.webapp.model.Section"/>
            <h3><a>${type.title}</a></h3>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <input type="text" name="${type}" size="75" value="<%=section%>">
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <textarea name="${type}" cols="75" rows="5"><%=section%></textarea>
                </c:when>
                <c:when test="${type == 'QUALIFICATIONS' || type == 'ACHIEVEMENT'}">
                    <textarea name="${type}" cols="75" rows="5"><%=String.join("\n", ((ListSection) section).getContent())%></textarea>
                </c:when>
                <c:when test="${type=='EDUCATION' || type=='EXPERIENCE'}">
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getContent()%>" varStatus="counter">
                        <dl>
                            <dt>Название учреждения:</dt>
                            <dd><input type="text" name="${type}" size="100" value="${org.homePage.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт учреждения:</dt>
                            <dd><input type="text" name="${type}url" size="100" value="${org.homePage.url}"></dd>
                        </dl>
                        <br/>
                        <div style="margin-left: 30px">
                            <c:forEach var="pos" items="${org.positions}">
                                <jsp:useBean id="pos" type="ru.javaops.webapp.model.Organization.Position"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd><input type="text" name="${type}${counter.index}startDate" size="10"
                                               value="<%=DateUtil.format(pos.getDateBegin())%>" placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd><input type="text" name="${type}${counter.index}endDate" size="10"
                                               value="<%=DateUtil.format(pos.getDateEnd())%>" placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name="${type}${counter.index}title" size="10"
                                               value="${pos.title}">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><input type="text" name="${type}${counter.index}description" size="10"
                                               value="${pos.description}">
                                    </dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
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
    </script>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
