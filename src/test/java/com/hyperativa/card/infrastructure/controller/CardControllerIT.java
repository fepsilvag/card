package com.hyperativa.card.infrastructure.controller;

import com.hyperativa.card.domain.constants.ErrorCodeEnum;
import com.hyperativa.card.infrastructure.controller.util.IntegrationTestUtil;
import com.hyperativa.openapi.api.CardsApi;
import com.hyperativa.representation.CardRequestRepresentation;
import com.hyperativa.representation.CardResponseRepresentation;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
@DisplayName("CardController Integration Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CardControllerIT implements CardsApi {

    @Autowired
    private MockMvc mockMvc;

    private static CardResponseRepresentation cardResponseRepresentation1;
    private static CardResponseRepresentation cardResponseRepresentation2;

    @Test
    @Order(1)
    @DisplayName("When create card in path /cards should return created")
    void createCard_shouldReturnCreated() throws Exception {
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
    @DisplayName("When create card in path /cards should return conflict")
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
    @DisplayName("When upload cards in path /cards/upload should return created")
    void uploadCards_shouldReturnCreated() throws Exception {
        // Given
        String cardNumbers = "5714881935140055\n1541984119621714";
        MockMultipartFile file = new MockMultipartFile("file", "cards.txt",
                "text/txt", cardNumbers.getBytes());

        // Then
        uploadCards(mockMvc, file)
                .andExpect(status().isCreated());
    }

    @Test
    @Order(4)
    @DisplayName("When upload cards in path /cards/upload should return bad request")
    void uploadCards_shouldReturnBadRequest() throws Exception {
        // Given
        String cardNumbers = "\n1541984119621714";
        MockMultipartFile file = new MockMultipartFile("file", "cards.txt",
                "text/txt", cardNumbers.getBytes());

        // Then
        uploadCards(mockMvc, file)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCodeEnum.CAR005.toString()));
    }

    @Test
    @Order(4)
    @DisplayName("When upload cards in path /cards/upload should return conflict")
    void uploadCards_shouldReturnConflict() throws Exception {
        // Given
        String cardNumbers = "1841521415416714\n1841521415416714";
        MockMultipartFile file = new MockMultipartFile("file", "cards.txt",
                "text/txt", cardNumbers.getBytes());

        // Then
        uploadCards(mockMvc, file)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(ErrorCodeEnum.CAR001.toString()));
    }

    @Test
    @Order(6)
    @DisplayName("When find card in path /cards should return ok")
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

    @Test
    @Order(7)
    @DisplayName("When find card in path /cards should return not found")
    void whenFind_shouldReturnNotFound() throws Exception {
        // Then
        searchCard(mockMvc, "aa")
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCodeEnum.CAR002.toString()));
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

    static ResultActions uploadCards(MockMvc mockMvc, MockMultipartFile file) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .multipart("/cards/upload")
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA));
    }

    static ResultActions searchCard(MockMvc mockMvc, String cardNumber) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders
                .get("/cards")
                .param("cardNumber", cardNumber)
                .contentType(MediaType.APPLICATION_JSON));
    }

}