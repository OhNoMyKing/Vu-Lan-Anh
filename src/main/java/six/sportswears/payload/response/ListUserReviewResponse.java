package six.sportswears.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListUserReviewResponse {
    private List<UserReviewResponse> userReviewResponseList;
    private Long currentPage;
    private Long totalPage;

}
