package org.solar.system.hexa.domain.port.out;

public interface MessageTranslatorProviderPort {

    String translate(String key);

    String translate(String key, Object... args);
}
