package br.com.coupons.modules.controller;


import br.com.coupons.modules.dto.CouponRequest;
import br.com.coupons.modules.model.Coupon;
import br.com.coupons.modules.service.CouponService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CouponService couponService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testaChamadaCriarCupon() throws Exception {

        CouponRequest request = new CouponRequest(
                "ABC123",
                "Test",
                BigDecimal.TEN,
                LocalDateTime.now().plusDays(10),
                true
        );

        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                new BigDecimal("10.0"),
                LocalDateTime.now().plusDays(1),
                false
        );

        when(couponService.create(anyString(), anyString(), any(), any(), anyBoolean()))
                .thenReturn(coupon);

        mockMvc.perform(post("/coupons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ABC123"));
    }

    @Test
    void testChamadaGetById() throws Exception{

        Coupon coupon = new Coupon(
                "ABC123",
                "Test",
                new BigDecimal("10.0"),
                LocalDateTime.now().plusDays(1),
                false
        );

        when(couponService.findById(1L)).thenReturn(coupon);

        mockMvc.perform(get("/coupons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("ABC123"));
    }

    @Test
    void testaChamadaDeleteCoupon() throws Exception{

        doNothing().when(couponService).delete(1L);

        mockMvc.perform(delete("/coupons/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Removido com sucesso! "));
    }
}
