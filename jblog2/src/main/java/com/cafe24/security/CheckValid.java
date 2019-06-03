package com.cafe24.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Retention(RUNTIME)
@Target(METHOD)
public @interface CheckValid {
	public enum Role {
		EXIST, ADMIN
	}
	Role role() default Role.EXIST;
}
