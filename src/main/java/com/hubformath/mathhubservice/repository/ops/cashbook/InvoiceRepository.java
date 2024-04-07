package com.hubformath.mathhubservice.repository.ops.cashbook;

import com.hubformath.mathhubservice.model.ops.cashbook.Invoice;
import com.hubformath.mathhubservice.model.sis.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, String> {

    Optional<Invoice> findByStudent(Student student);
}
