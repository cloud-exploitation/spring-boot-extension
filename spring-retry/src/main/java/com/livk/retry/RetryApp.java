package com.livk.retry;

import com.livk.commons.spring.SpringLauncher;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

/**
 * <p>
 * RetryApp
 * </p>
 *
 * @author livk
 */
@EnableRetry
@SpringBootApplication
public class RetryApp {

    public static void main(String[] args) {
        SpringLauncher.run(RetryApp.class, args);
    }

}
