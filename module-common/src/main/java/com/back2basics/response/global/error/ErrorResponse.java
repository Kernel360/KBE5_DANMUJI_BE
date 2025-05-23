package com.back2basics.response.global.error;

import com.back2basics.response.global.code.CommonErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<FieldError> errors;

	public ErrorResponse(List<FieldError> errors) {
		this.errors = errors;
	}

	// todo errorCode field 수정
	public static ErrorResponse of() {
		return new ErrorResponse(Collections.emptyList());
	}

	public static ErrorResponse of(BindingResult bindingResult) {
		return new ErrorResponse(FieldError.of(bindingResult));
	}

	public static ErrorResponse of(MethodArgumentTypeMismatchException e) {
		String value = e.getValue() == null ? "" : e.getValue().toString();
		return new ErrorResponse(
			FieldError.of(e.getName(), value, CommonErrorCode.INVALID_TYPE_VALUE.getMessage()));
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	public static class FieldError {

		private String field;
		private String value;
		private String reason;

		public FieldError(String field, String value, String reason) {
			this.field = field;
			this.value = value;
			this.reason = reason;
		}

		public static List<FieldError> of(String field, String value, String reason) {
			List<FieldError> list = new ArrayList<>();
			list.add(new FieldError(field, value, reason));
			return list;
		}

		public static List<FieldError> of(BindingResult bindingResult) {
			return bindingResult.getFieldErrors().stream()
				.map(error -> new FieldError(
					error.getField(),
					error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
					error.getDefaultMessage()))
				.collect(Collectors.toList());
		}

		public static List<FieldError> of(Set<ConstraintViolation<?>> violations) {
			return violations.stream()
				.map(v -> new FieldError(
					v.getPropertyPath().toString(),
					"",
					v.getMessage()))
				.collect(Collectors.toList());
		}
	}
}