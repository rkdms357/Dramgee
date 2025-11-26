package portfolio;

import java.util.List;
import main.MainController;

public class PortfolioView {

    public static void printPortfolio(List<PortfolioDTO> list) {
        System.out.println("============내 보유 자산 현황=============");

        if (list == null || list.isEmpty()) {
            System.out.println("보유 중인 코인이 없습니다. 거래소에서 매수해보세요");
            System.out.printf("보유 투자금: %,d 원\n", MainController.loginUser.getCash());
            return;
        }

        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("%8s | %6s | %10s | %10s | %10s | %8s\n",
                "코인명", "수량", "평단가", "현재가", "평가손익", "수익률");
        System.out.println("--------------------------------------------------------------------------------");

        int totalProfit = 0;
        int totalCoinValue = 0;

        for (PortfolioDTO p : list) {
            System.out.printf("%8s | %6d | %,10d | %,10d | %,11d | %12f%%\n",
                    p.getName(),
                    p.getQuantity(),
                    p.getAvgPrice(),
                    p.getCurrentPrice(),
                    p.getProfit(),
                    p.getYield()
            );
            totalProfit += p.getProfit();
            totalCoinValue += p.getTotalValue();
        }

        int myCash = MainController.loginUser.getCash();
        int totalAsset = myCash + totalCoinValue;

        System.out.println("--------------------------------------------------------------------------------");
        System.out.printf("보유 투자금 : %,15d 원\n", myCash);
        System.out.printf("코인 평가액 : %,15d 원\n", totalCoinValue);
        System.out.printf("총 평가손익 : %,15d 원\n", totalProfit);
        System.out.println("================================================================================");
        System.out.printf("총 자산 (투자금+코인) : %,d 원\n", totalAsset);
        System.out.println("================================================================================");
    }
}