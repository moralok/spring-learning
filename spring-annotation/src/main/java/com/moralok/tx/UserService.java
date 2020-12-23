package com.moralok.tx;

import com.moralok.tx.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author moralok
 * @since 2020/12/23 3:50 下午
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void insertUser() {
        userDao.insert();
        System.out.println("插入完成");
        int i = 10/0;
    }
}
