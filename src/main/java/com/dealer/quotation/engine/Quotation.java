package com.dealer.quotation.engine;
import java.time.LocalDate;
public record Quotation(String requestId, LocalDate requestDate, LocalDate validTill, String inverterCode,
 double inverterPrice, String batteryCode, double batteryUnitPrice, int batteryCount,
 double batteryTotal, double installation, double trolleyTotal, double preGstTotal,
 double discount, double taxableTotal, double gst, long grandTotal) {}