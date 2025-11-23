package trade;

import java.util.*;
import asset.*;
import main.*;
import portfolio.*;

public class TradeController implements ControllerInterface {
    Scanner sc = new Scanner(System.in);
    TradeService tradeService = new TradeService();
    AssetService assetService = new AssetService();
    PortfolioService portfolioService = new PortfolioService();

    @Override
    public void execute(Scanner sc) {
        this.sc = sc;
        boolean isStop = false;
        if (MainController.loginUser == null) {
            TradeView.print("로그인이 필요한 서비스입니다.");
            return;
        }
        while (!isStop) {
            TradeView.menu();
            int job = sc.nextInt();
            switch (job) {
                case 1 -> f_buy();
                case 2 -> f_sell();
                case 3 -> f_history();
                case 99 -> isStop = true;
                default -> TradeView.print("잘못된 선택입니다.");
            }
        }
    }

    // 1. 매수하기
    private void f_buy() {
        System.out.println("=============매수(구매)하기==============");
        System.out.println("내 잔고: " + MainController.loginUser.getCash() + "원");
        coinPricePrint();

        String symbol = null;
        int currentPrice = 0;
        while (true) {
            System.out.print("매수(구매)할 코인 약어 입력 (99. 이전으로)>> ");
            symbol = sc.next();
            if(symbol.equals("99")) {
                TradeView.print("매수를 취소합니다.");
                return;
            }

            String assetId = "KRW-" + symbol.toUpperCase();
            currentPrice = new service.CoinService().getPrice(assetId);
            if(currentPrice > 0) break;

            TradeView.print("존재하지 않는 코인입니다. 다시 입력해 주세요.");
        }

        System.out.print("매수(구매)할 개수 입력>> ");
        int count = 0;
        try {
            count = sc.nextInt();
        } catch (Exception e) {
            TradeView.print("숫자만 입력해야 합니다.");
            TradeView.print("매수를 취소합니다.");
            sc.nextLine();
            return;
        }

        TradeView.print("거래 처리 중입니다...🐿");
        String msg = tradeService.buyCoin(symbol, count);
        TradeView.print(msg);
    }
    // 2. 매도하기
    private void f_sell() {
        System.out.println("=============매도(판매)하기==============");
        String userId = MainController.loginUser.getUserId();
        List<PortfolioDTO> myList = portfolioService.getMyPortfolio(userId);

        if (myList == null || myList.isEmpty()) {
            TradeView.print("보유중인 코인이 없습니다!");
            TradeView.print("매수(구매)를 먼저 진행해주세요.");
            return;
        }

        new PortfolioController().printMyPortfolio();
        coinPricePrint();

        String symbol = null;
        int myQuantity = 0;
        while (true) {
            System.out.print("매도(판매)할 코인 약어 입력 (99. 이전으로)>> ");
            symbol = sc.next();
            if(symbol.equals("99")) {
                TradeView.print("매도를 취소합니다.");
                return;
            }

            String assetId = "KRW-" + symbol.toUpperCase();
            myQuantity = tradeService.getQuantity(userId, assetId);

            if (myQuantity > 0) {
                break;
            } else {
                System.out.println("보유하지 않은 코인입니다. 다시 입력해 주세요.");
            }
        }

        System.out.print("매도(판매)할 개수 입력>> ");
        int count = 0;
        try {
            count = sc.nextInt();
        } catch (Exception e) {
            TradeView.print("숫자만 입력해야 합니다.");
            TradeView.print("매도를 취소합니다.");
            sc.nextLine();
            return;
        }

        TradeView.print("거래 처리 중입니다...🐿");
        String msg = tradeService.sellCoin(symbol, count);
        TradeView.print(msg);

        System.out.println("내 잔고: " + MainController.loginUser.getCash() + "원");
    }

    private void coinPricePrint() {
        AssetView.print("빗썸에서 실시간 시세를 가져오는 중입니다...🐿️");
        List<AssetDTO> list = assetService.getAllAssets();
        AssetView.printAssetList(list);
    }

    // 매수/매도 기록 보기
    private void f_history() {
        System.out.println("=============거래 내역 조회==============");
        String userId = MainController.loginUser.getUserId();
        List<TradeDTO> list = tradeService.getTradeHistory(userId);

        if(list.isEmpty()) {
            System.out.println("아직 거래 기록이 없습니다. 매수를 먼저 진행해 주세요.");
            return;
        }

        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-12s %-6s %-10s %9s %12s\n", "날짜", "구분", "코인", "수량", "단가");
        System.out.println("-----------------------------------------------------------");

        for (TradeDTO t : list) {
            String coinName = t.getAssetId().replace("KRW-", "");
            String type = t.getTradeType().trim().equals("BUY") ? "🔴매수" : "🔵매도";
            System.out.printf("%s  %-6s %-10s %,10d개 %,12d원\n",
                    t.getTradeDate(),      // 날짜
                    type,                  // 매수/매도
                    coinName,              // BTC
                    t.getTradeQuantity(),  // 수량
                    t.getTradePrice()      // 가격
            );
        }
        System.out.println("-----------------------------------------------------------");
    }
}