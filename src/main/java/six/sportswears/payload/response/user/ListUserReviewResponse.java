package six.sportswears.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import six.sportswears.payload.response._user.UserReviewResponse;

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
