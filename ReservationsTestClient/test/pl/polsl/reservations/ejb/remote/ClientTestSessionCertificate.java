package pl.polsl.reservations.ejb.remote;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

/**
 *
 * @author matis
 */
public class ClientTestSessionCertificate {
    
    private static final String randomUUID = UUID.randomUUID().toString();
    //Singleton
    private static final ClientTestSessionCertificate clientSessionCertificate = new ClientTestSessionCertificate();
    
    private ClientTestSessionCertificate(){}

    public static ClientTestSessionCertificate getInstance(){
        return clientSessionCertificate;
    }
    
    public String getCertificate() throws UnsupportedEncodingException {
        return Base64.getMimeEncoder().encodeToString(randomUUID.getBytes("utf-8"));
    }

    public String getRandomUUID() {
        return randomUUID;
    }
    
}
