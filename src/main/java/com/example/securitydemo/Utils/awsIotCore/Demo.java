//package com.example.securitydemo.Utils.awsIotCore;
//
//public class Demo {
//    String clientEndpoint = "<prefix>-ats.iot.<region>.amazonaws.com";   // use value returned by describe-endpoint --endpoint-type "iot:Data-ATS"
//    String clientId = "<unique client id>";                              // replace with your own client ID. Use unique client IDs for concurrent connections.
//    String certificateFile = "<certificate file>";                       // X.509 based certificate file
//    String privateKeyFile = "<private key file>";                        // PKCS#1 or PKCS#8 PEM encoded private key file
//
//    // SampleUtil.java and its dependency PrivateKeyReader.java can be copied from the sample source code.
//// Alternatively, you could load key store directly from a file - see the example included in this README.
//    KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
//    AWSIotMqttClient client = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
//
//// optional parameters can be set before connect()
//client.connect();
//}
