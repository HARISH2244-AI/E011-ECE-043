package com.dealer.quotation.engine;
public record SizingResult(String requestId, double runningLoadW, double vaBeforeMargin, double vaRequired,
 int inverterVaInitial, double largestMotorW, double surgeRequirementW, boolean surgeStepUp,
 int inverterVaFinal, int systemVoltage, double batteryAhRequired, int batteryCount,
 int batteryAhEach, String inverterCode, String batteryCode, String status) {}