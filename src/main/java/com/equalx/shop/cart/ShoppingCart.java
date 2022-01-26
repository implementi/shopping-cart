package com.equalx.shop.cart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.Setter;

public class ShoppingCart {

    @Setter
    private BigDecimal taxRate;

    private Map<Product, Integer> productAndQuantityMap;

    public void addProduct(Product product, Integer quantity) {
        assert product != null && quantity != null && quantity > 0;
        this.createOrGetProductAndQuantityMap().merge(product, quantity, Integer::sum);
    }

    private Map<Product, Integer> createOrGetProductAndQuantityMap() {
        if (this.productAndQuantityMap == null) {
            this.productAndQuantityMap = new HashMap<>();
        }
        return this.productAndQuantityMap;
    }

    public Map<Product, Integer> getProductAndQuantityMap() {
        return Collections.unmodifiableMap(this.createOrGetProductAndQuantityMap());
    }

    private BigDecimal getItemSum() {
        BigDecimal sum = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> item : this.createOrGetProductAndQuantityMap().entrySet()) {
            sum = sum.add(item.getKey().getUnitPrice().multiply(new BigDecimal(item.getValue())));
        }
        return sum;
    }

    public BigDecimal getTotalPrice() {
        BigDecimal sum = getItemSum();
        if (this.taxRate != null) {
            sum = sum.add(sum.multiply(taxRate.divide(BigDecimal.valueOf(100))));
        }
        return sum.setScale(3, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalTax() {
        BigDecimal value = BigDecimal.ZERO;
        if (this.taxRate != null && this.taxRate.compareTo(BigDecimal.ZERO) > 0) {
            value = this.getItemSum()
                    .multiply(this.taxRate.divide(BigDecimal.valueOf(100)))
                    .setScale(3, RoundingMode.HALF_UP)
                    .setScale(2, RoundingMode.HALF_UP);
        }
        return value;
    }

}
