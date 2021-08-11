package be.vlaanderen.hub.function;

public class Notification {
    private String message;

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
