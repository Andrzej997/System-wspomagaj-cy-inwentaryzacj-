package pl.polsl.reservations.ejb.local;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Class represents users certificates pool/list to preserve user session
 */
public class UsersCertifcatesPoolImpl implements UsersCertifcatesPool, Serializable {

    private static final long serialVersionUID = -6291443263110524272L;

    /**
     * Map of certificates and context, certificate is a key to user context
     */
    private final HashMap<String, UserContext> currentUsersMap;
    
    /**
     * Singleton
     */
    private static final UsersCertifcatesPoolImpl instance = new UsersCertifcatesPoolImpl();

    private UsersCertifcatesPoolImpl() {
        currentUsersMap = new HashMap<>();
    }

    public static UsersCertifcatesPoolImpl getInstance() {
        return instance;
    }

    /**
     * Method to add certificate to pool
     *
     * @param certificate String with certificate
     */
    @Override
    public void addCertificate(String certificate) {
        currentUsersMap.put(certificate, (UserContext) new UserContextImpl());
    }

    /**
     * Method to remove given certificate from pool
     *
     * @param certificate String with certificate
     */
    @Override
    public void removeCertificate(String certificate) {
        currentUsersMap.remove(certificate);
    }

    /**
     * Method to chech if given certificate is in pool
     *
     * @param certificate String with certificate
     * @return true if certificate is in pool
     */
    @Override
    public boolean checkCertificate(String certificate) {
        return currentUsersMap.containsKey(certificate);
    }

    /**
     * Method returns user context by his certificate
     *
     * @param certificate String with certificate
     * @return UserContext object
     */
    @Override
    public UserContext getUserContextByCertificate(String certificate) {
        return currentUsersMap.get(certificate);
    }

}
