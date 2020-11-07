package com.aep.s.aep6s.validacao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FuncaoValida.class)
public @interface FuncaoValid {
	 String message() default "Valor inválido";
	 
	 Class<?>[] groups() default {};
	 
	 Class<? extends Payload>[] payload() default {};
	 
	 String value() default "";
}
