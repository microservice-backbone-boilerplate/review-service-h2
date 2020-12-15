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
        //todo: initial records vs initialize records here for testing?
        // consider it
    }

    //  Read ops
    @Test
    public void reviewShouldReturnOneRecord() throws Exception {
        String url = "/review/1";
        String expectedMessage = "\"id\":1";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    public void reviewWithNotAvailableIDAndReturnsNotFound() throws Exception {
        String url = "/review/200";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string(blankOrNullString()));
    }

    @Test
    public void reviewWithNotValidIDAndReturnsNotFound() throws Exception {
        String url = "/review/1ax";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(content().string(blankOrNullString()));
    }

    @Test
    public void reviewWithMissingIDAndReturnNotSupportedMethod() throws Exception {
        String url = "/review/";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(405))
                .andExpect(content().string(blankOrNullString()));
    }

    //
    @Test
    public void reviewsWithZeroPagingOneHundredSizeAndReturnOneHundredRecords() throws Exception {
        String url = "/reviews/page/0/size/100";
        String expectedMessage = "\"id\":100";
        int recordCount = 100;

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$..id", hasSize(recordCount)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    public void reviewsWithZeroPagingOneSizeAndReturnsOneRecord() throws Exception {
        String url = "/reviews/page/0/size/1";
        String expectedMessage = "\"id\":1";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(expectedMessage)));
    }

    @Test
    public void reviewsSupportDefaultPaging() throws Exception {
        String url = "/reviews/";
        String expectedMessage = "\"id\":100";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(not(containsString(expectedMessage))));
    }

    @Test
    public void reviewsWithNotValidPageNumberAndReturnsNotFound() throws Exception {
        String url = "/reviews/page/1a";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(content().string(blankOrNullString()));

        //todo: Bad request is more appropriate, but it expects String in url. May be a ValidationService step?
    }

    @Test
    public void reviewsWithNotValidSizeNumberAndReturnsNotFound() throws Exception {
        String url = "/reviews/page/1/size/2a";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(400))
                .andExpect(content().string(blankOrNullString()));

        //todo: Bad request is more appropriate, but it expects String in url. May be a ValidationService step?
    }

    @Test
    public void reviewsWithBigPageNumberAndReturnsNoContent() throws Exception {
        String url = "/reviews/page/20/size/10";

        this.mockMvc.perform(get(url))
                .andDo(print())
                .andExpect(status().is(204))
                .andExpect(content().string(blankOrNullString()));
    }

    // service ops

    // save
    @Test
    public void reviewUpdateAllFieldsWithID() throws Exception {
        String url = "/review";
        String updatedReview = "{\"id\":10," +
                               "\"userName\":\"viladamir34\"," +
                               "\"productId\":7," +
                               "\"title\":\"title\"," +
                               "\"rating\":4," +
                               "\"description\":\"description\"," +
                               "\"verifiedPurchase\":true," +
                               "\"helpful\":true," +
                               "\"abuse\":true}";

        this.mockMvc.perform(post(url)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(updatedReview))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.verifiedPurchase", is(true)))
                .andExpect(jsonPath("$.helpful", is(true)))
                .andExpect(jsonPath("$.abuse", is(true)));
    }

    //todo: First, implement partial update w/ PUT, then test will pass
    @Test
    public void reviewUpdatePartiallyWithID() throws Exception {

        // set last values, then check
        String url = "/review";
        String updatedReview = "{\"id\":10," +
                                "\"userName\":\"viladamir34\"," +
                                "\"productId\":7," +
                                "\"title\":\"title\"," +
                                "\"rating\":4," +
                                "\"description\":\"description\"," +
                                "\"verifiedPurchase\":true," +
                                "\"helpful\":true," +
                                "\"abuse\":true}";

        this.mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedReview))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.title", is("title")))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.verifiedPurchase", is(true)))
                .andExpect(jsonPath("$.helpful", is(true)))
                .andExpect(jsonPath("$.abuse", is(true)));

        //update partially
        String lastUpdatedReview = "{\"id\":10," +
                                   "\"title\":\"title PARTIAL\"," +
                                   " }";

        this.mockMvc.perform(put(url)
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(lastUpdatedReview))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.title", is("title PARTIAL")))
                .andExpect(jsonPath("$.rating", is(4)))
                .andExpect(jsonPath("$.description", is("description")))
                .andExpect(jsonPath("$.verifiedPurchase", is(true)))
                .andExpect(jsonPath("$.helpful", is(true)))
                .andExpect(jsonPath("$.abuse", is(true)));
    }

    @Test
    public void reviewCreateWithoutID() throws Exception {
        String url = "/review";

        //todo: be sure >100 id is not available,
        // or delete all available records, first

        String newReview = "{" +
                           "\"userName\":\"viladamir34\"," +
                           "\"productId\":7," +
                           "\"title\":\"title\"," +
                           "\"rating\":4," +
                           "\"description\":\"description\"," +
                           "\"verifiedPurchase\":true," +
                           "\"helpful\":true," +
                           "\"abuse\":true}";

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
        String newReview = "{\"id\":1500," +
                           "\"userName\":\"viladamir34\"," +
                           "\"productId\":7," +
                           "\"title\":\"title\"," +
                           "\"rating\":4," +
                           "\"description\":\"description\"," +
                           "\"verifiedPurchase\":true," +
                           "\"helpful\":true," +
                           "\"abuse\":true}";

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
