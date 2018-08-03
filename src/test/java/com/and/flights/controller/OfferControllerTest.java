package com.and.flights.controller;

import com.and.flights.service.OfferService;
import com.and.flights.util.TestData;
import com.and.flights.util.Validator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.and.flights.model.transfer.OfferDtoWrite;
import com.and.flights.model.domain.Offer;
import com.and.flights.model.transfer.OfferDtoRead;
import com.and.flights.util.Mapper;
import com.and.flights.util.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OfferControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    private Offer offer;
    private OfferDtoRead offerDtoRead;
    private OfferDtoWrite offerDtoWrite;

    @Mock
    private OfferService offerService;

    @Mock
    private Mapper mapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private OfferController controller = new OfferController();

    @Before
    public void before() {
        offer = TestData.getOffer(Status.ACTIVE);
        offerDtoRead = TestData.getOfferDtoRead(Status.ACTIVE);
        offerDtoWrite = TestData.getOfferDtoWrite();

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        when(mapper.transformOfferToOfferDtoRead(offer)).thenReturn(offerDtoRead);
        when(mapper.transformOfferDtoWriteToOffer(any(OfferDtoWrite.class))).thenReturn(offer);
    }

    @Test
    public void shouldGetAllActiveOffers() throws Exception {

        when(offerService.getOffers(Optional.of(Status.ACTIVE))).thenReturn(Arrays.asList(offer));

        mockMvc.perform(MockMvcRequestBuilders.get("/offers?status=ACTIVE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(offer.getId().intValue())))
                .andExpect(jsonPath("$[0].productId", is(offer.getProductId().intValue())))
                .andExpect(jsonPath("$[0].description", is(offer.getDescription())))
                .andExpect(jsonPath("$[0].price", is(offer.getPrice())))
                .andExpect(jsonPath("$[0].currency", is(offer.getCurrency())))
                .andExpect(jsonPath("$[0].startDate", is(offer.getStartDate().getTime())))
                .andExpect(jsonPath("$[0].endDate", is(offer.getEndDate().getTime())))
                .andExpect(jsonPath("$[0].status", is(offer.getStatus().toString())));

        verify(offerService, times(1)).getOffers(Optional.of(Status.ACTIVE));
        verifyNoMoreInteractions(offerService);
    }

    @Test
    public void shouldGetAllOffers() throws Exception {

        when(offerService.getOffers(Optional.empty())).thenReturn(Arrays.asList(offer));

        mockMvc.perform(MockMvcRequestBuilders.get("/offers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(offer.getId().intValue())))
                .andExpect(jsonPath("$[0].productId", is(offer.getProductId().intValue())))
                .andExpect(jsonPath("$[0].description", is(offer.getDescription())))
                .andExpect(jsonPath("$[0].price", is(offer.getPrice())))
                .andExpect(jsonPath("$[0].currency", is(offer.getCurrency())))
                .andExpect(jsonPath("$[0].startDate", is(offer.getStartDate().getTime())))
                .andExpect(jsonPath("$[0].endDate", is(offer.getEndDate().getTime())))
                .andExpect(jsonPath("$[0].status", is(offer.getStatus().toString())));

        verify(offerService, times(1)).getOffers(Optional.empty());
        verifyNoMoreInteractions(offerService);
    }

    @Test
    public void shouldCreateOffer() throws Exception {

        when(offerService.createOffer(offer)).thenReturn(offer.getId());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(offerDtoWrite)))
                .andExpect(status().isCreated())
                .andExpect(content().string(offer.getId().toString()));

        verify(offerService, times(1)).createOffer(offer);
        verify(validator, times(1)).validateDates(any(OfferDtoWrite.class));
        verifyNoMoreInteractions(offerService);
    }

    @Test
    public void shouldGetOfferById() throws Exception {

        when(offerService.getOffer(offer.getId())).thenReturn(offer);

        mockMvc.perform(MockMvcRequestBuilders.get("/offers/" + offer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(offer.getId().intValue())))
                .andExpect(jsonPath("$.productId", is(offer.getProductId().intValue())))
                .andExpect(jsonPath("$.description", is(offer.getDescription())))
                .andExpect(jsonPath("$.price", is(offer.getPrice())))
                .andExpect(jsonPath("$.currency", is(offer.getCurrency())))
                .andExpect(jsonPath("$.startDate", is(offer.getStartDate().getTime())))
                .andExpect(jsonPath("$.endDate", is(offer.getEndDate().getTime())))
                .andExpect(jsonPath("$.status", is(offer.getStatus().toString())));

        verify(offerService, times(1)).getOffer(offer.getId());
        verifyNoMoreInteractions(offerService);
    }

    @Test
    public void shouldCancelOffer() throws Exception {

        Long offerId = new Long(10002);

        when(offerService.cancelOffer(offerId)).thenReturn(offerId);

        mockMvc.perform(MockMvcRequestBuilders.put("/offers/" + offerId))
                .andExpect(status().isOk())
                .andExpect(content().string(offerId.toString()));

        verify(offerService, times(1)).cancelOffer(offerId);
        verifyNoMoreInteractions(offerService);
    }
}
