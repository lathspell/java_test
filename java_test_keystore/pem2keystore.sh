#!/bin/bash -ue

KEY=foo.example.com.key
CERT=foo.example.com.crt
CA_CHAIN=ca_chain.crt
PASS=changeit
KEYSTORE=keystore.jks
ALIAS=foo.example.com
CA_ALIAS=ca

set -x

tmp_pem=$(mktemp)
tmp_p12=$(mktemp)
cat $KEY $CERT > $tmp_pem

openssl pkcs12 -export \
        -name $ALIAS \
        -in $tmp_pem \
        -out $tmp_p12 \
        -passout pass:$PASS

rm -f $KEYSTORE

keytool -importkeystore \
        -srcstorepass $PASS \
        -deststorepass $PASS \
        -srckeypass $PASS \
        -destkeypass $PASS \
        -srckeystore $tmp_p12 -srcstoretype PKCS12 -alias $ALIAS \
        -destkeystore $KEYSTORE

keytool -import -trustcacerts -noprompt \
        -file $CA_CHAIN \
        -alias $CA_ALIAS \
        -keystore $KEYSTORE \
        -storepass $PASS

keytool -list -keystore $KEYSTORE -storepass $PASS --rfc
keytool -list -keystore $KEYSTORE -storepass $PASS
