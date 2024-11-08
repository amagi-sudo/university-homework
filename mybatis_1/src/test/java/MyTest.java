import dgut.mapper.EmployeeMapper;
import dgut.pojo.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyTest {
    //按id查询
    @Test
    public void test01() throws IOException {
//        第一步
        InputStream in = Resources.getResourceAsStream("Mapper/mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        EmployeeMapper mapper;
        try (SqlSession sqlSession = factory.openSession()) {
//        第二步
            mapper = sqlSession.getMapper(EmployeeMapper.class);
        }
        //第3大步：调用接口对象的相应方法完成测试
        List<Employee> employee = mapper.selectAll();
        for (Employee employees : employee) {
            System.out.println(employees);
        }
//        按id查询
        System.out.println("=====================================");
        Employee employees = mapper.selectById(2);
        System.out.println(employees);
    }
    //新增员工信息
    @Test
    public void test02() throws IOException {
//        第一步
        InputStream in = Resources.getResourceAsStream("Mapper/mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
//        第二步
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        //第3大步：调用接口对象的相应方法完成测试
        //新增员工信息
        System.out.println("=====================================");
        Employee users = new Employee(4, "王六", 26, "扫地工");
        int n = mapper.add(users);
        if (n != 0) {
            System.out.println("成功");
        }
        sqlSession.commit();
        sqlSession.close();
    }
    // 按id修改
    @Test
    public void test03() throws IOException {
//        第一步
        InputStream in = Resources.getResourceAsStream("Mapper/mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
//        第二步
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        //第3大步：调用接口对象的相应方法完成测试
        // 按id修改
        Employee users = new Employee();
        users.setId(3);
        users.setName("易班");
        users.setAge(19);
        users.setPosition("总经理");
        int n = mapper.update(users);
        if (n != 0) {
            System.out.println("成功");

        }
        sqlSession.commit();
        sqlSession.close();
    }
    // 按id删除
    @Test
    public void test04() throws IOException {
//        第一步
        InputStream in = Resources.getResourceAsStream("Mapper/mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        SqlSession sqlSession = factory.openSession();
//        第二步
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        //第3大步：调用接口对象的相应方法完成测试
//        按id删除
        int result = mapper.delete(4);
        System.out.println(result);
        sqlSession.commit();
        sqlSession.close();

    }
}
