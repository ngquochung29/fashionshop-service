package shop.server.webconfig.gcpconfig;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import io.grpc.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author : Nguyen Quoc Hung
 * @mailto : hungnqdatn04@gmail.com
 * @created : 17/7/2025,
 **/
@Configuration
public class GCPConfig {

    @Value("${gcp.credentials.path}")
    private String credentialsPath;

    @Bean
    public Storage storage() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(credentialsPath.getBytes(StandardCharsets.UTF_8));
        GoogleCredentials credentials = GoogleCredentials.fromStream(byteArrayInputStream);
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }
}
