package com.alten.ing_tech.controller;

import com.alten.ing_tech.common.utils.Constants;
import com.alten.ing_tech.configuration.SecurityConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(SecurityConfiguration.class)
@WebMvcTest(controllers = HubController.class)
class HubControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(username = "user", roles = {Constants.USER,Constants.ADMIN,Constants.SUPER_ADMIN})
    void testRedirectLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    void testHomeNotAuthenticated() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user", roles = {Constants.USER,Constants.ADMIN,Constants.SUPER_ADMIN})
    void testFindAll() throws Exception {
        mockMvc.perform(get("/find-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    @WithMockUser(username = "user", roles = {Constants.USER})
    void testForbiddenRequests() throws Exception {
        mockMvc.perform(delete("/delete-product/2"))
                .andExpect(status().isForbidden());

        String product = "{\"id\":0,\"name\":\"new product\",\"description\":\"desc\",\"price\":1.0,\"currency\":\"USD\"}";
        mockMvc.perform(post("/add-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "user", roles = {Constants.ADMIN})
    void testAddProduct() throws Exception {
        String product = "{\"id\":0,\"name\":\"new product\",\"description\":\"desc\",\"price\":1.0,\"currency\":\"USD\"}";
        mockMvc.perform(post("/add-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(product))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new product"));

    }

    @Test
    @WithMockUser(username = "user", roles = {Constants.SUPER_ADMIN})
    void testChangePrice() throws Exception {
        String existingProduct = "{\"id\":1,\"price\":3000.0}";

        mockMvc.perform(put("/change-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(existingProduct))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(3000));
    }
}
