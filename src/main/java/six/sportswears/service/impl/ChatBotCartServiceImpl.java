package six.sportswears.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import six.sportswears.converter.ChatBotCartToCartReponse;
import six.sportswears.model.Cart;
import six.sportswears.model.CartItem;
import six.sportswears.model.User;
import six.sportswears.payload.response.ChatBotCartResponse;
import six.sportswears.repository.CartItemRepository;
import six.sportswears.repository.UserRepository;
import six.sportswears.service.ChatBotCartService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatBotCartServiceImpl implements ChatBotCartService {
    UserRepository userRepository;
    CartItemRepository cartItemRepository;
    ChatBotCartToCartReponse chatBotCartToCartReponse;
    @Override
    public ChatBotCartResponse getCartChatBot(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        String userName = user.getUsername();
        Cart cart = user.getCartList().get(0);
        List<CartItem> cartItemList = cartItemRepository.findAllByCart(cart);
        ChatBotCartResponse chatBotCartResponse =  chatBotCartToCartReponse.toChatBotCartReponse(cartItemList,userName);
        return chatBotCartResponse;
    }
}
