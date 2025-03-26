package org.solar.system.central.common.mdm.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

@Getter
public enum MdmLanguageSupported {
    
    fr_FR("fr-FR"),
	en_US("en-US")
    
    ;

    public final String lang;
	
	private MdmLanguageSupported(String lang) {
		this.lang = lang;
	}
	
	public static List<String> listFromEnum() {
		return Arrays.stream(MdmLanguageSupported.values())
				.map(l -> l.getLang())
				.collect(Collectors.toList());
	}

}

