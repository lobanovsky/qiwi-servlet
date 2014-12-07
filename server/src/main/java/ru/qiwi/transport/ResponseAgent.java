package ru.qiwi.transport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class ResponseAgent {

    @XmlElement(name = "result-code", required = true)
    int resultCode;

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
