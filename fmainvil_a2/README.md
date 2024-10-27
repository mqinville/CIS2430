***Name***: *Felix Mainville*
***ID***: *1279419, fmainvil@uoguelph.ca*


**1. GENERAL PROBLEM**

The goal of this assignment is to create an investment portfoloio management system.
This system will allow users to manage, and manipulate a variety of financial investments
that the user owns. This management system provides support for stocks, and mutualfunds:
Users can buy(new/existing investments), sell (existing investments), update (existing investments),
calculate theoretical profits of investments and search through investments in the portfolio based 
on inputted criteria (Symbol, name price range).

**2. GENERAL ASSUMPTIONS AND LIMITATIONS** 

This portfolio management system requires users to be familiar with investment concepts and 
investment portfolios. Stocks and mutual funds are stored within their own distinct arraylists 
in the portfolio, each investment has a name, symbol, price, quantity and commission (when applicable). 
Users can interact with the portfolio and individual investments using the investment properties listed above

LIMITATIONS - Here are some limitations regarding the portfolio project
- The current investment management system only stores investment quantities as whole integers.
  No fractional quantities of investments are allowed; if this is attempted, the program will exit.
- Price ranges in the search feature require a specific format of "x-y", where x and y are the lower and 
  upper bounds respectively, although some exceptions are made for slight misinputs "x - y",  Or even single bound price ranges ("x-, -x").
- Symbols for investments must be unique even if they are of different invesment types.
- The current system is based purely off current user input, once the program is stopped no entered data is saved to a file.
  Thus users must re-enter the previous data if they wish to access it. This also implies that investment updates must be handled manually
  by the user. No real-life stock prices are fetched.
- This is a command line based program, certain user inputs that are mispelled or other, may cause the program 
  to terminate early. A style of defensive programming was implemented to better handle these cases however, the user
  must still be cautious to respect given instructions for input.

**3. USER GUIDE**
- Java must be installed on your system (Specifically java17)
- Navigate to the program directory e.g. ```cd .../fmainvil_a1/```
- To **compile** the program: ```javac src/eportfolio/*.java```
- To **run** the program first navigate to the output directory ```cd .../fmainvil_a1/out```
- Once in the output directory: ```java eportfolio.Main``` to run **OR** ```jar cf fmainvil_a1.jar out/eportfolio/*.class``` to build    an executable jar file. 
- To use the program the user must select a prompt option from the given menu - We accept misinputs to fit defensive guidelines (Within reason)
  - After an option is selected, users must simply follow directons in order to accomplish
    a selected menu option.

**4. TEST PLAN**

Introduction : E-Portfolio Test Plan
This test plan is to ensure that the ePortfolio system meets the functional requirements of the assignment, and handles various edge cases, as specified in the CIS*2430 Assignment One Description. The ePortfolio system allows users to manage a portfolio of investments consisting of stocks and mutual funds. The core operations include buying, selling, updating investment prices, calculating portfolio gain, and searching for investments based on specified criteria.
This test plan outlines a series of test cases designed to verify the correctness, robustness, and error-handling capabilities of the system. Each test case targets a specific function within the system, including normal operations, boundary cases, and invalid inputs. 
The objectives of this test plan are:
To validate that the program correctly executes user commands for Buying, Selling, Updating Prices, Calculating total gain, and Searching (the inputted investments).
To ensure that the program handles edge cases such as minimum or maximum values and invalid inputs, providing appropriate feedback and maintaining stability.
To ensure that the system maintains data integrity and correctly updates or removes investments as required by the user's actions.
The test cases will be run manually, and the results will be documented. This test plan aims to cover a broad range of possible scenarios to ensure that the ePortfolio system functions as intended and provides a smooth user experience.
We will proceed in this test plan with 4 sections : 
Correctness
Robustness
Edge Cases
Input Validation

A. Correctness
Core functionality is tested to validate that the program works as expected.
‘Buy’ Functionality (Stocks and Mutual Funds)
Test Case 1: Buy a new stock.
Input: Symbol = "AAPL", Name = "Apple Inc.", Quantity = 100, Price = 120.00.
Expected Result: Stock is added with the correct quantity, price, and book value
✅ This works as expected and outlined above.
“New purchase! 100 shares of AAPL @120.0$ successfully purchased!”

