package member;

import java.util.Scanner;

import main.ControllerInterface;
import main.MainController; // MainController의 loginUser 변수를 쓰기 위해 임포트

public class MemberController implements ControllerInterface {
    Scanner sc = new Scanner(System.in);
    MemberService memberService = new MemberService();

    public void execute(Scanner sc) {
        this.sc = sc;
        boolean isStop = false;

        //비회원일때
        if (MainController.loginUser == null) {
            MemberView.menuGuest();
            int job = sc.nextInt();
            switch (job) {
                case 1 -> f_signUp();
                case 2 -> f_login();
                case 3 -> f_viewStocks();
                case 99 -> isStop = true;
                default -> System.out.println("잘못된 선택입니다.");
            }
        } else { //회원일때
            MemberView.menuMember(MainController.loginUser.getUserId());
            int job = sc.nextInt();
            switch (job) {
                case 1 -> {
                    System.out.println("==============내 정보=============");
                    MemberView.print(MainController.loginUser);
                }
                case 2 -> f_logout();
                case 3 -> f_delete();
                case 99 -> isStop = true;
                default -> System.out.println("잘못된 선택입니다.");
            }
        }
    }

    // 1. 회원가입
    private void f_signUp() {
        MemberDTO member = new MemberDTO();
        System.out.println("===============회원가입================");
        System.out.print("아이디 입력>> ");
        String userId = sc.next();

        MemberDTO checkMember = memberService.selectById(userId);
        if (checkMember != null) {
            MemberView.print("이미 존재하는 아이디입니다.");
            return;
        }

        System.out.print("비밀번호 입력>> ");
        String userPw = sc.next();

        member.setUserId(userId);
        member.setPassword(userPw);

        member.setPoints(100);       // 포인트 100점
        member.setCash(100000);     // 현금 10만원

        // 서비스 호출해서 DB에 저장
        String msg = memberService.insertService(member);
        MemberView.print(msg); // "회원가입 되었습니다" 출력됨

        if(msg.contains("회원가입 되었습니다")) {
            MemberView.print("🎉가입축하금으로 도토리 100개, 현금 10만원 지급되었습니다.");
        }
    }

    // 2. 내 정보 조회
    private void f_login() {
        System.out.println("===========내 정보 조회(접속)===========");
        System.out.print("조회(접속)할 아이디 입력>> ");
        String userId = sc.next();

        MemberDTO member = memberService.selectById(userId);
        if (member == null) {
            MemberView.print("존재하지 않는 회원입니다. 회원가입을 먼저 해주세요.");
            return;
        }

        System.out.print("비밀번호 입력>> ");
        String password = sc.next();

        if(!password.equals(member.getPassword())) {
            MemberView.print("비밀번호가 틀렸습니다.");
            return;
        }
        MainController.loginUser = member;
        MemberView.print(member);
    }

    // 3. 로그아웃
    private void f_logout() {
        MemberView.print("로그아웃 되었습니다.");
        MainController.loginUser = null;
    }

    // 4. 종목 보기
    private void f_viewStocks() {
        System.out.println("===============종목시세===============");
    }

    // 5. 회원 탈퇴
    private void f_delete() {
        System.out.println("===============회원탈퇴===============");
        System.out.print("탈퇴할 아이디 입력>> ");
        String userId = sc.next();

        MemberDTO member = memberService.selectById(userId);
        if (member == null) {
            MemberView.print("존재하지 않는 아이디입니다.");
            return;
        }

        System.out.print("비밀번호 입력>> ");
        String password = sc.next();
        if (!password.equals(member.getPassword())) {
            MemberView.print("비밀번호가 틀렸습니다.");
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
}