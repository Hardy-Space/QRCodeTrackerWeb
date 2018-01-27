import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2018/1/26.
 */
public class Test2 extends TestCase {

    public void test01() throws Exception {
        System.out.println("Hello,World!");
        String conf = "WEB-INF/applicationContext.xml";
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(conf);
        System.out.println(applicationContext);
    }
}