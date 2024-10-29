package ePortfolio;
/**
 * The Stock class represents a stock investment in the ePortfolio system.
 * It stores the symbol, name, quantity, price, and book value of the stock, 
 * and provides methods to calculate the gain and the sale payment for the stock.
 * The class also includes getters for the stock's attributes.
 */
public class Stock extends Investment {

    private static final double COMMISION = 9.99; // Constant double variable representing the commision for 'stock' operations
    
    /**
     * Stock class constructor - Constructs a new Stock object with the specified symbol, name, quantity, and price.
     * The book value is calculated by multiplying the quantity by the price and adding the commission.
     *
     * @param symbol the stock symbol, e.g., APPL for Apple
     * @param name the company name or stock name, e.g., Apple Inc.
     * @param quantity the number of shares purchased
     * @param price the price per share
     */
    public Stock(String symbol, String name, int quantity, double price) {
        super(symbol, name, quantity, price); // Call to super class constructor
        super.setBookValue(super.getBookValue() + COMMISION); // Update the book value accordingly for stocks by adding commision taxes
    }   

    /**
     * No argumentStock class constructor - Constructs a new Stock object
     */
    public Stock() {
        super();
    }

    @Override
    /**
     * Calculates and returns the total gain from the stock investment.
     * The gain is calculated as (quantity * current price - commission) - book value.
     *
     * @return the total gain from the investment
     */
    public double getGain() {
        return ((super.getPrice() * super.getQuantity()) - COMMISION) - super.getBookValue(); // Calculate the total gain with objects instance
    }

    /**
     * Calculates and returns the total payment from the investment sale sale.
     * The sale payment is calculated as (current price * current quantity) - book value.
     *
     * @return the total payment from the stock sale
     */
    public double getSalePayment() {
        return (super.getPrice() * super.getQuantity()) - COMMISION; // Calculate the payment from a stcok sale
    }

    /**
     * Returns the commission for buy and sale of stocks (Double)
     * @return the comission
     */
    public double getComission() {
        return COMMISION;
    }

    /**
     * Adds more stock investments by increasing the quantity and updating the book value.
     * This method allows the user to buy additional shares of an existing stock at a given price.
     * The total quantity and book value are updated accordingly, and a message is printed
     * to confirm the successful purchase.
     *
     * @param price the price at which the additional shares are purchased
     * @param quantity the number of additional shares being purchased
     */
    public void buyMoreStocks(double price, int quantity) {
        super.setPrice(price);
        super.setQuantity(quantity);
        super.setBookValue(super.getBookValue() + ((price * quantity) + COMMISION));
        System.out.println("Additional " + quantity + " shares of " + super.getSymbol() +" @"+ price + "$ succesfully purchased!\n");
    }

    /**
     * Sells a specified quantity of stock and updates the remaining quantity and book value.
     * The remaining shares and book value are recalculated based on the quantity sold.
     * The method prints detailed information about the sale, including payment and the remaining stock.
     *
     * @param price the price at which the shares are sold
     * @param quantity the number of shares being sold
     */
    public void sellSomeStocks(double price, int quantity) {
        int remainingQuantity = super.getQuantity() - quantity; // Calculate remainging quantity
        double bookValueRemaining = super.getBookValue() * ((double)remainingQuantity/((double)super.getQuantity())); // Calculate the remaining bookvalue, typecast to avoid integer division
        double bookValueSold = super.getBookValue() - bookValueRemaining;

        // Print sale info
        System.out.println(quantity + " shares of " + super.getSymbol() + " sold @" + price + "$!");
        System.out.println("Payment from sale: " + String.format("%.2f",bookValueSold) +"$. Remaining book value is: " + String.format("%.2f",bookValueRemaining) + "$."); // Print payment from sale
        System.out.println("Removing " + quantity +" amount of shares of " + super.getSymbol() +" from list. There are " + remainingQuantity + " shares left."); // Print removal message

        // Update the values of investment
        super.setPrice(price);
        super.setQuantity(remainingQuantity);
        super.setBookValue(bookValueSold);
    }

    @Override
    /**
     * Returns a string representation of the stock, including the symbol, name, quantity, price, and book value.
     * This method provides a detailed description of the stock object.
     *
     * @return a string representing the stock's details
     */
    public String toString() {
        return "Stock symbol: " + super.getSymbol() +
             "\nStock name: " + super.getName() + 
             "\nQuantity: " + super.getQuantity() + 
             "\nPrice: " + String.format("%.2f", super.getPrice()) + "$" +
             "\nbookValue: " + String.format("%.2f", super.getBookValue()) + "$"; 
    }
}