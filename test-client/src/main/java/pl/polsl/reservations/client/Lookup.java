package pl.polsl.reservations.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.polsl.reservations.ejb.remote.AbstractBusinessFacade;
import pl.polsl.reservations.ejb.remote.RoomManagementFacade;
import pl.polsl.reservations.ejb.remote.ScheduleFacade;
import pl.polsl.reservations.ejb.remote.UserFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class Lookup {

    private static InitialContext ic;
    private static ClientSessionCertificate clientSessionCertificate;

    static {
        InputStream resourceAsStream = null;
        String address = null;
        String port = null;
        try {
            clientSessionCertificate = ClientSessionCertificate.getInstance();
            Properties p = new Properties();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();
            String pathPropertiesFile = "/resources/server.xml";
            resourceAsStream = p.getClass().getResourceAsStream(pathPropertiesFile);
            if (resourceAsStream != null) {
                Document doc = dBuilder.parse(resourceAsStream);
                doc.getDocumentElement().normalize();
                NodeList hostAddress = doc.getElementsByTagName("hostAddress");
                NodeList hostPort = doc.getElementsByTagName("hostPort");
                Node node = hostAddress.item(0);
                address = null;
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    address = eElement.getTextContent();
                }
                node = hostPort.item(0);
                port = null;
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    port = eElement.getTextContent();
                }
            }
            p.put("java.rmi.server.useCodebaseOnly", "false");
            p.setProperty("java.naming.factory.initial",
                    "com.sun.enterprise.naming.SerialInitContextFactory");

            p.setProperty("java.naming.factory.url.pkgs",
                    "com.sun.enterprise.naming");

            p.setProperty("java.naming.factory.state",
                    "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");

            // optional.  Defaults to localhost.  Only needed if web server is running
            // on a different host than the appserver   
            if (address == null) {
                p.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
            } else {
                p.setProperty("org.omg.CORBA.ORBInitialHost", address);
            }

            // optional.  Defaults to 3700.  Only needed if target orb port is not 3700.
            if (port == null) {
                p.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            } else {
                p.setProperty("org.omg.CORBA.ORBInitialPort", port);
            }
            ic = new InitialContext(p);
            NamingEnumeration<NameClassPair> list = ic.list("");
            while (list.hasMore()) {
                System.out.println(list.next().getName());
            }
        } catch (NamingException ne) {
            throw new RuntimeException(ne);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Lookup.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (resourceAsStream != null) {
                try {
                    resourceAsStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(Lookup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static Object lookup(String jndiName) throws NamingException {
        return ic.lookup(jndiName);
    }

    public static Object getRemote(String jndiName) {
        Object o = null;
        try {
            o = Lookup.lookup(jndiName);
            if (o instanceof AbstractBusinessFacade) {
                try {
                    ((AbstractBusinessFacade) o).certificateBean(clientSessionCertificate.getCertificate());
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(Lookup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (NamingException ex) {
            System.out.println("Remote object doesn't exists!");
        }
        return o;
    }

    /**
     * Will get the ejb Local home factory. Clients need to cast to the type of
     * EJBHome they desire.
     *
     * @param jndiHomeName jndi home name matching the requested local home
     * @return the Local EJB Home corresponding to the homeName
     * @throws NamingException if the lookup fails
     */
    public static EJBLocalHome getLocalHome(String jndiHomeName) throws NamingException {
        return (EJBLocalHome) lookup(jndiHomeName);
    }

    /**
     * Will get the ejb Remote home factory. Clients need to cast to the type of
     * EJBHome they desire.
     *
     * @param jndiHomeName jndi home name matching the requested remote home
     * @param className desired type of the object
     * @return the EJB Home corresponding to the homeName
     * @throws NamingException if the lookup fails
     */
    public static EJBHome getRemoteHome(String jndiHomeName, Class className) throws NamingException {
        Object objref = lookup(jndiHomeName);
        return (EJBHome) PortableRemoteObject.narrow(objref, className);
    }

    /**
     * This method helps in obtaining the JMS connection factory.
     *
     * @param connFactoryName name of the connection factory
     * @return the factory for obtaining JMS connection
     * @throws NamingException if the lookup fails
     */
    public static ConnectionFactory getConnectionFactory(String connFactoryName) throws NamingException {
        return (ConnectionFactory) lookup(connFactoryName);
    }

    /**
     * This method obtains the topic itself for a caller.
     *
     * @param destName destination name
     * @return the Topic Destination to send messages to
     * @throws NamingException if the lookup fails
     */
    public static Destination getDestination(String destName) throws NamingException {
        return (Destination) lookup(destName);
    }

    /**
     * This method obtains the datasource itself for a caller.
     *
     * @param dataSourceName data source name
     * @return the DataSource corresponding to the name parameter
     * @throws NamingException if the lookup fails
     */
    public static DataSource getDataSource(String dataSourceName) throws NamingException {
        return (DataSource) lookup(dataSourceName);
    }

    /**
     * This method obtains the E-mail session itself for a caller.
     *
     * @param sessionName session name
     * @return the Session corresponding to the name parameter
     * @throws NamingException if the lookup fails
     */
    public static Session getSession(String sessionName) throws NamingException {
        return (Session) lookup(sessionName);
    }

    /**
     * Gets the URL corresponding to the environment entry name.
     *
     * @param envName the environment name
     * @return the URL value corresponding to the environment entry name
     * @throws NamingException if the lookup fails
     */
    public static URL getUrl(String envName) throws NamingException {
        return (URL) lookup(envName);
    }

    /**
     * Gets boolean value corresponding to the environment entry name.
     *
     * @param envName the environment name
     * @return the boolean value corresponding to the environment entry
     * @throws NamingException if the lookup fails
     */
    public static boolean getBoolean(String envName) throws NamingException {
        Boolean bool = (Boolean) lookup(envName);
        return bool;
    }

    /**
     * Gets string value corresponding to the environment entry name.
     *
     * @param envName the environment name
     * @return the String value corresponding to the environment entry name
     * @throws NamingException if the lookup fails
     */
    public static String getString(String envName) throws NamingException {
        return (String) lookup(envName);
    }

    public static void removeUserCertificate() {
        UserFacade userFacade = (UserFacade) getRemote("UserFacade");
        try {
            userFacade.removeCertificate(clientSessionCertificate.getCertificate());
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Lookup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static RoomManagementFacade getRoomManagementFacade() {
        return (RoomManagementFacade) getRemote("RoomManagementFacade");
    }

    public static UserFacade getUserFacade() {
        return (UserFacade) getRemote("UserFacade");
    }

    public static UserManagementFacade getUserManagementFacade() {
        return (UserManagementFacade) getRemote("UserManagementFacade");
    }

    public static ScheduleFacade getScheduleFacade() {
        return (ScheduleFacade) getRemote("ScheduleFacade");
    }

}
