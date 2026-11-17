# Home Inverter and Battery Sizing and Quotation Engine

**Project ID:** E011-ECE-043  
**Domain:** Home Inverter and Battery Sizing  
**Technology:** Java, Maven, CSV File Processing  
**Repository:** E011-ECE-043

## Overview

The **Home Inverter and Battery Sizing and Quotation Engine** is a Java-based application designed to automate the selection of suitable home inverters and batteries and generate corresponding price quotations.

The system analyses customer requirements, including appliance power consumption and backup duration, to calculate the required inverter capacity and battery specifications. It also considers motor starting surge requirements, product availability, installation charges, discounts, and GST.

The application processes input data from CSV files and generates structured output reports, helping simplify the inverter selection and quotation process.

## Objectives

- Automate inverter and battery capacity calculations.
- Reduce manual calculation errors.
- Recommend suitable inverter and battery configurations.
- Consider appliance loads and motor starting surge requirements.
- Calculate battery capacity based on backup duration.
- Generate quotations with installation charges, discounts, and GST.
- Produce structured CSV reports for easy analysis.

## Key Features

### 1. Appliance Load Calculation
- Reads appliance details from CSV files.
- Calculates total backup load using appliance wattage and quantity.
- Considers only appliances marked for backup operation.

### 2. Inverter Sizing
- Calculates the required inverter VA rating.
- Applies power factor and safety margin.
- Selects an appropriate standard inverter capacity.
- Checks motor starting surge requirements.
- Recommends a higher inverter capacity when required.

### 3. Battery Sizing
- Calculates battery capacity based on load and backup hours.
- Considers depth of discharge and inverter efficiency.
- Selects suitable standard battery capacities.
- Determines the required battery quantity.

### 4. Automated Quotation Generation
- Calculates inverter and battery costs.
- Includes installation and trolley charges.
- Applies eligible discounts.
- Calculates GST at 18%.
- Generates the final quotation amount.

### 5. CSV-Based Data Processing
- Reads customer requests, appliances, and product prices.
- Processes commands in the specified order.
- Generates structured sizing and quotation reports.

### 6. Request Validation
- Handles invalid appliance codes.
- Identifies invalid quantities.
- Handles requests with zero backup load.
- Identifies requests requiring consultation for larger inverter capacities.

## Technology Stack

| Component | Technology |
|---|---|
| Programming Language | Java |
| Build Tool | Apache Maven |
| Data Storage | CSV Files |
| Testing | JUnit |
| Development Environment | Visual Studio Code |
| Version Control | Git |
| Code Hosting | GitHub |

## Project Structure

```text
E011-ECE-043/
│
├── data/
│   ├── appliances.csv
│   ├── commands.txt
│   ├── price_list.csv
│   ├── request_items.csv
│   └── requests.csv
│
├── docs/
│   ├── decision-log.md
│   └── design-document.md
│
├── output/
│   ├── quotations.csv
│   └── sizing_worksheet.csv
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/dealer/quotation/
│   │           ├── Main.java
│   │           ├── engine/
│   │           ├── io/
│   │           └── model/
│   │
│   └── test/
│       └── java/
│
├── .gitignore
├── pom.xml
└── README.md
```

## Requirements

Before running the application, ensure the following are installed:

- Java JDK 17 or later.
- Apache Maven 3.9 or later.
- Visual Studio Code or another Java-compatible IDE.
- Git (optional, for repository management).

## How to Run the Project

### Step 1: Clone the Repository

```bash
git clone https://github.com/HARISH2244-AI/E011-ECE-043.git
```

### Step 2: Open the Project

```bash
cd E011-ECE-043
```

### Step 3: Run Unit Tests

```bash
mvn clean test
```

### Step 4: Execute the Application

```bash
mvn exec:java
```

### Step 5: Check Generated Reports

After successful execution, open the `output/` directory.

The application generates:

- `sizing_worksheet.csv` – Contains inverter and battery sizing results.
- `quotations.csv` – Contains the generated customer quotations.

## Input Files

| File | Description |
|---|---|
| appliances.csv | Appliance details, wattage, and motor load information |
| price_list.csv | Inverter and battery product prices |
| requests.csv | Customer request details and backup hours |
| request_items.csv | Appliance quantities and backup selection |
| commands.txt | Instructions controlling the execution sequence |

## Output Files

### Sizing Worksheet

Contains:
- Customer request ID
- Running load
- Required inverter capacity
- Motor surge requirement
- Final inverter capacity
- System voltage
- Required battery capacity
- Battery quantity
- Sizing status

### Quotations

Contains:
- Customer request information
- Inverter and battery details
- Product prices
- Installation and trolley charges
- Discount
- Taxable amount
- GST
- Final quotation amount

## Testing and Validation

The project includes automated unit tests to validate the sizing and quotation logic.

Testing covers:
- Standard inverter selection.
- Motor starting surge calculations.
- Battery capacity calculations.
- Battery quantity selection.
- Discount eligibility.
- GST calculations.
- Invalid customer request handling.
- Large-capacity consultation scenarios.

## Expected Execution Result

A successful execution processes the supplied sample commands and generates the output reports.

```text
Completed. Sizing rows: 40, quotations: 30. See output/.

BUILD SUCCESS
```

## Future Enhancements

- Develop a graphical user interface for easier operation.
- Integrate a relational database for customer and product management.
- Add PDF quotation generation.
- Introduce customer and administrator login.
- Add quotation history and reporting dashboards.
- Support additional inverter and battery product categories.

## Author

**Harish Naik N**  
B.E. – Electronics and Computer Engineering  
Atria Institute of Technology  
Bengaluru, Karnataka, India

## GitHub Repository

[Home Inverter and Battery Sizing and Quotation Engine](https://github.com/HARISH2244-AI/E011-ECE-043)

---

*Developed as an academic project to automate home inverter and battery sizing and quotation generation.*
