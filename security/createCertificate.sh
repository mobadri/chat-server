 keytool -genkey -alias registry -keyalg RSA -keystore registry.ks
  
 keytool -export -alias registry -keystore registry.ks -file registry.crt

 keytool -genkey -alias client -keyalg RSA -keystore client.ks

 keytool -import -alias registry -keystore client.ts -file registry.crt

 keytool -export -alias client -keystore client.ks -file client.crt

 keytool -import -alias client -keystore registry.ts -file client.crt
