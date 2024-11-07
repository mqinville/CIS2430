package ePortfolio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList; // Import arraylist functionality
import java.util.HashMap; // Import hashmao functionality 
import java.util.Scanner; // Import scanning functionality
import java.util.HashSet; // Import HashSets to take intersection of array list in serach functinality
import java.util.Set; // Import sets for array list intersections
import java.util.InputMismatchException; // Input exception object to handle input mismatches
import java.util.Map;

/**
* The Portfolio class represents a user's investment portfolio.
* It manages 2 seperate ArrayLists of stocks and mutual funds, allowing for the manipulation of these investments (e.g. buy/sell/update)
*/
public class Portfolio {
    // Private instance variables for our investinment portfolio
    private ArrayList<Investment> investmentPortfolio = new ArrayList<Investment>();   // Array list for stock investments
    private HashMap<String, ArrayList<Integer>> keywordSearchIndex; // Create hashmap to hold indices of name keywords from investment

    public Portfolio() {
        this.investmentPortfolio = new ArrayList<Investment>();    
        this.keywordSearchIndex = new HashMap<String, ArrayList<Integer>>();
    }

    /**
     * Prompts the user to buy an investment (either stock or mutual fund).
     * It checks if the investment already exists in the portfolio. If it does,
     * the method adds to the existing investment. If it doesn't, a new investment
     * is created and added to the portfolio. 
     *
     * @param scanner the Scanner object to read user input for investment information
     */
    public void buyInvestment(Scanner scanner) {
        Investment foundInvestment; // Investment object to hold the investent if it is already in list
        int buyQuantity; // Integer variables to store the quantity being bought and the position of an existing invesment in a stock
        double buyPrice; 
        String symbol, name, buyOption;

        System.out.print("\nWhat type of investment do you want to buy (stock/mutualfund): ");
        buyOption = scanner.nextLine().toLowerCase(); // Scan for invesment type and make lower case

        // If investment type is invalid return back to main
        if (!buyOption.equalsIgnoreCase("stock") && !buyOption.equalsIgnoreCase("mutualfund")) {
            System.out.println("Invalid invesment option, must be stock or mutualfund");
            return; // return back to main
        }

        symbol = fetchInvestmentSymbol(scanner); // Fetch user input for symbol
        buyQuantity = fetchInvestmentQuantity(scanner); // Fetch user input for quantitys
        buyPrice = fetchInvestmentPrice(scanner); // Fetch user input for the price

        foundInvestment = checkForSymbolInList(symbol); // Check if invesmtnent already exists, if it does store it in variable

        if (foundInvestment == null) {  // If the invesment doesnt exist in the portfolio then create new investment and add it to list
            name = fetchInvestmentName(scanner); // Fetch the name for the new invesment if not present in list
            
            switch(buyOption.toLowerCase()) {
                case "stock":
                    Stock newStock = new Stock(symbol, name, buyQuantity, buyPrice);
                    investmentPortfolio.add(newStock); // Add the new stock to the list
                    updateKeywordIndex();
                    System.out.println("New purchase! " + buyQuantity + " shares of " + symbol +" @"+ buyPrice + "$ successfully purchased!");                
                break;

                case "mutualfund":
                    MutualFund newMutFund = new MutualFund(symbol, name, buyQuantity, buyPrice); // Create new mutual fund object
                    investmentPortfolio.add(newMutFund); // Add the newly created mutfund to the list
                    updateKeywordIndex();
                    System.out.println("New purchase! " + buyQuantity + " units of " + symbol +" @"+ buyPrice + "$ successfully purchased!");
                break;

                default: 
                    System.out.println("Error occurred witht invesment type");
                break;
            }
        } else {
            if (foundInvestment instanceof Stock && buyOption.equalsIgnoreCase("stock")) { // If found investment is a stock update it variables
                ((Stock)foundInvestment).buyMoreStocks(buyPrice, buyQuantity); 
            } else if (foundInvestment instanceof MutualFund && buyOption.equalsIgnoreCase("mutualfund")) { // else updatevthe mututal fund variables
                ((MutualFund)foundInvestment).buyMoreFunds(buyPrice, buyQuantity);
            } else {
                System.out.println(symbol + " is already a symbol for other investment type.");
            }
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
        Investment foundInvestment;
        int sellQuantity; // Integer array that will hold the positions of where our investment is located. First index == stock, second == mutfund
        double sellPrice; // Variables that hold the price invesment is sold at
        String symbol; // Strings to hold the symbol and the type of invesment were dealing with

        symbol = fetchInvestmentSymbol(scanner); // Fetch user input for symbol
        foundInvestment = checkForSymbolInList(symbol);

        if (foundInvestment != null) { // If an investment with given symbol exosts in portfolio            
            sellQuantity = fetchInvestmentQuantity(scanner); // Fetch the amount of shares/units being sold
            sellPrice = fetchInvestmentPrice(scanner); // Fetch the price of the shares/units being sold

            // If an invalid sell quantity is given return from the function
            if (sellQuantity > foundInvestment.getQuantity()) {
                System.out.println("Error selling investment. Only " + foundInvestment.getQuantity() + "shares, cannot sell" + sellQuantity + "\n"); // Print error message 
                return; // return if sell quantity is greater than currently owned quantity

            } else if (sellQuantity == foundInvestment.getQuantity()) {
                if (foundInvestment instanceof Stock) {
                    printCompleteSaleInfo(sellPrice, ((Stock)foundInvestment).getSalePayment(), ((Stock)foundInvestment).getGain(), symbol, "stock"); // Print the sale info                    
                } else if (foundInvestment instanceof MutualFund) {
                    printCompleteSaleInfo(sellPrice, ((MutualFund)foundInvestment).getSalePayment(), ((MutualFund)foundInvestment).getGain(), symbol, "mutualfund"); // Print the sale info
                }
                investmentPortfolio.remove(foundInvestment); // Remove the investment from the list if we sell it completely
                updateKeywordIndex(); // Update the keyword search index after completely selling an investment
            } else if (sellQuantity < foundInvestment.getQuantity()) {
                if (foundInvestment instanceof Stock) { 
                    ((Stock)foundInvestment).sellSomeStocks(sellPrice, sellQuantity);
                } else if (foundInvestment instanceof MutualFund) {
                    ((MutualFund)foundInvestment).sellSomeMutualFunds(sellPrice, sellQuantity);
                }
            }
        } else {
            // Print that stock was not found in list if ivnalid symbol was given
            System.out.println("Error selling investment. " + symbol + " is not in the investment portfolio.\n");
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

        System.out.println("--- Updating Investment prices ---\n");
        for (Investment curInvestment : investmentPortfolio) {
            System.out.print("Updating the price for: " + curInvestment.getSymbol() + ". Enter the new price: ");
            newPrice = fetchInvestmentPrice(scanner);
            curInvestment.setPrice(newPrice);
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
     * @param scanner the Scanner object used to read user input for the search criteria
     */
    public void searchInvestmentPortfolio(Scanner scanner) {
        printKeywordSearchIndex();
        String symbol, priceRange, keyWordsString; // Strings that hold will hold info needed for invesment search
        boolean searchFound = false; // Boolean variable to see to denote if a search was found
        System.out.print("Enter a symbol for search: ");
        symbol = scanner.nextLine().toUpperCase().trim();

        System.out.print("Enter a price range for search: ");
        priceRange = scanner.nextLine();

        System.out.print("Enter a set of keywords for search: ");
        keyWordsString = scanner.nextLine().toLowerCase();
        System.out.println("\n");

        
        if (keyWordsString == null || keyWordsString.isBlank()) { // If the keywordString is null then search list sequantially
            // Loop through investments and print
            for (Investment curInvestment : investmentPortfolio) {
                if (curInvestment.equals(symbol,priceRange, keyWordsString)) {
                    searchFound = true;
                    System.out.println(curInvestment);
                }
            }
        } else {
            ArrayList<Integer> searchIndex = getSearchIndex(keyWordsString); // Else if keywords string given get the improved serach index

            if (searchIndex != null) { // If the fetched serach index is not null proceed with search
                for (int i : searchIndex) {
                    Investment curInvestment = investmentPortfolio.get(i); // Get the investment in our improved array index
                    if (curInvestment.equals(symbol, priceRange, "")) {
                        searchFound = true;
                        System.out.println(curInvestment); // Print the investment
                    }
    
                }
            }
        }   
        if (searchFound == false) {
            System.out.println("\nNo search results found."); // print that no search results were found if check is false
        }
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
     * Helper function that gets the search index of investments we must find access given a keyword string
     * @param keywords string variable containing the keywords we will be searching for
     * @return an arrayList containing the indices of investment we must print
     */
    private ArrayList<Integer> getSearchIndex(String keywords) {    

        String[] keywordPartition = keywords.trim().split(" "); // Split the given keyword string at every space
        Set<Integer> searchIndex, intersection;// Sets that will hold arrayList indices for intersection

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
        try (PrintWriter fileWriter = new PrintWriter(new FileWriter(fileName))) {
            for (Investment curInvestment : investmentPortfolio){
                // Write the type of investment before info
                if (curInvestment instanceof Stock) {
                    fileWriter.println("type = stock");
                } else if (curInvestment instanceof MutualFund) {
                    fileWriter.println("type = mutualfund");
                }
                fileWriter.println(curInvestment.fileToString()); // Print the investment info to the file
            }
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    /**
     * Fetches and reads in data for investments written to a textfile, read ALL investments in a file
     * @param fileName String variable containing the fileName of the file we must read from
     */
    public void readInvestmentsFromFile(String fileName) {
        Investment curInvestment; // Investmebt variable that will hold the latest nvestment read from file
        
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))){

            curInvestment = readInvestmentFromFile(fileReader); // Read the first investment from the file
            while (curInvestment != null) { // Keep reading while the investment is not null
                investmentPortfolio.add(curInvestment); // Add the current investment to the list if it is valid
                System.out.println(curInvestment);
                curInvestment = readInvestmentFromFile(fileReader); // Proceed reading the next investment in the list
            }
            updateKeywordIndex();
            return;
        } catch (FileNotFoundException e) {    
            System.out.println("File does not exist, will save on exit to new file name: " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading data from file.");
        } 
        return;
    }

    /**
     * Fetches information for data for a singular investment written to a textfile 
     * @param fileReader a bufferedReader object to read investment data from a file
     * @return an investment object with read investment data from the file
     * @throws IOException
     */
    public Investment readInvestmentFromFile(BufferedReader fileReader) throws IOException {
        String lineBeingRead;
        String[] lineReadSplit;
        String type, symbol, name;
        int quantity;
        double price, bookValue;  

        // Read the type of investment written to the file
        lineBeingRead = fileReader.readLine(); // Read the line and split it at the equals symbol
        if (lineBeingRead == null) {
            //if the file contains no more information return from the function
            return null;
        }

        lineReadSplit = lineBeingRead.split(" = ");
        //System.out.println(lineReadSplit[0]+ lineReadSplit[1]);
        type = lineReadSplit[1];

        // Read symbol
        lineBeingRead = fileReader.readLine(); // Read the line and split it at the equals symbol
        lineReadSplit = lineBeingRead.split(" = ");
        //System.out.println(lineReadSplit[0]+ lineReadSplit[1]);
        symbol = lineReadSplit[1];
        
        // Read name
        lineBeingRead = fileReader.readLine(); // Read the line and split it at the equals symbol
        lineReadSplit = lineBeingRead.split(" = ");
        //System.out.println(lineReadSplit[0]+ lineReadSplit[1]);
        name  = lineReadSplit[1];

        // Read quantity
        lineBeingRead = fileReader.readLine(); // Read the line and split it at the equals symbol
        lineReadSplit = lineBeingRead.split(" = ");
        //System.out.println(lineReadSplit[0]+ lineReadSplit[1]);
        quantity = Integer.parseInt(lineReadSplit[1]);

        // Read price
        lineBeingRead = fileReader.readLine(); // Read the line and split it at the equals symbol
        lineReadSplit = lineBeingRead.split(" = ");
        //System.out.println(lineReadSplit[0]+ lineReadSplit[1]);
        price = Double.parseDouble(lineReadSplit[1]);

        // Read bookValue
        lineBeingRead = fileReader.readLine(); // Read the line and split it at the equals symbol
        lineReadSplit = lineBeingRead.split(" = ");
        //System.out.println(lineReadSplit[0]+ lineReadSplit[1]);
        bookValue  = Double.parseDouble(lineReadSplit[1]);
        fileReader.readLine(); // Read the new line charcter

        // Return an instance of the investment
        if (type.equalsIgnoreCase("stock")) {
            return new Stock(symbol, name, quantity, price, bookValue);
        } else if (type.equalsIgnoreCase("mutualfund")) {
            return new MutualFund(symbol, name, quantity, price,bookValue);
        }
        return null;
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

    public void printKeywordSearchIndex() {
    if (keywordSearchIndex == null || keywordSearchIndex.isEmpty()) {
        System.out.println("The keyword search index is empty.");
        return;
    }

    // Loop through each entry in the HashMap
    for (Map.Entry<String, ArrayList<Integer>> entry : keywordSearchIndex.entrySet()) {
        String keyword = entry.getKey();
        ArrayList<Integer> indices = entry.getValue();
        
        System.out.print(keyword + ": ");
        
        // Print the list of indices for this keyword
        System.out.println(indices);
    }
}
}
