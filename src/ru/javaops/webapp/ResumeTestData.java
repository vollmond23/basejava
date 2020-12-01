package ru.javaops.webapp;

import ru.javaops.webapp.model.*;

import java.time.YearMonth;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume newResume = new Resume("Григорий Кислин");
        newResume.addContact(ContactType.PHONE, "+7 (921) 855-0482");
        newResume.addContact(ContactType.SKYPE, "grigory.kislin");
        newResume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        newResume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        newResume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        newResume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        newResume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");

        Section personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        newResume.addSection(SectionType.PERSONAL, personal);
        Section objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        newResume.addSection(SectionType.OBJECTIVE, objective);
        ListSection achievement = new ListSection();
        achievement.addString("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.addString("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.addString("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.addString("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.addString("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.addString("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        newResume.addSection(SectionType.ACHIEVEMENT, achievement);

        ListSection qualifications = new ListSection();
        qualifications.addString("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.addString("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.addString("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.addString("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.addString("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.addString("Python: Django");
        qualifications.addString("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.addString("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.addString("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.addString("Инструменты: Maven + plugin development, Gradle, настройка Ngnix, администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.addString("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.addString("Родной русский, английский \"upper intermediate\"");
        newResume.addSection(SectionType.QUALIFICATIONS, qualifications);

        ExpSection jobs = new ExpSection();
        jobs.addExperience(new Experience().addJob("Java Online Projects", "http://javaops.ru/", "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок.", YearMonth.of(2013, 10), null));
        jobs.addExperience(new Experience().addJob("Wrike", "https://www.wrike.com/", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.", YearMonth.of(2014, 10), YearMonth.of(2016, 1)));
        jobs.addExperience(new Experience().addJob("RIT Center", null, "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python", YearMonth.of(2012, 4), YearMonth.of(2014, 10)));
        jobs.addExperience(new Experience().addJob("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.", YearMonth.of(2010, 12), YearMonth.of(2012, 4)));
        jobs.addExperience(new Experience().addJob("Yota", "https://www.yota.ru/", "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)", YearMonth.of(2008, 6), YearMonth.of(2010, 12)));
        jobs.addExperience(new Experience().addJob("Enkata", "http://enkata.com/", "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).", YearMonth.of(2007, 3), YearMonth.of(2008, 6)));
        jobs.addExperience(new Experience().addJob("Siemens AG", "https://www.siemens.com/ru/ru/home.html", "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).", YearMonth.of(2005, 1), YearMonth.of(2007, 2)));
        jobs.addExperience(new Experience().addJob("Alcatel", "http://www.alcatel.ru/", "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).", YearMonth.of(1997, 9), YearMonth.of(2005, 1)));
        newResume.addSection(SectionType.EXPERIENCE, jobs);

        ExpSection educations = new ExpSection();
        educations.addExperience(new Experience().addEdu("Coursera", "https://www.coursera.org/course/progfun", "\"Functional Programming Principles in Scala\" by Martin Odersky", YearMonth.of(2013, 3), YearMonth.of(2013, 5)));
        educations.addExperience(new Experience().addEdu("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", YearMonth.of(2011, 3), YearMonth.of(2011, 4)));
        educations.addExperience(new Experience().addEdu("Siemens AG", "http://www.siemens.ru/", "3 месяца обучения мобильным IN сетям (Берлин)", YearMonth.of(2005, 1), YearMonth.of(2005, 4)));
        educations.addExperience(new Experience().addEdu("Alcatel", "http://www.alcatel.ru/", "6 месяцев обучения цифровым телефонным сетям (Москва)", YearMonth.of(1997, 9), YearMonth.of(1998, 3)));
        Experience newEdu = new Experience().addEdu("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", "Инженер (программист Fortran, C)", YearMonth.of(1987, 9), YearMonth.of(1993, 7));
        newEdu.addEduPeriod(YearMonth.of(1993, 9), YearMonth.of(1996,7), "Аспирантура (программист С, С++)");
        educations.addExperience(newEdu);
        newResume.addSection(SectionType.EDUCATION, educations);

        newResume.printToConsole();
    }
}
