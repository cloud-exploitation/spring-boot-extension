package com.livk.yauaa.webflux.example;

import com.livk.commons.spring.SpringLauncher;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>
 * YauaaWebApp
 * </p>
 *
 * @author livk
 */
@SpringBootApplication
public class YauaaWebApp {
    public static void main(String[] args) {
        SpringLauncher.run(YauaaWebApp.class, args);
    }
}
