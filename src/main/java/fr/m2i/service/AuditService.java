package fr.m2i.service;

public interface AuditService {
    // Enregistre un événement dans un journal d'audit
    void recordDiscountEvent(String invoiceId, double amount, double discount);
}
