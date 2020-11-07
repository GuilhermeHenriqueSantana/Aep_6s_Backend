package com.aep.s.aep6s.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FuncaoValida implements ConstraintValidator<FuncaoValid, String>{

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		switch (value) {
		case "ADMINISTRADOR":
			return true;
		case "PROFESSOR":
			return true;
		case "LIVRE":
			return true;
		}
		return false;
	}

}
