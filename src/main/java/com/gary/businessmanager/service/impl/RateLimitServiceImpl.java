package com.gary.businessmanager.service.impl;

import com.gary.businessmanager.service.RateLimitService;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RateLimitServiceImpl implements RateLimitService {

    public static void main(String[] args) {
        // 这里的1表示每秒允许处理的量为1个
        RateLimiter limiter = RateLimiter.create(0.5);

        int i = 0;
        for (;;) {
            if (i > 10) {
                limiter.setRate(2);
            }
            // 请求RateLimiter, 超过permits会被阻塞
            limiter.acquire();
            System.out.println("call execute.." + i++);
        }
    }

}
