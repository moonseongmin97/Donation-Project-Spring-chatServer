package com.example.demo.donate.donate.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//DonationResponseDto.java
public class DonateResponseDto {
 private Long donationId;
 private Long userId;
 private String userName;
 private BigDecimal amount;
 private LocalDateTime donationDate;
 private String message;
 private String status;
 private Boolean isCancelled;
 private Long createdBy;
 private Long cancelledBy;
 private LocalDateTime cancelledDate;
 private String reasonForCancellation;
 private Long bankId;
 private LocalDateTime lastUpdatedDate;
 private String notes;
 
 
 
 

 // Getters and Setters
 
public Long getDonationId() {
	return donationId;
}
public void setDonationId(Long donationId) {
	this.donationId = donationId;
}
public Long getUserId() {
	return userId;
}
public void setUserId(Long userId) {
	this.userId = userId;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public BigDecimal getAmount() {
	return amount;
}
public void setAmount(BigDecimal amount) {
	this.amount = amount;
}
public LocalDateTime getDonationDate() {
	return donationDate;
}
public void setDonationDate(LocalDateTime donationDate) {
	this.donationDate = donationDate;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public Boolean getIsCancelled() {
	return isCancelled;
}
public void setIsCancelled(Boolean isCancelled) {
	this.isCancelled = isCancelled;
}
public Long getCreatedBy() {
	return createdBy;
}
public void setCreatedBy(Long createdBy) {
	this.createdBy = createdBy;
}
public Long getCancelledBy() {
	return cancelledBy;
}
public void setCancelledBy(Long cancelledBy) {
	this.cancelledBy = cancelledBy;
}
public LocalDateTime getCancelledDate() {
	return cancelledDate;
}
public void setCancelledDate(LocalDateTime cancelledDate) {
	this.cancelledDate = cancelledDate;
}
public String getReasonForCancellation() {
	return reasonForCancellation;
}
public void setReasonForCancellation(String reasonForCancellation) {
	this.reasonForCancellation = reasonForCancellation;
}
public Long getBankId() {
	return bankId;
}
public void setBankId(Long bankId) {
	this.bankId = bankId;
}
public LocalDateTime getLastUpdatedDate() {
	return lastUpdatedDate;
}
public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
	this.lastUpdatedDate = lastUpdatedDate;
}
public String getNotes() {
	return notes;
}
public void setNotes(String notes) {
	this.notes = notes;
}
 
 
}