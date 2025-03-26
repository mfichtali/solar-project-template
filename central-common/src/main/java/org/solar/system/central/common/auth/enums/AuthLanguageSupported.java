package org.solar.system.central.common.auth.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum AuthLanguageSupported {
    
	fr_FR("fr-FR"),
	en_US("en-US"),
    en_GB("en-GB"),
    ar_MA("ar-MA")
    
    ;

    public final String lang;
	
	private AuthLanguageSupported(String lang) {
		this.lang = lang;
	}
	
	public static List<String> listFromEnum() {
		return Arrays.stream(AuthLanguageSupported.values())
				.map(AuthLanguageSupported::getLang)
				.collect(Collectors.toList());
	}

}

