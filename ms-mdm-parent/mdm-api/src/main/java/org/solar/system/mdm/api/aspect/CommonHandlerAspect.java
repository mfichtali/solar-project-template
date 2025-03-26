package org.solar.system.mdm.api.aspect;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.solar.system.central.common.all.annotations.HistorizeDataPerform;
import org.solar.system.central.common.all.enums.ActionSystemEnum;
import org.solar.system.central.common.all.enums.KafkaKeyLabelEnum;
import org.solar.system.central.common.all.exceptions.BusinessException;
import org.solar.system.central.common.all.exceptions.TechnicalException;
import org.solar.system.central.common.audit.dto.AuditErrorInfo;
import org.solar.system.central.common.audit.enums.AuditErrorTypeEnum;
import org.solar.system.mdm.service.api.KafkaProducerService;
import org.solar.system.mdm.service.api.ScheduleTaskTxOutboxService;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.solar.system.central.common.all.messaging.KafkaGroupHeaderEnum.MDM_2_AUDIT;
import static org.solar.system.central.common.all.utils.ConstantUtils.ANNOTATION_HISTORIZE_DATA;
import static org.solar.system.central.common.all.utils.ConstantUtils.ANNOTATION_LOG_EXECUTION_TIME;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class CommonHandlerAspect extends AbstractAspect {

	private final ScheduleTaskTxOutboxService scheduleTaskTxOutboxService;

	/** Kafka Producer service */
	private final KafkaProducerService producer;

	@AfterThrowing(pointcut = "execution(* org.solar.system.mdm.api.rest..*.*(..))", throwing = "ex")
	public void handleExceptions(JoinPoint joinPoint, Exception ex) {

		/* Class Name */
		String packageName = joinPoint.getTarget().getClass().getPackageName();
		String className = joinPoint.getTarget().getClass().getSimpleName();

		/* Method Information */
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		AuditErrorTypeEnum auditErrorType;
		String errMessage = ex.getMessage();
		String classExceptionName = ex.getClass().getSimpleName();

		if (ex instanceof BusinessException) {
			log.error("AOP FUNCTIONAL ERR - <? extends BusinessException> occurred: {}", ex.getMessage());
			auditErrorType = ((BusinessException) ex).getErrorType();
		} else if(ex instanceof TechnicalException){
			log.error("AOP TECHNICAL ERR - <? extends TechnicalException> occurred: {}", ex.getMessage());
			auditErrorType = ((TechnicalException) ex).getErrorType();
		} else {
			log.error("AOP TECHNICAL ERR - Exception occurred: {}", ex.getMessage());
			auditErrorType = AuditErrorTypeEnum.TECHNICAL;
		}

		AuditErrorInfo auditError = new AuditErrorInfo()
				.setErrorType(auditErrorType)
				.setErrorMessage(errMessage)
				.setClassExceptionName(classExceptionName);
		
		scheduleTaskTxOutboxService.storeScheduleActionTx(() -> mapper.auditErrorToTxOutboxMapper(auditError, ActionSystemEnum.UPDATE, this::writeObjectAsString),
				KafkaKeyLabelEnum.KEY_AUDIT_ON_ERROR, Set.of(MDM_2_AUDIT.name()), packageName + "." + className, method.getName());

	}

	@Around(ANNOTATION_LOG_EXECUTION_TIME)
	public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

		Instant start = Instant.now();

		/* Class Name */
		String className = joinPoint.getTarget().getClass().getSimpleName();

		/* Method Information */
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();

		Object proceed = joinPoint.proceed();

		Instant end = Instant.now();

		log.info("Class/Method [{}|{}] is executed in {}", className, signature.getMethod().getName(),
				Duration.between(start, end));

		return proceed;

	}

	@Before(ANNOTATION_HISTORIZE_DATA)
	public void beforeHistorizeData(JoinPoint joinPoint) {

		if (!appProperties.isHistorizePerformEnabled()) {
			return;
		}

		/* Class Name */
		String packageName = joinPoint.getTarget().getClass().getPackageName();
		String className = joinPoint.getTarget().getClass().getSimpleName();

		/* Method Information */
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		/** Annotation information */
		HistorizeDataPerform dataPerform = method.getAnnotation(HistorizeDataPerform.class);
		ActionSystemEnum action = dataPerform.action();

		int index = 0;
		Map<String, Object> map = new HashMap<>();
		for (String param : signature.getParameterNames()) {
			Object valueParam = joinPoint.getArgs()[index];
			if (valueParam instanceof Optional<?>) {
				valueParam = ((Optional) valueParam).map(v -> {
					if (ClassUtils.isPrimitiveOrWrapper(v.getClass())) {
						return Objects.toString(v);
					}
					return v;
				}).orElse(null);
			}
			map.put(param, valueParam);
			index++;
		}

		scheduleTaskTxOutboxService.storeScheduleActionTx(writeObjectAsString(map), action, KafkaKeyLabelEnum.KEY_HISTORIZE_DATA, Set.of(MDM_2_AUDIT.name()),
				UUID.randomUUID(), HistorizeDataPerform.class.getSimpleName(), packageName + "." + className, method.getName());

	}

}
