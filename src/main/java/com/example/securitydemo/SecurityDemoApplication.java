package com.example.securitydemo;

import com.example.securitydemo.Service.DetectorUtil;
import com.example.securitydemo.Service.FileEncodingService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
@ImportResource("classpath:springProfiles-config.xml")
public class SecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }


    String appDesciption = "@project.description@";
    String appVersion = "@project.version@";

//    @Autowired
//    private OpenAPI openAPI;

//    @Override
//    public void run(ApplicationArguments args) throws Exception {
////        String postmanJson = PostmanExportUtils.exportOpenAPI(openAPI);
////
////        try (FileOutputStream stream = new FileOutputStream("postman-collection.json")) {
////            stream.write(postmanJson.getBytes());
////        } catch (IOException e) {
////            // handle exception
////        }
////        FileEncodingService fileEncodingService = new FileEncodingService();
////        String curPath = "/home/bjit/gcp_uploads/All_Valid_Data_2.csv";
////        String newPath = "/home/bjit/gcp_uploads/All_Valid_Data_3.csv";
////        FileInputStream curFile = new FileInputStream(curPath);
////        FileInputStream newFile = new FileInputStream(newPath);
////        InputStreamReader curInput = new InputStreamReader(curFile);
////        DetectorUtil.getCharsetName(curFile.readAllBytes());
////        fileEncodingService.changeFileEncoding(curPath, newPath, curInput.getEncoding(), StandardCharsets.ISO_8859_1.name());
////        DetectorUtil.getCharsetName(newFile.readAllBytes());
//    }


//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("sample application API")
//                        .version(appVersion)
//                        .description(appDesciption)
//                        .termsOfService("http://swagger.io/terms/")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
//    }

}
