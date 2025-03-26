package org.solar.system.central.common.all.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.solar.system.central.common.all.enums.ActionSystemEnum;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HistorizeDataPerform {

	public ActionSystemEnum action() default ActionSystemEnum.NONE;
	public boolean enabled() default true;
	
}
