package org.example.test;

import org.example.pojo.Users;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class application {
    @Test
    public void test() {
        String resources="mybatis-config.xml";
        Reader reader=null;
        SqlSession sqlSession=null;
        try {
            reader= Resources.getResourceAsReader(resources);
            SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
            sqlSession=sqlSessionFactory.openSession();
            Users users = sqlSession.selectOne("findById",1);
            if (users==null){
                throw new RuntimeException("查找数据失败");
            }
            System.out.println("查询成功");
            System.out.println("uname:"+users.getUname());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(sqlSession!=null){
                sqlSession.close();
            }
        }
    }
}
