package com.moralok.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * @author moralok
 * @since 2020/12/29 2:06 下午
 */
@Service
public class AccountService {

    @EventListener(classes = ApplicationEvent.class)
    public void listen(ApplicationEvent event) {
        System.out.println("AccountService..监听到 " + event);
    }
}
