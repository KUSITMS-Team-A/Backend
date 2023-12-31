package com.backend.domain.store.dto;

import com.backend.domain.store.entity.BusinessHour;
import com.backend.domain.user.dto.RepresentativeDto;
import com.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadStoreDetailsDto {

    private RepresentativeDto user;

    private Long storeId;

    private String storeName;

    private String category;

    private String description;

    private String address;

    private Double latitude;

    private Double longitude;

    private List<BusinessHourDto> businessHours;

    private String phoneNumber;

    private Double distance;

    private String mapUrl;

    private boolean isPicked;

    public static ReadStoreDetailsDto from(StoreDetailsDto store, List<BusinessHour> businessHours, User user) {
        return ReadStoreDetailsDto.builder()
                .user(RepresentativeDto.from(user))
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .category(store.getCategory().getCategoryName())
                .description(store.getDescription())
                .address(store.getAddress())
                .latitude(store.getLatitude())
                .longitude(store.getLongitude())
                .phoneNumber(store.getPhoneNumber())
                .distance(store.getDistance())
                .mapUrl(store.getMapUrl())
                .isPicked(store.getIsPicked())
                .businessHours(createBusinessHourDtos(businessHours))
                .build();
    }

    private static List<BusinessHourDto> createBusinessHourDtos(List<BusinessHour> businessHours) {
        return businessHours.stream()
                .map(BusinessHourDto::from)
                .toList();
    }

}
