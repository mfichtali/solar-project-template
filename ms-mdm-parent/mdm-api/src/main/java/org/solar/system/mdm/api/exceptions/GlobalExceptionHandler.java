package org.solar.system.mdm.api.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.solar.system.central.common.all.exceptions.BusinessException;
import org.solar.system.central.common.all.pojo.EntityWrapper;
import org.solar.system.central.common.all.pojo.ErrorWrapper;
import org.solar.system.central.common.all.pojo.PropertyInputValidator;
import org.solar.system.central.common.all.utils.DateUtils;
import org.solar.system.central.common.all.utils.ServiceUtils;
import org.solar.system.mdm.service.config.TranslatorProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static org.solar.system.central.common.all.utils.ConstantUtils.PATTERN_EXPRESSION_BETWEEN_BRACKET;
import static org.solar.system.central.common.all.utils.ConstantUtils.PATTERN_INDEX_PROPERTY_VALIDATOR;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErrorWrapper<String> error = ErrorWrapper.<String>builder()
				.date(DateUtils.toStringFormat(LocalDateTime.now()))
				.message(ex.getMessage()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorWrapper<String> error = ErrorWrapper.<String>builder()
				.date(DateUtils.toStringFormat(LocalDateTime.now()))
				.message(ex.getMessage()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ EntityNotFoundException.class })
	public ErrorWrapper<String> handleEntityNotFoundException(EntityNotFoundException ex) {
		return ServiceUtils.errorWrapperAsString(ex.getMessage());
	}

	@ExceptionHandler({ RuntimeException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorWrapper<String> handleRuntimeException(RuntimeException runtimeException, HttpServletRequest request,
			HttpServletResponse response) {
		return ServiceUtils.errorWrapperAsString(runtimeException.getMessage());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public EntityWrapper<PropertyInputValidator> handleConstraintViolation(ConstraintViolationException exception) {

		String errorMessage;
		PropertyInputValidator validationResult = null;
		Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

		List<ConstraintViolation<?>> violationsToList = violations.stream()//
				.sorted(Comparator.comparing(//
						ConstraintViolation::getPropertyPath, //
						(p1, p2) -> p2.toString().compareTo(p1.toString()) * -1))//
				.collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(violationsToList)) {

			ConstraintViolation<?> violationContent = violationsToList.get(0);
			String propertyPath = violationContent.getPropertyPath().toString();
			errorMessage = violationContent.getMessage();
			validationResult = validationErrorProperty(propertyPath, errorMessage);

		}
		return EntityWrapper.<PropertyInputValidator>builder().success(Boolean.FALSE)
				.date(DateUtils.toStringFormat(LocalDateTime.now())).result(validationResult).build();
	}

	private PropertyInputValidator validationErrorProperty(String propertyPath, String errorMessage) {

		PropertyInputValidator validationResult = new PropertyInputValidator();
		String subStr = StringUtils.substring(propertyPath, propertyPath.lastIndexOf(".") - 4);
		String[] tabStr = StringUtils.split(subStr, '.');

		if (tabStr[0].contains("]")) {
			Matcher matcher = ServiceUtils.findMatcher(PATTERN_INDEX_PROPERTY_VALIDATOR, tabStr[0]);
			if (matcher.find()) {
				validationResult.setIndex(matcher.group(2));
			}
		} else {
			validationResult.setPropertyName(tabStr[1]);
		}

		Matcher matcher = ServiceUtils.findMatcher(PATTERN_EXPRESSION_BETWEEN_BRACKET, errorMessage);
		if (matcher.find()) {
			errorMessage = TranslatorProvider.getMsg(StringUtils.substringBetween(errorMessage, "{", "}"));
		}

		validationResult.setMessage(errorMessage);
		return validationResult;

	}

	@ExceptionHandler({ BusinessException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorWrapper<String> handleBadRequest(BusinessException runtimeException) {
		return ServiceUtils.errorWrapperAsString(runtimeException.getMessage());
	}

}
