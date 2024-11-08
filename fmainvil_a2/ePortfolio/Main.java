package ePortfolio;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Declare new scanner
        Portfolio investmentPortoflio = new Portfolio(); // Create a portfolio object 
        String fileName;

        
        try {
            fileName = args[0];
            investmentPortoflio.readInvestmentsFromFile(fileName); // Read existing investments from the file
        } catch (ArrayIndexOutOfBoundsException e) {
            fileName = "portfolio.txt";
            System.out.println("No file name was given creating general \'portfolio.txt\' file");
            investmentPortoflio.readInvestmentsFromFile(fileName); // Read existing investments from the file
        }

        do {
            printMenu(); //  Print the menu
            System.out.print("Select a menu option: ");
            String menuSelection = scanner.nextLine().toLowerCase(); // Get user inpt and convert to lower case
            
            switch(menuSelection) {
                case "b":
                case "bu":
                case "buy": // Buy invesment
                    investmentPortoflio.buyInvestment(scanner);
                break;

                case "sel":
                case "sell": // Sell investment
                    investmentPortoflio.sellInvestment(scanner);
                break;

                case "u":
                case "up":
                case "upd":
                case "upda":
                case "updat":
                case "update": // Update prices for all invesments, does not affect bookvalue
                    investmentPortoflio.updateInvestmentPrices(scanner);
                break;

                case "g":
                case "ga":
                case "gai":
                case "gain":
                case "get gain":
                case "getgain": // Get gain of investments in portofolio, does not actually sell the investments (hypothetical return)
                    investmentPortoflio.getGainPortfolio();
                break;

                case "sea":
                case "sear":
                case "searc":
                case "search": // Search for existing invesment in portoflio
                    investmentPortoflio.searchInvestmentPortfolio(scanner);
                break;

                case "q": // Alternate quit input case, will simply fall through
                case "qu":
                case "qui":
                case "quit":
                    investmentPortoflio.saveInvestmentsToFile(fileName); // Save current investment information to a file
                    scanner.close();
                    System.exit(0);
                break;

                default:
                    System.out.println(menuSelection + " is not a valid menu option, please try again!"); // Print error meassage for bad user input
                break;
            }
        } while (true);

    }

    public static void printMenu() {
        System.out.println("\n------------------------- Main Menu -------------------------");
        System.out.println("Buy: own a new investment or add more quantity to an existing investment.");
        System.out.println("Sell: reduce some quantity of an existing investment.");
        System.out.println("Update: refresh the prices of all existing investments.");
        System.out.println("getGain: compute the total gain of the portfolio by accumulating the gains of all individual investments.");
        System.out.println("Search: find all investments that match a search request and display all attributes of these investments.");
        System.out.println("Quit: quit the program.");
    }

}


