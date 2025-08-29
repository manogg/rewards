**Rewards Program:**

**Overview**
The Rewards Program is a Spring Boot application that calculates reward points for customers based on their transaction history. The system applies reward calculation rules dynamically using feature toggles It supports:

****Basic Rewards**:** Standard reward calculation.
**Feature Flags:** Enable or disable additional reward strategies like flat-per-dollar or holiday bonuses.
**Monthly and Total Points Calculation:** Provides breakdown of rewards by month and a cumulative total.

**Technologies Used:**
Java 8
Spring Boot 2.x
Spring Web (REST API)
SpringDoc OpenAPI (Swagger UI)
Togglz (Feature Toggle Management)
Maven (Build Tool)
Lombok (Reduce boilerplate code)

**Feature Toggles:**

togglz.feature-BASIC_REWARD=true
togglz.feature-FLAT_PER_DOLLAR=false
togglz.feature-HOLIDAY_BONUS=false

**Access the Application:**

API Base URL: GET /api/rewards?customerId=CUST1002&startDate=2025-08-10&endDate=2025-08-30
Header: X-API-VERSION: 1

Swagger UI: http://localhost:9091/swagger-ui/index.html
Togglz Console: http://localhost:9091/togglz-console

**API Response Format:**

{
  "statusCode": 200,
  "message": "Reward points calculated successfully.",
  "payload": {
    "customerId": "CUST1002",
    "monthlyPoints": {
      "2025-06": 450,
      "2025-08": 10
    },
    "totalPoints": 460,
    "transactions": [
      {
        "customerId": "CUST1002",
        "amount": 300,
        "transactionDate": "2025-06-20"
      },
      {
        "customerId": "CUST1002",
        "amount": 60,
        "transactionDate": "2025-08-10"
      }
    ],
    "message": "Reward points calculated successfully."
  }
}

Build the project : mvn clean install
Run the application: mvn spring-boot:run

**Example:**
If transaction amount = $120:
- $50 → 0 points
- $50-$100 → 50 points
- Above $100 (20 dollars) → 40 points (20 * 2)
Total = 90 points

