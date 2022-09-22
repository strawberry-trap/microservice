package com.microservice.member.demo;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncTaskService {

    @Async
    public CompletableFuture<Void> task (int i) throws Exception {
        try {
            Thread.sleep(1000);
            if (i == 5) {
                Thread.sleep(5000);
                // throw new Exception("5-th task intentional exception");
            }

//            if (i == 6) throw new Exception("6 exception!");
//            if (i == 7) throw new Exception("7 exception!");
            System.out.println(i + "-th thread job done");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(null);
    }
}
