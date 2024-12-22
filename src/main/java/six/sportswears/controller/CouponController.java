package six.sportswears.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import six.sportswears.payload.response.CouponCaculationResponse;
import six.sportswears.service.impl.CouponService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)


public class CouponController {
    private final CouponService couponService;
    @GetMapping("/calculate")
    public ResponseEntity<CouponCaculationResponse> calculateCouponValue(
        @RequestParam("couponCode") String couponCode,
        @RequestParam("totalAmount") double totalAmount){
        try{
            double finalAmount = couponService.caculateCouponValue(couponCode,totalAmount);
            CouponCaculationResponse couponCaculationResponse = CouponCaculationResponse.builder()
                    .result(finalAmount)
                    .errorMessage("")
                    .build();
            return ResponseEntity.ok(couponCaculationResponse);
        }catch (Exception e){
              return ResponseEntity.badRequest().body(CouponCaculationResponse.builder()
                      .result(totalAmount)
                      .errorMessage(e.getMessage())
                      .build());
        }
    }
}
