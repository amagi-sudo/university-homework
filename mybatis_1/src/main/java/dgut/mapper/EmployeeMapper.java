package dgut.mapper;

import dgut.pojo.Employee;
import java.util.List;

public interface EmployeeMapper {
    //查询所有记录
    List<Employee> selectAll();
    //按id查询
    Employee selectById(int id);
    //添加记录
    int add(Employee employee);
    //按id修改记录
    int update(Employee employee);
    //按id删除记录
    int delete(int id);
}
