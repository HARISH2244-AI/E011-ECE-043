package com.dealer.quotation.model;
import java.time.LocalDate;
import java.util.List;
public record CustomerRequest(String requestId, LocalDate requestDate, String customerName, String city, int backupHours, List<RequestLineItem> items) {}