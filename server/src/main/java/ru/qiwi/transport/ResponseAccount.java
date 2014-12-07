package ru.qiwi.transport;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement(name = "response")
public class ResponseAccount {

    @XmlElement(name = "result-code", required = true)
    private int resultCode;
    @XmlElement(name = "bal", required = true)
    private BigDecimal amount;


    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
