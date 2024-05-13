To create certificate, run:
```shell
#!/usr/bin/env bash

# generate key pair with certificate for server
keytool -genkeypair -alias server -keyalg RSA -keysize 2048 \
  -dname "CN=localhostServer" \
  -keystore server.p12 -keypass serverKeyPassword \
  -storetype PKCS12 -storepass serverStorePassword \
  -validity 3650
  
# generate key pair with certificate for client
keytool -genkeypair -alias client -keyalg RSA -keysize 2048 \
  -dname "CN=localhostClient" \
  -keystore client.p12 -keypass clientKeyPassword \
  -storetype PKCS12 -storepass clientStorePassword \
  -validity 3650
  
# export client certificate and import it to serverTrustStore
keytool -export -alias client -keystore client.p12 \
  -file client.cer -storepass clientStorePassword
keytool -import -alias client -file client.cer \
  -keystore server-truststore.p12 -storepass serverTrustStorePassword -noprompt

# export server certificate and import it to clientTrustStore
keytool -export -alias server -keystore server.p12 \
  -file server.cer -storepass serverStorePassword
keytool -import -alias server -file server.cer \
  -keystore client-truststore.p12 -storepass serverTrustStorePassword -noprompt
```