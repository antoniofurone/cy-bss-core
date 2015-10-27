package org.cysoft.bss.core.web;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

@Retention(RUNTIME)
public @interface CyBssService {
	public String name();
}
