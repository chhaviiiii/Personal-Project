package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ProductTypeTest {

    @Test
    void checkProductTypeClothes() {
        assertEquals(ProductType.CLOTHES, ProductType.valueOf("CLOTHES"));
    }

    @Test
    void checkProductTypeElectronics() {
        assertEquals(ProductType.ELECTRONICS, ProductType.valueOf("ELECTRONICS"));
    }

    @Test
    void checkProductTypeFood() {
        assertEquals(ProductType.FOOD, ProductType.valueOf("FOOD"));
    }

    @Test
    void checkProductTypeMakeup() {
        assertEquals(ProductType.MAKEUP, ProductType.valueOf("MAKEUP"));
    }
}