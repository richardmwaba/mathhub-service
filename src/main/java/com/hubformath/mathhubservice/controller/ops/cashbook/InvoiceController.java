package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.config.ModelMapperConfig;
import com.hubformath.mathhubservice.dto.ops.cashbook.InvoiceDto;
import com.hubformath.mathhubservice.model.ops.cashbook.Invoice;
import com.hubformath.mathhubservice.service.ops.cashbook.InvoiceService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(path = "/api/v1/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class InvoiceController {

    private final ModelMapper modelMapper;

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(final ModelMapperConfig modelMapperConfig, final InvoiceService invoiceService) {
        this.modelMapper = modelMapperConfig.createModelMapper();
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public ResponseEntity<CollectionModel<EntityModel<InvoiceDto>>> getAllInvoices() {
        List<InvoiceDto> invoices = invoiceService.getAllInvoices().stream()
                                                  .map(invoice -> modelMapper.map(invoice, InvoiceDto.class))
                                                  .toList();

        CollectionModel<EntityModel<InvoiceDto>> invoiceCollectionModel = toCollectionModel(invoices);

        return ResponseEntity.ok().body(invoiceCollectionModel);
    }

    @PostMapping("/invoices")
    public ResponseEntity<EntityModel<InvoiceDto>> createInvoice(@RequestBody final InvoiceDto invoiceRequestDto) {
        Invoice newInvoice = invoiceService.createInvoice(invoiceRequestDto);
        EntityModel<InvoiceDto> invoiceEntityModel = toModel(modelMapper.map(newInvoice, InvoiceDto.class));
        return ResponseEntity.created(invoiceEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                             .body(invoiceEntityModel);
    }

    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<EntityModel<InvoiceDto>> getInvoiceById(@PathVariable final String invoiceId) {
        try {
            Invoice invoice = invoiceService.getInvoiceById(invoiceId);
            EntityModel<InvoiceDto> invoiceEntityModel = toModel(modelMapper.map(invoice, InvoiceDto.class));
            return ResponseEntity.ok().body(invoiceEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/invoices/{invoiceId}")
    public ResponseEntity<EntityModel<InvoiceDto>> replaceInvoice(@RequestBody final InvoiceDto invoiceDto,
                                                                  @PathVariable final String invoiceId) {
        try {
            Invoice invoiceRequest = modelMapper.map(invoiceDto, Invoice.class);
            Invoice updatedInvoice = invoiceService.updateInvoice(invoiceId, invoiceRequest);
            EntityModel<InvoiceDto> invoiceEntityModel = toModel(modelMapper.map(updatedInvoice, InvoiceDto.class));
            return ResponseEntity.ok().body(invoiceEntityModel);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/invoices/{invoiceId}")
    public ResponseEntity<String> deleteInvoice(@PathVariable final String invoiceId) {
        try {
            invoiceService.deleteInvoice(invoiceId);
            return ResponseEntity.ok().body("Invoice deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<InvoiceDto> toModel(final InvoiceDto invoice) {
        return EntityModel.of(invoice,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InvoiceController.class)
                                                                        .getInvoiceById(invoice.getInvoiceId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InvoiceController.class)
                                                                        .getAllInvoices()).withRel("invoices"));
    }

    private CollectionModel<EntityModel<InvoiceDto>> toCollectionModel(final Iterable<? extends InvoiceDto> invoices) {
        List<EntityModel<InvoiceDto>> invoiceList = StreamSupport.stream(invoices.spliterator(), false)
                                                                 .map(this::toModel)
                                                                 .toList();

        return CollectionModel.of(invoiceList,
                                  WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InvoiceController.class)
                                                                            .getAllInvoices())
                                                   .withSelfRel());
    }
}
