package six.sportswears.converter;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import six.sportswears.model.Cart;
import six.sportswears.model.CartItem;
import six.sportswears.payload.response.CartItemResponse;
import six.sportswears.payload.response.CartResponse;
import six.sportswears.payload.response.ChatBotCartResponse;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatBotCartToCartReponse {
    ModelMapper modelMapper;
    ChatBotCartItemToCartItemResponse cartItemToCartItemReponse;
    //
    public ChatBotCartResponse toChatBotCartReponse(List<CartItem> cartItemList,String userName){
        Cart cart = new Cart();
        cart.setCartItemList(cartItemList);
        //
        List<CartItemResponse> cartItemResponseList = new ArrayList<>();
        for(CartItem x : cartItemList){
            cartItemResponseList.add(cartItemToCartItemReponse.ChatBotToCartItemReponse(x));
        }
        ChatBotCartResponse chatBotCartResponse = new ChatBotCartResponse();
        chatBotCartResponse.setCartItemResponses(cartItemResponseList);
        chatBotCartResponse.setUserName(userName);
        chatBotCartResponse.setTotalQuantity(cart.getTotalQuantity());
        chatBotCartResponse.setTotalAmount(cart.getCartAmount());
        return chatBotCartResponse;
    }
}
