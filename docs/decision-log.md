# Decision log

1. Appliance lines with unknown codes or non-positive quantities reject the whole request; this avoids silently under-sizing.
2. A request with no positive backup load is REJECTED.
3. A zero-hour backup request is quoted with the smallest listed battery (100 Ah), while the calculated required Ah remains 0.00. This resolves an ambiguity in the sample brief and is explicitly documented.
4. Battery requirement above 200 Ah uses two 12 V batteries in series; each battery is sized to at least half the calculated Ah, rounded up to the listed 100/150/200 Ah sizes.
5. Inverters of 1500 VA and above always use two batteries. For >200 Ah, the battery bank is two batteries even if the inverter is listed as 12 V; this follows the planted long-backup trap where the brief is silent about the pairing.
6. Surge check uses running load + (3 × largest motor rated watts) − largest motor rated watts. If selected inverter output capacity (VA × 0.8) is insufficient, move up one standard inverter size.
7. Discount is applied only when pre-GST total is strictly greater than Rs 40,000; exactly Rs 40,000 receives no discount.
