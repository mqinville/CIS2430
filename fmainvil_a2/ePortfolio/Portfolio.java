package ePortfolio;

import java.util.ArrayList; // Import arraylist functionality
import java.util.Scanner; // Import scanning functionality
import java.util.InputMismatchException;

/**
* The Portfolio class represents a user's investment portfolio.
* It manages 2 seperate ArrayLists of stocks and mutual funds, allowing for the manipulation of these investments (e.g. buy/sell/update)
*/
public class Portfolio {
    // Private instance variables for our investinment portfolio
    private ArrayList<Stock> stocksPortfolio = new ArrayList<Stock>();   // Array list for stock investments
    private ArrayList<MutualFund> mutualFundsPortfolio = new ArrayList<MutualFund>(); // Array list for mutual fund investments

    /**
     * Prompts the user to buy an investment (either stock or mutual fund).
     * It checks if the investment already exists in the portfolio. If it does,
     * the method adds to the existing investment. If it doesn't, a new investment
     * is created and added to the portfolio. 
     *
     * @param scanner the Scanner object to read user input for investment information
     */
    public void buyInvestment(Scanner scanner) {

        int buyQuantity = 0, investmentPoition; // Integer variables to store the quantity being bought and the position of an existing invesment in a stock
        double buyPrice = -0.01; 
        String symbol, name, buyOption;

        System.out.print("\nWhat type of investment do you want to buy (stock/mutualfund): ");
        buyOption = scanner.nextLine().toLowerCase(); // Scan for invesment type and make lower case

        // If investment type is invalid return back to main
        if (!buyOption.equalsIgnoreCase("stock") && !buyOption.equalsIgnoreCase("mutualfund")) {
            System.out.println("Invalid invesment option, must be stock or mutualfund");
            return; // return back to main
        }

        symbol = fetchInvestmentSymbol(scanner); // Fetch user input for symbol
        buyQuantity = fetchInvestmentQuantity(scanner); // Fetch user input for quantity
        buyPrice = fetchInvestmentPrice(scanner); // Fetch user input for the price

        switch (buyOption) {
            case "stock":
                investmentPoition = checkForSymbolInList(symbol, "stock");

                if (investmentPoition == -1) { // If the stsock doesnt already exist, create new object
                    if (checkForSymbolInList(symbol, "mutualfund") == -1) { // if the symbol is not in the mutual funds list proceed with new purchase
                    
                        name = fetchInvestmentName(scanner);
                        
                        Stock newStock = new Stock(symbol, name, buyQuantity, buyPrice);
                        stocksPortfolio.add(newStock);
                        System.out.println("New purchase! " + buyQuantity + " shares of " + symbol +" @"+ buyPrice + "$ successfully purchased!");
                        return;
                    } else {
                        System.out.println(symbol + " is already a mutual fund. Symbols must be unique.");
                        return;
                    }
                }
                stocksPortfolio.get(investmentPoition).buyMoreStocks(buyPrice, buyQuantity); // Buy additional stocks
            break;
            case "mutualfund":
                investmentPoition = checkForSymbolInList(symbol, "mutualfund");
                if (investmentPoition == -1) { // If the stsock doesnt already exist, create new object
                    if (checkForSymbolInList(symbol, "stock") == -1) { // If the symbol is not in the stock list proceed with new purchase
                        
                        name = fetchInvestmentName(scanner);

                        MutualFund newMutFund = new MutualFund(symbol, name, buyQuantity, buyPrice); // Instantiate the new mutualfund object
                        mutualFundsPortfolio.add(newMutFund); // Add new mutual fund to the list
                        System.out.println("New purchase! " + buyQuantity + " units of " + symbol +" @"+ buyPrice + "$ successfully purchased!");
                        return;
                    } else {
                        System.out.println(symbol + " is already a stock. Symbols must be unique.");
                        return;
                    }
                }
                mutualFundsPortfolio.get(investmentPoition).buyMoreFunds(buyPrice, buyQuantity); // Buy additional mutualfunds
            break;
        }    
    }

