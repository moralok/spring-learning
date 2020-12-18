package com.moralok.service;

import com.moralok.dao.ResourceBookDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * @author moralok
 * @since 2020/12/18 4:11 下午
 */
@Service
public class ResourceBookService {

    // @Resource(name = "manualResourceBookDao")
    @Inject
    private ResourceBookDao resourceBookDao;

    @Override
    public String toString() {
        return "ResourceBookService{" +
                "resourceBookDao=" + resourceBookDao +
                '}';
    }
}
