package com.dealer.quotation.model;
public record RequestLineItem(String applianceCode, int quantity, boolean onBackup) {}