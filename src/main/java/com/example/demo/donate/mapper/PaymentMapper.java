package com.example.demo.donate.mapper;


import com.example.demo.donate.entity.PaymentEntity;
import com.example.demo.donate.payment.dto.PaymentRequestDto;
import com.example.demo.donate.payment.dto.PaymentResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentMapper { 
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    // Payment Entity -> PaymentResponseDto 변환
    @Mapping(target = "paymentId", source = "paymentId")
    @Mapping(target = "bankId", source = "bank.bankId")
    @Mapping(target = "paymentReference", source = "paymentReference")
    @Mapping(target = "amount", source = "amount")
    @Mapping(target = "paymentDate", source = "paymentDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "paymentStatus", source = "paymentStatus")
    PaymentResponseDto toResponseDto(PaymentEntity payment);

    // PaymentRequestDto -> Payment Entity 변환
    @Mapping(target = "paymentId", ignore = true)
    @Mapping(target = "bank", ignore = true)  // bank는 서비스 계층에서 설정 필요
    PaymentEntity toEntity(PaymentRequestDto dto);
}