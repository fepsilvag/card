package com.hyperativa.card.infrastructure.controller;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import com.hyperativa.card.infrastructure.controller.util.IntegrationTestUtil;
import com.hyperativa.openapi.api.CardsApi;
import com.hyperativa.representation.CardRequestRepresentation;
import com.hyperativa.representation.CardResponseRepresentation;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith({MockitoExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardControllerIT implements CardsApi {

    @Autowired
    private MockMvc mockMvc;

    private static CardResponseRepresentation cardResponseRepresentation1;
    private static CardResponseRepresentation cardResponseRepresentation2;

    @Test
    @Order(1)
    void createCard_shouldReturnOk() throws Exception {
        // Given
        CardRequestRepresentation cardRequestRepresentation1 = new CardRequestRepresentation("4794851231000854");
        CardRequestRepresentation cardRequestRepresentation2 = new CardRequestRepresentation("1487912311701354");

        // Then
        cardResponseRepresentation1 = IntegrationTestUtil.getMapper().readValue(
                createCard(mockMvc, cardRequestRepresentation1)
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").exists())
                        .andExpect(jsonPath("$.cardNumber").value(cardRequestRepresentation1.getCardNumber()))
                        .andReturn().getResponse().getContentAsString()
                , CardResponseRepresentation.class);

        cardResponseRepresentation2 = IntegrationTestUtil.getMapper().readValue(
                createCard(mockMvc, cardRequestRepresentation2)
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id").exists())
                        .andExpect(jsonPath("$.id", not(cardResponseRepresentation1.getId())))
                        .andExpect(jsonPath("$.cardNumber").value(cardRequestRepresentation2.getCardNumber()))
                        .andReturn().getResponse().getContentAsString()
                , CardResponseRepresentation.class);
    }

    @Test
    @Order(2)
    void createCard_shouldReturnConflict() throws Exception {
        CardRequestRepresentation cardRequestRepresentation1 = new CardRequestRepresentation(
                cardResponseRepresentation1.getCardNumber());

        CardRequestRepresentation cardRequestRepresentation2 = new CardRequestRepresentation(
                cardResponseRepresentation2.getCardNumber());

        // Then
        createCard(mockMvc, cardRequestRepresentation1)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(ErrorCodeEnum.CAR001.toString()));

        createCard(mockMvc, cardRequestRepresentation2)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(ErrorCodeEnum.CAR001.toString()));
    }

    @Test
    @Order(3)
    void whenFind_shouldReturnSavedCard() throws Exception {
        // Then
        searchCard(mockMvc, cardResponseRepresentation1.getCardNumber())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardResponseRepresentation1.getId() + ""))
                .andExpect(jsonPath("$.id", not(cardResponseRepresentation2.getId())));

        searchCard(mockMvc, cardResponseRepresentation2.getCardNumber())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(cardResponseRepresentation2.getId() + ""))
                .andExpect(jsonPath("$.id", not(cardResponseRepresentation1.getId())));
    }

    static ResultActions createCard(
            MockMvc mockMvc,
            CardRequestRepresentation cardRequestRepresentation
    ) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .post("/cards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(IntegrationTestUtil.convertObjectToJson(cardRequestRepresentation)));
    }

    static ResultActions searchCard(MockMvc mockMvc, String cardNumber) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get("/cards")
                .param("cardNumber", cardNumber)
                .contentType(MediaType.APPLICATION_JSON));
    }

}