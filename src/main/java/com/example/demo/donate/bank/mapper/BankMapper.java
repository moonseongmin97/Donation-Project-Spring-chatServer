package com.example.demo.donate.bank.mapper;

import com.example.demo.donate.bank.dto.BankRequestDto;
import com.example.demo.donate.bank.dto.BankResponseDto;
import com.example.demo.donate.bank.entity.BankEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BankMapper {
    BankMapper INSTANCE = Mappers.getMapper(BankMapper.class);

    // BankRequestDto -> Bank Entity 변환
    @Mapping(target = "bankId", ignore = true)
    BankEntity toEntity(BankRequestDto dto);
    
    // Bank Entity -> BankResponseDto 변환
    @Mapping(target = "bankId", source = "bankId")
    @Mapping(target = "bankName", source = "bankName")
    @Mapping(target = "bankCode", source = "bankCode")
    @Mapping(target = "branchName", source = "branchName")
    BankResponseDto toResponseDto(BankEntity bank);


}
