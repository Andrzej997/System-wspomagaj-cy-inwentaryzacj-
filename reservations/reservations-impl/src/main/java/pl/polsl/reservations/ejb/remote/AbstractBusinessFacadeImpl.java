package pl.polsl.reservations.ejb.remote;

import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.ejb.local.UsersCertifcatesPool;
import pl.polsl.reservations.ejb.local.UsersCertifcatesPoolImpl;

/**
 *
 * @author matis
 */
public abstract class AbstractBusinessFacadeImpl implements AbstractBusinessFacade {

    private String certificate;

    private final UsersCertifcatesPool usersCertifcatesPool;

    protected AbstractBusinessFacadeImpl() {
        certificate = null;
        usersCertifcatesPool = UsersCertifcatesPoolImpl.getInstance();
    }

    @Override
    public Boolean certificateBean(String certificate) {
        this.certificate = certificate;
        if (certificate != null) {
            boolean checkCertificate = getUsersCertifcatesPool().checkCertificate(certificate);
            if (getUsersCertifcatesPool().checkCertificate(certificate)) {
                return checkCertificate;
            } else {
                //pierwsze wywolanie jakiegokolwiek beana
                getUsersCertifcatesPool().addCertificate(certificate);
                return true;
            }
        }
        return false;
    }

    public final UserContext getCurrentUserContext() {
        return getUsersCertifcatesPool().getUserContextByCertificate(certificate);
    }

    @Override
    public String getUserCertificate() {
        return certificate;
    }

    public UsersCertifcatesPool getUsersCertifcatesPool() {
        return usersCertifcatesPool;
    }

}
