package com.example.InventoryManagement;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class InventoryManagementApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
	void contextLoads() {
	}

    @Test
    @DisplayName("Test create")
    public void createTest() throws Exception {
        String productDto = """
                {
                	"id": null,
                	"cost": "50",
                	"description": "Клей"
                }""";

        mockMvc.perform(post("/rest/admin-ui/products")
                        .content(productDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test delete")
    public void deleteTest() throws Exception {
        Long id = 0L;

        mockMvc.perform(delete("/rest/admin-ui/products/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test delete many")
    public void deleteManyTest() throws Exception {
        String ids = "[]";

        mockMvc.perform(delete("/rest/admin-ui/products")
                        .param("ids", ids))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test find all")
    public void findAllTest() throws Exception {
        mockMvc.perform(get("/rest/admin-ui/products"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test get by id")
    public void getByIdTest() throws Exception {
        String id = "0";

        mockMvc.perform(get("/rest/admin-ui/products/{id}", id))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test get list")
    public void getListTest() throws Exception {
        mockMvc.perform(get("/rest/admin-ui/products/all"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test get many")
    public void getManyTest() throws Exception {
        String ids = "[152, 202, 252]";

        mockMvc.perform(get("/rest/admin-ui/products/by-ids")
                        .param("ids", ids))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test patch")
    public void patchTest() throws Exception {
        String id = "0";
        String patchNode = "[]";

        mockMvc.perform(patch("/rest/admin-ui/products/{id}", id)
                        .content(patchNode)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Test patch many")
    public void patchManyTest() throws Exception {
        String ids = "[]";
        String patchNode = "[]";

        mockMvc.perform(patch("/rest/admin-ui/products")
                        .content(patchNode)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ids", ids))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
