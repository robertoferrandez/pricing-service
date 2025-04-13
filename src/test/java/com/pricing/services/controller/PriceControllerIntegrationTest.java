package com.pricing.services.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pricing.services.model.dto.login.LoginDto;
import com.pricing.services.model.dto.prices.PriceDto;
import com.pricing.services.security.JwtAuthFilter;
import com.pricing.services.security.JwtUtil;
import com.pricing.services.service.PriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class PriceControllerIntegrationTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PriceController priceController;

    @Autowired
    private AuthController authController;

    @Autowired
    private PriceService priceService;

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @BeforeEach
    public void setUp() throws Exception {
//        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
        token = getJwtToken();
    }


    private String getJwtToken() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        // Realiza una solicitud de login para obtener el token
        String responseJson = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(LoginDto.builder().username("admin").password("1234").build())))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();


        JsonNode jsonResponse = mapper.readTree(responseJson);
        return jsonResponse.get("jwt").asText();
    }

    @Test
    public void testGetPricePeticion10AM14Junio() throws Exception {
        // Simula un precio para la primera prueba (10:00 AM del 14 de junio)
        PriceDto expectedResponse = PriceDto.builder()
                .brandId(1L)
                .startDate("2020-06-14-00.00.00")
                .endDate("2020-12-31-23.59.59")
                .productId(35455L)
                .price(35.50)
                .currency("EUR")
        .build();
        String responseJson = mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14-10.00.00")
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PriceDto actualPriceDto = objectMapper.readValue(responseJson, PriceDto.class);

        assertEquals(expectedResponse, actualPriceDto);
    }

    @Test
    public void testGetPricePeticion4PM14Junio() throws Exception {
        PriceDto expectedResponse = PriceDto.builder()
                .brandId(1L)
                .startDate("2020-06-14-15.00.00")
                .endDate("2020-06-14-18.30.00")
                .productId(35455L)
                .price(25.45)
                .currency("EUR")
                .build();


        String responseJson = mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14-16.00.00")  // Hora: 4:00 PM
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PriceDto actualPriceDto = objectMapper.readValue(responseJson, PriceDto.class);

        assertEquals(expectedResponse, actualPriceDto);
    }

    @Test
    public void testGetPricePeticion9PM14Junio() throws Exception {
        // Simula un precio para la tercera prueba (9:00 PM del 14 de junio)
        PriceDto expectedResponse = PriceDto.builder()
                .brandId(1L)
                .startDate("2020-06-14-00.00.00")
                .endDate("2020-12-31-23.59.59")
                .productId(35455L)
                .price(35.50)
                .currency("EUR")
                .build();


        String responseJson =  mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14-21.00.00")  // Hora: 9:00 PM
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PriceDto actualPriceDto = objectMapper.readValue(responseJson, PriceDto.class);

        assertEquals(expectedResponse, actualPriceDto);
    }

    @Test
    public void testGetPricePeticion10AM15Junio() throws Exception {
        // Simula un precio para la cuarta prueba (10:00 AM del 15 de junio)
        PriceDto expectedResponse = PriceDto.builder()
                .brandId(1L)
                .startDate("2020-06-15-00.00.00")
                .endDate("2020-06-15-11.00.00")
                .productId(35455L)
                .price(30.50)
                .currency("EUR")
                .build();


        String responseJson = mockMvc.perform(get("/prices")
                        .param("date", "2020-06-15-10.00.00")  // Hora: 10:00 AM (día 15)
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PriceDto actualPriceDto = objectMapper.readValue(responseJson, PriceDto.class);

        assertEquals(expectedResponse, actualPriceDto);
    }

    @Test
    public void testGetPrice_Peticion9PM16Junio() throws Exception {
        // Simula un precio para la quinta prueba (9:00 PM del 16 de junio)

        PriceDto expectedResponse = PriceDto.builder()
                .brandId(1L)
                .startDate("2020-06-15-16.00.00")
                .endDate("2020-12-31-23.59.59")
                .productId(35455L)
                .price(38.95)
                .currency("EUR")
                .build();

        String responseJson = mockMvc.perform(get("/prices")
                        .param("date", "2020-06-16-21.00.00")  // Hora: 9:00 PM (día 16)
                        .param("product_id", "35455")
                        .param("brand_id", "1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PriceDto actualPriceDto = objectMapper.readValue(responseJson, PriceDto.class);

        assertEquals(expectedResponse, actualPriceDto);
    }

}
