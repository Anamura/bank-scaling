package com.murava.bankscaling;

import com.murava.bankscaling.controller.AccountController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BalanceTransferTest extends AbstractTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getAccountWebAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .get("/api/v1/account/{accountId}", 1)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void transferBetweenAccountsWebAPI() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .put("/api/v1/account/transfer/{to}", 1, 2, 100)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void transferAccountIdNotFound() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/account/transfer/2/1.0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }
}
