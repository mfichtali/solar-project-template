package org.solar.system.audit.service.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.system.audit.service.config.properties.AppLinkerProperties;
import org.solar.system.central.common.all.messaging.MonoDataKF;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public abstract class AbstractCommon {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Tracer tracer;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AppLinkerProperties appProperties;

    public String currentTraceId() {
        Span span = tracer.currentSpan();
        return Optional.ofNullable(span).map(Span::context).map(TraceContext::traceId).orElse(StringUtils.EMPTY);
    }

    public String currentSpanId() {
        Span span = tracer.currentSpan();
        return Optional.ofNullable(span).map(Span::context).map(TraceContext::spanId).orElse(StringUtils.EMPTY);
    }

    /**
     * Write Data as Json String format
     *
     * @param data
     * @param pretty
     * @return
     */
    public String writeObjectAsString(Object data, boolean pretty) {
        try {
            if (pretty) {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            }
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("Error write Object as String", e);
            return null;
        }
    }

    /**
     * @param data & pretty format is false
     * @return
     */
    public String writeObjectAsString(Object data) {
        return writeObjectAsString(data, false);
    }

    public <T> T convertMonoStringAsObject(MonoDataKF<String> message, Class<T> clazz) {

        try {
            return objectMapper.readValue(message.getData(), clazz);
        } catch (JsonProcessingException e) {
            log.error("Error converting message to object", e);
        }
        return null;
    }

}
