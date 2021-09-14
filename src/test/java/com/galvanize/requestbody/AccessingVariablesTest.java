package com.galvanize.requestbody;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccessingVariables.class)
public class AccessingVariablesTest {

    @Autowired
    MockMvc mvc;

    // Request parameters
    @Test
    public void getCarReturnsFormattedString() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/car?make=Toyota&model=Camry");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("The Toyota Camry was made in 2015"));
    }

    // Path Variables

    @Test
    public void correctlyCalculatesAreaOfARectangle() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/rectangle/5/2");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("The area of the rectangle is 10"));
    }

    @Test
    public void badRequestIfRectangleIsMissingDimension() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/rectangle/5");

        this.mvc.perform(request)
                .andExpect(status().is(404));
    }


    // Request Body
    // "{\"author\" : \"Dwayne\", \"content\" : \"Firsties!\"}"
    @Test
    public void commentsCanBeFormatted() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.post("/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"author\" : \"Dwayne\", \"content\" : \"Firsties!\"}");

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Dwayne says Firsties!"));
    }

    @Test
    public void facebookCanGenerateAComment() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.post("/comments")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content(buildUrlEncodedFormEntity(
                        "content", "Firsties!",
                        "author", "Dwayne"
                ));

//        RequestBuilder request = MockMvcRequestBuilders.post("/comments")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .param("content", "Firsties!")
//                .param("author", "Dwayne");
//                .content(buildUrlEncodedFormEntity(
//                        "content", "Firsties!",
//                        "author", "Dwayne"
//                ));

        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Dwayne says Firsties!"));
    }

    private String buildUrlEncodedFormEntity(String... params) {
        if( (params.length % 2) > 0 ) {
            throw new IllegalArgumentException("Need to give an even number of parameters");
        }
        StringBuilder result = new StringBuilder();
        for (int i=0; i<params.length; i+=2) {
            if( i > 0 ) {
                result.append('&');
            }
            try {
                result.
                        append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).
                        append('=').
                        append(URLEncoder.encode(params[i+1], StandardCharsets.UTF_8.name()));
            }
            catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return result.toString();
    }

}





























//    @Test
//    public void facebookCanGenerateAComment() throws Exception{
//        MockHttpServletRequestBuilder request = post("/comments")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .content(buildUrlEncodedFormEntity(
//                        "content", "Firsties!",
//                        "author", "Dwayne"
//                ));
//
//        this.mvc.perform(request)
//                .andExpect(status().isOk())
//                .andExpect(content().string("Dwayne says Firsties!"));
//    }
//
//    private String buildUrlEncodedFormEntity(String... params) {
//        if( (params.length % 2) > 0 ) {
//            throw new IllegalArgumentException("Need to give an even number of parameters");
//        }
//        StringBuilder result = new StringBuilder();
//        for (int i=0; i<params.length; i+=2) {
//            if( i > 0 ) {
//                result.append('&');
//            }
//            try {
//                result.
//                        append(URLEncoder.encode(params[i], StandardCharsets.UTF_8.name())).
//                        append('=').
//                        append(URLEncoder.encode(params[i+1], StandardCharsets.UTF_8.name()));
//            }
//            catch (UnsupportedEncodingException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return result.toString();
//    }
