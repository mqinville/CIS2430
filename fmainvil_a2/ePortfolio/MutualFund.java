package ePortfolio;

/**
 * The MutualFund class represents a mutual fund investment in the ePortfolio system.
 * It stores the symbol, name, quantity, price, and book value of the mutual fund, 
 * and provides methods to calculate gain, perform buy and sell operations, and compare 
 * mutual funds based on search criteria. 
 * The class also includes getters and setters for its attributes.
 */
public class MutualFund {

    // Declare common investment attributes - Make them private as we want to hide our investment data
    private String symbol; // This will hold the investment tag Eg. SSETX
    private String name; // This will hold the company/stock name Eg. BNY Mellon Growth Fund Class I
    private int quantity; // We define the quantities as integer, assume no fractional shares are allowed
    private double price; 
    private double bookValue;
    private static final double REDEMPTION_FEE = 45.00; // Constant double variable representing the redemption fee applied to mutual fund sell operations
    
    /**
     * MutualFund class constructor - Constructs a new mutualfund object with the specified symbol, name, quantity, and price.
     * The book value is calculated by multiplying the quantity by the price and adding the commission if applicableq.
     *
     * @param symbol the stock symbol, e.g., APPL for Apple
     * @param name the company name or stock name, e.g., Apple Inc.
     * @param quantity the number of shares purchased
     * @param price the price per share
     */
    public MutualFund(String symbol, String name, int quantity, double price) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = quantity * price;
    }

    /**
     * No argumentStock class constructor - Constructs a new Stock object
     */
    public MutualFund() {

    }

    /**
     * Calculates and returns the total gain from the mutualfund investment.
     * The gain is calculated as (quantity * current price - commission) - book value.
     *
     * @return the total gain from the investment
     */
    public double getGain() {
        return ((this.quantity * this.price) - REDEMPTION_FEE)- this.bookValue;
    }

    /**
     * Calculates and returns the total payment from the mutualfund sale.
     * The sale payment is calculated as (current price * current quantity) - book value.
     *
     * @return the total payment from the stock sale
     */
    public double getSalePayment(){
        return ((this.quantity * this.price) - REDEMPTION_FEE);
    }

    /**
     * Returns the mutualfund symbol as a string
     * @return the mutualfund symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the mutualfund name as a string
     * @return the mutualfund name 
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the amount of units owned of the mutualfund (Integer)
     * @return the quantity of the mutualfund
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the price of  the mutualfund (Double)
     * @return the price of the mutualfund
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the bookvalue of currently owned mutualfund  (Double)
     * @return the mutualfundd current book value
     */
    public double getBookValue() {
        return bookValue;
    }

    /**
     * Returns the redemption fee for sale of mutualfunds (Double)
     * @return the redemption fee
     */
    public double getRedemptionfee() {
        return REDEMPTION_FEE;
    }

    // Setters for each of instance variables

    /**
     * Sets the mutualfund symbol, if the provided symbol is valid (non-null, non-empty).
     * If the symbol is null or empty, an error message is printed, and the symbol is not updated.
     *
     * @param symbol the new mutualfund symbol to set (String)
     */
    public void setSymbol(String symbol) { 
        // Assign symbol if valid string is given
        if (symbol != null && !(symbol.isEmpty()) && symbol != " ") {
            this.symbol = symbol;
        } else { // else if string is empty print error message, assign nothing
            System.out.println("Mutualfund symbol cannot be empty/null.");
        }
    }

    /**
     * Sets the mutualfund name, if the provided name is valid (non-null, non-empty).
     * If the name is null or empty, an error message is printed, and the name is not updated.
     *
     * @param name the new mutualfund name to set (String)
     */
    public void setName(String name) {
        if (name != null && !(name.isEmpty()) && name != " ") {
            this.name = name;
        } else { // else if string is empty print error message, assign nothing
            System.out.println("Mutualfund name cannot be empty/null.");
        }
    }

    /**
     * Sets the mutualfund quantity, ensuring that the quantity is non-negative.
     * If the provided quantity is negative, an error message is printed, and the quantity is not updated.
     *
     * @param quantity the new quantity of mutualfund to set
     */
    public void setQuantity(int quantity) {
        // If a valid quantity is enetered (Cant have less than 0 of something)
        if (quantity >= 0){
            this.quantity = quantity;
        } else {
            System.out.println("Mutualfund quantity cannot be negative.");
        }
    }

    /**
     * Sets the mutualfund price, ensuring that the price is non-negative.
     * If the provided quantity is negative, an error message is printed, and the quantity is not updated.
     *
     * @param price the new quantity of mutualfund to set
     */
    public void setPrice(double price) {
        if (price >= 0.0){
            this.price = price;
        } else {
            System.out.println("Stock price cannot be negative.");
        }
    }

    /**
     * Updates the book value based on the price and quantity,.
     * If the price is negative or the quantity is less than or equal to 0, an error message is printed.
     *
     * @param price the price per unit used to update the book value
     * @param quantity the number of units used to update the book value
     */
    public void setBookValue(double price, int quantity) {
        if (price >= 0 && quantity > 0) {
            this.bookValue = price * quantity; // Update book value
        } else if (price < 0) {
            System.out.println("Price cannot be negative");
        } else {
            System.out.println("Quatity cannot be negative");
        }
    }

    /**
     * Adds more mutualfund investments by increasing the quantity and updating the book value.
     * This method allows the user to buy additional shares of an existing mutualfund at a given price.
     * The total quantity and book value are updated accordingly, and a message is printed
     * to confirm the successful purchase.
     *
     * @param price the price at which the additional units are purchased
     * @param quantity the number of additional units being purchased
     */
    public void buyMoreFunds(double price, int quantity) {
        this.price = price;
        this.quantity = this.quantity + quantity;
        this.bookValue = this.bookValue + (price * quantity);
        System.out.println("Additional " + quantity + " units of " + symbol +" @"+ price + "$ succesfully purchased!");
    }

    /**
     * Sells a specified quantity of mutualfund and updates the remaining quantity and book value.
     * The remaining units and book value are recalculated based on the quantity sold.
     * The method prints detailed information about the sale, including payment and the remaining stock.
     *
     * @param price the price at which the units are sold
     * @param quantity the number of units being sold
     */
    public void sellSomeMutualFunds(double price, int quantity) {
        int remainingQuantity = this.quantity - quantity; // Calculate remainging quantity
        double bookValueRemaining = this.bookValue * ((double)remainingQuantity/((double)this.quantity)); // Calculate the remaining bookvalue, typecast to avoid integer division
        double bookValueSold = this.bookValue - bookValueRemaining;

        // Print sale info
        System.out.println(quantity + " units of " + symbol + "sold @" + price + "$!");
        System.out.println("Payment from sale: " + String.format("%.2f",bookValueSold) +"$. Remaining book value is: " + String.format("%.2f",bookValueRemaining) + "$."); // Print payment from sale
        System.out.println("Removing " + quantity +" amount of units of " + symbol +" from list. There are " + remainingQuantity + " units left."); // Print removal message

        this.price = price;
        this.quantity = remainingQuantity;
        this.bookValue = bookValueRemaining;
    }

    public void setBookValue(double bookValue) {
        this.bookValue = bookValue;
    }

    /**
     * Returns true or false if a stocks symbol is the same as the one passed
     * @param priceRange string containg the price range we are comparing
     * @return true if the mutualfunds symbol matches argument
     */
    private boolean symbolMatch(String symbol) {
        // If the symbol is blank or empty, set the match to true 
        if (symbol == null || symbol.isBlank()) {
            return true;// Set the match for the symbol if the input was empty
        } else if (!symbol.isBlank()) { // If the symbol is not blank go through matching process
            return this.symbol.equalsIgnoreCase(symbol); // Set the variable of symbol match, true if it is equal to the symbol name
        }
        return true;
    }

    /**
     * Returns true or false if a mutualfunds price falls with passed price range
     * @param priceRange string containg the price range we are comparing
     * @return true if mutualfunds's price is within range false otherwise
     */
    private boolean priceMatch(String priceRange) {
        String[] partitionedPriceRange = new String[2]; // Create array of two string that will hole split od price range string, will determine the bound of the price match
        Double lowerBound = Double.NEGATIVE_INFINITY, upperBound=Double.POSITIVE_INFINITY; // Wrapper class doubles that will hold the nounds we are comparing

        if (priceRange == null || priceRange.isBlank()) {
            return true; // If passed string is emepty simply return true
        }

        priceRange = priceRange.replaceAll("\\s+", ""); // Replaces any spaces in the price range
        // Assign range bounds accordingly 
        if (priceRange.contains("-")) { // If the price range conatains a dash, deal with upper and lowe bound
            partitionedPriceRange = priceRange.split("-", 2);

            if (!partitionedPriceRange[0].isEmpty()) { // If the first string partitioned is not blank fetch lower ound
                lowerBound = Double.parseDouble(partitionedPriceRange[0]); // Assign the lower bound using the wrapper class
            }
             if (partitionedPriceRange.length > 1 && !partitionedPriceRange[1].isEmpty()) { // If the seond string partitioned is not blank, fetch lower bound
                upperBound = Double.parseDouble(partitionedPriceRange[1]); // Fetch the lower bound using wrapper class
            }

        } else if (!priceRange.contains("-")) { // If no dash is in the string deal with equals comparison
            // Make the upper and lower bound the same value
            lowerBound = Double.parseDouble(priceRange); 
            upperBound = Double.parseDouble(priceRange); 
        }
        return (this.price >= lowerBound) && (this.price <= upperBound); // Return the boolean expression
    }

    /**
     * Returns true or false if a mutualfunds name conatins given keywords 
     * @param priceRange string containg the keywords to compare with investment name
     * @return true or false if mutual fund is within price range
     */
    private boolean keywordMatch(String keyWordsString) {
        String[] partitionedKeyWords; // String array to hold the keyword splits
        String[] partitionedName; // String array that holds the split name
        boolean matchFound = false;


        if (keyWordsString == null || keyWordsString.isBlank()) {
            return true; // Return true if the keyword string is null or empty
        }

        partitionedKeyWords = keyWordsString.toLowerCase().trim().split("\\s+"); // Split the keyword string at every space and store each string into array
        partitionedName = this.name.toLowerCase().trim().split("\\s+"); // Split the keyword string at every space and store each string into array

        for (int i = 0; i < partitionedKeyWords.length; i++) {
            matchFound = false; // Reset the match found variable for every new keyword partition
            for (int j = 0; j < partitionedName.length; j++) {
                if (partitionedName[j].equalsIgnoreCase(partitionedKeyWords[i])) {
                    matchFound = true; // Set match to true if a word is present in the name
                    break; // Break from current iteration and continue
                }
            }
            if (matchFound == false) { // If no matchb is found then return false
                return false;
            }
        }

        return true; // Return true if all keywords are in the list
    }

    /**
     * Checks if the mutualfund matches the specified search criteria, including symbol, price range, and keywords in the name.
     * The method compares the mutualfund's symbol, checks if the price falls within a specified range, and verifies that all keywords 
     * appear in the mutualfund's name. If the criteria are met, it returns true.
     *
     * @param symbol the mutualfund symbol to match (can be blank or null to match any symbol)
     * @param priceRange the price range to match in the format "lower-upper" (can be blank or null to match any price)
     * @param keyWordsString the keywords to search for in the mutualfund name (can be blank or null to match any name)
     * @return true if the mutualfund matches the provided criteria, otherwise false
     */
    public boolean equals(String symbol, String priceRange, String keyWordsString) {
        boolean symbolMatch = false, priceRangeMatch = false, nameMatch = false; // Boolean variables tracking if arguments match

        symbolMatch = symbolMatch(symbol); // Check if the symbol is the same

        priceRangeMatch = priceMatch(priceRange); // Check if its withing price range

        nameMatch = keywordMatch(keyWordsString); // Check if the name matches
        
        return symbolMatch && priceRangeMatch && nameMatch; // Return the value of all checks in conjunction
    }

    /**
     * Returns a string representation of the mutualfund, including the symbol, name, quantity, price, and book value.
     * This method provides a detailed description of the mutualfund object.
     *
     * @return a string representing the mutualfund's details
     */
    public String toString() {
        return "\nMutualfund symbol: " + symbol +"\nMutual fund name: " + name + "\nQuantity: " + quantity + "\nPrice: " + String.format("%.2f", price) + "$\nbookValue: " + String.format("%.2f", bookValue) +"$";
    }
}
