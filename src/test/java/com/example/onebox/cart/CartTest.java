package com.example.onebox.cart;

import com.example.onebox.audit.AuditService;
import com.example.onebox.cart.model.CartDto;
import com.example.onebox.cartrow.CartRowService;
import com.example.onebox.common.exception.EntityNotFoundException;
import com.example.onebox.common.exception.InvalidDataException;
import com.example.onebox.config.mapper.CartEntityMapper;
import com.example.onebox.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {

    @InjectMocks
    private CartServiceImpl cartService;

    @Mock
    private AuditService auditService;

    @Mock
    private CustomerService customerService;

    @Mock
    private CartRowService cartRowService;

    @Mock
    private CartEntityMapper cartEntityMapper;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this); // This initializes fields annotated with Mockito annotations
        Field field = CartServiceImpl.class.getDeclaredField("carts");
        field.setAccessible(true);
        field.set(cartService, new ConcurrentHashMap<>());
    }

    @Test
    public void getAllCartsThrowsExceptionWhenNoCartsFound() {
        assertThrows(EntityNotFoundException.class, () -> cartService.getAllCarts());
    }

    @Test
    public void getCartsByCustomerIdThrowsExceptionWhenNoCartsFound() {
        assertThrows(EntityNotFoundException.class, () -> cartService.getCartsByCustomerId(1L));
    }

    @Test
    public void saveCartThrowsExceptionWhenCustomerIsNull() {
        CartDto cartDto = new CartDto();
        assertThrows(InvalidDataException.class, () -> cartService.save(cartDto));
    }

    @Test
    public void getCartThrowsExceptionWhenCartNotFound() {
        assertThrows(EntityNotFoundException.class, () -> cartService.getCart(1L));
    }

}