package br.com.valter.picpaysimplificado.exception;

import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class CustomHandler extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final List<FieldError> errors = ex.getFieldErrors();
        ProblemDetail detail = ProblemDetail.forStatus(BAD_REQUEST);
        Map<String, Set<String>> errorMap = errors
                .stream()
                .collect(Collectors
                        .groupingBy(FieldError::getField,
                                Collectors.mapping(FieldError::getDefaultMessage,
                                        Collectors.toSet())));

        detail.setTitle("Validation failed");
        detail.setStatus(BAD_REQUEST.value());
        detail.setProperty("time",LocalDateTime.now
                        (ZoneId
                        .of("America/Sao_Paulo"))
                .truncatedTo(SECONDS));
        detail.setProperty("errors",errorMap);
        return  ResponseEntity.status(BAD_REQUEST).body(detail);

    }

}
