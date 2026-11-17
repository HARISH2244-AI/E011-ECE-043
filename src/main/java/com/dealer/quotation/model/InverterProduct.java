package com.dealer.quotation.model;
public record InverterProduct(int va, double price) {
    public int voltage() { return va >= 1500 ? 24 : 12; }
}