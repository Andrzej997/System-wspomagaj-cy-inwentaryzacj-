package pl.polsl.reservations.client;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;

/**
 *
 * @author matis
 */
public class ClientSessionCertificate {

    private static final String randomUUID = UUID.randomUUID().toString();
    //Singleton
    private static final ClientSessionCertificate clientSessionCertificate = new ClientSessionCertificate();

    private ClientSessionCertificate() {
    }

    public static ClientSessionCertificate getInstance() {
        return clientSessionCertificate;
    }

    public String getCertificate() throws UnsupportedEncodingException {
        return Base64.getMimeEncoder().encodeToString(randomUUID.getBytes("utf-8"));
    }

    public String getRandomUUID() {
        return randomUUID;
    }

}
