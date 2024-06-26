#!/usr/bin/env bash

PROJ_DIR=.

# --------------------------------------
USER="tandcode"
ROOT_CA_CN="api-ca.skshukla.com"
SERVER_CN="$(hostname).local"
CLIENT_CN="api-client.skshukla.com"
# --------------------------------------

CERT_DIR=$PROJ_DIR/work/certs
mkdir -p $CERT_DIR
rm -rf $CERT_DIR/*.*
rm -rf $PROJ_DIR/src/main/resources/*.jks
rm -rf $PROJ_DIR/src/main/resources/*.p12

# Generate Certs
#------------------------------------------------------
echo -e "UA\nKyiv\nKyiv\n$USER\n$USER\n$USER\n$USER@example.com" | \
  openssl req -x509 -sha256 -days 3650 -newkey rsa:4096 -keyout $CERT_DIR/rootCA.key \
  -out $CERT_DIR/rootCA.crt -passout pass:12345678

echo -e "UA\nKyiv\nKyiv\n$USER\n$USER\n$USER\n$USER@example.com\n12345678\n12345678" | \
  openssl req -new -newkey rsa:4096 -keyout $CERT_DIR/server.key -out $CERT_DIR/server.csr -passout pass:12345678



echo "authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
subjectAltName = @alt_names
[alt_names]
DNS.1 = ${SERVER_CN}" > $CERT_DIR/server.ext

openssl x509 -req -CA $CERT_DIR/rootCA.crt -CAkey $CERT_DIR/rootCA.key -in $CERT_DIR/server.csr \
  -out $CERT_DIR/server.crt -days 365 -CAcreateserial -extfile $CERT_DIR/server.ext -passin pass:12345678


openssl pkcs12 -export -out $CERT_DIR/server.p12 -name "server-alias" -inkey $CERT_DIR/server.key \
  -in $CERT_DIR/server.crt -passin pass:12345678 -password pass:12345678


echo -e '12345678\n12345678\n12345678' | \
  keytool -importkeystore -srckeystore $CERT_DIR/server.p12 \
  -srcstoretype PKCS12 -destkeystore $CERT_DIR/myserver_keystore.jks -deststoretype JKS




echo -e '12345678\n12345678' | \
  keytool -import -trustcacerts -noprompt -alias ca -ext san=dns:${SERVER_CN},ip:127.0.0.1 \
  -file $CERT_DIR/rootCA.crt -keystore $CERT_DIR/myserver_truststore.jks



# CLIENT
echo -e "UA\nKyiv\nKyiv\n$USER\n$USER\n$USER\n$USER@example.com\n12345678\n12345678" | \
  openssl req -new -newkey rsa:4096 -keyout $CERT_DIR/client.key -out $CERT_DIR/client.csr -passout pass:12345678

openssl x509 -req -CA $CERT_DIR/rootCA.crt -CAkey $CERT_DIR/rootCA.key -in $CERT_DIR/client.csr \
  -out $CERT_DIR/client.crt -days 365 -CAcreateserial -passin pass:12345678


openssl pkcs12 -export -out $CERT_DIR/client.p12 -name "client" -inkey $CERT_DIR/client.key \
  -in $CERT_DIR/client.crt -passin pass:12345678 -password pass:12345678


cp -rf $CERT_DIR/*.jks $PROJ_DIR/src/main/resources

#curl -v --cacert ./work/certs/rootCA.crt https://$(hostname).local:8443/hello
#curl -v --cacert ./work/certs/rootCA.crt --cert ./work/certs/client.crt https://$(hostname).local:8443/hello