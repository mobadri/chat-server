package com.chat.server.config.database.connection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class NetworkDatabaseConfig {
    private String serverIp;
    private String serverPortNumber;
    private String databaseIP;
    private String databasePortNumber;
    private String databaseName;
    private String userName;
    private String userPassword;

    private static NetworkDatabaseConfig instance;

    private NetworkDatabaseConfig() {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
            Document parse = documentBuilder.parse(new File("configuration.xml"));
            parse.getDocumentElement().normalize();
            NodeList networkConfig1 = parse.getElementsByTagName("networkConfig");
            NodeList databaseConfig1 = parse.getElementsByTagName("databaseConfig");

            for (int i = 0; i < networkConfig1.getLength(); i++) {
                Node item = networkConfig1.item(i);
                Element eElement = (Element) item;
                this.serverIp = eElement.getElementsByTagName("server-ip").item(i).getTextContent().trim();
                this.serverPortNumber = eElement.getElementsByTagName("server-port").item(i).getTextContent().trim();
            }
            for (int i = 0; i < databaseConfig1.getLength(); i++) {
                Node item = databaseConfig1.item(i);
                Element eElement = (Element) item;
                this.databaseIP = eElement.getElementsByTagName("database-ip").item(i).getTextContent().trim();
                this.databasePortNumber = eElement.getElementsByTagName("database-port").item(i).getTextContent().trim();
                this.databaseName = eElement.getElementsByTagName("database-name").item(i).getTextContent().trim();
                this.userName = eElement.getElementsByTagName("user-name").item(i).getTextContent().trim();
                this.userPassword = eElement.getElementsByTagName("user-password").item(i).getTextContent().trim();
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getServerPortNumber() {
        return serverPortNumber;
    }

    public String getDatabaseIP() {
        return databaseIP;
    }

    public String getDatabasePortNumber() {
        return databasePortNumber;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public synchronized static NetworkDatabaseConfig getInstance() {
        if (instance == null) {
            return new NetworkDatabaseConfig();
        }
        return instance;
    }
}
