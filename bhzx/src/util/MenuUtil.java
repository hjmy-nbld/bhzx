package util;

import java.util.Scanner;

public class MenuUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static void printMainMenu() {
        System.out.println("\n===== 教务管理系统 =====");
        System.out.println("1. 登录");
        System.out.println("2. 退出系统");
        System.out.print("请输入选择: ");
    }
    
    public static void printAdminMenu() {
        System.out.println("\n===== 管理员菜单 =====");
        System.out.println("1. 用户管理");
        System.out.println("2. 课程管理");
        System.out.println("3. 任课教师管理");
        System.out.println("4. 选课学生管理");
        System.out.println("5. 修改个人资料");
        System.out.println("6. 退出登录");
        System.out.print("请输入选择: ");
    }
    
    public static void printTeacherMenu() {
        System.out.println("\n===== 教师菜单 =====");
        System.out.println("1. 修改课程信息");
        System.out.println("2. 管理选课学生");
        System.out.println("3. 管理作业");
        System.out.println("4. 管理知识点");
        System.out.println("5. 修改个人资料");
        System.out.println("6. 退出登录");
        System.out.print("请输入选择: ");
    }
    
    public static void printStudentMenu() {
        System.out.println("\n===== 学生菜单 =====");
        System.out.println("1. 查看课程信息");
        System.out.println("2. 完成作业");
        System.out.println("3. 修改个人资料");
        System.out.println("4. 退出登录");
        System.out.print("请输入选择: ");
    }

    public static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("请输入有效的整数！");
            scanner.next();
            System.out.print(prompt);
        }
        int result = scanner.nextInt();
        scanner.nextLine();
        return result;
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            System.out.println("请输入有效的数字！");
            scanner.next();
            System.out.print(prompt);
        }
        double result = scanner.nextDouble();
        scanner.nextLine();
        return result;
    }
}