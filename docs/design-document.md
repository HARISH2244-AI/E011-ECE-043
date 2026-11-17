# Design document — E011-ECE-043

## Architecture
- `model`: immutable Java records for appliance, products, request line items and customer requests.
- `engine`: `SizingEngine` implements load, inverter, surge and battery sizing; `PricingEngine` computes quote totals.
- `io`: CSV readers and output writers.
- `Main`: processes command-file entries in order and writes the two required CSV outputs.

## Key formulas
- Running load = sum(watts × quantity) for `on_backup=true` items.
- VA before margin = running load / 0.8; required VA = VA before margin × 1.25; round to 2 decimals before choosing the next listed size.
- Motor surge requirement = running load + 3 × largest motor watts − largest motor watts.
- Battery Ah = running load × backup hours / (12 × 0.8 × 0.85), rounded to 2 decimals before comparing with 200 Ah.
- Quote = inverter + batteries + Rs 1,500 installation + Rs 1,200 trolley per battery; 5% discount if pre-GST > Rs 40,000; GST 18%; grand total rounded to nearest rupee.

## Ambiguities resolved
See `decision-log.md` for zero-hour, invalid-line, no-load, and battery-bank choices.
