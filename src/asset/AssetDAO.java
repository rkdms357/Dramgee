package asset;

import java.sql.*;
import java.util.*;
import util.DBUtil;

public class AssetDAO {
    // 1. 전체 목록 조회
    public List<AssetDTO> selectAll() {
        List<AssetDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String sql = "select * from asset order by name";
        try {
            conn = DBUtil.dbconnect();
            st = conn.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                AssetDTO asset = new AssetDTO();
                asset.setAssetId(rs.getString("asset_id"));
                asset.setSymbol(rs.getString("symbol"));
                asset.setName(rs.getString("name"));
                asset.setCurrentPrice(rs.getInt("current_price"));
                list.add(asset);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, st, rs);
        }
        return list;
    }

    // 2. 가격 업데이트
    public int updatePrice(String assetId, int price) {
        Connection conn = null;
        PreparedStatement st = null;
        int result = 0;
        String sql = "update asset set current_price = ? where asset_id = ?";
        try {
            conn = DBUtil.dbconnect();
            st = conn.prepareStatement(sql);
            st.setInt(1, price);
            st.setString(2, assetId);
            result = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, st, null);
        }
        return result;
    }
}