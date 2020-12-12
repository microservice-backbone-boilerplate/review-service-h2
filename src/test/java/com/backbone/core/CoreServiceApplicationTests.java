package com.backbone.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
class CoreServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void dummyShouldReturnDefaultMessage() throws Exception {
        String url = "/dummy";
        String expectedMessage = "Hello";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    public void dummyShouldReturnGivenMassage() throws Exception {
        String messageGiven = "Abidin";
        String url = "/dummy/" + messageGiven;

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(messageGiven)));
    }

    @Test
    public void reviewShouldReturnFirstRecord() throws Exception {
        String url = "/review/1";
        String expectedMessage = "\"id\":1";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    public void reviewShouldNotReturnAnyRecord() throws Exception {
        String url = "/review/101";
        String expectedMessage = "Not Found";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(blankOrNullString()));
    }

}
