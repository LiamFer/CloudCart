package com.liamfer.CloudCart;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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
public class AdminControllerTest {
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
    void shouldCreateUpdateAndDeleteProduct() throws Exception {
        String token = this.generateAuthToken();
        // Testando a Criação do Produto
        ProductDTO dto = new ProductDTO("Tênis Nike Air", "Confortável e estiloso", 499.90, 10, true);
        String json = new ObjectMapper().writeValueAsString(dto);
        MvcResult postResult = mockMvc.perform(post("/admin/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();
        String responseJson = postResult.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseJson);
        Long productId = jsonNode.get("id").asLong();
        System.out.printf("CREATED: Produto %d criado com Sucesso!\n",productId);

        // Testando Update no Produto
        ProductDTO putDto = new ProductDTO("Tênis Adidas Speed 2", "Meia Maratona Ideal", 199.90, 15, true);
        String putJson = new ObjectMapper().writeValueAsString(putDto);
        mockMvc.perform(put("/admin/products/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(putJson))
                .andExpect(status().isOk())
                .andReturn();
        System.out.printf("PUT: Produto %d editado com Sucesso!\n",productId);

        // Teste de Deletar o Produto
        mockMvc.perform(delete("/admin/products/"+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
        System.out.printf("DELETE: Produto %d deletado com Sucesso!\n",productId);
    }

}
