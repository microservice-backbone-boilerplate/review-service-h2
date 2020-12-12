package com.backbone.core;

import com.backbone.core.demo.DummyController;
import com.backbone.core.demo.ReviewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SmokeTests {

    @Autowired
    DummyController dummyController;

    @Autowired
    ReviewController reviewController;

    @Test
    void contextLoads() {
        assertThat(dummyController).isNotNull();
        assertThat(reviewController).isNotNull();
    }
}
