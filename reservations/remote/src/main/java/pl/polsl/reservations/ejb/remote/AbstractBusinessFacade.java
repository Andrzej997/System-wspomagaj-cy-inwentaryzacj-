package pl.polsl.reservations.ejb.remote;

/**
 *
 * @author Mateusz Sojka
 * @version 1.0
 * 
 * Interface which is base to all remote bussines interfaces
 */
public interface AbstractBusinessFacade {

    /**
     * Method certificates all business beans
     * 
     * @param certificate Client certificate
     * @return true if success
     */
    Boolean certificateBean(String certificate);

    /**
     * Method to get current bean certificate
     * @return String with bean certificate
     */
    String getUserCertificate();

}
