package fr.m2i.service;

import fr.m2i.model.Product;
import fr.m2i.repository.ProductRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private InvoiceService invoiceService;

    @Test
    public void shouldVerifyDiscountIsApplied() {
        Set<String> ids = Set.of("1", "2");
        when(productRepository.findAllById(ids)).thenReturn(List.of(
                new Product("1", 60),
                new Product("3,", 70)
        ));

        assertEquals(117.0, invoiceService.calculateTotalAmount("INV-DISC003", ids));
    }

    @Test
    void shouldNotifyAuditService_whenDiscountIsApplied() {
        // Arrange
        Product p1 = new Product("p1", 130.0);
        Product p2 = new Product("p2", 220.0);
        Set<String> productIds = Set.of("p1", "p2");
        when(productRepository.findAllById(productIds)).thenReturn(List.of(p1, p2));

        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> amountCaptor = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Double> discountCaptor = ArgumentCaptor.forClass(Double.class);

        // Act
        double finalAmount = invoiceService.calculateTotalAmount("FACTURE-0030011320", productIds);

        // Assert - partie 1 : vérifier le montant retourné
        // 350 * 0.9 = 180
        assertThat(finalAmount).isCloseTo(315.0, within(0.01));

        // Verify & Capture
        verify(auditService).recordDiscountEvent(
                idCaptor.capture(),
                amountCaptor.capture(),
                discountCaptor.capture()
        );

        // Assert - partie 2 : vérifier les arguments capturés avec Soft Assertions
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(idCaptor.getValue())
                    .isEqualTo("FACTURE-0030011320");
            softly.assertThat(amountCaptor.getValue())
                    .as("Amount before discount")
                    .isCloseTo(315.0, within(0.01));
            softly.assertThat(discountCaptor.getValue())
                    .as("Discount amount")
                    .isCloseTo(35.0, within(0.01));
        });
    }

}