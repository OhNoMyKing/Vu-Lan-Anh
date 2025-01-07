package six.sportswears.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import six.sportswears.payload.request.IDAndNoPage;
import six.sportswears.payload.request.SearchRequest;
import six.sportswears.payload.response.sportswear.ListSportswearResponse;
import six.sportswears.payload.response.user.ListUserReviewResponse;
import six.sportswears.payload.response.sportswear.SportswearResponse;
import six.sportswears.service.UserReviewService;
import six.sportswears.service.impl.SportswearServiceImpl;
import six.sportswears.service.impl.SportswearServiceRedisImpl;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/web")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)


public class HomeController {

    SportswearServiceImpl sportswearService;
    SportswearServiceRedisImpl sportswearServiceRedis;
    UserReviewService userReviewService;

    //    @GetMapping("/search")
//    public ResponseEntity<Object> getAllSportswear(@ModelAttribute SearchRequest searchRequest) {
//        if (searchRequest.getNoPage() == null) {
//            searchRequest.setNoPage(1L);
//        }
//        Page<SportswearResponse> sportswearDisplays = sportswearService.getAllSportswearDisplayForCustomer(searchRequest);
//        ServiceResponse<Page<SportswearResponse>> response = new ServiceResponse<>("success", sportswearDisplays);
//        return new ResponseEntity<Object>(response, HttpStatus.OK);
////        return  new ResponseEntity<>(new ListSportswearResponse(sportswearDisplays, searchRequest.getNoPage(), (long) sportswearDisplays.getTotalPages()), HttpStatus.OK);
//    }
    @GetMapping("/search")
    public ResponseEntity<ListSportswearResponse> getAllSportswear(@ModelAttribute SearchRequest searchRequest) {
        if (searchRequest.getNoPage() == null) {
            searchRequest.setNoPage(1L);
        }
        ListSportswearResponse sportswearResponseRedis = null;
        //kiem tra Redis
        try{
            sportswearResponseRedis= sportswearServiceRedis.getAllSportswearDisplayForCustomer(searchRequest);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        //truong hop data khong co trong redis
        if(sportswearResponseRedis == null){
            ListSportswearResponse sportswearResponse = sportswearService.getAllSportswearDisplayForCustomer(searchRequest);
            //xoa data redis cu
            //cap nhat data redis moi
            try{
                sportswearServiceRedis.saveAllProducts(searchRequest,sportswearResponse);
            }catch (JsonProcessingException e){
                e.printStackTrace();
            }
            return ResponseEntity.ok(sportswearResponse);
        }
//        return sportswearService.getAllSportswearDisplayForCustomer(searchRequest);
        return ResponseEntity.ok(sportswearResponseRedis);
    }

    //    @GetMapping("/searchtest")
//    public ResponseEntity<Object> getAllSportswearTest() {
//        SearchRequest searchRequest = new SearchRequest(null, 1L, null);
//        if (searchRequest.getNoPage() == null) {
//            searchRequest.setNoPage(1L);
//        }
//        Page<SportswearResponse> sportswearDisplays = sportswearService.getAllSportswearDisplayForCustomer(searchRequest);
//        ServiceResponse<Page<SportswearResponse>> response = new ServiceResponse<>("success", sportswearDisplays);
//        return new ResponseEntity<Object>(response, HttpStatus.OK);
//    }
    @GetMapping("/detail/{id}")
    public ResponseEntity<SportswearResponse> detail(@PathVariable Long id) {
        return sportswearService.getSportswearByID(id);
    }
    @PostMapping("/user-review")
    public ResponseEntity<ListUserReviewResponse> getAllUserReview(@RequestBody IDAndNoPage idAndNoPage) {
        if(idAndNoPage.getNoPage() == null) {
            idAndNoPage.setNoPage(1L);
        }
        return userReviewService.getListUserReviewResponse(idAndNoPage.getSportswearID(), idAndNoPage.getNoPage());
    }

}

