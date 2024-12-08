package ePortfolio;

/**
 * The Main class is the main entry point for the ePortfolio application.
 */
public class Main {
    /**
     * Main method - The main entry point for the ePortfolio application.
     * @param args the command line arguments (not used)
     */
    public static void main(String[] args) {
        Portfolio investmentPortoflio = new Portfolio(); // Create a portfolio object 

        PortfolioGui main = new PortfolioGui(investmentPortoflio); // Create a new main object
        main.setVisible(true);
    }
}


