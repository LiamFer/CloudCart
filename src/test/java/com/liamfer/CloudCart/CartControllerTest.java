package com.liamfer.CloudCart;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.liamfer.CloudCart.dto.cart.AddCartItemDTO;
import com.liamfer.CloudCart.dto.cart.CartItemAmountDTO;
import com.liamfer.CloudCart.dto.product.ProductDTO;
import com.liamfer.CloudCart.dto.user.LoginUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
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
    void shouldGetCart() throws Exception {
        String token = this.generateAuthToken();
        mockMvc.perform(get("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.items").isArray())
                .andReturn();
        System.out.println("OK: Carrinho Retornado com Sucesso!");
    }

    @Test
    void shouldAddItemInCart() throws Exception {
        String token = this.generateAuthToken();
        AddCartItemDTO dto = new AddCartItemDTO(30L,1);
        String json = new ObjectMapper().writeValueAsString(dto);
        mockMvc.perform(post("/cart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println("CREATED: Produto adicionado com Sucesso no Carrinho!");
    }

    @Test
    void shouldEditItemInCart() throws Exception {
        String token = this.generateAuthToken();
        CartItemAmountDTO dto = new CartItemAmountDTO(3);
        String json = new ObjectMapper().writeValueAsString(dto);
        mockMvc.perform(patch("/cart/item/23") // Endpoint -> /cart/item/:id
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.product").exists())
                .andReturn();
        System.out.println("PATCHED: Quantidade do Produto Editada com Sucesso no Carrinho!");
    }
}
