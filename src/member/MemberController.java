package member;

import java.util.Scanner;
import main.*;
import portfolio.PortfolioController;

public class MemberController implements ControllerInterface {
    Scanner sc = new Scanner(System.in);
    MemberService memberService = new MemberService();

    public void execute(Scanner sc) {
        boolean isStop = false;
        while (!isStop) {
            this.sc = sc;
            //비회원일때
            if (MainController.loginUser == null) {
                MemberView.menuGuest();
                int job = sc.nextInt();
                switch (job) {
                    case 1 -> f_signUp();
                    case 2 -> f_login();
                    case 99 -> {isStop = true;}
                    default -> System.out.println("잘못된 선택입니다.");
                }
            } else { //회원일때
                MemberView.menuMember(MainController.loginUser.getUserId());
                int job = sc.nextInt();
                switch (job) {
                    case 1 -> f_myPortfolio();
                    case 2 -> f_logout();
                    case 3 -> f_delete();
                    case 99 -> {isStop = true;}
                    default -> System.out.println("잘못된 선택입니다.");
                }
            }
        }
    }

    // 1. 회원가입
    private void f_signUp() {
        MemberDTO member = new MemberDTO();
        System.out.println("================회원가입================");
        String userId = null;
        while(true) {
            System.out.print("아이디 입력 (99. 이전으로)>> ");
            userId = sc.next();

            if(userId.equals("99")) return;

            MemberDTO checkMember = memberService.selectById(userId);
            if (checkMember == null) break;
            System.out.println("이미 존재하는 아이디입니다. 다시 입력해주세요.");
        }

        System.out.print("비밀번호 입력>> ");
        String userPw = sc.next();

        member.setUserId(userId);
        member.setPassword(userPw);

        member.setCash(1000000);     // 현금 100만원

        // 서비스 호출해서 DB에 저장
        String msg = memberService.insertService(member);
        MemberView.print(msg); // "회원가입 되었습니다" 출력됨

        if(msg.contains("회원가입 되었습니다")) {
            MemberView.print("🎉가입축하금으로 투자금 100만원 지급되었습니다.");
        }
    }

    // 2. 로그인
    private void f_login() {
        System.out.println("=================로그인=================");
        String userId = null;
        MemberDTO member = null;
        while(true) {
            System.out.print("아이디 입력 (99. 이전으로)>> ");
            userId = sc.next();

            if(userId.equals("99")) return;

            member = memberService.selectById(userId);
            if (member != null) break;
            System.out.println("존재하지 않는 아이디입니다. 다시 입력해주세요.");
        }

        while(true) {
            System.out.print("비밀번호 입력 (99. 이전으로)>> ");
            String password = sc.next();

            if(password.equals("99")) return;
            if(password.equals(member.getPassword())) {
                break;
            }
            System.out.println("비밀번호가 틀렸습니다. 다시 입력해주세요.");
        }

        MainController.loginUser = member;
        MemberView.print(member);
        System.out.println("👋안녕하세요, " + member.getUserId() + "님");
    }

    // 3. 로그아웃
    private void f_logout() {
        MemberView.print("로그아웃 되었습니다.");
        MainController.loginUser = null;
    }

    // 4. 회원 탈퇴
    private void f_delete() {
        System.out.println("================회원탈퇴================");
        String userId = MainController.loginUser.getUserId();

        // (로그인이 풀렸거나 할 때)
        MemberDTO member = memberService.selectById(userId);
        if (member == null) {
            MemberView.print("회원 정보를 찾을 수 없습니다.");
            return;
        }

        System.out.print("본인 확인을 위해 비밀번호 입력>> ");
        String password = sc.next();
        if (!password.equals(member.getPassword())) {
            MemberView.print("비밀번호가 틀렸습니다.");
            MemberView.print("탈퇴를 취소합니다...");
            return;
        }

        String msg = memberService.deleteService(userId);
        MemberView.print(msg);

        // 만약 로그인 중인 아이디를 삭제했다면, 로그아웃 처리
        if(MainController.loginUser != null && MainController.loginUser.getUserId().equals(userId)) {
            MainController.loginUser = null;
            System.out.println("로그아웃 되었습니다.");
        }
    }

    // 5. 내 보유 자산 확인
    private void f_myPortfolio() {
        new PortfolioController().printMyPortfolio();
    }
}