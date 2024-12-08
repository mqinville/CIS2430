package ePortfolio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList; // Import arraylist functionality
import java.util.HashMap; // Import hashmao functionality 
import java.util.Scanner; // Import scanning functionality
import java.util.HashSet; // Import HashSets to take intersection of array list in serach functinality
import java.util.InputMismatchException; // Input exception object to handle input mismatches

/**
* The Portfolio class represents a user's investment portfolio.
* It manages 2 seperate ArrayLists of stocks and mutual funds, allowing for the manipulation of these investments (e.g. buy/sell/update)
*/
public class Portfolio {
    // Private instance variables for our investinment portfolio
    private final ArrayList<Investment> investmentPortfolio = new ArrayList<>();   // Array list for stock investments
    private final HashMap<String, ArrayList<Integer>> keywordSearchIndex = new HashMap<>(); // Create hashmap to hold indices of name keywords from investment

    /**
     * No argument constructor for the portfolio class
     */
    public Portfolio() {
    }

    /**
     * Fetches the investment portfolio
     * @return the investment portfolio
     */
    public ArrayList<Investment> getInvestmentPortfolio() {
        return investmentPortfolio;
    }

    /**
     * Fetches one one investment from the investment portfolio
     * @param symbol the symbol of the investment we want to fetch
     * @return the investment object if it is found in the portfolio
     */
    public Investment getOneInvestment(String symbol) {
        for (Investment curInvestment : investmentPortfolio) {
            if (curInvestment.getSymbol().equalsIgnoreCase(symbol)) {
                return curInvestment;
            }
        }
        return null;
    }

    /**
     * Prompts the user to buy an investment (either stock or mutual fund).
     * It checks if the investment already exists in the portfolio. If it does,
     * the method adds to the existing investment. If it doesn't, a new investment
     * is created and added to the portfolio. 
     *  
     * @param type the type of investment (stock or mutual fund)
     * @param symbol the symbol of the investment
     * @param name the name of the investment
     * @param buyQuantity the quantity of the investment to buy
     * @param buyPrice the price of the investment
     * @throws IllegalArgumentException if the quantity or price is negative, or if the symbol is empty
     * @return a message indicating the success of the purchase
     */
    public String buyInvestment(String type, String symbol, String name, int buyQuantity, double buyPrice) throws IllegalArgumentException {
        Investment foundInvestment; // Investment object to hold the investent if it is already in list

        if (buyQuantity <= 0 || buyPrice <= 0) {
            throw new IllegalArgumentException("Quantity and/or price must be positive.");
        }
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol cannot be empty.");
        }

        foundInvestment = checkForSymbolInList(symbol); // Check if invesmtnent already exists, if it does store it in variable

        if (foundInvestment == null) {  // If the invesment doesnt exist in the portfolio then create new investment and add it to list    
            
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be empty.");
            }

