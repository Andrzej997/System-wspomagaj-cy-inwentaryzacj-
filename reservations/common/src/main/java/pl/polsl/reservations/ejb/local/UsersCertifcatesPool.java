package pl.polsl.reservations.ejb.local;

/**
 *
 * @author matis
 */
public interface UsersCertifcatesPool {
    
    boolean checkCertificate(String certificate);

    void removeCertificate(String certificate);

    void addCertificate(String certificate);

    UserContext getUserContextByCertificate(String certificate);
    
}
