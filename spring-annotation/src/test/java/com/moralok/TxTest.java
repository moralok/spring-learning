package com.moralok;

import com.moralok.tx.TxConfig;
import com.moralok.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author moralok
 * @since 2020/12/23 3:52 下午
 */
public class TxTest {

    @Test
    public void insertTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = ac.getBean(UserService.class);
        userService.insertUser();
        ac.close();
    }
}
