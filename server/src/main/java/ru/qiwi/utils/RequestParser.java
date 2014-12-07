package ru.qiwi.utils;

import org.apache.commons.lang3.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.qiwi.transport.Request;
import ru.qiwi.transport.RequestTypeEnum;

public class RequestParser extends DefaultHandler {

    private Request req = new Request();
    private String thisElement = StringUtils.EMPTY;


    public Request getReq() {
        return req;
    }


    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
    }


    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        thisElement = StringUtils.EMPTY;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (thisElement.equals(Request.REQUEST_TYPE)) {
            String type = new String(ch, start, length);
            RequestTypeEnum requestEnum = RequestTypeEnum.fromString(type);
            req.setRequestType(requestEnum);
        }
        if (thisElement.equals(Request.LOGIN)) {
            req.setLogin(new String(ch, start, length));
        }
        if (thisElement.equals(Request.PASSWORD)) {
            req.setPassword(new String(ch, start, length));
        }
    }


}
