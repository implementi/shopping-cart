package com.equalx.shop.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ShoppingCartTest {

    @Test
    void testAddOneItem() {
        ShoppingCart cart = new ShoppingCart();
        Product product1 = new Product("Dove Soap", new BigDecimal(39.99F));

        cart.addProduct(product1, 1);

        Map<Product, Integer> prodQtyMap = cart.getProductAndQuantityMap();
        assertNotNull(prodQtyMap);
        assertEquals(1, prodQtyMap.size());
        assertTrue(prodQtyMap.containsKey(product1));

    }

    @Test
    void testAddMultipleItems() {
        ShoppingCart cart = new ShoppingCart();
        Product product1 = new Product("Dove Soap", new BigDecimal(39.99F));
        Product product1Copy = new Product("Dove Soap", new BigDecimal(39.99F));

        cart.addProduct(product1, 5);
        cart.addProduct(product1Copy, 3);

        Map<Product, Integer> prodQtyMap = cart.getProductAndQuantityMap();
        assertNotNull(prodQtyMap);
        assertEquals(1, prodQtyMap.size());

        assertTrue(prodQtyMap.containsKey(product1));
        assertEquals(8, prodQtyMap.get(product1));

        BigDecimal totalPrice = cart.getTotalPrice();
        assertEquals(BigDecimal.valueOf(319.92).setScale(2), totalPrice);
    }

    @Test
    void testTaxAndPriceCalculation() {
        ShoppingCart cart = new ShoppingCart();
        Product product1 = new Product("Dove Soap", new BigDecimal(39.99F));
        Product product2 = new Product("Axe Deo", new BigDecimal(99.99F));

        cart.addProduct(product1, 2);
        cart.addProduct(product2, 2);
        cart.setTaxRate(BigDecimal.valueOf(12.5F));

        Map<Product, Integer> productQtyMap = cart.getProductAndQuantityMap();
        assertNotNull(productQtyMap);
        assertEquals(2, productQtyMap.size());

        assertTrue(productQtyMap.containsKey(product1));
        assertEquals(2, productQtyMap.get(product1));

        assertTrue(productQtyMap.containsKey(product2));
        assertEquals(2, productQtyMap.get(product2));

        BigDecimal totalTax = cart.getTotalTax();
        assertEquals(BigDecimal.valueOf(35.00).setScale(2), totalTax);

        BigDecimal totalPrice = cart.getTotalPrice();
        assertEquals(BigDecimal.valueOf(314.96).setScale(2), totalPrice);
    }

}
