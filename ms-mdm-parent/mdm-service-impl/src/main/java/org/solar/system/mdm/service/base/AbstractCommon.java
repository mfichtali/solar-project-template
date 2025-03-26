package org.solar.system.mdm.service.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.TraceContext;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.solar.system.central.common.all.messaging.MonoDataKF;
import org.solar.system.mdm.service.config.properties.AppLinkerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@RequiredArgsConstructor
public abstract class AbstractCommon {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Tracer tracer;

    @Value("${spring.profiles.active}")
    protected String activeProfile;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected AppLinkerProperties appProperties;

    public Pair<String, String> currentThreadTracing() {
        Span currentSpan = tracer.currentSpan();
        return Optional.ofNullable(currentSpan)
                .map(Span::context)
                .map(context -> Pair.of(context.traceId(), context.spanId()))
                .orElse(Pair.of(StringUtils.EMPTY, StringUtils.EMPTY));
    }

    public String currentTraceId() {
        Span currentSpan = tracer.currentSpan();
        return Optional.ofNullable(currentSpan)
                .map(Span::context)
                .map(TraceContext::traceId)
                .orElse(StringUtils.EMPTY);
    }

    public String currentSpanId() {
        Span currentSpan = tracer.currentSpan();
        return Optional.ofNullable(currentSpan)
                .map(Span::context)
                .map(TraceContext::spanId)
                .orElse(StringUtils.EMPTY);
    }

    /**
     * Write Data as Json String format
     *
     * @param data
     * @param pretty
     * @return
     */
    public String writeObjectAsString(Object data, Boolean pretty) {
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

    public String classInfoFullName() {
        String className = this.getClass().getSimpleName();
        String packageName = this.getClass().getPackageName();
        return String.format("%s.%s", packageName, className);
    }

    public <S, T> T convertMessage(S source, Class<T> clazz) {
        return objectMapper.convertValue(source, clazz);
    }

    public <T> T convertMonoStringAsObject(MonoDataKF<String> message, Class<T> clazz) {
        return objectMapper.convertValue(message.getData(), clazz);
    }
}
