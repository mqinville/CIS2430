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
     * No argumentStock class constructor - Constructs a new Stock object
     */
    public MutualFund() {
        super();
    }

    @Override
    /**
     * Calculates and returns the total gain from the mutualfund investment.
     * The gain is calculated as (quantity * current price - commission) - book value.
     *
     * @return the total gain from the investment
     */
    public double getGain() {
        return ((super.getPrice() * super.getQuantity()) - REDEMPTION_FEE)- super.getBookValue();
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
     * Adds more mutualfund investments by increasing the quantity and updating the book value.
     * This method allows the user to buy additional shares of an existing mutualfund at a given price.
     * The total quantity and book value are updated accordingly, and a message is printed
     * to confirm the successful purchase.
     *
     * @param price the price at which the additional units are purchased
     * @param quantity the number of additional units being purchased
     */
    public void buyMoreFunds(double price, int quantity) {
        super.setPrice(price);
        super.setQuantity(quantity);
        super.setBookValue(super.getBookValue() + (price * quantity));
        System.out.println("Additional " + quantity + " units of " + super.getSymbol() +" @"+ price + "$ succesfully purchased!");
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
        int remainingQuantity = super.getQuantity() - quantity; // Calculate remainging quantity
        double bookValueRemaining = super.getBookValue() * ((double)remainingQuantity/((double)super.getQuantity())); // Calculate the remaining bookvalue, typecast to avoid integer division
        double bookValueSold = super.getBookValue() - bookValueRemaining;

        // Print sale info
        System.out.println(quantity + " units of " + super.getSymbol() + "sold @" + price + "$!");
        System.out.println("Payment from sale: " + String.format("%.2f",bookValueSold) +"$. Remaining book value is: " + String.format("%.2f",bookValueRemaining) + "$."); // Print payment from sale
        System.out.println("Removing " + quantity +" amount of units of " + super.getSymbol() +" from list. There are " + remainingQuantity + " units left."); // Print removal message

        // Update values of the investment
        super.setPrice(price);
        super.setQuantity(remainingQuantity);
        super.setBookValue(bookValueSold);
    }

    /**
     * Returns a string representation of the mutualfund, including the symbol, name, quantity, price, and book value.
     * This method provides a detailed description of the mutualfund object.
     *
     * @return a string representing the mutualfund's details
     */
    public String toString() {
        return "\nMutualfund symbol: " + super.getSymbol() +
               "\nMutual fund name: " + super.getName() + 
               "\nQuantity: " + super.getQuantity() + 
               "\nPrice: " + String.format("%.2f", super.getPrice()) + "$" +
               "\nbookValue: " + String.format("%.2f", super.getBookValue()) +"$";
    }
}
