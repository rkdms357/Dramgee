package asset;

import java.util.List;

public class AssetView {
    public static void print(String msg) {
        System.out.println(msg);
    }

    public static void menu() {
        System.out.println("=============코인 시세 조회==============");
    }

    public static void printAssetList(List<AssetDTO> list) {
        System.out.println("--------------------------------------");
        System.out.printf(" %-7s | %-6s | %13s \n", "코인명", "약어", "현재가(KRW)");
        System.out.println("--------------------------------------");
        for (AssetDTO asset : list) {
            System.out.printf(" %-7s | %-6s | %,13d원 \n",
                    asset.getName(),
                    asset.getSymbol(),
                    asset.getCurrentPrice()
            );
        }
        System.out.println("--------------------------------------");
    }
}