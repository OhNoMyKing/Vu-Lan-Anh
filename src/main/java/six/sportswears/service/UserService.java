package six.sportswears.service;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import six.sportswears.payload.request.ReviewRequest;
import six.sportswears.payload.request.UserRequest;
import six.sportswears.payload.response.user.ListUserResponse;
import six.sportswears.payload.response.MessageResponse;
import six.sportswears.payload.response._user.UserResponse;

import java.util.List;

@Service
@Transactional
public interface UserService {
    ResponseEntity<ListUserResponse> getALlUserForAdmin(String key, Long noPage);
    ResponseEntity<UserResponse> getProfileUser();
    ResponseEntity<MessageResponse> feedBack(List<ReviewRequest> reviewRequestList);
    ResponseEntity<MessageResponse> changeProfile(UserRequest userRequest);
    ResponseEntity<MessageResponse> updateUser(UserResponse userResponse);
}
