misc examples
=============

SSL
---

        openssl pkcs12 -export -name myserver -inkey myserver.key -in myserver.crt -out tmp.p12
        keytool -importkeystore -srcstoretype PKCS12 -srckeystore tmp.p12 -alias myserver -destkeystore keystore.jks
        keytool -import -v -trustcacerts -alias myintermediate -file myintermediate.crt -keystore keystore.jks
        rm tmp.p12

