package com.example.demo.donate.bank.dto;



public class BankResponseDto {
	 private Long bankId;
	 private String bankName;
	 private String bankCode;
	 private String branchName;
	
	    
	    // Getters and Setters
		public String getBankName() {
			return bankName;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public String getBankCode() {
			return bankCode;
		}
		public void setBankCode(String bankCode) {
			this.bankCode = bankCode;
		}
		public String getBranchName() {
			return branchName;
		}
		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}
}