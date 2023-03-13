package com.raken.email.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raken.email.constant.ApplicationConstants;
import com.raken.email.dto.EmailDto;
import com.raken.email.exception.BadRequestException;
import com.raken.email.exception.SendEmailException;
import com.raken.email.service.EmailService;
import com.raken.email.service.EnrichService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Dayan Kodippily
 */

@WebMvcTest(EmailNotificationController.class)
class EmailNotificationControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @MockBean
    EmailService emailService;

    @MockBean
    EnrichService enrichService;

    private EmailDto emailDto;


    @BeforeEach
    void setup() {
        emailDto = new EmailDto();
        emailDto.setToAddresses(Arrays.asList("dkodippily@gmail.co", "abc@mail.com"));
        emailDto.setSubject("subject");
        emailDto.setContent("Content");
    }

    @Test
    public void sendEmailSuccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApplicationConstants.BASE_API_URL + ApplicationConstants.EMAIL_SERVICE_ENDPOINT)
                        .content(asJsonString(emailDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 202));
    }

    @Test
    public void sendEmailEnrichSuccess() throws Exception {

        when(enrichService.getQuoteOfTheDay()).thenAnswer((Answer<String>) invocation -> "A random quote");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApplicationConstants.BASE_API_URL + ApplicationConstants.EMAIL_SERVICE_ENDPOINT + "?enrich=true")
                        .content(asJsonString(emailDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(result -> assertTrue(result.getResponse().containsHeader("MAIL_QUEUE_ID")))
                .andExpect(result -> emailDto.getContent().contains("A random quote"));
    }

    @Test
    public void sendEmailBadRequest() throws Exception {


        emailDto.setToAddresses(null);
        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApplicationConstants.BASE_API_URL + ApplicationConstants.EMAIL_SERVICE_ENDPOINT)
                        .content(asJsonString(emailDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadRequestException))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().equals("Empty or null receiver address")));
    }

    @Test
    public void sendEmailDownstreamServiceSendEmailException() throws Exception {

        doThrow(new SendEmailException("API Error")).when(emailService).send(any());

        mockMvc.perform(MockMvcRequestBuilders
                        .post(ApplicationConstants.BASE_API_URL + ApplicationConstants.EMAIL_SERVICE_ENDPOINT)
                        .content(asJsonString(emailDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException().getMessage().equals("API Error")))
                .andExpect(result -> assertTrue(result.getResponse().getStatus() == 500));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Disabled("TODO")
    public void sendEmailApiAuthorizationException() throws Exception {
    }

    @Disabled("TODO")
    public void sendEmailDuplicateInputAddressesException() throws Exception {
    }


}