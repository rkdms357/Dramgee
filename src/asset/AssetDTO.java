package asset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssetDTO {

    private String assetId;
    private String symbol;
    private String name;
    private int currentPrice;
}
