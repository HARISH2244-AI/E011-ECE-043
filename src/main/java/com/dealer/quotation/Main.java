package com.dealer.quotation;
import com.dealer.quotation.engine.*; import com.dealer.quotation.io.*; import com.dealer.quotation.model.*;
import java.nio.file.*; import java.util.*;
public class Main {
 public static void main(String[] args)throws Exception {
  Path data=Path.of(args.length>0?args[0]:"data"), output=Path.of("output"); Files.createDirectories(output);
  Map<String,Appliance> appliances=null; CsvReader.PriceData prices=null; Map<String,CustomerRequest> requests=CsvReader.requests(data.resolve("requests.csv"),data.resolve("request_items.csv"));
  List<SizingResult> sizingRows=new ArrayList<>(); List<Quotation> quoteRows=new ArrayList<>();
  for(String raw:Files.readAllLines(data.resolve("commands.txt"))){String line=raw.trim();if(line.isEmpty()||line.startsWith("#"))continue;String[] p=line.split("\\|",-1);switch(p[0].trim()){
   case "LOAD_APPLIANCES" -> appliances=CsvReader.appliances(data.resolve(p[1].trim()));
   case "LOAD_PRICES" -> prices=CsvReader.prices(data.resolve(p[1].trim()));
   case "QUOTE" -> {if(appliances==null||prices==null)throw new IllegalStateException("Load commands must come before QUOTE");CustomerRequest r=requests.get(p[1].trim());if(r==null)continue;SizingEngine se=new SizingEngine(appliances,prices.inverters(),prices.batteries());SizingResult s=se.size(r);sizingRows.add(s);if(s.status().equals("QUOTED"))quoteRows.add(new PricingEngine(prices.inverters(),prices.batteries()).quote(r,s));}
   default -> throw new IllegalArgumentException("Unknown command: "+line);
  }}
  CsvWriter.sizing(output.resolve("sizing_worksheet.csv"),sizingRows);CsvWriter.quotations(output.resolve("quotations.csv"),quoteRows);
  System.out.println("Completed. Sizing rows: "+sizingRows.size()+", quotations: "+quoteRows.size()+". See output/.");
 }
}