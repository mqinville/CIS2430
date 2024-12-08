package ePortfolio;

/**
 * The abstracted Investment class represents the superclass for all investments.
 * It stores the symbol, name, quantity, price, and book value of the investment
 * and provides methods to calculate gain, perform buy and sell operations, and compare 
 * mutual funds based on search criteria. NOTE: For the investment superclass, commision 
 * taxes are not applied to buy or sell operations. The class also includes getters and 
 * setters for its attributes.
 * 
 * NOTE: We define instance variables as private as we do not want investment data to be accessible directly from our main or portfolio class.
 *       We keep the instance data private and access them with the corresponding mutator/accessor methods with the super constructor when they need to be referenced.
 *       While protected encapsulation would suffice, referencing them with the 'this.<variable>' method would hinder the transparency of which objects are being referenced and where.
 */
public abstract class Investment {
    // Declare common investment attributes - Make them private as we want to hide our investment data
    private String symbol; // This will hold the investment tag Eg. APPL for appke
    private String name; // This will hold the company/innvestment name Eg. Apple Inc.
    private int quantity; // We define the quantities as integer, assume no fractional shares are allowed
    private double price;  
    private double bookValue;

    // Declare abstract methods to be implemented by subclasses
    public abstract double getGain(); 
    public abstract double calcBookValue(); 
    public abstract double getPayment(); 
    public abstract String buyMore(double price, int quantity);
    public abstract String sellSome(double price, int quantity);

