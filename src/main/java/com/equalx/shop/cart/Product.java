package com.equalx.shop.cart;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Product {
    
    private final String name;
    private final BigDecimal unitPrice;

}