Test Case 2: Buy an existing stock (add more quantity).
Input: Symbol = "AAPL", Quantity = 50, Price = 130.00.
Expected Result: Stock quantity is updated, price is set to 130.00, and book value is correctly calculated.
✅ This works as expected and outlined above.
“Additional 50 shares of AAPL @130.0$ successfully purchased!”


Test Case 3: Buy a new mutual fund.
Input: Symbol = "VFIAX", Name = "Vanguard 500 Index Fund", Quantity = 200, Price = 250.00.
Expected Result: Mutual fund is added with the correct quantity, price, and book value.
✅ This works as expected and outlined above.
“New purchase of VFIAX successful!”
Test Case 4: Buy an existing mutual fund (add more quantity).
Input: Symbol = "VFIAX", Quantity = 50, Price = 130.00.
Expected Result: MutualFund quantity is updated, price is set to 130.00, and book value is correctly calculated.
✅ This works as expected and outlined above.
Additional 50 units of VFIAX @130.0$ succesfully purchased!
SEARCH RESULTS AFTER TEST CASES:
Stock symbol: AAPL
Stock name: Apple Inc.
Quantity: 150
Price: 130.0$
bookValue: 18519.98$

Mutualfund symbol: VFIAX
Mutual fund name: Vanguard 500 Index Fund
Quantity: 250
Price: 130.0
bookValue: 56500.0

‘Sell’ Functionality (Stocks and Mutual Funds)
Test Case 5: Sell part of a stock.
Input: Symbol = "AAPL", Quantity = 50, Price = 140.00.
Expected Result: Stock quantity is reduced by 50, and payment for the sale is calculated and displayed.
✅ This works as expected and outlined above:
0 shares of AAPLsold @140.0$!
Payment from sale: 6173.33$. Remaining book value is: 12346.65$.
Removing 50 amount of shares of AAPL from list. There are 100 shares left.

Test Case 6: Sell all of a stock.
Input: Symbol = "AAPL", Quantity = 100, Price = 140.00.
Expected Result: Stock is fully sold, removed from the portfolio, and payment is calculated.\
✅ This works as expected and outlined above:
All shares of AAPL sold @140.0$!
Payment from sale: 13990.01$.
Total gain from sale: 1643.36$.
No more shares left, removing AAPL from list


Test Case 7: Sell part of mutualfund
Input: Symbol=”VFIAX”, Quantity = 50 , Price = 140.00
Expected Result: Stock quantity is reduced by 50, and payment for the sale is calculated and displayed.
✅ This works as expected and outlined above:
50 units of VFIAXsold @140.0$!
Payment from sale: 11300.00$. Remaining book value is: 45200.00$.
Removing 50 amount of units of VFIAX from list. There are 200 units left.


Test Case 8: Sell all of a mutualfund.
Input: Symbol = "VFIAX", Quantity = 200, Price = 150.14.
Expected Result: mutualfund is fully sold, removed from the portfolio, and payment is calculated.
✅ This works as expected and outlined above:
All units of VFIAX sold @150.14$!
Payment from sale: 27990.01$.
Total gain from sale: -17209.99$. - WE BOUGHT AT 250, negative gain makes sense
No more shares left, removing VFIAX from list
This is the search result after removing all investments from the list:
No search reslts found.

‘Update’ Price Functionality
Test Case 9: Update prices for all stocks and mutual funds.
Input: New price for "AAPL" = 150.00, New price for "VFIAX" = 260.00.
Expected Result: The prices of all stocks and mutual funds are updated without affecting their quantities or book values.
✅ This works as expected and outlined above:
--- Updating stock prices ---
Updating the price for: AAPL
Enter the new price: 150.00
--- Updating mutual fund prices ---
Updating the price for: VFIAX
Enter the new price: 260.00
Upon a global search the values of the investments are properly adjusted