    /**
     * Prompts the user to sell an investment given an investment symbol.
     * It checks if the investment already exists in the portfolio. If it does,
     * the method removes from the existing investment updating its properties accordingly
     * if the sell quantity is less than current quantity. If it is equal the stock is removed
     * from the portfolio.  
     *
     * @param scanner the Scanner object to read user input for sale information
     */
    public void sellInvestment(Scanner scanner) {
        int sellQuantity, investmentPosition; // Integer array that will hold the positions of where our investment is located. First index == stock, second == mutfund
        double sellPrice; // Variables that hold the price invesment is sold at
        String symbol, investmentType; // Strings to hold the symbol and the type of invesment were dealing with

        symbol = fetchInvestmentSymbol(scanner); // Fetch user input for symbol

        investmentType = checkForSymbolInBothList(symbol); //  Check 

        if (!investmentType.equalsIgnoreCase("none")) { // If the symbol is in portfolio proceed with sale
            investmentPosition = checkForSymbolInList(symbol, investmentType); // Store the position of the investment
            
            sellQuantity = fetchInvestmentQuantity(scanner); // Fetch the amount of shares/units being sold
            sellPrice = fetchInvestmentPrice(scanner); // Fetch the price of the shares/units being sold

            // Decide which stock we are selling
            switch(investmentType) {
                case "stock":
                    Stock sellStock = stocksPortfolio.get(investmentPosition); // Fetch the stock at invesment positon

                    if (sellStock.getQuantity() == sellQuantity) { // If quantity being sold is the same as current stock quantity remove it from the list
                        printCompleteSaleInfo(sellPrice, sellStock.getSalePayment(), sellStock.getGain(), symbol, investmentType); // Print the sale info
                        stocksPortfolio.remove(investmentPosition); // Remove the stock from the list if sold completely
                    } else if (sellQuantity < sellStock.getQuantity()) { // If the sell quantity is less then the current quantity update the investment
                        sellStock.sellSomeStocks(sellPrice, sellQuantity); // Update investment
                    } else {    // If the quantity is greater than the amount of shares the invesment has print error message
                        System.out.println("Only " + sellStock.getQuantity() + " shares of "+ symbol + ". Cannot sell " + sellQuantity +".");
                    }
                break;
                    case "mutualfund":
                    MutualFund sellMutFund= mutualFundsPortfolio.get(investmentPosition); // Fetch the stock at invesment positon
                    
                    if (sellMutFund.getQuantity() == sellQuantity) { // If quantity being sold is the same as current mutualfund quantity remove it from the list
                        printCompleteSaleInfo(sellPrice, sellMutFund.getSalePayment(), sellMutFund.getGain(), symbol, investmentType);
                        mutualFundsPortfolio.remove(investmentPosition); // Remove the mutual fund from the list
                    } else if (sellQuantity < sellMutFund.getQuantity()) {
                        sellMutFund.sellSomeMutualFunds(sellPrice, sellQuantity);
                    } else {
                        System.out.println("Only " + sellMutFund.getQuantity() + " units of "+ symbol + ". Cannot sell " + sellQuantity +".");
                    }
                break;
            }
        } else {
            // Print that stock was not found if co
            System.out.println("Error selling investment. " + symbol + " is not in the portfolio. ");
        }
        return;
    }

    /**
     * Updates the prices of all stocks and mutual funds in the portfolio.
     * This method prompts the user to enter a new price for each investment (both stocks and mutual funds).
     * It ensures that the new price is non-negative before updating the investment's price.
     * @param scanner the Scanner object used to read user input for the new prices
     */
    public void updateInvestmentPrices (Scanner scanner) {
        double newPrice; // Variavle that will hold the the price of the new investments

        System.out.println("--- Updating stock prices ---\n");

        for (int i = 0; i < stocksPortfolio.size(); i++) {
            System.out.println("Updating the price for: " + stocksPortfolio.get(i).getSymbol());
            newPrice = fetchInvestmentPrice(scanner);
            stocksPortfolio.get(i).setPrice(newPrice); // Update the price
        }

        System.out.println("--- Updating mutual fund prices ---\n");

        for (int i = 0; i < mutualFundsPortfolio.size(); i++) {
            System.out.println("Updating the price for: " + mutualFundsPortfolio.get(i).getSymbol());
            newPrice = fetchInvestmentPrice(scanner);
            mutualFundsPortfolio.get(i).setPrice(newPrice); // Update the price
        }
    }

