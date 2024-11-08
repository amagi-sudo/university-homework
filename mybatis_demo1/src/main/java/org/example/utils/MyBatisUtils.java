package org.example.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.Reader;

public class MyBatisUtils {
    private static SqlSessionFactory sqlSessionFactory = null;

    static {
        try{
            //使用MyBatis提供的Resources类加载MyBatis的配置文件
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            //构建SqlSessionFactory工厂
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static SqlSession getSession(){//获取SqlSessionFactory对象的静态方法
        return sqlSessionFactory.openSession();
    }
}
