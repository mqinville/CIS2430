package ePortfolio;

// GUI imports
import java.awt.*;
import java.awt.event.ActionEvent   ;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * The PortfolioGui class represents the graphical user interface for the ePortfolio system.
 */
public class PortfolioGui extends JFrame {

    // GUI dimensions
    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;

    // Panels for the main GUI and commands
    private final CardLayout frameLayout; // Card layout to switch between panels
    private final JPanel mainPanel; // Main panel for the GUI
    private final JPanel buyPanel; // Buy panel for the GUI
    private final JPanel sellPanel; // Sell panel for the GUI
    private final JPanel updatePanel; // Update panel for the GUI
    private final JPanel gainPanel; // Gain panel for the GUI
    private final JPanel searchPanel; // Search panel for the GUI

    // BUY PANEL COMPONENTS
    private JComboBox<String> investmentType; // Dropdown for investment type
    private JTextField symbolField; // Symbol field for investment
    private JTextField nameField; // Name field for investment
    private JTextField quantityField; // Quantity field for investment
    private JTextField priceField; // Price field for investment
    private JButton buyButton; // Buy button for investment
    private JButton resetBuyButton; // Reset button for investment
    private JTextArea buyMessageArea; // Message area for investment

    // SELL PANEL COMPONENTS
    private JTextField sellSymbolField; // Symbol field for investment
    private JTextField sellQuantityField; // Quantity field for investment
    private JTextField sellPriceField; // Price field for investment
    private JButton sellButton; // Sell button for investment
    private JButton resetSellButton; // Reset button for investment
    private JTextArea sellMessageArea; // Message area for investment

    // UPDATE PANEL COMPONENTS
    private JTextField updateSymbolField; // Non editable
    private JTextField updateNameField; // Non editable
    private JTextField updatePriceField; // Editable
    private JButton updateNextButton; // Next button to go to next investment
    private JButton updatePrevButton; // Previous button to go to previous investment
    private JButton updateSaveButton; // Save button to save the updated price
    private JTextArea updateMessageArea; // Message area for investment

    // GAIN PANEL COMPONENTS
    private JTextField totalGainField; // must be non editable
    private JTextArea gainMessageArea; // must be non editable

    // SEARCH PANEL COMPONENTS
    private JTextField searchSymbolField; // Symbol field for investment
    private JTextField searchNameField; // Name field for investment
    private JTextField searchLowerlField; // Lower price field for investment
    private JTextField searchHigherField;   // Hig her price field for investment
    private JTextArea searchMessageArea; // Message area for investment
    private JButton searchButton; // Search button for investment 
    private JButton resetSearchButton;  // Reset button for investment


    private final Portfolio investmentPortfolio; // Portfolio object to store investments
    private int currentInvestmentIndex = 0; // Current investment index for the update panel