    /**
     * Calculates and displays the total gains for the stock and mutual fund portfolios.
     * This method iterates through both the stock and mutual fund portfolios, printing
     * the gain for each individual investment and summing the total gains for each portfolio.
     * Finally, it displays the total gains for both the stock and mutual fund portfolios,
     * as well as the combined total gain for the entire investment portfolio.
     */
    public void getGainPortfolio() {

        double[] gains = {0.00,0.00}; // Create integer array, index one holds sum of gains for stocks, index 2 for mutualfunds

        System.out.println("\n");

        for (int i = 0; i < stocksPortfolio.size(); i++) {
            Stock curStock = stocksPortfolio.get(i);
            System.out.println("Gain for " + curStock.getSymbol() + ": " + String.format("%.2f", curStock.getGain()));
            gains[0] += curStock.getGain(); // Sum the gain for each stock
        }

        for (int i = 0; i < mutualFundsPortfolio.size(); i++) {
            MutualFund curMutFund = mutualFundsPortfolio.get(i);
            System.out.println("Gain for " + curMutFund.getSymbol() + ": " + String.format("%.2f", curMutFund.getGain()));
            gains[1] += curMutFund.getGain(); // Sum the gain for each stock
        }
        
        System.out.println("\n------------ PORTFOLIO GAINS ------------");
        System.out.println("Stock portfolio gains: " +  String.format("%.2f", gains[0]));
        System.out.println("Mutual funds portfolio gains: " + String.format("%.2f", gains[1]));
        System.out.println("Total investment portfolio gains: " + String.format("%.2f", gains[0]+gains[1]));
        System.out.println("------------ PORTFOLIO GAINS ------------");
    }

    /**
     * Searches the investment portfolio for stocks and mutual funds that match the user's input.
     * The user is prompted to enter a symbol, price range, and set of keywords for the search.
     * The method checks each investment (both stocks and mutual funds) to see if it matches
     * the provided criteria. If a match is found, the investment's details are printed.
     * If no matches are found, a message is displayed indicating that no results were found.
     *
     * @param scanner the Scanner object used to read user input for the search criteria
     */
    public void searchInvestmentPortfolio(Scanner scanner) {

        String symbol, priceRange, keyWordsString; // Strings that hold will hold info needed for invesment search
        boolean searchFound = false; // Boolean variable to see to denote if a search was found
        System.out.print("Enter a symbol for search: ");
        symbol = scanner.nextLine().toUpperCase().trim();

        System.out.print("Enter a price range for search: ");
        priceRange = scanner.nextLine();

        System.out.print("Enter a set of keywords for search: ");
        keyWordsString = scanner.nextLine().toLowerCase();
        System.out.println("\n");

        // Loop through stock investments
        for (int i = 0; i < stocksPortfolio.size(); i ++) { // Loop through the stock array
            if (stocksPortfolio.get(i).equals(symbol, priceRange, keyWordsString)) {
                searchFound = true;
                System.out.println(stocksPortfolio.get(i).toString() + "\n"); // If the the stock fits the given infromation, print the stock info
            }
        }

        // Loop through mutualfund investments
        for (int i = 0; i < mutualFundsPortfolio.size(); i ++) { // Loop through the stock array
            if (mutualFundsPortfolio.get(i).equals(symbol, priceRange, keyWordsString)) {
                searchFound =  true;
                System.out.println(mutualFundsPortfolio.get(i).toString() + "\n"); // If the the mtutualfund fits the given infromation, print the mutuualfund info
            }
        }

        if (searchFound == false) {
            System.out.println("\nNo search results found."); // print that no search results were found if check is false
        }

    }

    /**
     * Checks if a symbol is present in either the stock or mutual fund list, based on the specified type.
     * If the symbol is found in the appropriate list, its position (index) in that list is returned.
     * If the symbol is not found, the method returns -1.
     *
     * @param symbol the symbol to search for in the portfolio
     * @param type the type of investment ("stock" or "mutualfund") to determine which list to search
     * @return the index position of the symbol in the specified list, or -1 if the symbol is not found
     */
    private int checkForSymbolInList(String symbol, String type) {

        int position; // Will store the index of the invesment within its own list

        // If boolean variable is true, we check for the symbol being in the stock list;
        if (type.equalsIgnoreCase("stock")) { // stock list
            for (int i = 0; i < stocksPortfolio.size(); i++) {
                Stock curStock = stocksPortfolio.get(i);
                if(curStock.getSymbol().equalsIgnoreCase(symbol)) {
                    position = i;
                    return position; // Return true if the symbol is present in the stock list
                }
            }
        } else if (type.equalsIgnoreCase("mutualfund")) { // If false, chexk to see if symbol is in mutualfunds list
            for (int i = 0; i < mutualFundsPortfolio.size(); i++) {
                MutualFund curMutFund = mutualFundsPortfolio.get(i);
                if (curMutFund.getSymbol().equalsIgnoreCase(symbol)) {
                    position = i;
                    return position; // Return true if the mutual fund is in the mutual funds list
                }
            }
        }
        return -1; // return -1 indicating invesment is not in a list
    }

