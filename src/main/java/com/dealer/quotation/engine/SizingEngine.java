package com.dealer.quotation.engine;

import com.dealer.quotation.model.*;
import java.util.*;

public class SizingEngine {
    private static final int[] INVERTER_SIZES = {600, 850, 1100, 1500, 2000};
    private static final int[] BATTERY_SIZES = {100, 150, 200};
    private static final double PF = 0.8, SAFETY = 1.25, DOD = 0.8, EFFICIENCY = 0.85;
    private final Map<String, Appliance> appliances;
    private final Map<Integer, InverterProduct> inverters;
    private final Map<Integer, BatteryProduct> batteries;

    public SizingEngine(Map<String, Appliance> appliances, Map<Integer, InverterProduct> inverters, Map<Integer, BatteryProduct> batteries) {
        this.appliances = appliances; this.inverters = inverters; this.batteries = batteries;
    }

    public SizingResult size(CustomerRequest request) {
        double load = 0, largestMotor = 0;
        try {
            for (RequestLineItem item : request.items()) {
                if (item.quantity() <= 0) return rejected(request.requestId());
                Appliance a = appliances.get(item.applianceCode());
                if (a == null) return rejected(request.requestId());
                if (item.onBackup()) {
                    load += (double)a.watts() * item.quantity();
                    if (a.motorLoad()) largestMotor = Math.max(largestMotor, a.watts());
                }
            }
        } catch (RuntimeException ex) { return rejected(request.requestId()); }
        if (load <= 0) return rejected(request.requestId());
        double before = round2(load / PF), required = round2(before * SAFETY);
        int initial = chooseSize(required);
        if (initial == 0) return new SizingResult(request.requestId(),load,before,required,0,largestMotor,0,false,0,0,0,0,0,"","","CONSULT_3KVA");
        double surge = largestMotor == 0 ? 0 : load + (3 * largestMotor) - largestMotor;
        int selected = initial;
        boolean stepped = false;
        while (selected > 0 && largestMotor > 0 && selected * PF < surge) {
            selected = nextSize(selected); stepped = true;
        }
        if (selected == 0) return new SizingResult(request.requestId(),load,before,required,initial,largestMotor,surge,stepped,0,0,0,0,0,"","","CONSULT_3KVA");
        double ah = request.backupHours() == 0 ? 0 : round2(load * request.backupHours() / (12.0 * DOD * EFFICIENCY));
        boolean twoBatteryMinimum = selected >= 1500;
        int count; int each;
        if (ah > 200.0) { count = 2; each = chooseBattery(round2(ah / 2.0)); }
        else { count = twoBatteryMinimum ? 2 : 1; each = chooseBattery(ah); }
        if (each == 0) return rejected(request.requestId());
        BatteryProduct battery = batteries.get(each);
        InverterProduct inverter = inverters.get(selected);
        return new SizingResult(request.requestId(),load,before,required,initial,largestMotor,surge,stepped,selected,inverter.voltage(),ah,count,each,"INV-" + inverter.va(),"BAT-" + battery.ah(),"QUOTED");
    }
    private static int chooseSize(double value) { for (int s: INVERTER_SIZES) if (s >= value) return s; return 0; }
    private static int nextSize(int current) { for (int i=0;i<INVERTER_SIZES.length;i++) if (INVERTER_SIZES[i]==current && i+1<INVERTER_SIZES.length) return INVERTER_SIZES[i+1]; return 0; }
    private static int chooseBattery(double value) { for (int s: BATTERY_SIZES) if (s >= value) return s; return 0; }
    private static double round2(double v) { return Math.round(v * 100.0) / 100.0; }
    private static SizingResult rejected(String id) { return new SizingResult(id,0,0,0,0,0,0,false,0,0,0,0,0,"","","REJECTED"); }
}