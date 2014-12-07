package ru.qiwi.utils;

import org.apache.commons.io.IOUtils;
import org.xml.sax.SAXException;
import ru.qiwi.transport.Request;
import ru.qiwi.transport.ResponseAccount;
import ru.qiwi.transport.ResponseAgent;
import ru.qiwi.transport.ResultEnum;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;

public class XmlUtils {

    public static final String DEFAULT_ENCODING = "UTF-8";


    public static String requestToString(InputStream inputStream) throws IOException {
        return requestToString(inputStream, DEFAULT_ENCODING);
    }


    public static String requestToString(InputStream inputStream, String encoding) throws IOException {
        return IOUtils.toString(inputStream, encoding);
    }


    public static Request xmlToRequest(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        RequestParser requestParser = new RequestParser();
        parser.parse(is, requestParser);
        return requestParser.getReq();
    }


    public static String responseAgentToXml(ResultEnum result) {
        ResponseAgent response = new ResponseAgent();
        response.setResultCode(result.getCode());
        try {
            JAXBContext jc = JAXBContext.newInstance(ResponseAgent.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter xml = new StringWriter();
            marshaller.marshal(response, xml);
            return xml.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    public static String responseAccountToXml(ResultEnum result) {
        return responseAccountToXml(result, BigDecimal.ZERO);
    }


    public static String responseAccountToXml(ResultEnum result, BigDecimal amount) {
        ResponseAccount response = new ResponseAccount();
        response.setResultCode(result.getCode());
        response.setAmount(amount);
        try {
            JAXBContext jc = JAXBContext.newInstance(ResponseAccount.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter xml = new StringWriter();
            marshaller.marshal(response, xml);
            return xml.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

}