            if (type.toLowerCase().equals("stock")) {
                Stock newStock = new Stock(symbol, name, buyQuantity, buyPrice);
                investmentPortfolio.add(newStock); // Add the new stock to the list
                updateKeywordIndex();
                return "New purchase! " + buyQuantity + " shares of " + symbol +" @"+ buyPrice + "$ successfully purchased!";             
            }
            else if (type.toLowerCase().equals("mutualfund")) {
                MutualFund newMutFund = new MutualFund(symbol, name, buyQuantity, buyPrice); // Create new mutual fund object
                investmentPortfolio.add(newMutFund); // Add the newly created mutfund to the list
                updateKeywordIndex();
                return "New purchase! " + buyQuantity + " units of " + symbol +" @"+ buyPrice + "$ successfully purchased!";
            }                
        } else {
            return foundInvestment.buyMore(buyPrice, buyQuantity); // If the investment already exists in the portfolio then buy more of it (Uses polymporphism)
        }
        return ""; // get rid of warning
    }    
    
    /**
     * Prompts the user to sell an investment given an investment symbol.
     * It checks if the investment already exists in the portfolio. If it does,
     * the method removes from the existing investment updating its properties accordingly
     * if the sell quantity is less than current quantity. If it is equal the stock is removed
     * from the portfolio.  
     *
     * @param symbol the symbol of the investment to sell
     * @param sellQuantity the quantity of the investment to sell
     * @param sellPrice the price at which the investment is sold
     * @throws IllegalArgumentException if the quantity or price is negative, or if the symbol is empty
     * @return a message indicating the success of the sale
     */
    public String sellInvestment(String symbol, int sellQuantity, double sellPrice) throws IllegalArgumentException {
        Investment foundInvestment = checkForSymbolInList(symbol);
        String saleMessage = ""; // String variable to hold the sale message

        // Throw exceptions for invalid inputs
        if (sellPrice <= 0) {
            throw new IllegalArgumentException("Price must be positive.");
        } 
        if (sellQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol cannot be empty.");
        }

        if (foundInvestment != null) { // If an investment with given symbol exosts in portfolio            
            // If an invalid sell quantity is given return from the function
            if (sellQuantity > foundInvestment.getQuantity()) {
                throw new IllegalArgumentException("Invalid sell quantity, cannot be greater than currently owned quantity.");
            } else if (sellQuantity == foundInvestment.getQuantity()) {
                if (foundInvestment instanceof Stock) {
                     saleMessage = printCompleteSaleInfo(sellPrice, ((Stock)foundInvestment).getSalePayment(), ((Stock)foundInvestment).getGain(), symbol, "stock"); // Print the sale info                    
                } else if (foundInvestment instanceof MutualFund) {
                    saleMessage = printCompleteSaleInfo(sellPrice, ((MutualFund)foundInvestment).getSalePayment(), ((MutualFund)foundInvestment).getGain(), symbol, "mutualfund"); // Print the sale info
                }
                investmentPortfolio.remove(foundInvestment); // Remove the investment from the list if we sell it completely
                updateKeywordIndex(); // Update the keyword search index after completely selling an investment
                return saleMessage;
            } else if (sellQuantity < foundInvestment.getQuantity()) {
                saleMessage = foundInvestment.sellSome(sellPrice, sellQuantity); // Sell some of the investment (polymorphism)
            }
        } else {
            throw new IllegalArgumentException("Investment not found in portfolio.");
        }
        return saleMessage;
    }

    /**
     * Updates the prices of all stocks and mutual funds in the portfolio.
     * This method prompts the user to enter a new price for each investment (both stocks and mutual funds).
     * It ensures that the new price is non-negative before updating the investment's price.
     * @param symbol symbol of the investment we want to update
     * @param newPrice the new price we want to update the investment to
     * @return the investment object if it is found in the portfolio
     */
    public Investment updateInvestmentPrices (String symbol, double newPrice) {
        if (symbol == null || symbol.isBlank()) {
            return null;
        }

        Investment foundInvestment = checkForSymbolInList(symbol);

        if (foundInvestment != null) {
            foundInvestment.setPrice(newPrice); // Update the price
            return foundInvestment; // Return the invetsment if it is found in the list
        } else {
            return null;
        }
    
        /*System.out.println("--- Updating Investment prices ---\n");
        for (Investment curInvestment : investmentPortfolio) {
            System.out.print("Updating the price for: " + curInvestment.getSymbol() + ". Enter the new price: ");
            newPrice = fetchInvestmentPrice(scanner);
            curInvestment.setPrice(newPrice);
        }*/
    }

    /**
     * Calculates and displays the total gains for the stock and mutual fund portfolios.
     * This method iterates through both the stock and mutual fund portfolios, printing
     * the gain for each individual investment and summing the total gains for each portfolio.
     * Finally, it displays the total gains for both the stock and mutual fund portfolios,
     * as well as the combined total gain for the entire investment portfolio.
     */
    public void getGainPortfolio() {
        double[] gains = {0.00, 0.00}; // Create integer array, index one holds sum of gains for stocks, index 2 for mutualfunds

        System.out.println("\n");

        // Calculate gain for stocks and investments
        for (Investment curInvestment : investmentPortfolio) {
            if (curInvestment instanceof Stock) {
                System.out.println("Gain for " + ((Stock)curInvestment).getSymbol() + ": " + String.format("%.2f", ((Stock)curInvestment).getGain()));
                gains[0] += ((Stock)curInvestment).getGain();
            } else if (curInvestment instanceof MutualFund) {   
                System.out.println("Gain for " + ((MutualFund)curInvestment).getSymbol() + ": " + String.format("%.2f", ((MutualFund)curInvestment).getGain()));
                gains[1] += ((MutualFund)curInvestment).getGain();
            }
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
     * @param symbol the symbol of the investment to search for
     * @param lowerBound the lower bound of the price range
     * @param upperBound the upper bound of the price range
     * @param keyWordsString the set of keywords to search for
     * @return an array list of investments that match the search criteria
     */
    public ArrayList<Investment> searchInvestmentPortfolio(String symbol, double lowerBound, double upperBound, String keyWordsString) {
        boolean searchFound = false; // Boolean variable to see to denote if a search was found
        ArrayList<Investment> searchResults = new ArrayList<>(); // Array list to hold the investments found in the search
        
        
        if (keyWordsString == null || keyWordsString.isBlank()) { // If the keywordString is null then search list sequantially
            // Loop through investments and print
            for (Investment curInvestment : investmentPortfolio) {
                if (curInvestment.equalsSearch(symbol, lowerBound, upperBound, keyWordsString)) {
                    searchFound = true;
                    searchResults.add(curInvestment);
                    //System.out.println(curInvestment);
                }
            }
        } else {
            ArrayList<Integer> searchIndex = getSearchIndex(keyWordsString); // Else if keywords string given get the improved serach index

            if (searchIndex != null) { // If the fetched serach index is not null proceed with search
                for (int i : searchIndex) {
                    Investment curInvestment = investmentPortfolio.get(i); // Get the investment in our improved array index
                    if (curInvestment.equalsSearch(symbol, lowerBound, upperBound, "")) {
                        searchFound = true;
                        searchResults.add(curInvestment);
                        //System.out.println(curInvestment); // Print the investment
                    }
    
                }
            }
        }

        if (searchFound == false) {
            System.out.println("\nNo search results found."); // print that no search results were found if check is false
        }

        return searchResults; // Return the arraylist containing the search results found
    }

    /**
     * Checks if an investment is present in the investment portfolio
     * If the investment is found in the investment portfolio then return said investment list
     * @param symbol the symbol to search for in the portfolio
     * @return the investment if found, else return null
     */
    private Investment checkForSymbolInList(String symbol) {
        for (Investment curInvestment : investmentPortfolio) {
            if (curInvestment.getSymbol().equalsIgnoreCase(symbol)) {
                return curInvestment; // Return the invesment if it is present in the list
            }
        }
        return null; // Return null indicating that the investment is not in the portfolio
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
                if (investmentPrice <= 0.00) {
                    System.out.println("Price must be positive.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Enter a valid double value.");
                scanner.next(); // Clear the buffer for invalid input
            } finally {
                scanner.nextLine(); // Clear newline in input stream
            }
        } while (investmentPrice <= 0.00);

        return investmentPrice;
    }
    
    /**
     * Helper function that gets the search index of investments we must find access given a keyword string
     * @param keywords string variable containing the keywords we will be searching for
     * @return an arrayList containing the indices of investment we must print
     */
    private ArrayList<Integer> getSearchIndex(String keywords) {    

        String[] keywordPartition = keywords.trim().split(" "); // Split the given keyword string at every space
        HashSet<Integer> searchIndex, intersection;// Sets that will hold arrayList indices for intersection

        if (keywordSearchIndex.get(keywordPartition[0]) == null) {
            return null; // Return null indicting no investment with keyword
        }

        searchIndex = new HashSet<>(keywordSearchIndex.get(keywordPartition[0]));
        
        for (String curWord : keywordPartition) { // Loop through all given keywords    
            intersection = new HashSet<>(keywordSearchIndex.get(curWord)); // get the array list of the current keywords index we want to intersect
            searchIndex.retainAll(intersection); // Take the intersection of both sets -- make our serahc index keep the elements it only has in common with the intersection set
        }

        return new ArrayList<Integer>(searchIndex); // Return the search index as an array
    }

    /**
     * Function that clears and builds the hashmap search index
     * We call this everytime a new stock is bought or when an investment is sold completely
     */
    private void updateKeywordIndex() {
        keywordSearchIndex.clear(); // Clear the serach index

        for (Investment curInvestment : investmentPortfolio) {
            String[] keywordSplit = curInvestment.getName().toLowerCase().split(" "); 
            for (String keyword : keywordSplit) {
                keywordSearchIndex.putIfAbsent(keyword, new ArrayList<Integer>());
                keywordSearchIndex.get(keyword).add(investmentPortfolio.indexOf(curInvestment));
            }
        }
    }
    
    /**
     * Save existing investment data to specified file declared in command line
     * Saves investment data with specified format in document
     * @param fileName string variable containing the filename we want to save our data too
     */
    public void saveInvestmentsToFile(String fileName) {
        try (PrintWriter fileWriter = new PrintWriter(new FileOutputStream(fileName))) {
            for (Investment curInvestment : investmentPortfolio){
                // Write the type of investment before info
                if (curInvestment instanceof Stock) {
                    fileWriter.println("type = \"stock\"");
                } else if (curInvestment instanceof MutualFund) {
                    fileWriter.println("type = \"mutualfund\"");
                }
                fileWriter.println(curInvestment.fileToString()); // Print the investment info to the file
            }
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
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
    private String printCompleteSaleInfo(double sellPrice, double payment, double gain, String symbol, String investmentType) {
        if (investmentType.equalsIgnoreCase("stock")) {
            return "All shares of " + symbol + " sold @" + sellPrice + "$!\n" +
                "Payment from sale: " + String.format("%.2f", payment) + "$.\n" +
                "Total gain from sale: " + String.format("%.2f", gain) + "$.\n" +
                "No more shares left, removing " + symbol + " from list\n";

        } else if ( investmentType.equalsIgnoreCase("mutualfund")) {
            return "All units of " + symbol + " sold @" + sellPrice + "$!\n" +
                "Payment from sale: " + String.format("%.2f", payment) + "$.\n" +
                "Total gain from sale: " + String.format("%.2f", gain) + "$.\n" +
                "No more units left, removing " + symbol + " from list\n";
        }
        return ""; // get rid of warning
    }


}

