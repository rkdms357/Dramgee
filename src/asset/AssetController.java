package asset;

import java.util.List;
import java.util.Scanner;
import main.ControllerInterface;

public class AssetController implements ControllerInterface {
    Scanner sc;
    AssetService assetService = new AssetService();

    @Override
    public void execute(Scanner sc) {
        this.sc = sc;
        boolean isStop = false;
        while (!isStop) {
            AssetView.menu();
            int job = sc.nextInt();
            switch (job) {
                case 1 -> printAllAssets();
                case 99 -> isStop = true;
                default -> AssetView.print("잘못된 선택입니다.");
            }
        }
    }

    private void printAllAssets() {
        AssetView.print("빗썸에서 실시간 시세를 가져오는 중입니다...🐿️");
        List<AssetDTO> list = assetService.getAllAssets();
        AssetView.printAssetList(list);
    }
}