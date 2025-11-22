package member;

import java.sql.*;
import util.DBUtil;

public class MemberDAO {
    // 1. 내 정보 조회 (로그인 겸용)
    public MemberDTO selectById(String userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "select * from users where user_id = ?";
        MemberDTO member = null;
        try {
            conn = DBUtil.dbconnect();
            st = conn.prepareStatement(sql);
            st.setString(1, userId);
            rs = st.executeQuery();
            while (rs.next()) {
                member = new MemberDTO();
                member.setUserId(rs.getString("user_id"));
                member.setPassword(rs.getString("password"));
                member.setPoints(rs.getInt("points"));
                member.setCash(rs.getInt("cash"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, st, rs);
        }
        return member;
    }

    // 2. 회원 가입
    public String insert(MemberDTO member) {
        String msg = null;
        Connection conn = null;
        PreparedStatement st = null;
        String sql = "insert into users values (?, ?, ?, ?)";
        try {
            conn = DBUtil.dbconnect();
            st = conn.prepareStatement(sql);
            st.setString(1, member.getUserId());
            st.setString(2, member.getPassword());
            st.setInt(3, member.getPoints());
            st.setInt(4, member.getCash());
            int result = st.executeUpdate();
            if (result > 0) {
                msg = "회원가입 되었습니다.";
            } else {
                msg = "회원가입이 실패하였습니다."; // DB반영 안됨
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, st, null);
        }
        return msg;
    }

    // 3. 회원 탈퇴
    public String delete(String userId) {
        String msg = null;
        Connection conn = null;
        PreparedStatement st = null;
        String sql = "delete from users where user_id = ?";
        try {
            conn = DBUtil.dbconnect();
            st = conn.prepareStatement(sql);
            st.setString(1, userId);
            int result = st.executeUpdate();
            if (result > 0) {
                msg = "탈퇴가 완료되었습니다.";
            } else {
                msg = "탈퇴가 실패하였습니다."; // DB반영 안됨
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, st, null);
        }
        return msg;
    }
}