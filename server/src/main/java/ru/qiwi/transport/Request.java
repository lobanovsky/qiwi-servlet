package ru.qiwi.transport;

public class Request {

    public static final String REQUEST_TYPE = "request-type";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";

    private RequestTypeEnum requestType;
    private String login;
    private String password;


    public RequestTypeEnum getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestTypeEnum requestType) {
        this.requestType = requestType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
