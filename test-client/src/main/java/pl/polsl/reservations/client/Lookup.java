package pl.polsl.reservations.client;

import java.net.URL;
import java.util.Properties;
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

/**
 *
 * @author matis
 */
public class Lookup {

    private static final InitialContext ic;

    static {
        try {
            Properties p = new Properties();
            p.put("java.rmi.server.useCodebaseOnly", "false");
            ic = new InitialContext(p);
            NamingEnumeration<NameClassPair> list = ic.list("");
            while (list.hasMore()) {
                System.out.println(list.next().getName());
            }

        } catch (NamingException ne) {
            throw new RuntimeException(ne);
        }
    }

    private static Object lookup(String jndiName) throws NamingException {
        return ic.lookup(jndiName);
    }

    public static Object getRemote(String jndiName) {
        Object o = null;
        try {
            o = Lookup.lookup(jndiName);
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
}
