package com.dealer.quotation.io;

import com.dealer.quotation.engine.Quotation;
import com.dealer.quotation.engine.SizingResult;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

public class CsvWriter {
    public static void sizing(Path path, List<SizingResult> rows) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(path)) {
            w.write("request_id,running_load_w,va_before_margin,va_required,inverter_va_initial,largest_motor_w,surge_requirement_w,surge_step_up,inverter_va_final,system_voltage,battery_ah_required,battery_count,battery_ah_each,inverter_code,battery_code,status\n");
            for (SizingResult x : rows) {
                w.write(String.join(",", x.requestId(), n(x.runningLoadW()), n(x.vaBeforeMargin()), n(x.vaRequired()),
                    i(x.inverterVaInitial()), n(x.largestMotorW()), n(x.surgeRequirementW()), Boolean.toString(x.surgeStepUp()),
                    i(x.inverterVaFinal()), i(x.systemVoltage()), n(x.batteryAhRequired()), i(x.batteryCount()),
                    i(x.batteryAhEach()), x.inverterCode(), x.batteryCode(), x.status()) + "\n");
            }
        }
    }

    public static void quotations(Path path, List<Quotation> rows) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(path)) {
            w.write("request_id,request_date,valid_till,inverter_code,inverter_price,battery_code,battery_unit_price,battery_count,battery_total,installation,trolley_total,pre_gst_total,discount,taxable_total,gst,grand_total\n");
            for (Quotation x : rows) {
                w.write(String.join(",", x.requestId(), x.requestDate().toString(), x.validTill().toString(),
                    x.inverterCode(), n(x.inverterPrice()), x.batteryCode(), n(x.batteryUnitPrice()), i(x.batteryCount()),
                    n(x.batteryTotal()), n(x.installation()), n(x.trolleyTotal()), n(x.preGstTotal()), n(x.discount()),
                    n(x.taxableTotal()), n(x.gst()), Long.toString(x.grandTotal())) + "\n");
            }
        }
    }

    private static String n(double value) { return String.format(Locale.US, "%.2f", value); }
    private static String i(int value) { return Integer.toString(value); }
}
