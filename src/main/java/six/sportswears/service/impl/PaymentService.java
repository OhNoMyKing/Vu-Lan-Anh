package six.sportswears.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import six.sportswears.config.VNPAYConfig;
import six.sportswears.utils.VNPayUtil;
import six.sportswears.controller.PaymentDTO;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import  six.sportswears.controller.vnDTO;
@Service
@RequiredArgsConstructor
public class PaymentService {
    private final VNPAYConfig vnPayConfig;

    public PaymentDTO.VNPayResponse createVnPayPayment(vnDTO request) {
        long amount = Integer.parseInt(request.getAmount()) * 100L;
        String bankCode = request.getBank();

        // Sử dụng trực tiếp các trường trong VNPAYConfig mà không cần gọi getter
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));

        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        // Lấy địa chỉ IP từ request
//        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        // Xây dựng query URL
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);

        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getUrl() + "?" + queryUrl;

        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }
}
