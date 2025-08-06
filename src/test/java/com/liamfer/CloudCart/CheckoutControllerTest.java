package com.liamfer.CloudCart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.liamfer.CloudCart.dto.cart.AddCartItemDTO;
import com.liamfer.CloudCart.dto.user.LoginUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckoutControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    String generateAuthToken() throws Exception {
        LoginUserDTO dto = new LoginUserDTO("admin@email.com", "123456");
        String loginPayload = new ObjectMapper().writeValueAsString(dto);
        MvcResult result = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginPayload))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        return JsonPath.read(responseBody, "$.token");
    }

    @Test
    void shouldGetCheckoutOrders() throws Exception {
        String token = this.generateAuthToken();
        mockMvc.perform(get("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andReturn();
        System.out.println("OK: Ordens de Checkout Retornadas com Sucesso!");
    }

    @Test
    void shouldCreateCheckoutOrder() throws Exception {
        String token = this.generateAuthToken();
        mockMvc.perform(post("/checkout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.sessionId").exists())
                .andExpect(jsonPath("$.sessionUrl").exists())
                .andReturn();
        System.out.println("CREATED: Ordem de Checkout Gerada com Sucesso!");
    }

}