    /**
     * Investment class constructor - Constructs a new Investment object with the specified symbol, name, quantity, and price.
     * The book value is calculated by multiplying the quantity by the price, NOTE: commision taxes are not added in the general Investment class 
     * @param symbol the investment symbol, e.g., APPL for Apple
     * @param name the company name or investment name, e.g., Apple Inc.
     * @param quantity the number of shares purchased
     * @param price the price per share
     */
    public Investment (String symbol, String name, int quantity, double price) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = (quantity * price);  // calculate and assign bookvalue DOES NOT APPLY INDIVIDUAL COMMISION TAXES
    }

    /**
     * Clone constructor - Constructs a new Investment object that is a clone of the specified investment.
     * @param investment the investment object to be cloned
     */
    public Investment(Investment investment) {
        this.symbol = investment.symbol;
        this.name = investment.name;
        this.quantity = investment.quantity;
        this.price = investment.price;
        this.bookValue = investment.bookValue;
    }

    /**
     * Investment class constructor - Constructs a new Investment object with the specified symbol, name, quantity, price and bookvalue.
     * The book value is set to the argument passed 
     * @param symbol the investment symbol, e.g., APPL for Apple
     * @param name the company name or investment name, e.g., Apple Inc.
     * @param quantity the number of shares purchased
     * @param price the price per share
     * @param bookValue the book value of the investment being read
     */
    public Investment (String symbol, String name, int quantity, double price, double bookValue) throws IllegalArgumentException {

        if (quantity < 0 || price < 0) {
            throw new IllegalArgumentException("Quantity and price must be positive values.");
        }

        if (symbol == null || name == null || symbol.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException("Symbol and name cant be null");
        }

        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = bookValue;  // calculate and assign bookvalue DOES NOT APPLY INDIVIDUAL COMMISION TAXES
    }

    /**
     * No argument investment class constructor - constructs new investment
     */
    public Investment() {

    }


    /**
     * Returns a string representation of the investment, including the symbol, name, quantity, price, and book value.
     * This method provides a detailed description of the investment object.
     *
     * @return a string representing the investment's details
     */
    @Override
    public String toString() {
        return "Investment symbol: " + this.symbol +
             "\nInvesment name: " + this.name + 
             "\nQuantity: " + this.quantity + 
             "\nPrice: " + String.format("%.2f", this.price) + "$" +
             "\nbookValue: " + String.format("%.2f", this.bookValue) + "$\n";
    }

    /**
     * Returns a string representation of the investment to write to file, including the symbol, name, quantity, price, and book value.
     * This method provides a detailed description of the investment object, formatted to be written to a file.
     *
     * @return a string representing the investment's details to be written to a file
     */
    public String fileToString() {
        return "symbol = \"" + this.symbol + "\"" +
             "\nname = \"" + this.name + "\"" +
             "\nquantity = \"" + this.quantity + "\"" +
             "\nprice = \"" + String.format("%.2f", this.price) + "\"" +
             "\nbookValue = \"" + String.format("%.2f", this.bookValue) + "\"\n";
    }

    /**
     * Updates the quantity of an investment for buy/sell operations on investments
     * @param addedQuantity the quantity to be added
     */
    public void updateQuantity (int addedQuantity) {
        this.quantity = this.quantity + addedQuantity; // Add the new quatities together
    }


    /**
     * Returns true or false if a stocks symbol is the same as the one passed
     * @param symbol string containg the symbol we are comparing
     * @return a string representing the stock's details
     */
    public boolean symbolMatch(String symbol) {
        // If the symbol is blank or empty, set the match to true 
        if (symbol == null || symbol.isBlank()) {
            return true;// Set the match for the symbol if the input was empty
        } else if (!symbol.isBlank()) { // If the symbol is not blank go through matching process
            return this.symbol.equalsIgnoreCase(symbol); // Set the variable of symbol match, true if it is equal to the symbol name
        }
        return true;
    }

    /**
     * Returns true or false if a stocks price falls with passed price range
     * @param priceRange string containg the price range we are comparing
     * @return a string representing the stock's details
     */
    public boolean priceMatch(double lowerBound, double upperBound) {
        return (this.price >= lowerBound) && (this.price <= upperBound); // Return the boolean expression
    }

    /**
     * Returns true or false if a stocks name conatins given keywords 
     * @param priceRange string containg the keywords to compare with investment name
     * @return true or false if withtin stock's price range
     */
    public boolean keywordMatch(String keyWordsString) {
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

    
    @Override
    /**
     * Compares the current investment object with another object to determine if they are equal.
     * The method compares the investment's symbol with the other object's symbol.
     *
     * @param otherObject the object to compare with
     * @return true if the objects are equal, otherwise false
     */
    public boolean equals(Object otherObject) {

        if (this == otherObject) {
            return true; // If the current instance is the object being compared return true
        } else if ((otherObject == null) || (otherObject.getClass() != this.getClass())) {
            return false; // If the object being compared is null or not of the same class return false
        } 
        
        return this.symbol.equalsIgnoreCase(((Investment)otherObject).symbol);
    }

    /**
     * Checks if the stock matches the specified search criteria, including symbol, price range, and keywords in the name.
     * The method compares the stock's symbol, checks if the price falls within a specified range, and verifies that all keywords 
     * appear in the stock's name. If the criteria are met, it returns true.
     *
     * @param symbol the stock symbol to match (can be blank or null to match any symbol)
     * @param priceRange the price range to match in the format "lower-upper" (can be blank or null to match any price)
     * @param keyWordsString the keywords to search for in the stock name (can be blank or null to match any name)
     * @return true if the stock matches the provided criteria, otherwise false
     */
    public boolean equalsSearch(String symbol, double lowerBound, double upperBound, String keyWordsString) {
        boolean symbolMatch = false, priceRangeMatch = false, nameMatch = false; // Boolean variables tracking if arguments match

        symbolMatch = symbolMatch(symbol); // Check if the symbol is the same

        priceRangeMatch = priceMatch(lowerBound,upperBound); // Check if its withing price range

        nameMatch = keywordMatch(keyWordsString); // Check if the name matches
        
        return symbolMatch && priceRangeMatch && nameMatch; // Return the value of all checks in conjunction
    }

    // GETTERS

    /**
     * Returns the investment symbol as a string
     * @return the investment symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * Returns the investment name as a string
     * @return the investment name 
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the amount of shares owned of the investment (Integer)
     * @return the quantity of the investment
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Returns the price of  the investment (Double)
     * @return the price of the investment
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the bookvalue of currently owned investment units  (Double)
     * @return the investments current book value
     */
    public double getBookValue() {
        return bookValue;
    }

    // SETTERS
    
    /**
     * Sets the stock symbol, if the provided symbol is valid (non-null, non-empty).
     * If the symbol is null or empty, an error message is printed, and the symbol is not updated.
     * @param symbol the new stock symbol to set (String)
     * @return true is the passed symbol is valid
     */
    public boolean setSymbol(String symbol) throws IllegalArgumentException {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol cannot be null or empty");
        }

        this.symbol = symbol;
        return true;
    }

    /**
     * Sets the stock symbol, if the provided symbol is valid (non-null, non-empty).
     * If the symbol is null or empty, an error message is printed, and the symbol is not updated.
     *  @return true if passed name is valid
     * @param symbol the new stock symbol to set (String)
     */
    public boolean setName(String name) throws IllegalArgumentException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }

        this.name = name;
        return true;
    }

    /**
     * Sets the MutualFund quantity, ensuring that the quantity is non-negative.
     * If the provided quantity is negative, an error message is printed, and the quantity is not updated.
     *
     * @param quantity the new quantity of MutualFund to set
     * @return truee if passed quantity is valid
     */
    public boolean setQuantity(int quantity) throws IllegalArgumentException {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        this.quantity = quantity;
        return true;
    }

    /**
     * Sets the stock price, ensuring that the price is non-negative.
     * If the provided quantity is negative, an error message is printed, and the quantity is not updated.
     *
     * @param price the new quantity of stock to set
     * @return true if the passed price was valid
     */
    public boolean setPrice(double price) throws IllegalArgumentException {
        if (price >= 0.0){
            this.price = price;
            return true;
        } else {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    /**
     * Updates the book value based on the given bookvalue.
     * @param bookValue The value of the new bookvlaue to be set
     * @return true if valid bookvalue was passsed
     */
    public boolean setBookValue(double bookValue) throws IllegalArgumentException {
        
        if (bookValue < 0) {
            throw new IllegalArgumentException("Book value cannot be negative");
        }

        this.bookValue = bookValue;
        return true;
    }

    /**
     * Updates the book value based on the given price and quantity. NOTE: COmmisions taxes are not applied in the parent class
     * @param bookValue The value of the new bookvlaue to be set
     * 
     * @return true if valid bookvalue price and quantity were passsed
     */
    public boolean setBookValue(double price, int quantity) throws IllegalArgumentException {
        if (price >= 0 && quantity > 0) {
            this.bookValue = (price * quantity); // Update book value, commision not applied
        } else if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        } else {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        return true; // return true indicating valid set
    }
}
