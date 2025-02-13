package six.sportswears.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import six.sportswears.utils.VNPayUtil;

import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Configuration
@ConfigurationProperties(prefix = "payment.vn-pay")
public class VNPAYConfig {
    private final String url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    private final String returnUrl = "http://localhost:8080/money/vn-pay-callback";
    private final String tmnCode = "AD6VIAPZ";
    private final String secretKey = "MLTEYR74L68BLYGX0HBAGA5OXKPH2HQ5";
    private final String version = "2.1.0";
    private final String command = "pay";
    private final String orderType = "other";



    public Map<String, String> getVNPayConfig() {
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", this.version);
        vnpParamsMap.put("vnp_Command", this.command);
        vnpParamsMap.put("vnp_TmnCode", this.tmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef", VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" + VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.returnUrl);

        // Tạo thời gian hiện tại và thời gian hết hạn
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);  // Thêm 15 phút cho thời gian hết hạn
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);

        return vnpParamsMap;
    }
}
