package portfolio;

import java.util.*;
import main.*;

public class PortfolioController implements ControllerInterface {
    Scanner sc = new Scanner(System.in);
    PortfolioService portfolioService = new PortfolioService();

    @Override
    public void execute(Scanner sc) {
        this.sc = sc;
        printMyPortfolio();
    }

    public void printMyPortfolio() {
        String userId = MainController.loginUser.getUserId();
        System.out.println("자산 정보를 불러오는 중입니다...🐿");
        List<PortfolioDTO> list = portfolioService.getMyPortfolio(userId);
        PortfolioView.printPortfolio(list);
    }
}