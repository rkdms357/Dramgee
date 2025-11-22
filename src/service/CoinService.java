package service;

import asset.AssetView;

import java.net.URL;
import java.util.Scanner;

public class CoinService {

    private static final String BITHUMB_URL = "https://api.bithumb.com/v1/ticker?markets=";

    public int getPrice(String marketCode) {
        // 예: marketCode가 "KRW-BTC"
        String urlStr = BITHUMB_URL + marketCode;

        try {
            // 1. 연결, 데이터 읽기
            URL url = new URL(urlStr);
            Scanner sc = new Scanner(url.openStream());
            String json = sc.nextLine(); // 한 줄 읽어옴

            // 2. 가격만 뽑아내기
            return parsePrice(json);
        } catch (Exception e) {
            AssetView.print("시세 조회 실패");
            return 0;
        }
    }

    private int parsePrice(String json) {
        try {
            // 빗썸에서 가져온 JSON 형태: "trade_price": 98000000
            // 1. "trade_price": 를 기준으로 반으로 쪼개기
            String[] part1 = json.split("\"trade_price\":");

            // 2. 뒷부분(98000000, ...)을 다시 쉼표(,)로 쪼개기
            String[] part2 = part1[1].split(",");

            // 3. 맨 앞에 있는 게 가격
            String priceStr = part2[0];

            // 4. 소수점(.00) 떼고 정수로 변환
            return (int)Double.parseDouble(priceStr);
        } catch (Exception e) {
            return 0;
        }
    }
}