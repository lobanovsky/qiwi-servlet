package ru.qiwi.transport;

import org.apache.commons.lang3.StringUtils;

public enum RequestTypeEnum {
    NEW_AGT("new-agt"),
    AGT_BAL("agt-bal");

    private String type;


    RequestTypeEnum(String type) {
        this.type = type;
    }


    public static RequestTypeEnum fromString(String text) {
        if (StringUtils.isBlank(text)) return null;
        for (RequestTypeEnum item : RequestTypeEnum.values()) {
            if (text.equalsIgnoreCase(item.type)) {
                return item;
            }
        }
        return null;
    }
}
