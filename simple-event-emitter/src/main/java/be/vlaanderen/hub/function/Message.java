package be.vlaanderen.hub.function;

public class Message {
    private String notificatieId;
    private String correlationId;
    private String clientId;
    private String onderwerp;

    public String getNotificatieId() {
        return notificatieId;
    }

    public void setNotificatieId(String notificatieId) {
        this.notificatieId = notificatieId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOnderwerp() {
        return onderwerp;
    }

    public void setOnderwerp(String onderwerp) {
        this.onderwerp = onderwerp;
    }
}
