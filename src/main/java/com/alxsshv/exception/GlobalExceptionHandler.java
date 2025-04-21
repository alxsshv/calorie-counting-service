package com.alxsshv.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**Класс предназначен для обработки исключений,
 * возникающих в процессе обработки http-запроса
 * и подготовке ответа, содержащего сообщение
 * о возникшей ошибке.
 * @author Шварёв Алексей
 * @version 1.0*/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**Метод обработки исключений {@link EntityNotFoundException}.
     * Сообщение может возникать в случае, если в БД отсутствует запись
     * о сущности с указанным идентификатором (id) или идентификатор
     * указан некорректно.
     * @param ex - исключение EntityNotFoundException..
     * @return возвращает объект {@link ResponseEntity},
     * со статусом 404 и сообщением об ошибке*/
    @ExceptionHandler
    ResponseEntity<String> handleEntityNotFoundException(
            final EntityNotFoundException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    /**Метод обработки исключений {@link ConstraintViolationException}.
     * Сообщение может возникать в случае, если при формировании http-запроса
     * не соблюдены правила валидации передаваемого объекта.
     * @param ex - исключение ConstraintViolationException.
     * @return возвращает объект {@link ResponseEntity},
     * со статусом 400 (BAD REQUEST) и сообщением об ошибке*/
    @ExceptionHandler
    ResponseEntity<String> handleConstraintViolationException(
            final ConstraintViolationException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    /**Метод обработки исключений {@link DataProcessingException}.
     * Сообщение может возникать в случае, возникновения исключения
     * в процессе выполнения бизнес логики сервиса,
     * связанное с ошибками в http-запросе.
     * @param ex - исключение DataProcessingException.
     * @return возвращает объект {@link ResponseEntity},
     * со статусом 400 (BAD REQUEST) и сообщением об ошибке*/
    @ExceptionHandler
    ResponseEntity<String> handleDataProcessingException(
            final DataProcessingException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
