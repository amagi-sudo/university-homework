package org.example.pojo; // 定义包名，用于组织类

// 定义 Employee 类，表示员工的基本信息
public class Employee {
    private int id; // 员工 ID
    private String name; // 员工姓名
    private int age; // 员工年龄
    private String position; // 员工职位

    // 设置员工职位的方法
    public void setPosition(String position) {
        this.position = position; // 将参数 position 赋值给成员变量 position
    }

    // 获取员工姓名的方法
    public String getName() {
        return name; // 返回员工姓名
    }

    // 设置员工姓名的方法
    public void setName(String name) {
        this.name = name; // 将参数 name 赋值给成员变量 name
    }

    // 获取员工 ID 的方法
    public int getId() {
        return id; // 返回员工 ID
    }

    // 设置员工 ID 的方法
    public void setId(int id) {
        this.id = id; // 将参数 id 赋值给成员变量 id
    }

    // 获取员工年龄的方法
    public int getAge() {
        return age; // 返回员工年龄
    }

    // 设置员工年龄的方法
    public void setAge(int age) {
        this.age = age; // 将参数 age 赋值给成员变量 age
    }

    // 获取员工职位的方法
    public String getPosition() {
        return position; // 返回员工职位
    }

    // 重写 toString 方法，返回员工的字符串表示
    @Override
    public String toString() {
        return "员工信息{" + "ID=" + id + ", 姓名='" + name + '\'' +
                ", 年龄=" + age + ", 职位='" + position + '\'' + "}"; // 返回包含员工信息的字符串
    }
}