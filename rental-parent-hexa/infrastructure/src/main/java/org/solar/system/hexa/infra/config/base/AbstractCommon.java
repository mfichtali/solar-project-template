package org.solar.system.hexa.infra.config.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class AbstractCommon {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${spring.profiles.active}")
    protected String activeProfile;

    @Value("${application.endpoint-api-key}")
    protected String endpointApikey;

    @Autowired
    private Tracer tracer;

    @Autowired
    protected ObjectMapper objectMapper;

//    @Autowired
//    protected AppLinkerProperties appProperties;

//    public Pair<String, String> currentThreadTracing() {
//        Span currentSpan = tracer.currentSpan();
//        return Optional.ofNullable(currentSpan)
//                .map(Span::context)
//                .map(context -> Pair.of(context.traceId(), context.spanId()))
//                .orElse(Pair.of(StringUtils.EMPTY, StringUtils.EMPTY));
//    }

//    protected String currentTraceId() {
//        Span span = tracer.currentSpan();
//        return Optional.ofNullable(span)
//                .map(Span::context)
//                .map(TraceContext::traceId)
//                .orElse(StringUtils.EMPTY);
//    }

//    protected String currentSpanId() {
//        Span span = tracer.currentSpan();
//        return Optional.ofNullable(span)
//                .map(Span::context)
//                .map(TraceContext::spanId)
//                .orElse(StringUtils.EMPTY);
//    }

    /**
     * Write Data as Json String format
     *
     * @param data
     * @param pretty
     * @return
     */
    protected String writeObjectAsString(Object data, Boolean pretty) {
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
    protected String writeObjectAsString(Object data) {
        return writeObjectAsString(data, false);
    }

    protected String classInfoFullName() {
        String className = this.getClass().getSimpleName();
        String packageName = this.getClass().getPackageName();
        return String.format("%s.%s", packageName, className);
    }
}
