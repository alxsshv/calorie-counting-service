package com.alxsshv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**Основной класс для запуска приложения.
 * @author Шварёв Алексей
 * @version 1.0*/
@SpringBootApplication
public class Main {
    /**Метод, предназначенный для запуска приложения.
     * @param args - параметры запуска приложения*/
    public static void main(final String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
