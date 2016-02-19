package test.com.rise.shop.dao.art.utils;

import com.rise.shop.domain.art.mysql.User;
import com.rise.shop.domain.query.UserQuery;
import com.rise.shop.persistence.generate.GenerateSqlAndIbatisXmlTool;

/**
 * Created by wangdi on 16-2-19.
 */
public class TestGenerateSqlAndXml {

    public static void main(String[] args) {
        System.out.println(GenerateSqlAndIbatisXmlTool.generate(User.class, UserQuery.class));
    }

}