    /**
     * Constructs the PortfolioGui.
     * @param investmentPortfolio Portfolio object containing investments.
     */
    public PortfolioGui(Portfolio investmentPortfolio) {
        super("ePortfolio");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameLayout = new CardLayout();
        setLayout(frameLayout);

        this.investmentPortfolio = investmentPortfolio; // initilize the portfolio object for the gui

        mainPanel = new JPanel();
        buyPanel = new JPanel();
        sellPanel = new JPanel();
        updatePanel = new JPanel();
        gainPanel = new JPanel();
        searchPanel = new JPanel();

        // Setup all gui windows/components
        setupMenu(); // Setup the command menu to switch between panels
        setupWelcomeWindow();
        setupBuyPanel();
        setupSellPanel();
        setupUpdatePanel();
        setupGainPanel();
        setupSearchPanel();

        // Add each window to the JFrame
        add(mainPanel, "mainPanel");
        add(buyPanel, "buyPanel");
        add(sellPanel, "sellPanel");
        add(updatePanel, "updatePanel");
        add(gainPanel, "gainPanel");
        add(searchPanel, "searchPanel");

        frameLayout.show(getContentPane(), "mainPanel"); // show the main panel initially to display welcome message
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu commandsMenu = new JMenu("Commands");
        JMenuItem buyItem = new JMenuItem("Buy");
        JMenuItem sellItem = new JMenuItem("Sell");
        JMenuItem updateItem = new JMenuItem("Update");
        JMenuItem gainItem = new JMenuItem("Gain");
        JMenuItem searchItem = new JMenuItem("Search");
        JMenuItem quitItem = new JMenuItem("Quit");

        buyItem.addActionListener(new BuyPanelListener());
        sellItem.addActionListener(new SellPanelListener());
        updateItem.addActionListener(new UpdatePanelListener());
        gainItem.addActionListener(new GainPanelListener());
        searchItem.addActionListener(new SearchPanelListener());
        quitItem.addActionListener(new QuitPanelListener());

        commandsMenu.add(buyItem);
        commandsMenu.add(sellItem);
        commandsMenu.add(updateItem);
        commandsMenu.add(gainItem);
        commandsMenu.add(searchItem);
        commandsMenu.add(quitItem);

        menuBar.add(commandsMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Sets up the buy panel with all the required components.
     */
    private void setupBuyPanel() {
        JLabel buyLabel = new JLabel("Buying an investment"); // Create page title for the buy page
        JLabel typeLabel = new JLabel("Type: ");
        JLabel symbolLabel = new JLabel("Symbol: ");
        JLabel nameLabel = new JLabel("Name: ");
        JLabel quantityLabel = new JLabel("Quantity: ");
        JLabel priceLabel = new JLabel("Price: ");
        JLabel buyMessageLabel = new JLabel("Messages: ");
        symbolField = new JTextField(10);
        nameField = new JTextField(10);
        quantityField = new JTextField(10);
        priceField = new JTextField(10);
        buyButton = new JButton("Buy");
        resetBuyButton = new JButton("Reset");
        investmentType = new JComboBox<>();
        buyMessageArea = new JTextArea(10, 20);

        symbolField.setSize(new Dimension(250, 25));
        nameField.setSize(new Dimension(250, 25));
        quantityField.setSize(new Dimension(250, 25));
        priceField.setSize(new Dimension(250, 25));
        investmentType.setSize(new Dimension(250, 25));
        buyButton.setMaximumSize(new Dimension(150, 25));
        resetBuyButton.setMaximumSize(new Dimension(150, 25));

        typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        symbolLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        symbolField.setHorizontalAlignment(SwingConstants.LEFT);
        nameField.setHorizontalAlignment(SwingConstants.LEFT);
        quantityField.setHorizontalAlignment(SwingConstants.LEFT);
        priceField.setHorizontalAlignment(SwingConstants.LEFT);

        buyPanel.setLayout(new BoxLayout(buyPanel, BoxLayout.Y_AXIS)); // Assign box layouyt to the buy panel as described in lecture slides
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS)); // align items horizontally
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(6,2,5,5)); // align items vertically
        topLeftPanel.setAlignmentX(LEFT_ALIGNMENT);
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS)); // align items vertically
        topRightPanel.setAlignmentX(RIGHT_ALIGNMENT);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS)); // align items horizontally
        topLeftPanel.setPreferredSize(new Dimension(300, 200)); // Constrain panel size
        topRightPanel.setPreferredSize(new Dimension(300, 200));

        topLeftPanel.add(buyLabel);
        topLeftPanel.add(new JLabel(""));
        topLeftPanel.add(typeLabel);
        investmentType.addItem("Stock");
        investmentType.addItem("MutualFund");
        topLeftPanel.add(investmentType);
        topLeftPanel.add(symbolLabel);
        topLeftPanel.add(symbolField);
        topLeftPanel.add(nameLabel);
        topLeftPanel.add(nameField);
        topLeftPanel.add(quantityLabel);
        topLeftPanel.add(quantityField);
        topLeftPanel.add(priceLabel);
        topLeftPanel.add(priceField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // align items horizontally
        buyButton.setAlignmentX(CENTER_ALIGNMENT);
        buyButton.addActionListener(new BuyButtonListener());
        resetBuyButton.setAlignmentX(CENTER_ALIGNMENT);
        resetBuyButton.addActionListener(new ResetBuyButtonListener());
        buttonPanel.add(buyButton);
        buttonPanel.add(resetBuyButton);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        topRightPanel.add(buttonPanel);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS)); // align items horizontally
        messagePanel.setAlignmentX(CENTER_ALIGNMENT);
        buyMessageLabel.setAlignmentX(LEFT_ALIGNMENT);
        buyMessageArea.setAlignmentX(CENTER_ALIGNMENT);
        buyMessageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(buyMessageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // Add a
        messagePanel.add(buyMessageLabel);
        messagePanel.add(scrollPane); 

        topPanel.add(topLeftPanel);
        topPanel.add(topRightPanel);
        bottomPanel.add(messagePanel);

        buyPanel.add(topPanel);
        buyPanel.add(bottomPanel);
    }

    /**
     * Sets up the sell panel with all the required components.
     */
    private void setupSellPanel() {
        JLabel sellTitleLabel = new JLabel("Selling an investment");
        JLabel symbolLabel = new JLabel("Symbol: ");
        JLabel quantityLabel = new JLabel("Quantity: ");
        JLabel priceLabel = new JLabel("Price: ");
        JLabel sellMessageLabel = new JLabel("Messages: ");
        sellSymbolField = new JTextField();
        sellQuantityField = new JTextField();
        sellPriceField = new JTextField();
        sellButton = new JButton("Sell");
        resetSellButton = new JButton("Reset");
        sellMessageArea = new JTextArea(10, 20);

        sellSymbolField.setSize(new Dimension(250, 25));
        nameField.setSize(new Dimension(250, 25));
        sellQuantityField.setSize(new Dimension(250, 25));
        sellPriceField.setSize(new Dimension(250, 25));

        sellButton.setMaximumSize(new Dimension(150, 25));
        resetSellButton.setMaximumSize(new Dimension(150, 25));

        sellPanel.setLayout(new BoxLayout(sellPanel, BoxLayout.Y_AXIS)); // Assign box layouyt to the buy panel as described in lecture slides
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS)); // align items horizontally
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(6,2,5,5)); // align items vertically
        topLeftPanel.setAlignmentX(LEFT_ALIGNMENT);
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS)); // align items vertically
        topRightPanel.setAlignmentX(RIGHT_ALIGNMENT);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS)); // align items horizontally
        topLeftPanel.setPreferredSize(new Dimension(300, 200)); // Constrain panel size
        topRightPanel.setPreferredSize(new Dimension(300, 200));

        topLeftPanel.add(sellTitleLabel);
        topLeftPanel.add(new JLabel(""));
        topLeftPanel.add(new JLabel(""));
        topLeftPanel.add(new JLabel(""));
        topLeftPanel.add(symbolLabel);
        topLeftPanel.add(sellSymbolField);
        topLeftPanel.add(quantityLabel);
        topLeftPanel.add(sellQuantityField);
        topLeftPanel.add(priceLabel);
        topLeftPanel.add(sellPriceField);
        
        symbolLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        sellSymbolField.setHorizontalAlignment(SwingConstants.LEFT);
        sellQuantityField.setHorizontalAlignment(SwingConstants.LEFT);
        sellPriceField.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // align items horizontally
        sellButton.setAlignmentX(CENTER_ALIGNMENT);
        sellButton.addActionListener(new SellButtonListener());
        resetSellButton.setAlignmentX(CENTER_ALIGNMENT);
        resetSellButton.addActionListener(new ResetSellButtonListener());
        buttonPanel.add(sellButton);
        buttonPanel.add(resetSellButton);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        topRightPanel.add(buttonPanel);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS)); // align items horizontally
        messagePanel.setAlignmentX(CENTER_ALIGNMENT);
        sellMessageArea.setAlignmentX(CENTER_ALIGNMENT);
        sellMessageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(sellMessageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        messagePanel.add(sellMessageLabel);
        messagePanel.add(scrollPane);

        topPanel.add(topLeftPanel);
        topPanel.add(topRightPanel);
        bottomPanel.add(messagePanel);

        sellPanel.add(topPanel);
        sellPanel.add(bottomPanel);
    }

    /**
     * Sets up the update panel with all the required components.
     */
    private void setupUpdatePanel() {
        JLabel updateTitleLabel = new JLabel("Selling an investment");
        JLabel symbolLabel = new JLabel("Symbol: ");
        JLabel nameLabel = new JLabel("Name: ");
        JLabel priceLabel = new JLabel("Price: ");
        JLabel updateMessageLabel = new JLabel("Messages: ");
        updateSymbolField = new JTextField(10);
        updateNameField = new JTextField(10);
        updatePriceField = new JTextField(10);
        updateNextButton = new JButton("Next");
        updatePrevButton = new JButton("Previous");
        updatePrevButton.setEnabled(false); // Disable the previous button initially
        updateSaveButton = new JButton("Save");
        updateMessageArea = new JTextArea(10, 20);
        
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS)); // Assign box layouyt to the buy panel as described in lecture slides
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS)); // align items horizontally
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(6,2,5,5)); // align items vertically        topLeftPanel.setAlignmentX(LEFT_ALIGNMENT);
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS)); // align items vertically
        topRightPanel.setAlignmentX(RIGHT_ALIGNMENT);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS)); // align items horizontally
        topLeftPanel.setPreferredSize(new Dimension(300, 200)); // Constrain panel size
        topRightPanel.setPreferredSize(new Dimension(300, 200));

        updateSymbolField.setSize(new Dimension(250, 25));
        updateSymbolField.setEditable(false); // Make it non editable
        updateNameField.setSize(new Dimension(250, 25));
        updateNameField.setEditable(false); // Make it non editable
        updatePriceField.setSize(new Dimension(250, 25));

        updatePrevButton.setMaximumSize(new Dimension(150, 25));
        updateNextButton.setMaximumSize(new Dimension(150, 25));
        updateSaveButton.setMaximumSize(new Dimension(150, 25));

        topLeftPanel.add(updateTitleLabel);
        topLeftPanel.add(new JLabel(""));
        topLeftPanel.add(new JLabel(""));
        topLeftPanel.add(new JLabel(""));
        topLeftPanel.add(symbolLabel);
        topLeftPanel.add(updateSymbolField);
        topLeftPanel.add(nameLabel);
        topLeftPanel.add(updateNameField);
        topLeftPanel.add(priceLabel);
        topLeftPanel.add(updatePriceField);

        symbolLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        updateSymbolField.setHorizontalAlignment(SwingConstants.LEFT);
        updateNameField.setHorizontalAlignment(SwingConstants.LEFT);
        updatePriceField.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // align items horizontally
        updateNextButton.setAlignmentX(CENTER_ALIGNMENT);
        updateNextButton.addActionListener(new UpdateNextButton());
        updatePrevButton.setAlignmentX(CENTER_ALIGNMENT);
        updatePrevButton.addActionListener(new UpdatePrevButton());
        updateSaveButton.setAlignmentX(CENTER_ALIGNMENT);
        updateSaveButton.addActionListener(new UpdateSaveButton());
        buttonPanel.add(updatePrevButton);
        buttonPanel.add(updateNextButton);
        buttonPanel.add(updateSaveButton);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        topRightPanel.add(buttonPanel);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS)); // align items horizontally
        messagePanel.setAlignmentX(CENTER_ALIGNMENT);
        updateMessageArea.setAlignmentX(CENTER_ALIGNMENT);
        updateMessageArea.setEditable(false);
        messagePanel.add(updateMessageLabel);
        JScrollPane scrollPane = new JScrollPane(updateMessageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        messagePanel.add(scrollPane);
        topPanel.add(topLeftPanel);
        topPanel.add(topRightPanel);
        bottomPanel.add(messagePanel);

        updatePanel.add(topPanel);
        updatePanel.add(bottomPanel);
    }
    
    /**
     * Sets up the gain panel with all the required components. 
     */
    private void setupGainPanel() {
        JLabel gainTitleLabel = new JLabel("Getting total gain");
        JLabel individualGainsLabel = new JLabel("Individual gains: ");
        JLabel totalGainLabel = new JLabel("Total gain: ");
        totalGainField = new JTextField(10);
        gainMessageArea = new JTextArea(10, 20);
        gainPanel.setLayout(new BoxLayout(gainPanel, BoxLayout.Y_AXIS)); // Assign box layouyt to the buy panel as described in lecture slides

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // align items horizontally
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // align items horizontally

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS)); // align items horizontally
        gainTitleLabel.setAlignmentX(LEFT_ALIGNMENT);
        titlePanel.add(gainTitleLabel);
        titlePanel.setAlignmentX(LEFT_ALIGNMENT);
        topPanel.add(titlePanel);

        JPanel totalGainPanel = new JPanel();
        totalGainPanel.setLayout(new FlowLayout()); // align items horizontally
        totalGainLabel.setAlignmentX(CENTER_ALIGNMENT);
        totalGainField.setAlignmentX(CENTER_ALIGNMENT);
        totalGainField.setAlignmentY(CENTER_ALIGNMENT);
        totalGainField.setEditable(false); // Make it non editable
        totalGainPanel.add(totalGainLabel);
        totalGainPanel.add(totalGainField);
        totalGainPanel.setAlignmentX(CENTER_ALIGNMENT);
        topPanel.add(totalGainPanel);

        JPanel individualGainsPanel = new JPanel();
        individualGainsPanel.setLayout(new BoxLayout(individualGainsPanel, BoxLayout.Y_AXIS)); // align items horizontally
        individualGainsLabel.setAlignmentX(LEFT_ALIGNMENT);
        individualGainsPanel.add(individualGainsLabel);
        individualGainsPanel.setAlignmentX(CENTER_ALIGNMENT);
        individualGainsPanel.setAlignmentY(CENTER_ALIGNMENT);
        gainMessageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(gainMessageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        individualGainsPanel.add(scrollPane);

        bottomPanel.add(individualGainsPanel);
        gainPanel.add(topPanel);
        gainPanel.add(bottomPanel);
    }
    
    /**
     * Sets up the search panel with all the required components. 
     */
    private void setupSearchPanel() {
        JLabel searchTitleLabel = new JLabel("Searching investments");
        JLabel symbolLabel = new JLabel("Symbol: ");
        JLabel nameLabel = new JLabel("Name: ");
        JLabel lowerLabel = new JLabel("Lower price: ");
        JLabel higherLabel = new JLabel("Higher price: ");
        JLabel searchMessageLabel = new JLabel("Search Results: ");
        resetSearchButton = new JButton("Reset");
        searchButton = new JButton("Search");
        searchSymbolField = new JTextField();
        searchNameField = new JTextField();
        searchLowerlField = new JTextField();
        searchHigherField = new JTextField();
        searchMessageArea = new JTextArea(10, 20);

        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS)); // Assign box layouyt to the buy panel as described in lecture slides
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS)); // align items horizontally
        JPanel topLeftPanel = new JPanel();
        topLeftPanel.setLayout(new GridLayout(6,2,5,5)); // align items vertically
        topLeftPanel.setAlignmentX(LEFT_ALIGNMENT);
        JPanel topRightPanel = new JPanel();
        topRightPanel.setLayout(new BoxLayout(topRightPanel, BoxLayout.Y_AXIS)); // align items vertically
        topRightPanel.setAlignmentX(RIGHT_ALIGNMENT);
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS)); // align items horizontally
        topLeftPanel.setPreferredSize(new Dimension(300, 200)); // Constrain panel size
        topRightPanel.setPreferredSize(new Dimension(300, 200));

        searchSymbolField.setSize(new Dimension(250, 25));
        searchNameField.setSize(new Dimension(250, 25));
        searchLowerlField.setSize(new Dimension(250, 25));
        searchHigherField.setSize(new Dimension(250, 25));
        searchButton.setMaximumSize(new Dimension(150, 25));
        resetSearchButton.setMaximumSize(new Dimension(150, 25));

        symbolLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lowerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        higherLabel.setHorizontalAlignment(SwingConstants.CENTER);

        searchSymbolField.setHorizontalAlignment(SwingConstants.LEFT);
        searchNameField.setHorizontalAlignment(SwingConstants.LEFT);
        searchLowerlField.setHorizontalAlignment(SwingConstants.LEFT);
        searchHigherField.setHorizontalAlignment(SwingConstants.LEFT);

        topLeftPanel.add(searchTitleLabel);
        topLeftPanel.add(new JLabel(""));
        topLeftPanel.add(symbolLabel);
        topLeftPanel.add(searchSymbolField);
        topLeftPanel.add(nameLabel);
        topLeftPanel.add(searchNameField);
        topLeftPanel.add(lowerLabel);
        topLeftPanel.add(searchLowerlField);
        topLeftPanel.add(higherLabel);
        topLeftPanel.add(searchHigherField);
        topLeftPanel.add(new JLabel(""));
        topLeftPanel.add(new JLabel(""));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // align items horizontally
        searchButton.setAlignmentX(CENTER_ALIGNMENT);
        searchButton.addActionListener(new SearchButtonListener());
        resetSearchButton.setAlignmentX(CENTER_ALIGNMENT);
        resetSearchButton.addActionListener(new SearchResetButtonListener());
        buttonPanel.add(resetSearchButton);
        buttonPanel.add(searchButton);
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        topRightPanel.add(buttonPanel);

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS)); // align items horizontally
        messagePanel.setAlignmentX(CENTER_ALIGNMENT);
        searchMessageArea.setAlignmentX(CENTER_ALIGNMENT);
        searchMessageArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchMessageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        messagePanel.add(searchMessageLabel);
        messagePanel.add(scrollPane);

        topPanel.add(topLeftPanel);
        topPanel.add(topRightPanel);
        bottomPanel.add(messagePanel);
        
        searchPanel.add(topPanel);
        searchPanel.add(bottomPanel);
    }

    /**
     * Initializes the welcome window of the gui. Displays welcome message with instruction 
     */
    private void setupWelcomeWindow() {
        JLabel welcomeLabel = new JLabel("Welcome to ePortfolio.");
        JLabel instructionsLabel = new JLabel("<html>Choose a command from the “Commands” menu to buy or sell an investment, update prices for all investments, get gain for the portfolio, search for relevant investments, or quit the program.</html>");

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // arrange items verticall

        welcomeLabel.setAlignmentX(CENTER_ALIGNMENT);
        instructionsLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Add the labels to the panel to display welcome message
        mainPanel.add(welcomeLabel);
        mainPanel.add(instructionsLabel);        
    }


    /**
     * Switches the window to the desired window.
     * @param windowToSwitchTo string of the window to switch to
     */
    private void switchWindow(String windowToSwitchTo) {
        // The idea to use a cardlayout to swicth between panels was taken from stack overflow. I was encoutering many issues
        // with the layout managers i was using (items wouldnt render), this stack overflow link had a similar solution to one being
        // used here which accomplisdhesd exactly what i needed https://stackoverflow.com/questions/55845306/how-do-i-switch-between-panels-using-a-cardlayout

        frameLayout.show(getContentPane(), windowToSwitchTo); // switch to the desired window
        

        // Display the currentInvestmentIndex investment in the update panel if it is being swapped to
        if (windowToSwitchTo.equals("updatePanel") && !investmentPortfolio.getInvestmentPortfolio().isEmpty()) {
            Investment investment = investmentPortfolio.getInvestmentPortfolio().get(currentInvestmentIndex);
            updateSymbolField.setText(investment.getSymbol());
            updateNameField.setText(investment.getName());
            updatePriceField.setText(String.valueOf(investment.getPrice()));
        } else if (windowToSwitchTo.equals("gainPanel") && !investmentPortfolio.getInvestmentPortfolio().isEmpty()) {
            double totalGain = 0;
            gainMessageArea.setText(""); // Clear the gain message area from non avaulable message
            
            // Print individual gains to the individiual gains area
            for (Investment curInvestment : investmentPortfolio.getInvestmentPortfolio()) {
                gainMessageArea.append("Individual gain for "+ curInvestment.getSymbol() + " is: " + String.format("%.2f", curInvestment.getGain()) + "$\n");
                totalGain += curInvestment.getGain();
            }

            // Set total gain field to the total gain
            totalGainField.setText(String.format("%.2f", totalGain) + "$");
        } else {
            totalGainField.setText("N/A");
            gainMessageArea.setText("N/A");
        }
        
        
    }

    /**
     * Listener for the Buy command in the commands menu. Switches the window to the buy panel.
     */
    private class BuyPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switchWindow("buyPanel");
        }
    }

    /**
     * Listener for the sell command in the commands menu. Switches the window to the sell panel.
     */
    private class SellPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switchWindow("sellPanel");
        }
    }   

    /**
     * Listener for the update command in the commands menu. Switches the window to the update panel.
     */
    private class UpdatePanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switchWindow("updatePanel");
        }
    }

    /**
     * Listener for the gain command in the commands menu. Switches the window to the gain panel.
     */
    private class GainPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switchWindow("gainPanel");
        }
    }

    /**
     * Listener for the search command in the commands menu. Switches the window to the search panel.
     */
    private class SearchPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switchWindow("searchPanel");
        }
    }

    /**
     * Listener for the quit option in the commands menu. Saves the portfolio to a file and exits the program.
     */
    private class QuitPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Save the program info to a file
            System.exit(0);
        }
    }

    /**
     * Listener for the reset button in the buy panel. Resets all fields in the buy panel.
     */
    private class ResetBuyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Set all fields to be empty
            symbolField.setText("");
            nameField.setText("");
            quantityField.setText("");
            priceField.setText("");
            buyMessageArea.setText("");
        }
    }

    /**
     * Listener for the reset button in the sell panel. Resets all fields in the sell panel.
     */
    private class ResetSellButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Set all fields to be empty
            sellSymbolField.setText("");
            sellQuantityField.setText("");
            sellPriceField.setText("");
            sellMessageArea.setText("");
        }
    }

    /**
     * Button listener for the buy button in the buy window. Buys the investment based on the input values.
     * Makes call to the investmentPortfolio object to buy the investment.
     */
    private class BuyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the values from the fields in buy window
            String type = investmentType.getSelectedItem().toString();
            String symbol = symbolField.getText().trim().toUpperCase();
            String name = nameField.getText().trim();
            int quantity;
            double price;

            // Catch invalid inputs
            try {
                quantity = Integer.parseInt(quantityField.getText());
                price = Double.parseDouble(priceField.getText());
                // Call the investmentPortfolio object to buy the investment
                try {
                    buyMessageArea.append(investmentPortfolio.buyInvestment(type, symbol, name, quantity, price) + "\n");
                } catch (IllegalArgumentException buyException) {
                    buyMessageArea.setText(buyException.getMessage());
                }
            } catch (NumberFormatException valueException) {
                buyMessageArea.setText("Invalid input. Please enter a valid number for quantity and/or price.\n");
            }
            // Display a message to the user
        }
    }

    /**
     * Listener for sell button in the sell panel. Sells the investment based on the input values.
     * Makes call to the investmentPortfolio object to sell the investment.
     */
    private class SellButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the values from the fields in sell window
            String symbol = sellSymbolField.getText().trim().toUpperCase();
            int quantity;
            double price;

            try {
                quantity = Integer.parseInt(sellQuantityField.getText());
                price = Double.parseDouble(sellPriceField.getText());

                try {
                    sellMessageArea.append(investmentPortfolio.sellInvestment(symbol, quantity, price) + "\n");
                } catch (IllegalArgumentException sellException) {
                    sellMessageArea.append(sellException.getMessage() + "\n"); // Print the error message to the user
                }
                
            } catch (NumberFormatException valueException) {
                sellMessageArea.append("Invalid input. Please enter a valid number for quantity and/or price.\n");
            }

            // Display a message to the user
        }
    }

    /**
     * Listener for the save button in the update panel. Updates the price of the investment in the portfolio.
     * Makes call to the investmentPortfolio object to update the price of the investment.
     */
    private class UpdateSaveButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double newPrice; // Integer variable of the new price to be updated
            String symbol = updateSymbolField.getText().trim().toUpperCase(); // Get the symbol of the investment to update
            try {
                newPrice = Double.parseDouble(updatePriceField.getText());
                investmentPortfolio.updateInvestmentPrices(symbol, newPrice);

                if (investmentPortfolio.updateInvestmentPrices(symbol, newPrice) != null && newPrice >= 0) {
                    updateMessageArea.append("\n\nPrice for "+ symbol +" updated successfully!\n");
                    updateMessageArea.append(investmentPortfolio.getOneInvestment(symbol).toString() + "\n");
                } else if (newPrice <= 0) {
                    updateMessageArea.append("Price cannot be negative. Please enter a valid price.\n");
                    
                } else {
                    updateMessageArea.append("Investment not found. Cannot update price\n");
                }

            } catch (NumberFormatException valueException) {
                updateMessageArea.append("Invalid input. Please enter a valid number for price.\n");
            }
        }
    }

    /**
     * Listener for the next button in the update panel. Updates the fields to display the next investment in the portfolio.
     */
    private class UpdateNextButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            
            if (currentInvestmentIndex < investmentPortfolio.getInvestmentPortfolio().size() - 1) {
                updatePrevButton.setEnabled(true); // enable the previous button
                ++currentInvestmentIndex;
                if (currentInvestmentIndex == investmentPortfolio.getInvestmentPortfolio().size() - 1) {
                    updateNextButton.setEnabled(false); // dsiable the button
                }
                Investment investment = investmentPortfolio.getInvestmentPortfolio().get(currentInvestmentIndex);
                System.out.println(investment);
                updateSymbolField.setText(investment.getSymbol() + "\n");
                updateNameField.setText(investment.getName() + "\n");
                updatePriceField.setText(String.valueOf(investment.getPrice()) + "\n");
                //updateMessageArea.setText("");
            } else {
                updateMessageArea.setText("No more investments to update.\n");
            }
        }
    }

    /**
     * Listener for the previous button in the update panel. Updates the fields to display the previous investment in the portfolio.
     */
    private class UpdatePrevButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentInvestmentIndex > 0) {
                updateNextButton.setEnabled(true); // enable the previous button
                currentInvestmentIndex--;
                if (currentInvestmentIndex == 0) {
                    updatePrevButton.setEnabled(false); // dsiable the button
                }
                Investment investment = investmentPortfolio.getInvestmentPortfolio().get(currentInvestmentIndex);
                updateSymbolField.setText(investment.getSymbol());
                updateNameField.setText(investment.getName());
                updatePriceField.setText(String.valueOf(investment.getPrice()));
               //updateMessageArea.setText("");
            } else {
                updateMessageArea.setText("No more investments to update.\n");
            }
        }
    }

    /**
     * Listener for the search button in the search panel. Searches the investment portfolio for investments based on the search criteria.
     */
    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String symbol = searchSymbolField.getText().trim().toUpperCase();
            String name = searchNameField.getText().toLowerCase().trim();
            double lowerPrice;
            double higherPrice;

            try {
                if (!searchLowerlField.getText().isBlank()) {
                    lowerPrice = Double.parseDouble(searchLowerlField.getText());
                } else {
                    lowerPrice = 0.00;
                }
                if (!searchHigherField.getText().isBlank()) {
                    higherPrice = Double.parseDouble(searchHigherField.getText());
                } else {
                    higherPrice = Double.MAX_VALUE;
                }

                if (lowerPrice < 0 || higherPrice < 0) {
                    searchMessageArea.setText("Price for search cannot be negative. Please enter a valid price.");
                    return;
                } else if (lowerPrice > higherPrice) {
                    searchMessageArea.setText("Lower price cannot be greater than higher price. Please enter a valid price range.");
                    return;
                }
                
                ArrayList<Investment> searchResults = investmentPortfolio.searchInvestmentPortfolio(symbol, lowerPrice, higherPrice, name);

                if  (searchResults.isEmpty()) {
                    searchMessageArea.setText("No investments found.");
                } else {
                    searchMessageArea.setText("Search results: \n\n");
                    for (Investment investment : searchResults) {
                        searchMessageArea.append(investment.toString() + "\n");
                    }
                }
                
            } catch (NumberFormatException valueException) {
                searchMessageArea.setText("Invalid input. Please enter a valid number for price.");

            }

            
        }
    }

    /**
     * Listener for the reset button in the search panel. Resets all fields to be empty.
     */
    private class SearchResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Set all fields to be empty
            searchSymbolField.setText("");
            searchNameField.setText("");
            searchLowerlField.setText("");
            searchHigherField.setText("");
            searchMessageArea.setText("");
        }
    }

}
