package com.alxsshv.exception;

/**
 * Класс, описывающий исключения, связанные с обработкой
 *  данных при использовании сервиса.
 *   * @author Шварёв Алексей
 *  * @version 1.0*/
public class DataProcessingException extends RuntimeException {
    /**Конструктор для класса DataProcessingException.
     * @param message - строка сообщения об ошибке,
     * которая привела к возникновению исключения*/
    public DataProcessingException(final String message) {
        super(message);
    }
}
