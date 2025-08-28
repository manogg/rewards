Rewards Program Service:
=========================

An application that calculates customer reward points based on transaction history using different reward strategies controlled via feature toggles (Togglz).

Access the API at:
========================

GET: http://localhost:9091/api/rewards/{customerId}

API Response Format:
=========================

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

Features Implemented:
==========================

Feature Toggles using Togglz :

  BASIC_REWARD (Enabled)

  FLAT_PER_DOLLAR (Disabled)

  HOLIDAY_BONUS (Disabled)

Calculates monthly reward points and total points for a customer.

Returns detailed response including:

  customerId

  monthlyPoints (Grouped by yyyy-MM)

  totalPoints

  transactions list

  message

Handles asynchronous calls using CompletableFuture.

Error Handling with proper message and status code.
