package com.aep.s.aep6s.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TurnoValida implements ConstraintValidator<TurnoValid, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		switch (value) {
		case "MANHA":
			return true;
		case "TARDE":
			return true;
		case "NOITE":
			return true;
		}
		return false;
	}

}
