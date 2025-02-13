package six.sportswears.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import six.sportswears.payload.request.*;
import six.sportswears.payload.response.*;
import six.sportswears.payload.response.sportswear.ListSportswearResponse;
import six.sportswears.payload.response.sportswear.ListSportswearRevenueResponseInAMonth;
import six.sportswears.payload.response.sportswear.SportswearResponse;
import six.sportswears.payload.response.sportswear.SportswearRevenueResponse;
import six.sportswears.payload.response.user.ListUserResponse;
import six.sportswears.service.OrdersService;
import six.sportswears.service.SportswearService;
import six.sportswears.service.SportswearServiceRedis;
import six.sportswears.service.UserService;
import six.sportswears.service.impl.SportswearServiceImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    SportswearServiceRedis sportswearServiceRedis;
    SportswearService sportswearService;
    OrdersService ordersService;
    UserService userService;
    SportswearServiceImpl sportswearServiceImpl;
    SimpMessagingTemplate messagingTemplate;
    @PostMapping("/add-product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> addProductByAdmin(@Valid @RequestBody CreateSportswearRequest createSportswearRequest) {
        if(createSportswearRequest.getQuantity() == null) {
            createSportswearRequest.setQuantity(1L);
        }
        ResponseEntity<MessageResponse> response =  sportswearService.createSportswearByAdmin(createSportswearRequest);
        ResponseEntity<SportswearResponse> sportswearUpdate = sportswearServiceImpl.getSportswearByID(createSportswearRequest.getId());
        messagingTemplate.convertAndSend("/topic/sportswearUpdated",sportswearUpdate);
        sportswearServiceRedis.clear();
//        messagingTemplate.convertAndSend("/topic/products","Product updated");
        return  response;
    }

//   0 @PostMapping("/update-product")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<MessageResponse> updateProductByAdmin(@Valid @RequestBody AddSportswearRequest addSportswearRequest) {
//        return sportswearService.addOrUpdateSportswearByAdmin(addSportswearRequest);
//    }
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListSportswearResponse> getAllSportswear(@ModelAttribute SearchRequest searchRequest) {
        if (searchRequest.getNoPage() == null) {
            searchRequest.setNoPage(1L);
        }
        return sportswearService.getAllSportswearDisplayForAdmin(searchRequest);
    }
//
//
    @GetMapping("/delete-product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteProductByAdmin(@Valid @PathVariable Long id) {
        return sportswearService.deleteSportswearByID(id);
    }

    @PostMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListOrderResponse> getAllOrderForAdminByStatus(@RequestBody StatusAndNoPage statusAndNoPage) {
        if(statusAndNoPage.getNoPage() == null) {
            statusAndNoPage.setNoPage(1L);
        }
        return ordersService.getAllOrderResponseForAdminByStatus(statusAndNoPage.getStatus(), statusAndNoPage.getNoPage());
    }
    @PostMapping("/change-status-orders")
    @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<MessageResponse> changeOrderStatus(@RequestBody StatusAndNoPage statusAndNoPage) {
        // su dung noPage nhu id
        return ordersService.changeOrderStatus(statusAndNoPage.getStatus(), statusAndNoPage.getNoPage());
    }

    @GetMapping("/get-all-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListUserResponse> getALlUserForAdmin(@ModelAttribute SearchRequest searchRequest) {
        if(searchRequest.getNoPage() == null) {
            searchRequest.setNoPage(1L);
        }
        return userService.getALlUserForAdmin(searchRequest.getKey(),searchRequest.getNoPage());
    }
    @GetMapping("/test")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SportswearRevenueResponse> test() {

        return sportswearService.getSportswearRevenueResponseByIDAndTime(34L, "11");
    }
    @GetMapping("/test/{sportswearID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListSportswearRevenueResponseInAMonth> test2(@PathVariable Long sportswearID) {

        return sportswearService.getListSportswearRevenueResponseInAMonth(sportswearID, 2024);
    }

    @GetMapping("/chart")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListSportswearRevenueResponseInAMonth> test1() {

        return sportswearService.getAllSportswearRevenueResponse(2024);
    }
    @GetMapping("/chart-admin/{month}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ListLabelAndValue> test3(@PathVariable String month) {

        return sportswearService.getListLabelAndValue(month, 2024);
    }

    @PostMapping("/update-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> updateUser(@RequestBody UserRequest userRequest) {
        // su dung noPage nhu id
        return userService.changeProfile(userRequest);
    }
}
