package com.backbone.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CoreServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
    }

    @Test
    public void dummyReturnsDefaultMessage() throws Exception {
        String url = "/dummy";
        String expectedMessage = "Hello";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    public void dummyReturnGivenMassage() throws Exception {
        String messageGiven = "Abidin";
        String url = "/dummy/" + messageGiven;

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(messageGiven)));
    }

    //  Read ops
    @Test
    public void reviewShouldReturnOneRecord() throws Exception {
        String url = "/review/1";
        String expectedMessage = "\"id\":1";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$..id", hasSize(1)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    public void reviewGetsNotAvailableIDAndReturnsNotFound() throws Exception {
        String url = "/review/200";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string(blankOrNullString()));
    }

    @Test
    public void reviewGetsNotValidIDAndReturnsNotFound() throws Exception {
        String url = "/review/1ax";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(content().string(blankOrNullString()));
    }

    @Test
    public void reviewGetsNoIDAndReturnsNotFound() throws Exception {
        String url = "/review/";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(405))
                .andExpect(content().string(blankOrNullString()));
    }

    //
    @Test
    public void reviewsShouldReturnOneHundredRecords() throws Exception {
        String url = "/reviews/page/0/size/100";
        String expectedMessage = "\"id\":100";
        int recordCount = 100;

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$..id", hasSize(recordCount)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[99].id", is(100)))
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    public void reviewsReturnOneRecord() throws Exception {
        String url = "/reviews/page/0/size/1";
        String expectedMessage = "\"id\":1";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    public void reviewsReturnDefaultPaging() throws Exception {
        String url = "/reviews/";
        String expectedMessage = "\"id\":100";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(not(containsString(expectedMessage))));
    }

    @Test
    public void reviewsGetNotValidPageNumberAndReturnsNotFound() throws Exception {
        String url = "/reviews/page/1a";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(content().string(blankOrNullString()));
    }

    @Test
    public void reviewsReturnOKButNoRecordAfterLastPage() throws Exception {
        String url = "/reviews/page/20/size/10";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(204))
                .andExpect(content().string(blankOrNullString()));
    }

    // service ops

    // save
    @Test
    public void reviewUpdateWithAllFieldsAndID() throws Exception {
        String url = "/review";
        String updatedReview = "{\"id\":10,\n" +
                               " \"userName\":\"viladamir34\",\n" +
                               " \"productId\":7,\n" +
                               " \"title\":\"title\",\n" +
                               " \"rating\":4,\n" +
                               " \"description\":\"description\",\n" +
                               " \"verifiedPurchase\":true,\n" +
                               " \"helpful\":true,\n" +
                               " \"abuse\":true}";

        this.mockMvc.perform(post(url)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(updatedReview))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.verifiedPurchase", is(true)))
                .andExpect(jsonPath("$.description", is("description")));
    }

    //todo: First, implement partial update w/ PUT, then test will pass
    @Test
    public void reviewUpdatePartialWithSomeFieldsAndID() throws Exception {
        String url = "/review";
        String updatedReview = "{\"id\":10,\n" +
                               " \"title\":\"title PARTIAL\",\n" +
                               " }";

        //set last values, then check
        reviewUpdateWithAllFieldsAndID();

        this.mockMvc.perform(put(url)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(updatedReview))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.title", is("title PARTIAL")))
                .andExpect(jsonPath("$.verifiedPurchase", is(true)))
                .andExpect(jsonPath("$.description", is("description")));
    }

    @Test
    public void reviewCreateWithoutID() throws Exception {
        String url = "/review";
        String newReview = "{" +
                "          \"userName\":\"viladamir34\",\n" +
                "          \"productId\":7,\n" +
                "          \"title\":\"title\",\n" +
                "          \"rating\":4,\n" +
                "          \"description\":\"description\",\n" +
                "          \"verifiedPurchase\":true,\n" +
                "          \"helpful\":true,\n" +
                "          \"abuse\":true}";

        this.mockMvc.perform(post(url)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(newReview))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(greaterThan(100))))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.verifiedPurchase", is(true)))
                .andExpect(jsonPath("$.helpful", is(true)))
                .andExpect(jsonPath("$.abuse", is(true)));
    }

    @Test
    public void reviewCreateWithoutRequestBodyAndReturnsBadRequest() throws Exception {
        String url = "/review";

        this.mockMvc.perform(post(url)
                               .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(400));
    }

    @Test
    public void reviewCreateWithIDButReturnsAutoGeneratedID() throws Exception {
        String url = "/review";
        String newReview = "{\"id\":1500,\n" +
                "          \"userName\":\"viladamir34\",\n" +
                "          \"productId\":7,\n" +
                "          \"title\":\"title\",\n" +
                "          \"rating\":4,\n" +
                "          \"description\":\"description\",\n" +
                "          \"verifiedPurchase\":true,\n" +
                "          \"helpful\":true,\n" +
                "          \"abuse\":true}";

        this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(newReview))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(not(1500))))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.verifiedPurchase", is(true)))
                .andExpect(jsonPath("$.helpful", is(true)))
                .andExpect(jsonPath("$.abuse", is(true)));
    }

    // delete
    @Test
    public void reviewDeleteWithID() throws Exception {
        String url = "/review/99";

        this.mockMvc.perform(delete(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        //check deleted record
        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(404));
    }

    //todo: after some operations, there must be steps (or other kind of methods)
    // which checks the cache
}
