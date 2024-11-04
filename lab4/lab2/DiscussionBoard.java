/*
Name: Felix Mainville
ID: 1279419

Compile command: javac lab2/*.java --- Compile all .java files
Run command: java lab2.DiscussionBoard -- Run the program with jre
Jar command: java -jar DiscussionBoard.jar
*/

package discussionbard;

import java.util.Scanner;
import java.util.ArrayList;

public class DiscussionBoard {
    private ArrayList<User> userNameList = new ArrayList<User>();   // Array list for stock investments
    private ArrayList<Post> userPostList = new ArrayList<Post>();   // Array list for stock investments

    // _________ MAIN METHOD __________-
    public static void main(String[] args) { // Main method

        Scanner scanner = new Scanner(System.in);  // Create new scanner
        DiscussionBoard discBoard = new DiscussionBoard();

        int userChoice; // Integer varibale that will hold menu selection of the user
        boolean runProgram = true; // Set boolean - determines if program should continue running

        // Variables for user name
        String firstName;
        String userName;
        String keyWord; // String variable for function 5
        String[] partition;


        while (runProgram) {
            printMenu(); // Print menu

            System.out.print("Enter your meneu choice : ");
            userChoice = scanner.nextInt(); // Scan user input
            scanner.nextLine();
            
            // Switch statement with case for every menu option
            switch(userChoice) {

                case 1 : // Create user
                    // Prompt for user name until valid string is typed
                    do {
                        System.out.print("Enter your first name: ");
                        firstName = scanner.nextLine();
                    } while(firstName == null || firstName.isBlank());
                    System.out.print("Enter your username: "); 
                    userName = scanner.nextLine();

                    // If the function returns false print that user cannot be added
                    if (discBoard.createUser(firstName, userName) ==  false) {
                        System.out.println("Cannot create user, username alreasy exists.");
                    } else {
                        System.out.println("User succesfully created");
                    }
                break;

                case 2 : // Create new post
                    // Ask for username before creating post
                    do{
                        System.out.print("Enter your username: ");
                        userName = scanner.nextLine();
                        userName = userName.toLowerCase(); // Make username input lower case
                    } while (userName == null || userName.isBlank()); // Repeat while username is null or blank

                    if (discBoard.createPost(userName, scanner) == false) { // Print failure message if user isnt registered
                        System.out.println("You do not have permission to create a post. Register as a user first.");
                    } else {
                        System.out.println("Post sucsefully created"); // Print success if message was crated
                    }
                break;

                case 3 : // View all posts
                    // Loop through post arraylist
                    for (int i = 0; i < discBoard.userPostList.size(); i++) {
                        System.out.println(discBoard.userPostList.get(i).toString());  // Print each user post
                    }
                break;

                case 4 : // View all post with given username
                    System.out.print("Enter a user name: ");
                    userName = scanner.nextLine(); // Scan username input
                    boolean postedFour = false;

                    for (int i = 0; i < discBoard.userPostList.size(); i++) {
                        User user = discBoard.userPostList.get(i).getMessageID(); // Fetch the user name from current post
                        if (userName.equalsIgnoreCase(user.getUserName())) {
                            postedFour = true;
                            System.out.println(discBoard.userPostList.get(i).toString());  // If user has pasted a mesage print that corresponding message
                        }
                    }

                    if (postedFour == false) {
                        System.out.println("No message posted with that username");
                    }

                break;
                    
                case 5 : // View all post with given keyword
                    System.out.print("Enter the keyword: ");
                    keyWord = scanner.nextLine(); // Scan keyword from keyboard
                    boolean postedFive = false;

                    for (int i = 0;  i < discBoard.userPostList.size(); i++) {
                        partition = discBoard.userPostList.get(i).getMessageContent().split(" "); //  Split the user message at each space
                        for (String word: partition) {
                            if (keyWord.equals(word)) {
                                postedFive = true;
                                System.out.println(discBoard.userPostList.get(i).toString()); // Print the post if the given keyword is present
                            }
                        }
                    }

                    if (postedFive == false) {
                        System.out.println("No message contains keyword.");
                    }

                break;

                case 6 : // End programn
                    System.exit(0); // End the program on user input
                break;

                default :
                    System.out.println("!! Invalid menu choice !!");
                break;

                
            }
        }
        scanner.close(); // Close scanner instance
    }

    // ________ CLASS METHODS ________
    //                              Assume fullname is valid before passed, i.e loop valid name is given
    public boolean createUser(String fullName, String userName) {

        if (userName == null || userName.isBlank()) {
            userName = fullName.split(" ")[0].toLowerCase(); // Assign userName to lowercase of full name given if no username was entered
        }

        for (int i = 0; i < userNameList.size(); i++) {
            User user = userNameList.get(i); // Fetch the user at index i and assign it to a new copy of this object

            // Verify if user name already exists in the list
            if (userName.equalsIgnoreCase(user.getUserName())) {
                return false; // Return false indicating user already exist within the array list
            }   
        }

        User newUser = new User(userName, fullName); // Create new user object
        userNameList.add(newUser); // Add user to the user list

        return true; // Return true if user was created succesfully
    }

    public boolean createPost(String userName, Scanner scanner) {

        for (int i = 0; i < userNameList.size(); i++) {
            User user = userNameList.get(i); // Fetch the user at index i and assign it to a new copy of this object

            // Verify if user name already exists in the list
            if (userName.equalsIgnoreCase(user.getUserName())) {
             
                System.out.print("Enter message title: ");
                String postTitle = scanner.nextLine(); // Scan the title of the post from keyboard
                System.out.print("Enter message content: ");
                String postContent = scanner.nextLine(); // Scan the title of the post from keyboard

                Post newPost = new Post(postTitle, postContent, user); // Create new post object
                userPostList.add(newPost); // Add new post to the list

                return true; // Return true indicating post was succesfully created
            }   
        }

        return false; // return false indicating user doesnt exist
    }

    // Method to print menu
    public static void printMenu() {
        System.out.println(
            "\n(1) Create new user\n" + //
            "(2) Create new post\n" + //
            "(3) View all posts\n" + //
            "(4) View all posts with a given username\n" + //
            "(5) View all posts with a given keyword\n" + //
            "(6) End Program"
            );
    }
}