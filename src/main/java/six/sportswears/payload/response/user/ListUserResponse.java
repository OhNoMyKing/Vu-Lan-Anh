package six.sportswears.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import six.sportswears.payload.response._user.UserResponse;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListUserResponse {
    private List<UserResponse> userResponseList;
    private Long totalUser;
    private Long currentPage;
    private Long totalPage;
}
