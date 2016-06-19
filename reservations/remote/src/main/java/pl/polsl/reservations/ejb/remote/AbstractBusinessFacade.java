package pl.polsl.reservations.ejb.remote;

/**
 *
 * @author matis
 */
public interface AbstractBusinessFacade {

    Boolean certificateBean(String certificate);

    String getUserCertificate();

}
