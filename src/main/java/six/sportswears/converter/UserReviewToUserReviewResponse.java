package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.UserReview;
import six.sportswears.payload.response._user.UserReviewResponse;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserReviewToUserReviewResponse {
    ModelMapper modelMapper;
    public long getDaysBetween(Date x) {
        // Chuyển đổi Date thành LocalDate
        LocalDate dateX = x.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();

        // Tính số ngày giữa hai ngày
        return ChronoUnit.DAYS.between(dateX, currentDate);


    }
    public UserReviewResponse toUserReviewResponse(UserReview userReview) {
        UserReviewResponse userReviewResponse = new UserReviewResponse();
        userReviewResponse.setUserName(userReview.getUser().getFirstName() +"_" + userReview.getUser().getLastName());
        userReviewResponse.setMain_image(userReview.getUser().getMain_image());
        userReviewResponse.setTime(getDaysBetween(userReview.getTimeCreated()));
        userReviewResponse.setRate(userReview.getRating_value());
        userReviewResponse.setComment(userReview.getComment());
        return userReviewResponse;
    }

}
