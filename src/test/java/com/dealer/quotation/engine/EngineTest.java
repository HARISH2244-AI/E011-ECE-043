package com.dealer.quotation.engine;
import com.dealer.quotation.model.*; import org.junit.jupiter.api.Test; import java.time.LocalDate; import java.util.*; import static org.junit.jupiter.api.Assertions.*;
class EngineTest {
 private Map<String,Appliance> apps(Appliance... a){Map<String,Appliance>m=new HashMap<>();for(var x:a)m.put(x.applianceCode(),x);return m;}
 private Map<Integer,InverterProduct> inv(){Map<Integer,InverterProduct>m=new HashMap<>();for(int v:new int[]{600,850,1100,1500,2000})m.put(v,new InverterProduct(v,10000));return m;}
 private Map<Integer,BatteryProduct> bat(){Map<Integer,BatteryProduct>m=new HashMap<>();for(int v:new int[]{100,150,200})m.put(v,new BatteryProduct(v,9000));return m;}
 private CustomerRequest req(int watts,int hours,boolean motor){return new CustomerRequest("T",LocalDate.parse("2026-01-01"),"Test","City",hours,List.of(new RequestLineItem("A",1,true)));}
 @Test void inverterVoltageIsComputed(){assertEquals(12,new InverterProduct(1100,1).voltage());assertEquals(24,new InverterProduct(1500,1).voltage());}
 @Test void exact200AhStaysOneBattery(){var s=new SizingEngine(apps(new Appliance("A","Load",408,false)),inv(),bat()).size(req(408,4,false));assertEquals(200.0,s.batteryAhRequired());assertEquals(1,s.batteryCount());assertEquals(200,s.batteryAhEach());}
 @Test void zeroBackupHoursUsesMinimumBattery(){var s=new SizingEngine(apps(new Appliance("A","Load",100,false)),inv(),bat()).size(req(100,0,false));assertEquals(0,s.batteryAhRequired());assertEquals(100,s.batteryAhEach());}
 @Test void noBackupLoadIsRejected(){var r=new CustomerRequest("T",LocalDate.now(),"x","x",2,List.of(new RequestLineItem("A",1,false)));assertEquals("REJECTED",new SizingEngine(apps(new Appliance("A","x",100,false)),inv(),bat()).size(r).status());}
 @Test void negativeQuantityIsRejected(){var r=new CustomerRequest("T",LocalDate.now(),"x","x",2,List.of(new RequestLineItem("A",-1,true)));assertEquals("REJECTED",new SizingEngine(apps(new Appliance("A","x",100,false)),inv(),bat()).size(r).status());}
 @Test void unknownApplianceIsRejected(){var r=new CustomerRequest("T",LocalDate.now(),"x","x",2,List.of(new RequestLineItem("MISSING",1,true)));assertEquals("REJECTED",new SizingEngine(apps(),inv(),bat()).size(r).status());}
}