Calculate Total Gain’ (or Loss) functionality
Test Case 10: Calculate total gain for the portfolio.
Input: Current prices for all investments.
Expected Result: Gain for each investment is calculated and displayed, along with the total portfolio gain.
✅ This works as expected and outlined above:
Gain for AAPL: 2905.02
Gain for VFIAX: 2490.01
------------ PORTFOLIO GAINS ------------
Stock portfolio gains: 2905.02
Mutual funds portfolio gains: 2490.01
Total investment portfolio gains: 5395.03
------------ PORTFOLIO GAINS ------------

‘Search’ Functionality
Test Case 11: Search for an investment by symbol.
Input: Symbol = "AAPL".
Expected Result: The stock "AAPL" is found and displayed.
✅ This works as expected and outlined above:
Stock symbol: AAPL
Stock name: Apple Inc.
Quantity: 150
Price: 150.0$
bookValue: 19584.99$

Test Case 12: Search for an investment by price range.
Input: Price range = "100-1000".
Expected Result: Any investment with a price within the range is displayed.
✅ This works as expected and outlined above:
Stock symbol: AAPl
Stock name: Apple Inc
Quantity: 150
Price: 130.00$
bookValue: 19509.99$

Mutualfund symbol: VFIAX
Mutual fund name: Vertex Mutual Fund 500
Quantity: 250
Price: 230.00$
bookValue: 57500.0

2. Robustness Testing
Testing for system resilience and stability, especially when unexpected inputs are provided.
Invalid Inputs for Buy Functionality
Test Case 13: Buy stock with an invalid quantity (negative, zero).
Input: Symbol = "AAPL", Quantity = -50, Price = 120.00.
Expected Result: Error message ("Quantity must be greater than 0") is displayed, prompted for input
✅ This works as expected and outlined above:
Quantity must be greater than 0.


Test Case 14: Buy stock with an invalid price (negative).
Input: Symbol = "AAPL", Quantity = 100, Price = -120.00.
Expected Result: Error message ("Price cannot be negative") is displayed, and reprompt for price 
✅ This works as expected and outlined above:
Price cannot be negative.

Test Case 15: Buy mutualfund with an invalid quantity (negative, zero).
Input: Symbol = "VFIAX", Quantity = -50, Price = 120.00.
Expected Result: Error message ("Quantity must be greater than 0") is displayed, reprompt for quantity
✅ This works as expected and outlined above:
Quantity must be greater than 0.
Test Case 16: Buy mutualfund with an invalid price (negative).
Input: Symbol = "VFIAX", Quantity = 100, Price = -120.00.
Expected Result: Error message ("Price cannot be negative") is displayed, and no stock is added.
✅ This works as expected and outlined above:
Price cannot be negative.
Invalid Inputs for Sell Functionality
Test Case 17: Sell stock with quantity exceeding available quantity.
Input: Symbol = "AAPL", Quantity = 500, Price = 140.00 (when only 50 are owned).
Expected Result: Error message ("Only 100 shares available. Cannot sell 200") is displayed.
✅ This works as expected and outlined above:
Only 50 shares of AAPL. Cannot sell 500.
Test Case 18: Sell stock that does not exist.
Input: Symbol = "GOOGL",
Expected Result: Error message ("GOOGL is not in the portfolio") is displayed.
✅ This works as expected and outlined above:
Error selling investment. GOOGL is not in the portfolio. 

Test Case 19: Sell Mutualfund with quantity exceeding available quantity.
Input: Symbol = "VFIAX", Quantity = 5000, Price = 140.00 (when only 34 are owned).
Expected Result: Error message ("Only 34 shares available. Cannot sell 200") is displayed.
✅ This works as expected and outlined above:
Only 34 units of VFIAX. Cannot sell 5000.


Test Case 20: Sell mutualfund that does not exist.
Input: Symbol = "GOOGL"
Expected Result: Error message ("GOOGL is not in the portfolio") is displayed.
✅ This works as expected and outlined above:
Error selling investment. GOOGL is not in the portfolio. 


