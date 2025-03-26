package org.solar.system.mdm.service.config;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.solar.system.central.common.mdm.enums.MdmLanguageSupported;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;

import static org.solar.system.central.common.all.utils.HttpConstUtils.HEADER_ACCEPT_LANGUAGE;

@Slf4j
@Component
public class LocaleResolver extends AcceptHeaderLocaleResolver {

	private final List<String> languageSupported = MdmLanguageSupported.listFromEnum();
	private static final List<Locale> SUPPORTED_LOCALES = new ArrayList<Locale>();

	@PostConstruct
	private void init() {
		languageSupported.stream()//
				.map(lang -> new Locale.Builder().setLanguageTag(lang).build())//
				.forEach(SUPPORTED_LOCALES::add);
	}

	@Override
	public Locale resolveLocale(HttpServletRequest request) {

		String headerLang = request.getHeader(HEADER_ACCEPT_LANGUAGE);
		if (StringUtils.isEmpty(headerLang)) {
			return Locale.getDefault();
		}

		headerLang = headerLang.replace("_", "-");
		log.info("Accept-Language : {}", headerLang);
		List<LanguageRange> parsedLocale = Locale.LanguageRange.parse(headerLang);
		return Locale.lookup(parsedLocale, SUPPORTED_LOCALES);

	}
}
