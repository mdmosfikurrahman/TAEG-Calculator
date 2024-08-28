# TAEG Calculation Service

This project is a Spring Boot application that calculates the Total Annual Effective Rate (TAEG) for loans. It includes RESTful endpoints to receive loan details, validate inputs, and return the calculated TAEG.

## Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Validation](#validation)
- [Contributing](#contributing)
- [License](#license)

## Overview

The TAEG Calculation Service is designed to:
- Accept loan details such as loan amount, interest rate, number of payments, and insurance cost.
- Validate the input values.
- Calculate the TAEG based on the provided loan details.
- Return the calculated TAEG, along with the calculation timestamp and a unique loan ID.

## Architecture

- **Model**: Defines the `Loan` entity, which is mapped to the `loan` table in the database.
- **DTOs**: 
  - `LoanRequest`: Data Transfer Object for incoming loan data.
  - `LoanResponse`: Data Transfer Object for the response, containing the calculated TAEG.
- **Service**: 
  - `TAEGService`: Interface defining the TAEG calculation method.
  - `TAEGServiceImpl`: Implementation of the service that handles business logic.
- **Validator**: 
  - `LoanRequestValidator`: Validates the loan request data.
- **Repository**: 
  - `LoanRepository`: JPA repository for performing CRUD operations on the `Loan` entity.
- **Controller**: 
  - `TAEGController`: REST controller that handles API requests for TAEG calculation.

## Technologies

- Java 17
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Lombok
- Maven

## Installation

### Prerequisites

- Java 17 or higher
- Maven 3.6.x or higher
- MySQL database

### Steps

1. Clone the repository:

   \`\`\`bash
   git clone https://github.com/yourusername/taeg-calculation-service.git
   cd taeg-calculation-service
   \`\`\`

2. Update the database connection properties in `src/main/resources/application.yml`:

   \`\`\`yaml
   spring:
     datasource:
       url: jdbc:mysql://<your-database-url>:3306/<your-database-name>
       username: <your-database-username>
       password: <your-database-password>
   \`\`\`

3. Build the project using Maven:

   \`\`\`bash
   mvn clean install
   \`\`\`

4. Run the application:

   \`\`\`bash
   mvn spring-boot:run
   \`\`\`

## Usage

Once the application is running, you can use tools like Postman or `curl` to send HTTP requests to the API.

## API Endpoints

### Calculate TAEG

- **URL**: `/api/taeg/calculate`
- **Method**: `POST`
- **Request Body**: 
  - `loanAmount`: double - The amount of the loan.
  - `interestRate`: double - The annual interest rate of the loan.
  - `numberOfPayments`: int - The number of payments to be made.
  - `insuranceCost`: double - The insurance cost associated with the loan.
  
- **Response**: 
  - `loanId`: Long - The unique identifier for the loan.
  - `taeg`: double - The calculated Total Annual Effective Rate.
  - `calculationTime`: LocalDateTime - The timestamp of the calculation.
