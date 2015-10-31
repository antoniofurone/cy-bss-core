package org.cysoft.bss.core.web.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface CyBssOperation {
	public String name();
}
