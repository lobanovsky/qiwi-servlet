package ru.qiwi.transport;

public enum ResultEnum {
    //new-agt
    OK(0, "Такой агент уже существует"),
    DUPLICATE_AGENT(1, "Такой агент уже  зарегистрирован"),
    WRONG_LOGIN(2, "Неверный формат телефона"),
    BAD_PASSWORD(3, "Плохой пароль"),
    OTHER(5, "другая ошибка повторите позже"),
    //agt-bal
    AGENT_NOT_EXITS(11, "Агент с данным логином не существет"),
    ACCOUNT_NOT_EXITS(12, "Счет для агентане существет");

    private final int code;
    private final String description;

    private ResultEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }

}
