package com.example.demo.donate.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//PaymentResponseDto.java
public class PaymentResponseDto {
 private Long paymentId;
 private Long bankId;
 private String paymentReference;
 private BigDecimal amount;
 private LocalDateTime paymentDate;
 private String paymentStatus;
 
 
 
 // Getters and Setters
public Long getPaymentId() {
	return paymentId;
}
public void setPaymentId(Long paymentId) {
	this.paymentId = paymentId;
}
public Long getBankId() {
	return bankId;
}
public void setBankId(Long bankId) {
	this.bankId = bankId;
}
public String getPaymentReference() {
	return paymentReference;
}
public void setPaymentReference(String paymentReference) {
	this.paymentReference = paymentReference;
}
public BigDecimal getAmount() {
	return amount;
}
public void setAmount(BigDecimal amount) {
	this.amount = amount;
}
public LocalDateTime getPaymentDate() {
	return paymentDate;
}
public void setPaymentDate(LocalDateTime paymentDate) {
	this.paymentDate = paymentDate;
}
public String getPaymentStatus() {
	return paymentStatus;
}
public void setPaymentStatus(String paymentStatus) {
	this.paymentStatus = paymentStatus;
}
 
 
}