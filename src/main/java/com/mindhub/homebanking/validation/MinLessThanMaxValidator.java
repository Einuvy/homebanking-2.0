package com.mindhub.homebanking.validation;

import com.mindhub.homebanking.models.DTO.request.LoanCreationDTO;
import com.mindhub.homebanking.validation.Annotation.MinLessThanMax;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinLessThanMaxValidator implements ConstraintValidator <MinLessThanMax, LoanCreationDTO> {
    @Override
    public boolean isValid(LoanCreationDTO value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        double minAmount = value.getMinAmount();
        double maxAmount = value.getMaxAmount();

        return minAmount <= maxAmount;
    }
}
