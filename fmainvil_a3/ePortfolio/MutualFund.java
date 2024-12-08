package ePortfolio;
/**
 * The MutualFund class represents a mutual fund investment in the ePortfolio system.
 * It stores the symbol, name, quantity, price, and book value of the mutual fund, 
 * and provides methods to calculate gain, perform buy and sell operations, and compare 
 * mutual funds based on search criteria. 
 * The class also includes getters and setters for its attributes.
 */
public class MutualFund extends Investment {

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
        super(symbol, name, quantity, price);
    }

    /**
     * MutualFund class constructor - Constructs a new mutualfund object with the specified symbol, name, quantity, price and bookValue.
     * The book value is set to the bookvalue argument passed
     *
     * @param symbol the stock symbol, e.g., APPL for Apple
     * @param name the company name or stock name, e.g., Apple Inc.
     * @param quantity the number of shares purchased
     * @param price the price per share
     * @param bookValue the stocks new bookValue
     */
    public MutualFund(String symbol, String name, int quantity, double price, double bookValue) {
        super(symbol, name, quantity, price, bookValue);
    }


    /**
     * MutualFund class copy constructor - Constructs a new mutualfund object with the same attributes as the provided mutualfund object.
     *
     * @param mutualFund the mutualfund object to be copied
     */
    public MutualFund(MutualFund mutualFund) {
        super(mutualFund.getSymbol(), mutualFund.getName(), mutualFund.getQuantity(), mutualFund.getPrice(), mutualFund.getBookValue());
    }


    /**
     * No argumentStock class constructor - Constructs a new Stock object
     */
    public MutualFund() {
        super();
    }

    /**
     * Calculates and returns the total payment from the mutualfund sale.
     * The sale payment is calculated as (current price * current quantity) - book value.
     *
     * @return the total payment from the stock sale
     */
    public double getSalePayment(){
        return ((super.getPrice() * super.getQuantity()) - REDEMPTION_FEE);
    }

    /**
     * Returns the redemption fee for sale of mutualfunds (Double)
     * @return the redemption fee
     */
    public double getRedemptionfee() {
        return REDEMPTION_FEE;
    }

    /**
     * Calculates and returns the book value of the mutualfund investment.
     * The book value is calculated as the price per unit multiplied by the quantity.
     * @return the book value of the mutualfund investment
     */
    @Override
    public double calcBookValue(){
        return (super.getPrice() * super.getQuantity());
    }
    
    /**
     * Calculates and returns the total gain from the mutualfund investment.
     * The gain is calculated as (quantity * current price - redemption fee) - book value.
     * Calls to the super constructor are ade to fetch values for price, quantity and book value.
     * 
     * @return the total gain from the investment
     */
    @Override
    public double getGain() {
        return ((super.getPrice() * super.getQuantity()) - REDEMPTION_FEE) - super.getBookValue();
    }

    /**
     * Calculates and returns the total payment from the mutualfund sale.
     * The sale payment is calculated as (current price * current quantity) - redemption fee.
     *
     * @return the total payment from the stock sale
     */
    @Override
    public double getPayment() {
        return ((super.getPrice() * super.getQuantity()) - REDEMPTION_FEE);
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
    public String buyMore(double price, int quantity) {
        super.setPrice(price);
        super.setQuantity(super.getQuantity() + quantity); // Update the quantity
        super.setBookValue(super.getBookValue() + (price * quantity));
        return "Additional " + quantity + " units of " + super.getSymbol() +" @"+ price + "$ succesfully purchased!";
    }

    /**
     * Sells a specified quantity of mutualfund and updates the remaining quantity and book value.
     * The remaining units and book value are recalculated based on the quantity sold.
     * The method prints detailed information about the sale, including payment and the remaining stock.
     *
     * @param price the price at which the units are sold
     * @param quantity the number of units being sold
     */
    public String sellSome(double price, int quantity) {
        int remainingQuantity = super.getQuantity() - quantity; // Calculate remainging quantity
        double bookValueRemaining = super.getBookValue() * ((double)remainingQuantity/((double)super.getQuantity())); // Calculate the remaining bookvalue, typecast to avoid integer division
        double bookValueSold = super.getBookValue() - bookValueRemaining;
        String saleMessage;

        // Print sale info
        saleMessage = quantity + " units of " + super.getSymbol() + "sold @" + price + "$!\n" +
                     "Payment from sale: " + String.format("%.2f",bookValueSold) +"$. Remaining book value is: " + String.format("%.2f",bookValueRemaining) + "$.\n" +
                     "Removing " + quantity +" amount of units of " + super.getSymbol() +" from list. There are " + remainingQuantity + " units left.\n";

        // Update values of the investment
        super.setPrice(price);
        super.setQuantity(remainingQuantity);
        super.setBookValue(bookValueRemaining);

        return saleMessage;
    }

    /**
     * Returns a string representation of the mutualfund, including the symbol, name, quantity, price, and book value.
     * This method provides a detailed description of the mutualfund object.
     *
     * @return a string representing the mutualfund's details
     */
    @Override
    public String toString() {
        return "Mutualfund symbol: " + super.getSymbol() +
               "\nMutual fund name: " + super.getName() + 
               "\nQuantity: " + super.getQuantity() + 
               "\nPrice: " + String.format("%.2f", super.getPrice()) + "$" +
               "\nbookValue: " + String.format("%.2f", super.getBookValue()) +"$\n";
    }
}
