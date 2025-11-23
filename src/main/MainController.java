package main;

import java.util.Scanner;

import member.MemberDTO;
import member.MemberController;
import asset.AssetController;
import trade.TradeController;
// import quiz.QuizController;

public class MainController {

    //로그인한 다람쥐 정보를 여기에 저장 (모든 컨트롤러가 공유)
    public static MemberDTO loginUser = null;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isStop = false;
        ControllerInterface controller = null;

        System.out.println("🐿️[투자초보다!람쥐 프로그램을 시작합니다]🐿️");

        while (!isStop) {
            System.out.println("============[🐿️메인 화면🐿️]=============");
            System.out.println("1. 회원 메뉴 (가입/로그인)");
            System.out.println("2. 코인 시세 조회");
            //System.out.println("4. 오늘의 퀴즈");
            if (loginUser != null) System.out.println("3. 코인 거래 메뉴 (매수/매도)");
            System.out.println("99. 프로그램 종료");
            System.out.println("======================================");
            System.out.print("메뉴 선택>> ");

            int job = sc.nextInt();
            switch (job) {
                case 1 -> controller = new MemberController();
                case 2 -> controller = new AssetController();
                case 3 -> controller = new TradeController();
                case 4 -> {
                    // controller = new QuizController();
                }
                case 99 -> {
                    isStop = true;
                    controller = null;
                    System.out.println("프로그램을 종료합니다. 성투하세요! 🐿️");
                }
                default -> {
                    System.out.println("없는 메뉴입니다. 다시 선택해 주세요.");
                    controller = null;
                }
            }
            if (controller != null) {
                controller.execute(sc);
            }
        }
        sc.close();
    }
}