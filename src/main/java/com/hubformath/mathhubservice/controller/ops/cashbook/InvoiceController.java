package com.hubformath.mathhubservice.controller.ops.cashbook;

import com.hubformath.mathhubservice.controller.util.CollectionModelUtils;
import com.hubformath.mathhubservice.model.ops.cashbook.Invoice;
import com.hubformath.mathhubservice.model.ops.cashbook.InvoiceRequest;
import com.hubformath.mathhubservice.service.ops.cashbook.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
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

@RestController
@RequestMapping(path = "/api/v1/ops")
@PreAuthorize("hasRole('ADMINISTRATOR') or hasRole('CASHIER')")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/invoices")
    public ResponseEntity<CollectionModel<?>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        return ResponseEntity.ok().body(toCollectionModel(invoices));
    }

    @PostMapping("/invoices")
    @SuppressWarnings("java:S1452") // Wildcard return required for readable messages when error occurs
    public ResponseEntity<?> createInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        try {
            EntityModel<Invoice> newInvoice = toModel(invoiceService.createInvoice(invoiceRequest));
            return ResponseEntity.created(newInvoice.getRequiredLink(IanaLinkRelations.SELF).toUri())
                                 .body(newInvoice);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/invoices/{invoiceId}")
    public ResponseEntity<EntityModel<Invoice>> getInvoiceById(@PathVariable String invoiceId) {
        try {
            EntityModel<Invoice> updatedInvoice = toModel(invoiceService.getInvoiceById(invoiceId));
            return ResponseEntity.ok().body(updatedInvoice);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/invoices/{invoiceId}")
    public ResponseEntity<EntityModel<Invoice>> replaceInvoice(@RequestBody InvoiceRequest invoiceRequest,
                                                               @PathVariable String invoiceId) {
        try {
            EntityModel<Invoice> updatedInvoice = toModel(invoiceService.updateInvoice(invoiceId, invoiceRequest));
            return ResponseEntity.ok().body(updatedInvoice);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/invoices/{invoiceId}")
    public ResponseEntity<String> deleteInvoice(@PathVariable String invoiceId) {
        try {
            invoiceService.deleteInvoice(invoiceId);
            return ResponseEntity.ok().body("Invoice deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private EntityModel<Invoice> toModel(Invoice invoice) {
        return EntityModel.of(invoice,
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InvoiceController.class)
                                                                        .getInvoiceById(invoice.getId()))
                                               .withSelfRel(),
                              WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InvoiceController.class)
                                                                        .getAllInvoices()).withRel("invoices"));
    }

    private CollectionModel<?> toCollectionModel(List<Invoice> invoiceList) {
        Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InvoiceController.class)
                                                              .getAllInvoices()).withSelfRel();
        List<EntityModel<Invoice>> invoices = invoiceList.stream()
                                                         .map(this::toModel)
                                                         .toList();

        return invoiceList.isEmpty()
                ? CollectionModelUtils.getEmptyEmbeddedCollectionModel(Invoice.class, link)
                : CollectionModel.of(invoices, link);
    }
}
