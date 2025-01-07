package six.sportswears.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import six.sportswears.converter.ChatBotCartToCartReponse;
import six.sportswears.model.Cart;
import six.sportswears.model.CartItem;
import six.sportswears.model.Sportswear;
import six.sportswears.model.User;
import six.sportswears.payload.response.chatbot.CartRequest;
import six.sportswears.payload.response.chatbot.ChatBotCartResponse;
import six.sportswears.repository.CartItemRepository;
import six.sportswears.repository.SportswearRepository;
import six.sportswears.repository.UserRepository;
import six.sportswears.service.ChatBotCartService;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatBotCartServiceImpl implements ChatBotCartService {
    private static final Logger logger = Logger.getLogger(ChatBotCartServiceImpl.class.getName());
    UserRepository userRepository;
    CartItemRepository cartItemRepository;
    SportswearRepository sportswearRepository;
    ChatBotCartToCartReponse chatBotCartToCartReponse;
    @Override
    public ChatBotCartResponse getCartChatBot(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        String userName = user.getUsername();
        Cart cart = user.getCartList().get(0);
        List<CartItem> cartItemList = cartItemRepository.findAllByCart(cart);
        ChatBotCartResponse chatBotCartResponse =  chatBotCartToCartReponse.toChatBotCartReponse(cartItemList,userName);
        // Log chi tiết của ChatBotCartResponse (nếu cần thiết)
        if (chatBotCartResponse != null) {
            logger.info("Generated ChatBotCartResponse: " + chatBotCartResponse.toString());
        } else {
            logger.warning("ChatBotCartResponse is null");
        }
        return chatBotCartResponse;
    }

    @Override
    public String addToCartChatBot(CartRequest cartRequest){
        User user = userRepository.findById(cartRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + cartRequest.getUserId()));
        Sportswear sportswear = sportswearRepository.findById(Long.valueOf(cartRequest.getProductId())).orElseThrow(() -> new RuntimeException("Sportswear not found with ID: " + cartRequest.getProductId()));
        Cart cart = user.getCartList().get(0);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(cartRequest.getQuantity());
        cartItem.setSize(cartRequest.getSize());
        cartItem.setGender("");
        cartItem.setCart(cart);
        cartItem.setSportswear(sportswear);
        cartItemRepository.save(cartItem);
        return "add cart success";
    }
    //
    public Double caculateTotalCartChatBot(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: "));
        Cart cart = user.getCartList().get(0);
        List<CartItem> cartItemList = cart.getCartItemList();
        Double totalAmount = 0.0;
        for(CartItem cartItem : cartItemList){
            Long quantity = cartItem.getQuantity();
            Sportswear sportswear = cartItem.getSportswear();
            Double price = sportswear.getPrice();
            totalAmount += quantity*price;
        }
        return totalAmount;
    }
}
