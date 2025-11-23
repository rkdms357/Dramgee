package trade;

public class TradeView {
    public static void menu() {
        System.out.println("==========[🐿️코인 거래 메뉴🐿️]===========");
        System.out.println("1. 매수하기(구매)  2. 매도하기(판매)");
        System.out.println("3. 거래 내역 조회 99. 메인화면으로");
        System.out.println("======================================");
        System.out.print("메뉴 선택>> ");
    }

    public static void print(String msg) {
        System.out.println(msg);
    }
}