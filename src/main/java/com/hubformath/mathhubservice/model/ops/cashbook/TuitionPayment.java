package com.hubformath.mathhubservice.model.ops.cashbook;

import com.hubformath.mathhubservice.model.sis.Student;
import com.hubformath.mathhubservice.model.systemconfig.PaymentMethod;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tuition_payments")
public class TuitionPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "tuition_payment_id")
    private UUID tuitionPaymentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cash_transaction_id")
    private CashTransaction cashTransaction;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "invoice_id")
    private UUID invoiceId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    @Column(name = "narration")
    private String narration;

    @Column(name = "created_by")
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public TuitionPayment(){}

    public TuitionPayment(final CashTransaction cashTransaction,
                          final Student student,
                          final PaymentMethod paymentMethod,
                          final Double amount,
                          final UUID invoiceId,
                          final Receipt receipt,
                          final String narration) {
        this.cashTransaction = cashTransaction;
        this.student = student;
        this.paymentMethod = paymentMethod;
        this.invoiceId = invoiceId;
        this.receipt = receipt;
        this.paymentDate = LocalDate.now();
        this.amount = amount;
        this.narration = narration;
        this.createdBy = null;
    }

    public UUID getTuitionPaymentId() {
        return tuitionPaymentId;
    }

    public void setTuitionPaymentId(UUID tuitionPaymentId) {
        this.tuitionPaymentId = tuitionPaymentId;
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

    public UUID getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(UUID  invoiceId) {
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
        return Objects.equals(getTuitionPaymentId(), that.getTuitionPaymentId())
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
        return Objects.hash(getTuitionPaymentId(),
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
                "tuitionPaymentId=" + tuitionPaymentId +
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