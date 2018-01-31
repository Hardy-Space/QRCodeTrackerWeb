import com.qdhualing.qrcodetracker.bean.DataBean;
import com.qdhualing.qrcodetracker.bean.DataInputParams;
import com.qdhualing.qrcodetracker.bean.JDBCDataSource;
import com.qdhualing.qrcodetracker.utils.JSONUtils;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/26.
 */
public class Test2 extends TestCase {

    public void test01() throws Exception {
//        System.out.println("Hello,World!");
//        String conf = "applicationContext.xml";
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(conf);
//        System.out.println(applicationContext);
//        JDBCDataSource d = (JDBCDataSource) applicationContext.getBean("JDBCDataSource");
//
//        System.out.println(d);
//        System.out.println(d.getUrl());

//        List<DataBean> beanList = new ArrayList<DataBean>();
//        DataBean b1 = new DataBean();
//        b1.setName("1111");
//        DataBean b2 = new DataBean();
//        b2.setName("2222");
//        DataBean b3 = new DataBean();
//        b3.setName("3333");
//        DataBean b4 = new DataBean();
//        b4.setName("4444");
//        beanList.add(b1);
//        beanList.add(b2);
//        beanList.add(b3);
//        beanList.add(b4);
//
//        DataInputParams params = new DataInputParams();
//        params.setNeedToInputDataList(beanList);

        String jsonStr = "{\"needToInputDataList\":[{\"dataType\":4,\"sqlFieldName\":\"ID\",\"value\":\"1\"},{\"dataType\":12,\"sqlFieldName\":\"QRCode_ID\",\"value\":\"2018128111731010001\"}],\"type\":30}}";
        DataInputParams params1 = JSONUtils.toBean(jsonStr,DataInputParams.class,"needToInputDataList",DataBean.class);
        for (int i = 0; i < params1.getNeedToInputDataList().size(); i++) {
            params1.getNeedToInputDataList().get(i);
        }
    }
}