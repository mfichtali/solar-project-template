package org.solar.system.hexa.infra.translator.adapter;

import org.solar.system.hexa.domain.port.out.MessageTranslatorProviderPort;
import org.springframework.stereotype.Component;

@Component
public class MessageTranslatorProviderAdapter implements MessageTranslatorProviderPort {
    @Override
    public String translate(final String key) {
        return "";
    }

    @Override
    public String translate(final String key, final Object... args) {
        return "";
    }
}
