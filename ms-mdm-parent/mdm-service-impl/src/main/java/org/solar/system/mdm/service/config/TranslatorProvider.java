package org.solar.system.mdm.service.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Component
public class TranslatorProvider {

	private static ReloadableResourceBundleMessageSource messageSource;

	private static LocaleResolver localeResolver;

	TranslatorProvider(ReloadableResourceBundleMessageSource messageSource, LocaleResolver localeResolver) {
		TranslatorProvider.messageSource = messageSource;
		TranslatorProvider.localeResolver = localeResolver;
	}

	public static String getMsg(String msg) {
		return getMsg(msg, getCurrentHttpRequest());
	}

	public static String getMsg(String msg, Object[] params) {
		return getMsg(msg, params, getCurrentHttpRequest());
	}

	public static String getMsg(String msg, HttpServletRequest request) {
		return messageSource.getMessage(msg, null, localeRequestFunction().apply(request));
	}
	
	public static String getMsg(String msg, Object[] params, HttpServletRequest request) {
		return messageSource.getMessage(msg, params, localeRequestFunction().apply(request));
	}

	private static Function<HttpServletRequest, Locale> localeRequestFunction() {
		return req -> {
			if(Objects.isNull(req)) {
				return LocaleContextHolder.getLocale();
			} else {
				return localeResolver.resolveLocale(req);
			}
		};
	}

	public static HttpServletRequest getCurrentHttpRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
				.filter(ServletRequestAttributes.class::isInstance)
				.map(ServletRequestAttributes.class::cast)
				.map(ServletRequestAttributes::getRequest).orElse(null);
	}

}
