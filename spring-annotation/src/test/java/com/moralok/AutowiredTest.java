package com.moralok;

import com.moralok.bean.AwareBean;
import com.moralok.bean.Boss;
import com.moralok.bean.Car;
import com.moralok.bean.Manager;
import com.moralok.config.AutowiredConfig;
import com.moralok.dao.BookDao;
import com.moralok.service.BookService;
import com.moralok.service.ResourceBookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 自动装配：是Spring利用依赖注入（DI）完成对容器中的Bean的依赖的赋值
 *
 * 默认情况下，依赖不能为null，可以通过require属性修改
 *
 * 自动装配的注解
 * 自动装配的位置
 * 无法自动装配的类对象（或者说需要特殊的方法装配）
 *
 * @author moralok
 * @since 2020/12/18 3:31 下午
 */
public class AutowiredTest {

    /**
     * 由后置处理器AutowiredAnnotationBeanPostProcessor处理；评论提到@Resource是由另一个后置处理器处理
     * 扫描进来的和自己new的产生两个Dao后，自动装配会选择哪一个呢？
     * 1. 默认先按照顺序、再按照名称。
     * 2. 使用注解@Qualifier指定要装配的组件的名称（使用方）
     * 3. 使用注解@Primary指定首选的组件（提供方），有且仅有一个的时候直接确定。
     * 和@Qualifier同时使用的时候按正常逻辑思考，使用方决定；多个标记@PrimarySpring会傻掉然后报错
     */
    @Test
    public void autowiredTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        BookService bookService = ac.getBean(BookService.class);
        System.out.println(bookService);
        BookDao bookDao = (BookDao) ac.getBean("bookDao");
        System.out.println(bookDao);
    }

    /**
     * 注解@Resource（JSR250）和@Inject（JSR330）
     * 注解@Resource默认按名称，可以指定名称。不能结合@Primary和require=false；@Qualifier通过name属性实现了
     * 注解@Inject（需要额外导入javax依赖）。支持@Primary和@Qualifier，不支持require=false
     */
    @Test
    public void resourceTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        ResourceBookService resourceBookService = ac.getBean(ResourceBookService.class);
        System.out.println(resourceBookService);
    }

    /**
     * 标注在属性上
     * 构造器和构造器参数上均可以（不标注只要可以调用到唯一的构造器也可以）
     * 方法上（可以和构造器统一理解为方法上吗），方法的参数上不行啊
     * 参数（普通方法测试不行啊，咋回事嘞）
     * 结合@Bean方法和参数上都可以默认不加呀
     */
    @Test
    public void autowiredOtherLocationTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        Boss boss = ac.getBean(Boss.class);
        System.out.println(boss);
        Car car = ac.getBean(Car.class);
        System.out.println(car);
        ac.close();
    }

    /**
     * 注解@Component结合@Autowired
     * 注解@Bean的方法的参数会默认从容器中获取
     * 但是！！！方法返回后，仍然可以对@Autowaired注解的属性进行依赖注入
     * 没有魔法？只是默认可以省略掉@Autowired注解？加上也可以指定优先级？
     *
     * 奇奇怪怪的细节不走通源码，记下来也没啥用啊
     */
    @Test
    public void beanAutowiredTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        Manager manager = ac.getBean(Manager.class);
        System.out.println(manager);
        Car car = ac.getBean("car", Car.class);
        System.out.println(car);
        ac.close();
    }

    /**
     * 感觉没看总结，大概不太会注意到一部分Bean是不能被@Autowired处理的吧
     * 注意到这点后，也没有立马意识上xxxAware接口的回调方法是特殊的自动装配方式
     * 好像看到一个说法，这保证了注入的一定是属于容器本身的"那个Bean"！！！
     */
    @Test
    public void awareTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutowiredConfig.class);
        AwareBean awareBean = ac.getBean(AwareBean.class);
        System.out.println(awareBean);
        ac.close();
    }
}
