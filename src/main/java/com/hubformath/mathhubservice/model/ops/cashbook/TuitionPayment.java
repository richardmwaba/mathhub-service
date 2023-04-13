package com.hubformath.mathhubservice.model.ops.cashbook;

import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TuitionPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cash_transaction_id")
    private CashTransaction cashTransaction;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    private LocalDate paymentDate;

    private Double amount;

    private Long invoiceId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    private String narration;

    private Long createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public TuitionPayment(){}

    public TuitionPayment(final CashTransaction cashTransaction,
                          final Student student,
                          final PaymentMethod paymentMethod,
                          final Double amount,
                          final Long InvoiceId,
                          final Receipt receipt,
                          final String narration) {
        this.cashTransaction = cashTransaction;
        this.student = student;
        this.paymentMethod = paymentMethod;
        this.invoiceId = InvoiceId;
        this.receipt = receipt;
        this.paymentDate = LocalDate.now();
        this.amount = amount;
        this.narration = narration;
        this.createdBy = null;
    }

    public Long getId() {
        return id;
    }

    public CashTransaction getCashTransaction() {
        return cashTransaction;
    }

    public void setCashTransaction(CashTransaction cashTransaction) {
        this.cashTransaction = cashTransaction;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TuitionPayment that)) return false;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getCashTransaction(), that.getCashTransaction())
                && Objects.equals(getStudent(), that.getStudent())
                && Objects.equals(getPaymentMethod(), that.getPaymentMethod())
                && Objects.equals(getPaymentDate(), that.getPaymentDate())
                && Objects.equals(getAmount(), that.getAmount())
                && Objects.equals(getInvoiceId(), that.getInvoiceId())
                && Objects.equals(getReceipt(), that.getReceipt())
                && Objects.equals(getNarration(), that.getNarration())
                && Objects.equals(getCreatedBy(), that.getCreatedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                            getCashTransaction(),
                            getStudent(),
                            getPaymentMethod(),
                            getPaymentDate(),
                            getAmount(),
                            getInvoiceId(),
                            getReceipt(),
                            getNarration(),
                            getCreatedBy());
    }

    @Override
    public String toString() {
        return "TuitionPayment{" +
                "id=" + id +
                ", cashTransaction=" + cashTransaction +
                ", student=" + student +
                ", paymentMethod=" + paymentMethod +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                ", invoiceNumber='" + invoiceId +
                ", receipt=" + receipt +
                ", narration='" + narration + '\'' +
                ", createdBy=" + createdBy +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}