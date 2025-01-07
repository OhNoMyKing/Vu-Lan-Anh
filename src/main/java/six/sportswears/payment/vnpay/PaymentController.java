package six.sportswears.payment.vnpay;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import six.sportswears.payload.response.vnpay.ResponseObject;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("money")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    SimpMessagingTemplate messagingTemplate;
    private final PaymentService paymentService;
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request));
    }
    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            messagingTemplate.convertAndSend("/vnp","success");
            return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
        } else {
            messagingTemplate.convertAndSend("/vnp","failed");
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }
//@GetMapping("/vn-pay-callback")
//public ResponseEntity<Void> payCallbackHandler(HttpServletRequest request) {
//    String status = request.getParameter("vnp_ResponseCode");
//    String orderId = request.getParameter("vnp_TxnRef");
//    String redirectUrl = "http://frontend.com/payment/result"; // Thay URL này bằng địa chỉ frontend của bạn
//
//    if ("00".equals(status)) {
//        // Giao dịch thành công
//        redirectUrl += "?status=success&orderId=" + orderId;
//    } else {
//        // Giao dịch thất bại
//        redirectUrl += "?status=failure&orderId=" + orderId;
//    }
//
//    // Chuyển hướng người dùng
//    return ResponseEntity.status(HttpStatus.FOUND)
//            .header("Location", redirectUrl)
//            .build();
// }
}