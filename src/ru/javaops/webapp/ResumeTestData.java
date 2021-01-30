package ru.javaops.webapp;

import ru.javaops.webapp.model.*;
import ru.javaops.webapp.util.DateUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) {
        System.out.println(createKislinResume());
    }

    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.PHONE, "+7 (111) 111-1111");
        resume.addContact(ContactType.SKYPE, "some_skype");
        resume.addContact(ContactType.EMAIL, "some@mail.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/some_guy");
        resume.addContact(ContactType.GITHUB, "https://github.com/some_guy");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/some_guy");
        resume.addContact(ContactType.HOMEPAGE, "http://some_guy.ru/");
//
//        Section personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
//        resume.addSection(SectionType.PERSONAL, personal);
//        Section objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
//        resume.addSection(SectionType.OBJECTIVE, objective);
//
//        List<String> achievement = new ArrayList<>();
//        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
//        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
//        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievement));
//
//        List<String> qualifications = new ArrayList<>();
//        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
//        qualifications.add("Родной русский, английский \"upper intermediate\"");
//        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));
//
//        List<Organization> jobs = new ArrayList<>();
//        jobs.add(new Organization("Java Online Projects", "http://javaops.ru/", DateUtil.of(2013, Month.OCTOBER), DateUtil.NOW, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
//        jobs.add(new Organization("Wrike", "https://www.wrike.com/", DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
//        jobs.add(new Organization("RIT Center", null, DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
//        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(jobs));
//
//        List<Organization> educations = new ArrayList<>();
//        educations.add(new Organization("Coursera", "https://www.coursera.org/course/progfun", DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY), "\"Functional Programming Principles in Scala\" by Martin Odersky", null));
//        Organization spbNIUITMO = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), "Инженер (программист Fortran, C)", null);
//        spbNIUITMO.addPosition(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), "Аспирантура (программист С, С++)", null);
//        educations.add(spbNIUITMO);
//        resume.addSection(SectionType.EDUCATION, new OrganizationSection(educations));

        return resume;
    }

    private static Resume createKislinResume() {
        Resume resume = new Resume("Григорий Кислин");
        resume.addContact(ContactType.PHONE, "+7 (921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.addContact(ContactType.GITHUB, "https://github.com/gkislin");
        resume.addContact(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.addContact(ContactType.HOMEPAGE, "http://gkislin.ru/");

        Section personal = new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.addSection(SectionType.PERSONAL, personal);
        Section objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.addSection(SectionType.OBJECTIVE, objective);

        List<String> achievement = new ArrayList<>();
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(achievement));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy, XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix, администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(qualifications));

        List<Organization> jobs = new ArrayList<>();
        jobs.add(new Organization("Java Online Projects", "http://javaops.ru/", DateUtil.of(2013, Month.OCTOBER), DateUtil.NOW, "Автор проекта.", "Создание, организация и проведение Java онлайн проектов и стажировок."));
        jobs.add(new Organization("Wrike", "https://www.wrike.com/", DateUtil.of(2014, Month.OCTOBER), DateUtil.of(2016, Month.JANUARY), "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."));
        jobs.add(new Organization("RIT Center", null, DateUtil.of(2012, Month.APRIL), DateUtil.of(2014, Month.OCTOBER), "Java архитектор", "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"));
        jobs.add(new Organization("Luxoft (Deutsche Bank)", "http://www.luxoft.ru/", DateUtil.of(2010, Month.DECEMBER), DateUtil.of(2012, Month.APRIL), "Ведущий программист", "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."));
        jobs.add(new Organization("Yota", "https://www.yota.ru/", DateUtil.of(2008, Month.JUNE), DateUtil.of(2010, Month.DECEMBER), "Ведущий специалист", "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"));
        jobs.add(new Organization("Enkata", "http://enkata.com/", DateUtil.of(2007, Month.MARCH), DateUtil.of(2008, Month.JUNE), "Разработчик ПО", "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."));
        jobs.add(new Organization("Siemens AG", "https://www.siemens.com/ru/ru/home.html", DateUtil.of(2005, Month.JANUARY), DateUtil.of(2007, Month.FEBRUARY), "Разработчик ПО", "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."));
        jobs.add(new Organization("Alcatel", "http://www.alcatel.ru/", DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(2005, Month.JANUARY), "Инженер по аппаратному и программному тестированию", "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM)."));
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(jobs));

        List<Organization> educations = new ArrayList<>();
        educations.add(new Organization("Coursera", "https://www.coursera.org/course/progfun", DateUtil.of(2013, Month.MARCH), DateUtil.of(2013, Month.MAY), "\"Functional Programming Principles in Scala\" by Martin Odersky", null));
        educations.add(new Organization("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", DateUtil.of(2011, Month.MARCH), DateUtil.of(2011, Month.APRIL), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", null));
        educations.add(new Organization("Siemens AG", "http://www.siemens.ru/", DateUtil.of(2005, Month.JANUARY), DateUtil.of(2005, Month.APRIL), "3 месяца обучения мобильным IN сетям (Берлин)", null));
        educations.add(new Organization("Alcatel", "http://www.alcatel.ru/", DateUtil.of(1997, Month.SEPTEMBER), DateUtil.of(1998, Month.MARCH), "6 месяцев обучения цифровым телефонным сетям (Москва)", null));
        Organization spbNIUITMO = new Organization("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", "http://www.ifmo.ru/", DateUtil.of(1987, Month.SEPTEMBER), DateUtil.of(1993, Month.JULY), "Инженер (программист Fortran, C)", null);
        spbNIUITMO.addPosition(DateUtil.of(1993, Month.SEPTEMBER), DateUtil.of(1996, Month.JULY), "Аспирантура (программист С, С++)", null);
        educations.add(spbNIUITMO);
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(educations));

        return resume;
    }
}
