package com.example.demo.auth.mapper;

import com.example.demo.auth.dto.MemberRequestDto;
import com.example.demo.auth.dto.MemberResponseDto;
import com.example.demo.auth.entity.MemberEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper (componentModel = "spring")
public interface MemberMapper {
    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    // MemberEntity -> MemberResponseDto로 변환
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "loginId", source = "loginId")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "addressLine1", source = "addressLine1")
    @Mapping(target = "addressLine2", source = "addressLine2")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "state", source = "state")
    @Mapping(target = "postalCode", source = "postalCode")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "profileImageUrl", source = "profileImageUrl")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "updatedAt", source = "updatedAt", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @Mapping(target = "lastLogin", source = "lastLogin", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    MemberResponseDto toResponseDto(MemberEntity entity);

    // MemberRequestDto -> MemberEntity로 변환
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", source = "status", defaultValue = "ACTIVE")
    MemberEntity toEntity(MemberRequestDto dto);
}

