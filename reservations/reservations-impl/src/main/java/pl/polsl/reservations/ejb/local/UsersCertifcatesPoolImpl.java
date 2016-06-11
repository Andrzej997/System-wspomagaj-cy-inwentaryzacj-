package pl.polsl.reservations.ejb.local;

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author matis
 */
public class UsersCertifcatesPoolImpl implements UsersCertifcatesPool, Serializable{

    private static final long serialVersionUID = -6291443263110524272L;

    private final HashMap<String, UserContext> currentUsersMap;
    private static final UsersCertifcatesPoolImpl instance = new UsersCertifcatesPoolImpl();

    private UsersCertifcatesPoolImpl() {
        currentUsersMap = new HashMap<>();
    }

    public static UsersCertifcatesPoolImpl getInstance() {
        return instance;
    }

    @Override
    public void addCertificate(String certificate) {
        currentUsersMap.put(certificate, (UserContext) new UserContextImpl());
    }

    @Override
    public void removeCertificate(String certificate) {
        currentUsersMap.remove(certificate);
    }

    @Override
    public boolean checkCertificate(String certificate) {
        return currentUsersMap.containsKey(certificate);
    }

    @Override
    public UserContext getUserContextByCertificate(String certificate) {
        return currentUsersMap.get(certificate);
    }

}
