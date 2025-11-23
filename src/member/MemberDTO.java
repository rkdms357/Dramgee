package member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class MemberDTO {

    @NonNull String userId;
    @NonNull String password;
    //int points;
    int cash;

    @Override
    public String toString() {
        return "보유 투자금=" + cash;
    }
}
