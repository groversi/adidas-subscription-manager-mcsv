package com.adidas.subscription.util;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Optional;

public enum GenderEnum {
    MALE("Male"),
    FEMALE("Female"),
    NON_BINARY("Non binary"),
    NOT_DECLARED("Not declared");

    private final GenderDTO genderDTO;

    GenderEnum(String displayName){
        this.genderDTO = new GenderDTO(displayName);
    }

    public static Optional<GenderEnum> getGenderByDisplayName(String displayName){
        return Optional.of(
                Arrays.asList(GenderEnum.values())
                        .stream()
                        .filter(e -> e.getGenderDisplayName().equals(displayName))
                        .findFirst()
                        .orElse(NOT_DECLARED)
        );
    }

    @JsonValue
    public String getGenderDisplayName() {return this.genderDTO.getDisplayName();}
}
