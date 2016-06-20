package pl.polsl.reservations.ejb.local;

/**
 *
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Interface represents users certificates pool/list
 */
public interface UsersCertifcatesPool {

    /**
     * Method to chech if given certificate is in pool
     *
     * @param certificate String with certificate
     * @return true if certificate is in pool
     */
    boolean checkCertificate(String certificate);

    /**
     * Method to remove given certificate from pool
     *
     * @param certificate String with certificate
     */
    void removeCertificate(String certificate);

    /**
     * Method to add certificate to pool
     *
     * @param certificate String with certificate
     */
    void addCertificate(String certificate);

    /**
     * Method returns user context by his certificate
     *
     * @param certificate String with certificate
     * @return UserContext object
     */
    UserContext getUserContextByCertificate(String certificate);

}
