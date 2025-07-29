package fr.m2i.service;

import fr.m2i.model.Product;
import fr.m2i.repository.ProductRepository;

import java.util.List;
import java.util.Set;

public class InvoiceService {
    private final ProductRepository productRepository;
    private final AuditService auditService;
    private static final double DISCOUNT_THRESHOLD = 100.0;
    private static final double DISCOUNT_RATE = 0.90; // 10% de réduction

    public InvoiceService(ProductRepository productRepository, AuditService auditService) {
        this.productRepository = productRepository;
        this.auditService = auditService;
    }

    public double calculateTotalAmount(String invoiceId,Set<String> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        double total = products.stream().mapToDouble(Product::getPrice).sum();

        if (total > DISCOUNT_THRESHOLD) {
            double discountAmount = total - (total * DISCOUNT_RATE);
            total *= DISCOUNT_RATE;
            auditService.recordDiscountEvent(invoiceId, total, discountAmount);
        }
        return total;
    }
}
