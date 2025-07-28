package fr.m2i.service;

import fr.m2i.model.Product;
import fr.m2i.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    public void shouldVerifyDiscountIsApplied() {
        Set<String> ids = Set.of("1", "2");
        when(productRepository.findAllById(ids)).thenReturn(List.of(
                new Product("1", 60),
                new Product("3,", 70)
        ));

        assertEquals(117.0, invoiceService.calculateTotalAmount(ids));
    }

}