package com.dealer.quotation.engine;
import com.dealer.quotation.model.*;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class PricingEngine {
    private static final double INSTALLATION=1500.0, TROLLEY_PER_BATTERY=1200.0, GST_RATE=0.18, DISCOUNT_RATE=0.05;
    private final Map<Integer,InverterProduct> inverters; private final Map<Integer,BatteryProduct> batteries;
    public PricingEngine(Map<Integer,InverterProduct> inverters, Map<Integer,BatteryProduct> batteries) { this.inverters=inverters; this.batteries=batteries; }
    public Quotation quote(CustomerRequest request, SizingResult sizing) {
        if (!"QUOTED".equals(sizing.status())) throw new IllegalArgumentException("Only QUOTED sizing results can be priced");
        InverterProduct inv=inverters.get(sizing.inverterVaFinal()); BatteryProduct bat=batteries.get(sizing.batteryAhEach());
        double batteryTotal=bat.price()*sizing.batteryCount(), trolley=TROLLEY_PER_BATTERY*sizing.batteryCount();
        double pre=inv.price()+batteryTotal+INSTALLATION+trolley;
        double discount=pre>40000.0 ? pre*DISCOUNT_RATE : 0.0;
        double taxable=pre-discount, gst=taxable*GST_RATE, grand=Math.round(taxable+gst);
        return new Quotation(request.requestId(),request.requestDate(),request.requestDate().plus(15,ChronoUnit.DAYS),"INV-" + inv.va(),inv.price(),"BAT-" + bat.ah(),bat.price(),sizing.batteryCount(),r2(batteryTotal),INSTALLATION,r2(trolley),r2(pre),r2(discount),r2(taxable),r2(gst),(long)grand);
    }
    private static double r2(double n){return Math.round(n*100.0)/100.0;}
}