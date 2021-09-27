package br.com.builders.api.cliente.handle;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import br.com.builders.api.cliente.exception.BusinessHttpExcception;
import br.com.builders.api.cliente.exception.ClienteJaCadastradoException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiRestControllerExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messages;

  @ExceptionHandler(BusinessHttpExcception.class)
  public ResponseEntity<?> handleBusinessHttpExcception(BusinessHttpExcception exception, WebRequest request) {
    final var builder = Error.builder();
    builder.status(exception.getStatus());
    builder.titulo(exception.getMessage());
    return super.handleExceptionInternal(exception, builder.build(), null, exception.getStatus(), request);
  }

  @ExceptionHandler(ClienteJaCadastradoException.class)
  public ResponseEntity<?> handleBusinessExcceptionClienteJaCadastrado(ClienteJaCadastradoException exception, WebRequest request) {
    final var builder = Error.builder();
    builder.status(HttpStatus.BAD_REQUEST);
    builder.titulo(exception.getMessage());
    return super.handleExceptionInternal(exception, builder.build(), null, HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    final var campos = ex.getBindingResult().getAllErrors().stream().map(error -> {
      final var builder = ErrorField.builder();
      builder.nome(error.getObjectName());
      builder.descricao(messages.getMessage(error, LocaleContextHolder.getLocale()));

      if (error instanceof FieldError) {
        builder.nome(((FieldError) error).getField());
      }

      return builder.build();
    }).collect(Collectors.toList());

    final Error error = Error.builder().status(status).titulo("Valores inválidos para o body da requisição")
        .momento(LocalDateTime.now()).campos(campos).build();

    return super.handleExceptionInternal(ex, error, headers, status, request);
  }

}