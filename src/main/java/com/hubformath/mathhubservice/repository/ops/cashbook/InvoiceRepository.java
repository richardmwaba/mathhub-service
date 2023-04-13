package com.hubformath.mathhubservice.repository.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
