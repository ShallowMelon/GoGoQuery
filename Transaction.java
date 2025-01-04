package pack;

import javafx.beans.property.*;

public class Transaction {
    private final IntegerProperty transactionID;
    private final IntegerProperty customerID;
    private final StringProperty customerEmail;
    private final StringProperty dateCreated;
    private final DoubleProperty total;
    private final StringProperty status;

    public Transaction(int transactionID, int customerID, String customerEmail, String dateCreated, double total, String status) {
        this.transactionID = new SimpleIntegerProperty(transactionID);
        this.customerID = new SimpleIntegerProperty(customerID);
        this.customerEmail = new SimpleStringProperty(customerEmail);
        this.dateCreated = new SimpleStringProperty(dateCreated);
        this.total = new SimpleDoubleProperty(total);
        this.status = new SimpleStringProperty(status);
    }

    public int getTransactionID() { return transactionID.get(); }
    public IntegerProperty transactionIDProperty() { return transactionID; }

    public int getCustomerID() { return customerID.get(); }
    public IntegerProperty customerIDProperty() { return customerID; }

    public String getCustomerEmail() { return customerEmail.get(); }
    public StringProperty customerEmailProperty() { return customerEmail; }

    public String getDateCreated() { return dateCreated.get(); }
    public StringProperty dateCreatedProperty() { return dateCreated; }

    public double getTotal() { return total.get(); }
    public DoubleProperty totalProperty() { return total; }

    public String getStatus() { return status.get(); }
    public StringProperty statusProperty() { return status; }
}
