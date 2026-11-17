# E011-ECE-043 — Home Inverter and Battery Sizing and Quotation Engine

## Requirements
- Java 17+ and Maven 3.9+.
- Keep the five official input files in `data/`: `appliances.csv`, `price_list.csv`, `requests.csv`, `request_items.csv`, `commands.txt`.

## Run
From the project root:
```powershell
mvn clean test
mvn exec:java
```
Outputs are written to `output/sizing_worksheet.csv` and `output/quotations.csv`.

## Design decisions
See `docs/design-document.md` and `docs/decision-log.md`. Invalid request lines and requests with no backup load are rejected; zero-hour requests use the minimum listed battery. This is a documented interpretation where the sample pack says the brief is silent.