3. Edge Cases
Testing for boundary conditions and ensuring the system handles them gracefully.
Edge Case for Buying and Selling
Test Case 21: Buy stock with quantity = 1 (minimum possible).
Input: Symbol = "AAPL", Quantity = 1, Price = 120.00.
Expected Result: Stock is added with the correct book value, and the system handles small values correctly.
✅ This works as expected and outlined above:
Additional 1 shares of AAPL @120.0$ successfully purchased!
Test Case 22: Sell stock with quantity = 1 (minimum possible).
Input: Symbol = "AAPL", Quantity = 1, Price = 150.00.
Expected Result: Stock quantity is reduced by 1, and the correct payment is calculated.
✅ This works as expected and outlined above:
1 shares of APPLsold @150.0$!
Payment from sale: 130.10$. Remaining book value is: 13009.88$.
Removing 1 amount of shares of APPL from list. There are 100 shares left.

Test Case 23: Buy Mutualfund with quantity = 1 (minimum possible).
Input: Symbol = "VFIAX", Quantity = 1, Price = 180.00.
Expected Result: Mutualfund is added with the correct book value, and the system handles small values correctly.
✅ This works as expected and outlined above:
Additional 1 units of VFIAX @180.0$ succesfully purchased!
Test Case 24: Sell Mutualfund with quantity = 1 (minimum possible).
Input: Symbol = "VFIAX", Quantity = 1, Price = 180.00.
Expected Result: mutualfund quantity is reduced by 1, and the correct payment is calculated.
✅ This works as expected and outlined above:
1 units of VFIAXsold @180.0$!
Payment from sale: 199.80$. Remaining book value is: 19980.20$.
Removing 1 amount of units of VFIAX from list. There are 100 units left.


Edge Case for Search
Test Case 25: Search for a stock with an empty search field.
Input: All search fields empty.
Expected Result: All investments are returned since no specific criteria were provided.
✅ This works as expected and outlined above:
Stock symbol: APPL
Stock name: Apple Inc.
Quantity: 100
Price: 150.00$
bookValue: 13009.881188118812$

Stock symbol: AAPL
Stock name: Apple Inc. 2
Quantity: 1
Price: 120.00$
bookValue: 129.99$

Mutualfund symbol: VFIAX
Mutual fund name: Vertex 500 Index Fund
Quantity: 100
Price: 180.00$
bookValue: 19980.20$


Test Case 26: Search for a stock with multiple keywords in the name.
Input: Keywords = "Vertex Fund".
Expected Result: Investments that contain both "Vertex" and "Fund" in the name, regardless of order, are returned.
✅ This works as expected and outlined above:
Mutualfund symbol: VFIAX
Mutual fund name: Vertex 500 Index Fund
Quantity: 100
Price: 180.00$
bookValue: 19980.20$

4. Input Validation
Ensuring that invalid or unexpected input is handled properly.
Invalid Inputs in General
Test Case 27: Enter invalid input when prompted for a menu option.
Input: "xyz" for the menu option.
Expected Result: No action is taken, and the user is prompted again to select a valid menu option.
✅ This works as expected and outlined above


Test Case 28: Enter a price range into search that has spaces in it: 
Input: “100 - 250”
Expected Result: Search still takes place and returns investments within range
✅ This works as expected and outlined above


Test Case 29: Enter a single bound price range into search functiom
Input: “100-”
Expected Result: Search still takes place and returns investments within range i.e >= 100
✅ This works as expected and outlined above


Test Case 30: Enter a mismatching datatype for a scan statement
Input: “string” for a price/quantity
Expected Result: Program exits and lists error messages
✅ This works as expected and outlined above


**5. IMPROVEMENTS**

This test plan validates correct operation in the 4 categories : Correctness, Robustness, Edge Cases and Input Validation (as per the General Assumptions and Limitations section above). 30 different test cases have been carried out to validate that the portfolio works as expected across the 4 categories.
Future improvements would include : 
- the ability to store the portfolio information to a file so that repeated entry (buy, updates, etc.) wouldn’t be required at every relaunch of the program (i.e. debugging, repeated use, etc.);
- Increased robustness validation of inputs for mismatching data type for scan statements; currently it simply closes the program, but would be more graceful if these cases were caught and the user re-prompted for correct input;
- A more stylized output of information when using the program so that the information would be more intuitive to read. This could be done through the use of spaces, text styling (Bold, italics, underline, color) and alignment of output.
