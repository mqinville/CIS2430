/*
Name: Felix Mainville
ID: 1279419

Compile command: javac discussionboard/*.java --- Compile all .java files
Run command: java discussionboard.DiscussionBoard -- Run the program with jre
Jar command: java -jar DiscussionBoard.jar
*/
package discussionboard;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

public class DiscussionBoard {
    private static HashMap<String, ArrayList<Integer>> postIndices = new HashMap<>(); // Hashmap for user post indices
    private ArrayList<User> userNameList = new ArrayList<User>();   // Array list for stock investments
    private ArrayList<Post> userPostList = new ArrayList<Post>();   // Array list for stock investments

    // _________ MAIN METHOD __________-
    public static void main(String[] args) { // Main method

        Scanner scanner = new Scanner(System.in);  // Create new scanner
        DiscussionBoard discBoard = new DiscussionBoard();

        boolean runProgram = true; // Set boolean - determines if program should continue running

        // Variables for user name
        String firstName;
        String userName;

        while (runProgram) {
            printMenu(); // Print menu
            int userInput = fetchUserChoice(scanner);
            // Switch statement with case for every menu option
            switch(userInput) {
                case 1 : // Create user
                    // Prompt for user name until valid string is typed
                    do {
                        System.out.print("Enter your first name: ");
                        firstName = scanner.nextLine();
                    } while(firstName == null || firstName.isBlank());
                    System.out.print("Enter your username: "); 
                    userName = scanner.nextLine();

                    // If the function returns false print that user cannot be added
                    if (discBoard.createUser(firstName, userName) ==  true) {
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
                    discBoard.viewAllPosts();
                break;

                case 4 : // View all post with given username
                    System.out.print("Enter a user name: ");
                    userName = scanner.nextLine(); // Scan username input
                    boolean postedFour = false;

                    postedFour = discBoard.viewPostByUserName(userName);

                    if (postedFour == false) {
                        System.out.println("No message posted with that username");
                    }
                break;
                
                case 5 : // End programn
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
        try {
            for (int i = 0; i < userNameList.size(); i++) {
                User user = userNameList.get(i); // Fetch the user at index i and assign it to a new copy of this object
    
                // Verify if user name already exists in the list
                if (userName.equalsIgnoreCase(user.getUserName())) {
                    return false; // Return false indicating user already exist within the array list
                }   
            }
            User newUser = new User(userName, fullName); // Create new user object
            userNameList.add(newUser); // Add user to the user list
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return false;
        }
        
    }

    public boolean createPost(String userName, Scanner scanner) {
        try {
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

                    postIndices.putIfAbsent(userName, new ArrayList<>()); // Create a new entry for the username if it is not already present in the list
                    postIndices.get(userName).add(userPostList.size() - 1); // Add the index of the message to the hashmap 

                    return true; // Return true indicating post was succesfully created
                }   
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return false; // return false indicating user doesnt exist
    }

    public void viewAllPosts() {
        for (Post curPost : userPostList) {
            System.out.println(curPost);
        }
    }

    public boolean viewPostByUserName(String userName) {
        int curIndex; // Integer variable to store the index of the current post index in keys arraylist

        if (postIndices.containsKey(userName)) {
            for (int i = 0; i < postIndices.get(userName).size(); i++) {
                curIndex = postIndices.get(userName).get(i); // Get the current value in the keywords arraylist
                System.out.println(userPostList.get(curIndex)); // Print the post at the index in the list
            }

            return true;
        }
        return false;
    }


    public static int fetchUserChoice(Scanner scanner) {

        int userChoice;

        while (true) {
            try {
                System.out.print("Enter your menu choice : ");
                userChoice = scanner.nextInt(); // Scan user input
                return userChoice;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid integer");
            } finally {
                scanner.nextLine();
            }
        }
    }

    // Method to print menu
    public static void printMenu() {
        System.out.println(
            "\n(1) Create new user\n" + //
            "(2) Create new post\n" + //
            "(3) View all posts\n" + //
            "(4) View all posts with a given username\n" + //
            "(5) End Program"
            );
    }
}