package asset;

import java.util.List;
import service.CoinService;

public class AssetService {

    AssetDAO assetDAO = new AssetDAO();
    CoinService coinService = new CoinService(); // 빗썸 API 담당

    // 전체 자산 조회 (API -> DB업데이트 -> 조회)
    public List<AssetDTO> getAllAssets() {
        // 1. DB에 있는 코인 목록을 가져옴
        List<AssetDTO> list = assetDAO.selectAll();
        // 2. 코인 하나하나 돌면서 가격을 초기화
        for (AssetDTO asset : list) {
            // 빗썸에서 가격 가져오기
            int price = coinService.getPrice(asset.getAssetId());
            if (price > 0) { // 가격이 0보다 크면 DB에 저장(UPDATE) -> 네트워크 오류 방지
                assetDAO.updatePrice(asset.getAssetId(), price);
                // 리스트에 있는 객체도 가격 업데이트
                asset.setCurrentPrice(price);
            }
        }
        return list;
    }
}