package member;

public class MemberView {
    public static void menuGuest() {
        System.out.println("=============[🐿️비회원 메뉴]=============");
        System.out.println("1. 회원가입  2. 로그인  99. 메인화면으로");
        System.out.println("======================================");
        System.out.print("메뉴 선택>> ");
    }

    public static void menuMember(String userId) {
        System.out.println("=============[🐿️회원 메뉴]==============");
        System.out.println("1. 내 보유 자산확인  2. 로그아웃");
        System.out.println("3. 탈퇴  99. 메인화면으로");
        System.out.println("======================================");
        System.out.print("메뉴 선택>> ");
    }

    public static void print(String msg) {
        System.out.println(msg);
    }

    public static void print(MemberDTO member) {
        if (member == null){
            System.out.println("아이디가 존재하지 않습니다.");
        } else {
            System.out.println(member);
        }
    }
}
