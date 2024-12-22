package six.sportswears.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import six.sportswears.model.Sportswear;
import six.sportswears.payload.response.ChatBotCartResponse;
import six.sportswears.payload.response.SportswearCR7;
import six.sportswears.payload.response.SportswearResponseCountAndDetails;
import six.sportswears.payload.response.SportswearResponseDetails;
import six.sportswears.service.CartItemService;
import six.sportswears.service.CartService;
import six.sportswears.service.impl.ChatBotCartServiceImpl;
import six.sportswears.service.impl.SportswearServiceImpl;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ChatBotController {
    SportswearServiceImpl sportswearService;
    CartItemService cartItemService;
    CartService cartService;
    ChatBotCartServiceImpl chatBotCartService;
    @GetMapping("/count-and-details")
    public SportswearResponseCountAndDetails getProductCountAndDetails(){
        Long count = sportswearService.getProductsCount();
        List<SportswearResponseDetails> products = sportswearService.getRemainingProducts();
        SportswearResponseCountAndDetails sportswearResponseCountAndDetails = new SportswearResponseCountAndDetails();
        sportswearResponseCountAndDetails.setCount(count);
        sportswearResponseCountAndDetails.setProducts(products);
        return sportswearResponseCountAndDetails;
    }
    @GetMapping("/shirt")
    public List<SportswearCR7> getProductByInformation(@RequestParam(value = "shirtNumber", required = false) Long shirtNumber,
                                                       @RequestParam(value = "teamName", required = false) String teamName,
                                                       @RequestParam(value = "playerName", required = false) String playerName) {

        // Kiểm tra xem có tham số nào được truyền vào không, nếu không thì đặt giá trị mặc định
        if (shirtNumber == null) {
            shirtNumber = 0L; // Giá trị mặc định nếu không có shirtNumber
        }
        if (teamName == null || teamName.equals("default")) {
            teamName = ""; // Giá trị mặc định nếu không có teamName
        }
        if (playerName == null || playerName.equals("default")) {
            playerName = ""; // Giá trị mặc định nếu không có playerName
        }

        // Tìm kiếm sản phẩm dựa trên các tham số
        List<Sportswear> sportswearList = sportswearService.findProductByInfo(shirtNumber, teamName, playerName);

        // Chuyển đổi kết quả sang danh sách SportswearCR7 để trả về cho người dùng
        List<SportswearCR7> sportswearCR7List = new ArrayList<>();
        for (Sportswear s : sportswearList) {
            SportswearCR7 sportswearCR7 = new SportswearCR7();
            sportswearCR7.setId(s.getId());
            sportswearCR7.setMainImage(s.getMain_image());
            sportswearCR7List.add(sportswearCR7);
        }
        return sportswearCR7List;
    }
    @GetMapping("/{userId}")
    public ChatBotCartResponse getCartForChatBot(@PathVariable("userId") String userId){
        if(userId == null){
            return null;
        }
        Long id = Long.parseLong(userId);
        return chatBotCartService.getCartChatBot(id);
    }
}
