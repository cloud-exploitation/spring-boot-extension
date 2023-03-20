package com.livk.rocket.producer.controller;

import com.livk.commons.jackson.JacksonUtils;
import com.livk.rocket.constant.RocketConstant;
import com.livk.rocket.producer.dto.RocketDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author laokou
 * @author livk
 */
@SpringBootTest
@AutoConfigureMockMvc
class RocketProducerTest {

    @Autowired
    MockMvc mockMvc;

    RocketDTO dto = new RocketDTO();

    @BeforeEach
    public void init() {
        String msg = "Java第一，老寇无敌。千秋万代，一统江湖。";
        dto.setData(msg);
    }


    @Test
    void sendMessage() throws Exception {
        mockMvc.perform(post("/api/send/{topic}", RocketConstant.LIVK_MESSAGE_TOPIC)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtils.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(log());
    }

    @Test
    void sendAsyncMessage() throws Exception {
        mockMvc.perform(post("/api/sendAsync/{topic}", RocketConstant.LIVK_MESSAGE_TOPIC)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtils.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(log());
    }

    @Test
    void sendOneMessage() throws Exception {
        mockMvc.perform(post("/api/sendOne/{topic}", RocketConstant.LIVK_MESSAGE_TOPIC)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtils.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(log());
    }

    @Test
    void sendTransactionMessage() throws Exception {
        mockMvc.perform(post("/api/sendTransaction/{topic}", RocketConstant.LIVK_MESSAGE_TOPIC)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JacksonUtils.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(log());
    }
}