    /**
     * Checks if a symbol is present in either the stock or mutual fund list.
     * If the symbol is found in the stock list, the method returns "stock".
     * If the symbol is found in the mutual fund list, the method returns "mutualfund".
     * If the symbol is not found in either list, the method returns "none".
     *
     * @param symbol the symbol to search for in both the stock and mutual fund lists
     * @return "stock" if the symbol is found in the stock list, "mutualfund" if found in the mutual fund list, 
     *         or "none" if the symbol is not found in either list
     */
    private String checkForSymbolInBothList(String symbol) {
        if (checkForSymbolInList(symbol, "stock") != -1) {
            return "stock";
        }  else if (checkForSymbolInList(symbol, "mutualfund") != -1) {
            return "mutualfund"; 
        } else {
            return "none";
        }
    }

    /**
     * Fetches user input for an investment symbol. Traps invalid inputs for symbol.
     * @param scanner the scanner used to get the input
     * @return the inputted label for the investment
     */
    private static String fetchInvestmentSymbol (Scanner scanner) {
        String investmentSymbol;
        do {
            System.out.print("\nEnter the symbol: ");
            investmentSymbol = scanner.nextLine().toUpperCase().trim();
        } while (investmentSymbol.isBlank() || investmentSymbol == null);

        investmentSymbol = investmentSymbol.trim(); // Remove any leading or trailing spaces
        return investmentSymbol;
    }

    /**
     * Fetches user input for an investment name. Traps invalid inputs for the name.
     * @param scanner the scanner used to get the input
     * @return the inputted name for the investment
     */
    private static String fetchInvestmentName (Scanner scanner) {
        String investmentName;
        do {
            System.out.print("This is a new investment. Name required: ");
            investmentName = scanner.nextLine();
        } while (investmentName == null || investmentName.isBlank());

        investmentName = investmentName.trim();
        return investmentName;
    }

    /**
     * Fetches user input for investment quantities. Traps invalid inputs such as mismatching type/negative values
     * @param scanner the scanner used to get the input
     * @return the inputted quantity for the investment
     */
    private static int fetchInvestmentQuantity(Scanner scanner) {
        int investmentQuantity = 0;
        
        do {
            System.out.print("\nEnter the quantity: ");
            try {
                investmentQuantity = scanner.nextInt();
                if((investmentQuantity <= 0)) {
                    System.out.println("Quantity must be greater than 0.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid integer.");
                scanner.next(); // Clear the buffer for invalid input
            } finally {
                scanner.nextLine(); // Clear newline in input stream
            }
        } while (investmentQuantity <= 0);

        return investmentQuantity;
    }
    
    /**
     * Fetches user input for investment prices. Traps invalid inputs such as mismatching type/negative values
     * @param scanner the scanner used to get the input
     * @return the inputted price for the investment
     */
    private static double fetchInvestmentPrice(Scanner scanner) {
        double investmentPrice = -1.0;

        do {
            System.out.print("\nEnter the price: ");
            try {   
                investmentPrice = scanner.nextDouble();
                if (investmentPrice < 0.00) {
                    System.out.println("Price cannot be negative.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid double value.");
                scanner.next(); // Clear the buffer for invalid input
            } finally {
                scanner.nextLine(); // Clear newline in input stream
            }
        } while (investmentPrice < 0.00);

        return investmentPrice;
    }


    /**
     * Prints the information for a completed sale, when an investment (either stock or mutual fund)
     * has been fully sold and removed from the portfolio.
     * The method prints the details of the sale, including the sale price, total payment received,
     * and the gain from the sale. It also informs the user that the investment is being removed from the portfolio.
     *
     * @param sellPrice the price at which the investment was sold
     * @param payment the total payment received from the sale
     * @param gain the total gain from the sale
     * @param symbol the symbol of the investment that was sold
     * @param investmentType the type of investment ("stock" or "mutualfund")
     */
    private void printCompleteSaleInfo(double sellPrice, double payment, double gain, String symbol, String investmentType) {
        if (investmentType.equalsIgnoreCase("stock")) {
            System.out.println("All shares of " + symbol + " sold @" + sellPrice +"$!");
            System.out.println("Payment from sale: " + String.format("%.2f",payment) + "$."); // Print payment from sale
            System.out.println("Total gain from sale: " + String.format("%.2f",gain) + "$."); // Print total gain after sale
            System.out.println("No more shares left, removing " + symbol +" from list"); // Print removal message

        } else if ( investmentType.equalsIgnoreCase("mutualfund")) {
            System.out.println("All units of " + symbol + " sold @" + sellPrice +"$!");
            System.out.println("Payment from sale: " + String.format("%.2f",payment) + "$."); // Print payment from sale
            System.out.println("Total gain from sale: " + String.format("%.2f",gain) + "$."); // Print total gain after sale
            System.out.println("No more shares left, removing " + symbol +" from list"); // Print removal message
        }
    }  
}
