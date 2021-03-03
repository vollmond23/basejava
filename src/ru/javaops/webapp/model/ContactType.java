package ru.javaops.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    EMAIL("Почта") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": <a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    SKYPE("Skype") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": <a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    HOMEPAGE("Домашняя страница") {
        @Override
        protected String toHtml0(String value) {
            return getTitle() + ": " + toHyperlink(value);
        }
    },
    GITHUB("Профиль GitHub") {
        @Override
        protected String toHtml0(String value) {
            return getTitle() + ": " + toHyperlink(value);
        }
    },
    LINKEDIN("Профиль LinkedIn") {
        @Override
        protected String toHtml0(String value) {
            return getTitle() + ": " + toHyperlink(value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow") {
        @Override
        protected String toHtml0(String value) {
            return getTitle() + ": " + toHyperlink(value);
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return value == null ? "" : toHtml0(value);
    }

    protected String toHyperlink(String string) {
        return "<a href='" + string + "'>" + string + "</a>";
    }
}
