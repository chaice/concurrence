package org.ccit.com;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuavaRateLimiter {

    public static void main(String[] args) {
        rateLimiterDemo();
    }

    static void rateLimiterDemo() {
        RateLimiter rateLimiter = RateLimiter.create(2.0);

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        final long[] prev = {System.nanoTime()};

        for (int i = 0; i < 20; i++) {
            rateLimiter.acquire();

            executorService.execute(() -> {
                long cur = System.nanoTime();

                System.out.println((cur - prev[0]) / 1000_000);

                prev[0] = cur;
            });
        }
    }
}
