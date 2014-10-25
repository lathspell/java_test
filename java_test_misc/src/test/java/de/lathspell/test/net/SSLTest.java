package de.lathspell.test.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * SSL/TLS Verbindung mit selbstsigniertem Zertifikat.
 */
public class SSLTest {

    private HostnameVerifier originalHV;
    private SSLSocketFactory originalSSLSocketFactory;

    @Before
    public void setUp() {
        originalSSLSocketFactory = javax.net.ssl.HttpsURLConnection.getDefaultSSLSocketFactory();
        originalHV = javax.net.ssl.HttpsURLConnection.getDefaultHostnameVerifier();
    }

    @After
    public void tearDown() {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(originalHV);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(originalSSLSocketFactory);
    }

    private void installFakeKeyStore() throws Exception {
        // 1. Keystore initialisieren und Inhalt von Resource laden.
        // Die Datei "keystore" muss irgendwo im CLASSPATH liegen und kann
        // über das keytool Programm modifiziert werden.
        InputStream is = ClassLoader.getSystemResourceAsStream("keystore");
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(is, null);

        // 2. Keystore über TrustManager in SSLContext einfügen.
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);
        TrustManager[] tm = tmf.getTrustManagers();
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, tm, null);

        // 3. Die DefaultSSLSocketFactory mit Hilfe des modifizierten SSLContextes definieren.
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // 4. Einen modifizierten HostnameVerifyer an die Klasse HttpsURLConnection übergeben.
        // Wird nur aufgerufen wenn das Zertifikat i.O. aber der Hostname falsch ist.
        HostnameVerifier myHV = new HostnameVerifier() {

            @Override
            public boolean verify(String hostname, SSLSession session) {
                try {
                    for (X509Certificate cert : (X509Certificate[]) session.getPeerCertificates()) {
                        if (cert.getSubjectX500Principal().getName().equals(session.getPeerHost())) {
                            return true;
                        }
                    }
                    return false;
                } catch (SSLPeerUnverifiedException ex) {
                    return false;
                }
            }
        };
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(myHV);
    }

    private String getFirstLine(String urlString) throws Exception {
        URL url = new URL(urlString);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String firstLine = in.readLine();
        while ((firstLine.equals("") || firstLine.equals("\n")) && in.ready()) {
            firstLine = in.readLine();
        }
        in.close();
        return firstLine;
    }

    /**
     * Testen mit gültigem Zertifikat.
     */
    @Test
    public void testValid() throws Exception {
        String firstLine = getFirstLine("https://accounts.google.com/ServiceLogin");
        assertTrue("was " + firstLine, firstLine.contains("DOCTYPE"));
    }

    /**
     * Testen mit gültigem Zertifikat einer anderen URL
     */
    @Ignore
    @Test
    public void testCertValidButUnknown() throws Exception {
        installFakeKeyStore();
        assertTrue(getFirstLine("https://www.netcologne.de/").contains("DOCTYPE"));
    }
}
