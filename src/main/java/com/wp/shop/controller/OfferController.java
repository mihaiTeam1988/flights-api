package com.wp.shop.controller;

import com.wp.shop.exception.handler.HttpExceptionResponse;
import com.wp.shop.model.domain.Offer;
import com.wp.shop.model.transfer.OfferDtoRead;
import com.wp.shop.model.transfer.OfferDtoWrite;
import com.wp.shop.service.OfferService;
import com.wp.shop.util.Mapper;
import com.wp.shop.util.Status;
import com.wp.shop.util.Validator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/offers")
public class OfferController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferController.class);

    @Autowired
    private OfferService offerService;

    @Autowired
    private Mapper mapper;

    @Autowired
    private Validator validator;

    @ApiOperation("Get all offers, filter by status query param")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OfferDtoRead.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad Request", response = HttpExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = HttpExceptionResponse.class)})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<OfferDtoRead> getOffers(@RequestParam(name = "status", required = false) Status status) {

        if (status != null) {
            LOGGER.info(String.format("Getting all offers for status [%s]", status.toString()));
        } else {
            LOGGER.info("Getting all offers");
        }

        return offerService.getOffers(Optional.ofNullable(status)).stream()
                .map(o -> mapper.transformOfferToOfferDtoRead(o))
                .collect(Collectors.toList());
    }

    @ApiOperation("Create offer")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = Long.class),
            @ApiResponse(code = 400, message = "Bad Request", response = HttpExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = HttpExceptionResponse.class),
            @ApiResponse(code = 409, message = "Offer already exists", response = HttpExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = HttpExceptionResponse.class)})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long createOffer(@Valid @RequestBody OfferDtoWrite offer, HttpServletResponse response) {

        validator.validateDates(offer);

        LOGGER.info(String.format("Creating offer [%s]", offer));

        Offer mappedOffer = mapper.transformOfferDtoWriteToOffer(offer);

        response.setStatus(HttpServletResponse.SC_CREATED);

        return offerService.createOffer(mappedOffer);
    }

    @ApiOperation("Get offer by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = OfferDtoRead.class),
            @ApiResponse(code = 400, message = "Bad Request", response = HttpExceptionResponse.class),
            @ApiResponse(code = 404, message = "Not found", response = HttpExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = HttpExceptionResponse.class)})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OfferDtoRead getOfferById(@PathVariable("id") Long id) {

        LOGGER.info(String.format("Getting offer with id [%s]", id));

        Offer offer = offerService.getOffer(id);
        return mapper.transformOfferToOfferDtoRead(offer);
    }

    @ApiOperation("Cancel offer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Long.class),
            @ApiResponse(code = 400, message = "Bad Request", response = HttpExceptionResponse.class),
            @ApiResponse(code = 409, message = "Offer cannot be cancelled", response = HttpExceptionResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = HttpExceptionResponse.class)})
    @PutMapping(value = "/{id}")
    public Long cancelOffer(@PathVariable("id") Long id) {

        LOGGER.info(String.format("Cancelling offer with id [%s]", id));

        return offerService.cancelOffer(id);
    }


}
