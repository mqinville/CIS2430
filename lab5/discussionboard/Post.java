package discussionboard;

public class Post {

    private String title;
    private String messageContent;
    private User messageID; // Reference to the user who posted the message
    
    // Post class constructor
    public Post(String title, String messageContent, User messageID) throws IllegalArgumentException{
        
        if (title == null || title.isBlank()) {
            this.title = "No subject";
        } else {
            this.title = title;
        }

        if (messageContent == null || messageContent.isBlank()) {
            throw new IllegalArgumentException("Post title cannot be empty."); // Throw exeption if invalid title is given
        } else {
            this.messageContent = messageContent;
        }

        this.messageID = messageID;
    }

    // Getters for insrance variables
    public String getTitle() {
        return title;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public User getMessageID() {
        return messageID;
    }

    public String toString() {
        return "\nCreated by: " + messageID.getFullName() + " (@" + messageID.getUserName() +")\n" + "Title: " + title + "\n" + messageContent;
    }

}
