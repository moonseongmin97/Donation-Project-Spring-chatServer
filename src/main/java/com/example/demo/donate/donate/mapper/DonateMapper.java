package com.example.demo.donate.donate.mapper;

import com.example.demo.donate.donate.dto.DonateRequestDto;
import com.example.demo.donate.donate.dto.DonateResponseDto;
import com.example.demo.donate.donate.entity.DonateEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DonateMapper {
	DonateMapper INSTANCE = Mappers.getMapper(DonateMapper.class);

    // DonationRequestDto -> Donation Entity 변환
    @Mapping(target = "donationId", ignore = true)
    @Mapping(target = "payment", ignore = true)  // Payment는 서비스 계층에서 설정 필요
    @Mapping(target = "bank", ignore = true)     // Bank는 서비스 계층에서 설정 필요
    DonateEntity toEntity(DonateRequestDto dto);
	
    // Donation Entity -> DonationResponseDto 변환
    @Mapping(target = "donationId", source = "donationId")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "donationDate", source = "donationDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "bankId", source = "bank.bankId")  // bank 객체의 ID 매핑
    @Mapping(target = "isCancelled", source = "isCancelled")
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "cancelledBy", source = "cancelledBy")
    @Mapping(target = "cancelledDate", source = "cancelledDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "reasonForCancellation", source = "reasonForCancellation")
    @Mapping(target = "lastUpdatedDate", source = "lastUpdatedDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "notes", source = "notes")
    DonateResponseDto toResponseDto(DonateEntity donation);


}