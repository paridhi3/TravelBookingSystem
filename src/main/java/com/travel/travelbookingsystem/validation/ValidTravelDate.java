package com.travel.travelbookingsystem.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TravelDateValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTravelDate {
    String message() default "Travel date must be between today and the next 6 days.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
