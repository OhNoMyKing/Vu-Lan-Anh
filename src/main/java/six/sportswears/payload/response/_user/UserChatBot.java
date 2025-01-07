package six.sportswears.payload.response._user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserChatBot {
    private String username;
    private String email;
    private String mainImage;
}
