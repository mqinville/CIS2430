package discussionboard;

public class User {
    private String userName; // Must be lower case, IF NOT GIVEN USE LOWER CASE OF THE FULL NAME
    private String fullName;
    
    // User class constructor
    public User(String userName, String fullName) throws IllegalArgumentException {
        
        // Full name must be given
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        } else {
            this.fullName = fullName;
        }
        
        // **** TEST IF SINGLE NAME STLL GETS SPLIT PROPERLY
        if (userName == null || userName.isBlank()) {
            this.userName = fullName.toLowerCase();
        } else {
            this.userName = userName.toLowerCase(); // Assign user name if it is given
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }
}
