package com.apollo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class ApolloApplicationTests {

    @Test
    void contextLoads() {
        // Confirms Spring context, entity metadata, and configurations load cleanly
    }
}
