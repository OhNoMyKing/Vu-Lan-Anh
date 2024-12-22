package six.sportswears.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="coupon")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="code", nullable = false, unique = true)
    private String code;
    @Column(name="active",nullable = false)
    private boolean active;
    @OneToMany(mappedBy = "coupon")
    private List<CouponCondition> couponConditions;
    @OneToMany(mappedBy = "coupon")
    private List<Orders> orders;
    @OneToMany(mappedBy = "coupon")
    private List<OrderItem> orderItems;
}
