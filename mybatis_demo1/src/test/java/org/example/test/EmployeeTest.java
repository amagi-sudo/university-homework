package org.example.test;

import org.apache.ibatis.session.SqlSession;
import org.example.pojo.Employee;
import org.example.utils.MyBatisUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTest extends JFrame {

    // 定义一个名为 MAPPER_PREFIX 的常量，类型为 String，表示在 EmployeeMapper 映射器中的前缀
    private static final String MAPPER_PREFIX = "mapper.EmployeeMapper.";
    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public EmployeeTest() {
        setTitle("员工管理系统");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
        loadEmployees(); // Load initial employee data
    }

    private void initializeComponents() {
        // 定义表格列名数组，包含 ID、姓名、年龄和职位
        String[] columnNames = {"ID", "姓名", "年龄", "职位"};

        // 创建用于表格的字体，设置字体样式和大小
        Font font = new Font("微软雅黑", Font.PLAIN, 20);
        Font buttonFont = new Font("微软雅黑", Font.BOLD, 16); // 设置按钮字体

        // 设置全局的消息对话框字体
        UIManager.put("OptionPane.messageFont", new Font("微软雅黑", Font.PLAIN, 30)); // 设置消息字体
        // 设置全局的按钮字体
        UIManager.put("OptionPane.buttonFont", new Font("微软雅黑", Font.PLAIN, 30)); // 设置按钮字体

        // 创建默认的表格模型，使用列名和行数初始化
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel); // 创建 JTable 组件

        // 设置表格的字体
        employeeTable.setFont(font);
        employeeTable.setRowHeight(20); // 设置行高，以适应更大的字体

        // 设置列标题的字体
        JTableHeader header = employeeTable.getTableHeader();
        header.setFont(new Font("微软雅黑", Font.BOLD, 24)); // 设置列标题的字体

        // 创建一个自定义的单元格渲染器，使文本居中显示
        DefaultTableCellRenderer centerRenderer = new CenteredTableCellRenderer();
        for (int i = 0; i < columnNames.length; i++) {
            // 为每一列设置居中渲染器
            employeeTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        employeeTable.setRowHeight(50); // 设置行高
        JScrollPane scrollPane = new JScrollPane(employeeTable); // 将表格放入滚动面板中

        // 创建四个按钮，用于不同的员工操作
        JButton findButton = new JButton("查找员工");
        JButton addButton = new JButton("添加员工");
        JButton updateButton = new JButton("更新员工");
        JButton deleteButton = new JButton("删除员工");

        // 设置按钮的字体
        findButton.setFont(buttonFont);
        addButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);
        deleteButton.setFont(buttonFont);

        // 设置按钮的首选大小（宽度和高度）
        Dimension buttonSize = new Dimension(150, 50); // 设置按钮的宽度和高度
        findButton.setPreferredSize(buttonSize);
        addButton.setPreferredSize(buttonSize);
        updateButton.setPreferredSize(buttonSize);
        deleteButton.setPreferredSize(buttonSize);

        // 为按钮添加动作监听器，定义每个按钮的点击事件
        findButton.addActionListener(e -> findByIdTest());
        addButton.addActionListener(e -> insertEmployeeTest());
        updateButton.addActionListener(e -> updateEmployeeTest());
        deleteButton.addActionListener(e -> deleteEmployeeTest());

        // 创建一个面板用于放置按钮
        JPanel buttonPanel = new JPanel();
        // 将所有按钮添加到面板
        buttonPanel.add(findButton);
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // 设置内容面板的布局为边界布局
        getContentPane().setLayout(new BorderLayout());
        // 将滚动面板添加到内容面板的中心区域
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        // 将按钮面板添加到内容面板的底部区域
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * 定义了一个私有方法，用于加载员工数据。
     **/
    private void loadEmployees() {
        // 清空表格模型中的所有行，准备重新加载员工数据
        tableModel.setRowCount(0);

        // 使用 try-with-resources 语句自动管理 SqlSession 的生命周期
        try (SqlSession sqlSession = MyBatisUtils.getSession()) {
            // 从数据库中查询所有员工，使用 MyBatis 的 selectList 方法
            List<Employee> employees = sqlSession.selectList(MAPPER_PREFIX + "findAll");

            // 遍历查询到的员工列表
            for (Employee employee : employees) {
                // 将每个员工的相关信息添加到表格模型中
                // 使用 Object 数组构造每一行的数据，分别为员工的 ID、姓名、年龄和职位
                tableModel.addRow(new Object[]{
                        employee.getId(),
                        employee.getName(),
                        employee.getAge(),
                        employee.getPosition()
                });
            }
        } catch (Exception e) {
            // 捕获异常并打印堆栈跟踪信息，方便调试
            e.printStackTrace();
        }
    }


    /*************
     * 查找员工
     * *************/
    private void findByIdTest() {
        // 创建一个新的 JTextField，设置大小
        JTextField inputField = new JTextField();
        inputField.setPreferredSize(new Dimension(300, 40)); // 设置宽度和高度

        // 设置输入文本的字体大小
        inputField.setFont(new Font("微软雅黑", Font.PLAIN, 20)); // 选择字体、样式和大小

        // 使用 JOptionPane 显示输入对话框
        Object[] message = {
                "请输入要查找的员工ID（用逗号分隔）:", inputField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "查找员工", JOptionPane.OK_CANCEL_OPTION);

        // 处理用户的输入
        if (option == JOptionPane.OK_OPTION) {
            String input = inputField.getText();
            if (input != null && !input.trim().isEmpty()) {
                try {
                    // 将输入的ID字符串分割成数组
                    String[] ids = input.split(",");
                    List<Integer> idList = new ArrayList<>();
                    for (String id : ids) {
                        idList.add(Integer.parseInt(id.trim())); // 转换并添加到列表中
                    }

                    try (SqlSession sqlSession = MyBatisUtils.getSession()) {
                        List<Employee> employees = sqlSession.selectList(MAPPER_PREFIX + "findByIds", idList);

                        if (!employees.isEmpty()) {
                            // 构建成功查找的员工信息字符串
                            StringBuilder messageBuilder = new StringBuilder("找到以下员工:\n");
                            for (Employee employee : employees) {
                                messageBuilder.append("{ID=").append(employee.getId())
                                        .append(", 姓名=").append(employee.getName())
                                        .append(", 年龄=").append(employee.getAge())
                                        .append(", 职位=").append(employee.getPosition() + "}").append("\n");
                            }
                            // 弹出提示框显示员工信息
                            JOptionPane.showMessageDialog(this, messageBuilder.toString(), "查找成功", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "未找到该员工", "查找结果", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "请输入有效的员工ID", "输入错误", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "查找过程中发生错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "输入不能为空", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        loadEmployees(); // 刷新表格
    }

    /*************
     * 添加员工
     * *************/
    public void insertEmployeeTest() {
        // 创建字符串数组用于存储用户输入
        String[] inputs = new String[4];
        String[] prompts = {
                "请输入新员工的ID:",
                "请输入新员工的姓名:",
                "请输入新员工的年龄:",
                "请输入新员工的职位:"
        };

        // 创建面板用于放置所有输入框
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(prompts.length, 2, 10, 10)); // 使用网格布局管理器

        // 循环创建每个输入框并添加到面板
        JTextField[] textFields = new JTextField[prompts.length];
        for (int i = 0; i < prompts.length; i++) {
            // 创建 JLabel 来显示提示信息，并设置字体
            JLabel label = new JLabel(prompts[i]);
            label.setFont(new Font("微软雅黑", Font.PLAIN, 24)); // 设置字体大小为24

            // 使用一个新的 JTextField 作为输入框
            textFields[i] = new JTextField();
            textFields[i].setPreferredSize(new Dimension(200, 40));
            textFields[i].setFont(new Font("微软雅黑", Font.PLAIN, 20));

            // 将标签和输入框添加到面板
            panel.add(label);
            panel.add(textFields[i]);
        }

        // 在对话框中显示输入提示和输入框
        int option = JOptionPane.showConfirmDialog(this, panel, "输入员工信息", JOptionPane.OK_CANCEL_OPTION);

        // 检查用户是否点击了“确认”
        if (option == JOptionPane.OK_OPTION) {
            // 获取所有输入框的内容
            for (int i = 0; i < textFields.length; i++) {
                inputs[i] = textFields[i].getText().trim();
                // 如果输入为空，显示错误消息并退出
                if (inputs[i].isEmpty()) {
                    JOptionPane.showMessageDialog(this, "输入不能为空。");
                    return;
                }
            }

            // 验证输入的 ID 和年龄
            try {
                int id = Integer.parseInt(inputs[0]);
                String name = inputs[1];
                int age = Integer.parseInt(inputs[2]);
                String position = inputs[3];

                if (age <= 0) {
                    throw new NumberFormatException("年龄必须是正数");
                }

                Employee employee = new Employee();
                employee.setId(id);
                employee.setName(name);
                employee.setAge(age);
                employee.setPosition(position);


                // 插入数据库
                try (SqlSession sqlSession = MyBatisUtils.getSession()) {
                    sqlSession.insert(MAPPER_PREFIX + "addEmployee", employee);
                    sqlSession.commit();
                    JOptionPane.showMessageDialog(this, "成功插入新员工: " + employee);
                    loadEmployees(); // 更新表格显示
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "请输入有效的ID和年龄。\n" + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "插入员工失败: " + e.getMessage());
            }
        }
    }


    /*************
     * 更新员工
     * *************/
    public void updateEmployeeTest() {
        // 创建一个 JPanel 用于承载所有的输入组件
        JPanel panel = new JPanel(new GridLayout(0, 1)); // 使用网格布局，纵向排列
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        JTextField positionField = new JTextField();

        // 设置字体
        Font font = new Font("微软雅黑", Font.PLAIN, 20);
        idField.setFont(font);
        nameField.setFont(font);
        ageField.setFont(font);
        positionField.setFont(font);

        // 创建 JLabel 并设置字体
        JLabel idLabel = new JLabel("请输入要更新的员工ID:");
        idLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        JLabel nameLabel = new JLabel("请输入新的员工姓名:");
        nameLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        JLabel ageLabel = new JLabel("请输入新的员工年龄:");
        ageLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
        JLabel positionLabel = new JLabel("请输入新的员工职位:");
        positionLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));

        // 将 JLabel 和 JTextField 添加到 JPanel
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(ageLabel);
        panel.add(ageField);
        panel.add(positionLabel);
        panel.add(positionField);

        // 显示输入对话框
        int option = JOptionPane.showConfirmDialog(this, panel, "更新员工信息", JOptionPane.OK_CANCEL_OPTION);

        // 检查用户选择
        if (option == JOptionPane.OK_OPTION) {
            String idInput = idField.getText().trim();
            String name = nameField.getText().trim();
            String ageInput = ageField.getText().trim();
            String position = positionField.getText().trim();

            if (!idInput.isEmpty() && !name.isEmpty() && !ageInput.isEmpty() && !position.isEmpty()) {
                try {
                    int id = Integer.parseInt(idInput);
                    int age = Integer.parseInt(ageInput);
                    Employee employee = new Employee();
                    employee.setId(id);
                    employee.setName(name);
                    employee.setAge(age);
                    employee.setPosition(position);

                    try (SqlSession sqlSession = MyBatisUtils.getSession()) {
                        int result = sqlSession.update(MAPPER_PREFIX + "updateEmployee", employee);
                        sqlSession.commit();
                        if (result > 0) {
                            JOptionPane.showMessageDialog(this, "成功更新 " + result + " 条数据");
                        } else {
                            JOptionPane.showMessageDialog(this, "更新数据失败，请输入表格中存在的ID");
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "请输入有效的ID和年龄");
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "更新失败: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "所有输入不能为空。");
            }
        }

        loadEmployees(); // 刷新表格
    }


    /*************
     * 删除员工
     * *************/
    private void deleteEmployeeTest() {
        JTextField inputField = new JTextField();// 创建一个新的 JTextField，设置大小
        inputField.setPreferredSize(new Dimension(300, 40)); // 设置宽度和高度

        // 设置输入文本的字体大小
        inputField.setFont(new Font("微软雅黑", Font.PLAIN, 20)); // 选择字体、样式和大小

        // 使用 JOptionPane 显示输入对话框
        Object[] message = {
                "请输入要删除的员工ID（用逗号分隔）:", inputField
        };
        int option = JOptionPane.showConfirmDialog(this, message, "删除员工", JOptionPane.OK_CANCEL_OPTION);

        // 处理用户的输入
        if (option == JOptionPane.OK_OPTION) {
            String input = inputField.getText();
            if (input != null && !input.trim().isEmpty()) {
                try {
                    // 将输入的ID字符串分割成数组
                    String[] ids = input.split(",");
                    List<Integer> idList = new ArrayList<>();
                    for (String id : ids) {
                        idList.add(Integer.parseInt(id.trim())); // 转换并添加到列表中
                    }

                    // 通过 MyBatis 执行删除操作
                    try (SqlSession sqlSession = MyBatisUtils.getSession()) {
                        int deletedCount = sqlSession.delete(MAPPER_PREFIX + "deleteByIds", idList);
                        sqlSession.commit(); // 提交事务

                        // 弹出提示框显示删除结果
                        if (deletedCount > 0) {
                            JOptionPane.showMessageDialog(this, "成功删除 " + deletedCount + " 个员工", "删除成功", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(this, "未找到任何员工以删除", "删除结果", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "请输入有效的员工ID", "输入错误", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "删除过程中发生错误", "错误", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "输入不能为空", "输入错误", JOptionPane.ERROR_MESSAGE);
            }
        }
        loadEmployees(); // 刷新表格
    }


    public static void main(String[] args) {
        // 使用 SwingUtilities.invokeLater 方法确保 Swing 组件的创建和更新在事件调度线程中执行
        SwingUtilities.invokeLater(() -> {
            EmployeeTest test = new EmployeeTest();
            test.setVisible(true);
        });
    }
    // 定义一个静态内部类 CenteredTableCellRenderer，继承自 DefaultTableCellRenderer
    static class CenteredTableCellRenderer extends DefaultTableCellRenderer {
        // 构造方法，用于初始化 CenteredTableCellRenderer 实例
        public CenteredTableCellRenderer() {
            // 设置单元格内容的水平对齐方式为居中对齐
            setHorizontalAlignment(SwingConstants.CENTER);
        }
    }
}