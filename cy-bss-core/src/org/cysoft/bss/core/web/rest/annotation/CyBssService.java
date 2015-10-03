package org.cysoft.bss.core.web.rest.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface CyBssService {
	public String name();
}
