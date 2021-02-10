package org.sunbird.contentvalidation.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ConfigsTest {

   final String CONTENTSERVICEHOST_CONST = "contentServiceHost";

    final String PROFANITY_SERVICE_CONST = "profanityServicePath";

    @Test
    void testConfig(){
        Configuration configuration = new Configuration();
        configuration.setContentServiceHost(CONTENTSERVICEHOST_CONST);
        configuration.setProfanityServicePath(PROFANITY_SERVICE_CONST);
        assertTrue(!configuration.getContentServiceHost().isEmpty());
        assertTrue(!configuration.getProfanityServicePath().isEmpty());
    }
}
