package six.sportswears.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import six.sportswears.constant.ERole;
import six.sportswears.converter.ChatBotOrderStatusToMessage;
import six.sportswears.model.*;
import six.sportswears.payload.response._user.UserChatBot;
import six.sportswears.payload.response.chatbot.*;
import six.sportswears.payload.response.chatbot.response.*;
import six.sportswears.repository.UserRepository;
import six.sportswears.service.CartItemService;
import six.sportswears.service.CartService;
import six.sportswears.service.impl.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/chatbot")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ChatBotController {
    CouponService couponService;
    SportswearServiceImpl sportswearService;
    CartItemService cartItemService;
    CartService cartService;
    ChatBotCartServiceImpl chatBotCartService;
    UserRepository userRepository;
    ChatBotOrderServiceImpl chatBotOrderService;
    ChatBotOrderStatusToMessage chatBotOrderStatusToMessage;
    ChatBotSportswearServiceImpl chatBotSportswearService;
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
        if (teamName == null || teamName.equals("None")) {
            teamName = ""; // Giá trị mặc định nếu không có teamName
        }
        if (playerName == null || playerName.equals("None")) {
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
    @GetMapping("/check-permission/{userId}")
    public ResponseEntity<?> checkPermission(@PathVariable("userId") String userId) {
        if (userId != null) {
            Long id = Long.parseLong(userId);
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User không tồn tại."));

            // Kiểm tra quyền admin
            List<Role> roles = user.getRoleList();
            boolean isAdmin = roles.stream().anyMatch(role -> role.getName() == ERole.ROLE_ADMIN);

            if (isAdmin) {
                // Lấy danh sách tất cả người dùng và chuyển đổi thành DTO
                List<User> allUsers = userRepository.findAll();
                List<UserChatBot> userChatBots = new ArrayList<>();

                // Chuyển đổi mỗi User thành UserChatBot DTO
                for (User u : allUsers) {
                    UserChatBot userChatBot = new UserChatBot(
                            u.getUsername(),
                            u.getEmail(),
                            u.getMain_image()
                    );
                    userChatBots.add(userChatBot);
                }
                System.out.println(userChatBots);
                return ResponseEntity.ok(userChatBots); // Trả về danh sách người dùng dưới dạng DTO
            } else {
                // Nếu không phải admin, trả về thông điệp không có quyền
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("Bạn không có quyền truy cập vào danh sách người dùng.");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid User ID");
    }
    @PostMapping("/add-cart")
    public ResponseEntity<String> addProductToCart(@RequestBody CartRequest cartRequest) {
            String result = chatBotCartService.addToCartChatBot(cartRequest);
            if(result.equalsIgnoreCase("add cart success")){
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.badRequest().body(result);
            }
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkoutCart(@RequestBody CheckoutRequest request) {
        // Kiểm tra userId
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "UserId không hợp lệ. Vui lòng đăng nhập."));
        }
        User user= userRepository.findById(Long.valueOf(request.getUserId())).orElseThrow(() -> new RuntimeException("User không tồn tại."));
        Cart cart = user.getCartList().get(0);
        //kiem tra couponCode
        if (request.getDiscountCode() == null || request.getDiscountCode().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "UserId không hợp lệ. Vui lòng đăng nhập."));
        }
        Long couponId = couponService.getCouponIdByCode(request.getDiscountCode());
        // Logic xử lý tính tổng tiền
        Double totalAmount = chatBotCartService.caculateTotalCartChatBot(Long.valueOf(request.getUserId()));
        // Chuyển chuỗi thành Double trước
        if (totalAmount == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "Không thể tính tổng tiền giỏ hàng."));
        }

        // Áp dụng mã giảm giá nếu có
        totalAmount = couponService.caculateCouponValue(request.getDiscountCode(), totalAmount);

        // Nếu bạn cần tổng tiền là kiểu int (chuyển từ double)
        int finalAmount = totalAmount.intValue(); // Ép kiểu thành int

        // Tạo phản hồi JSON trả về cho Rasa
        Map<String, Object> response = new HashMap<>();
        response.put("totalAmount", finalAmount); // Trả về totalAmount dưới dạng int
        response.put("userName",user.getUsername());
        response.put("address",user.getAddress());
        response.put("phone",user.getPhone());
        response.put("cartId",cart.getId());
        response.put("couponId",couponId);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/status-count")
    public ResponseEntity<?> getOrderStatusCountByMonth(@RequestBody OrderStatusCountRequest request){
        Long userId = request.getUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại."));
        List<Role> roles = user.getRoleList();
        boolean isAdmin = roles.stream().anyMatch(role -> role.getName() == ERole.ROLE_ADMIN);
        if (!isAdmin) {
            // Nếu không phải admin, trả về lỗi 403 Forbidden hoặc 401 Unauthorized
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền truy cập vào tài nguyên này.");
        }
        List<OrderStatusCountResponse> responses = chatBotOrderService.getOrderStatusCountByMonth(request);
        responses = chatBotOrderStatusToMessage.convertToStatusMessages(responses);
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/get-all-coupon")
    public ResponseEntity<List<CouponDTO>> getCoupons(){
        List<CouponDTO> couponDTOList = couponService.getAllCoupons();
        return ResponseEntity.ok(couponDTOList);
    }
    @PostMapping("/top-sportswear")
    public ResponseEntity<?>  findTopSportswear(@RequestBody RequestTopSportswearDto request) {
        if (request.getUserId() == null || request.getUserId().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, "UserId không hợp lệ. Vui lòng đăng nhập."));
            }
        User user = userRepository.findById(Long.valueOf(request.getUserId())).orElseThrow(() -> new RuntimeException("User không tồn tại."));
        // Kiểm tra quyền admin
        List<Role> roles = user.getRoleList();
        boolean isAdmin = roles.stream().anyMatch(role -> role.getName() == ERole.ROLE_ADMIN);
        if (isAdmin) {
                //xu ly service
            return ResponseEntity.ok(chatBotSportswearService.getTopSportswear(request));
        } else {
            // Nếu không phải admin, trả về thông điệp không có quyền
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Bạn không có quyền truy cập vào danh sách người dùng.");
        }
    }
}
