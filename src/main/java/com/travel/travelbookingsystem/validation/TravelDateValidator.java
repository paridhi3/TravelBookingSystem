package com.travel.travelbookingsystem.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class TravelDateValidator implements ConstraintValidator<ValidTravelDate, LocalDate> {

    @Override
    public boolean isValid(LocalDate travelDate, ConstraintValidatorContext context) {
        if (travelDate == null) return false;

        LocalDate today = LocalDate.now();
        LocalDate maxAllowedDate = today.plusDays(6);

        return !travelDate.isBefore(today) && !travelDate.isAfter(maxAllowedDate);
    }
}
