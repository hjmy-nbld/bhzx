import controller.LoginController;
import util.MenuUtil;

public class Main {
    public static void main(String[] args) {
        LoginController loginController = new LoginController();
        
        while (true) {
            MenuUtil.printMainMenu();
            int choice = MenuUtil.getIntInput("");
            
            switch (choice) {
                case 1:
                    loginController.login();
                    break;
                case 2:
                    System.out.println("再见！");
                    System.exit(0);
                default:
                    System.out.println("无效选择，请重新输入");
            }
        }
    }
}