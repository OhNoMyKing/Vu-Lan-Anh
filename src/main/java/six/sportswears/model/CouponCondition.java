package six.sportswears.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="coupon_conditions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CouponCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="coupon_id",nullable = false)
    private  Coupon coupon;
    @Column(name="attribute",nullable = false)
    private  String attribute;
    @Column(name="operator",nullable = false)
    private String operator;
    @Column(name="value",nullable = false)
    private  String value;
    @Column(name="discount_amount",nullable = false)
    private BigDecimal discountAmount;
}
