package portfolio;

import java.util.List;
import service.CoinService;

public class PortfolioService {
    PortfolioDAO portfolioDAO = new PortfolioDAO();
    CoinService coinService = new CoinService();

    public List<PortfolioDTO> getMyPortfolio(String userId) {
        // 1. DB에서 코인 목록 가져오기
        List<PortfolioDTO> list = portfolioDAO.selectAll(userId);
        // 2. 계산 (시세 조회 -> 수익률 계산)
        for (PortfolioDTO dto : list) {
            // 빗썸 현재가 조회
            int currentPrice = coinService.getPrice(dto.getAssetId());
            int quantity = dto.getQuantity();
            int avgPrice = dto.getAvgPrice();

            int totalValue = currentPrice * quantity; // 평가 금액 (팔면 받을 돈)
            int buyValue = avgPrice * quantity;       // 투자 원금 (내가 쓴 돈)
            int profit = totalValue - buyValue;       // 손익 (번 돈)

            double yield = 0;
            if (buyValue > 0) yield = ((double) profit / buyValue) * 100; // 수익률
            dto.setCurrentPrice(currentPrice);
            dto.setTotalValue(totalValue);
            dto.setProfit(profit);
            dto.setYield(yield);
        }
        return list;
    }
}