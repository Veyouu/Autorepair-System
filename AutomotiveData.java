import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.Vector;

public class AutomotiveData {
    private DefaultTableModel appointmentModel = new DefaultTableModel();
    private DefaultTableModel productModel = new DefaultTableModel();
    private DefaultTableModel cartModel = new DefaultTableModel();
    private DefaultTableModel billModel = new DefaultTableModel();
    private DefaultTableModel transactionModel = new DefaultTableModel();

    private int appointmentCount = 0;
    private int billCount = 0;
    private int receiptCount = 0;
    private int productCount = 0;

    private HashMap<Integer, Integer> serviceBillAppointmentIDs = new HashMap<>();

    public AutomotiveData() {
        appointmentModel.addColumn("ID");
        appointmentModel.addColumn("Client");
        appointmentModel.addColumn("Service");
        appointmentModel.addColumn("Vehicle");
        appointmentModel.addColumn("Details");
        appointmentModel.addColumn("Schedule");
        appointmentModel.addColumn("Status");
        appointmentModel.addColumn("Estimate");

        productModel.addColumn("ID");
        productModel.addColumn("Product");
        productModel.addColumn("Category");
        productModel.addColumn("Price");
        productModel.addColumn("Stock");

        cartModel.addColumn("ID");
        cartModel.addColumn("Product");
        cartModel.addColumn("Qty");
        cartModel.addColumn("Subtotal");

        billModel.addColumn("Bill #");
        billModel.addColumn("Client");
        billModel.addColumn("Type");
        billModel.addColumn("Description");
        billModel.addColumn("Amount");
        billModel.addColumn("Approval Status");
        billModel.addColumn("Payment Status");
        billModel.addColumn("Date");

        transactionModel.addColumn("Receipt #");
        transactionModel.addColumn("Bill #");
        transactionModel.addColumn("Client");
        transactionModel.addColumn("Amount");
        transactionModel.addColumn("Method");
        transactionModel.addColumn("Date");

        String[][] products = {
                {"1", "Engine Oil", "Fluids", "450.00", "50"},
                {"2", "Oil Filter", "Parts", "225.00", "40"},
                {"3", "Air Filter", "Parts", "375.00", "30"},
                {"4", "Fuel Filter", "Parts", "225.00", "25"},
                {"5", "Spark Plug", "Parts", "215.00", "60"},
                {"6", "Brake Pads", "Parts", "725.00", "35"},
                {"7", "Brake Shoes", "Parts", "450.00", "20"},
                {"8", "Brake Fluid", "Fluids", "275.00", "45"},
                {"9", "Motorcycle Tires", "Tires", "1600.00", "15"},
                {"10", "Car Tires", "Tires", "4000.00", "20"},
                {"11", "Batteries", "Electrical", "3100.00", "10"},
                {"12", "Coolant", "Fluids", "325.00", "50"},
                {"13", "Gear Oil", "Fluids", "235.00", "30"},
                {"14", "Transmission Oil", "Fluids", "475.00", "25"},
                {"15", "Motorcycle Chains", "Parts", "575.00", "18"}
        };

        for (String[] product : products) {
            productModel.addRow(product);
            productCount++;
        }
    }

    public DefaultTableModel getAppointmentModel() {
        return appointmentModel;
    }

    public DefaultTableModel getProductModel() {
        return productModel;
    }

    public DefaultTableModel getCartModel() {
        return cartModel;
    }

    public DefaultTableModel getBillModel() {
        return billModel;
    }

    public DefaultTableModel getTransactionModel() {
        return transactionModel;
    }

    public Vector<Vector> getAppointmentRecords() {
        return appointmentModel.getDataVector();
    }

    public Vector<Vector> getProductRecords() {
        return productModel.getDataVector();
    }

    public Vector<Vector> getCartRecords() {
        return cartModel.getDataVector();
    }

    public Vector<Vector> getBillRecords() {
        return billModel.getDataVector();
    }

    public Vector<Vector> getTransactionRecords() {
        return transactionModel.getDataVector();
    }

    public int nextAppointmentID() {
        return ++appointmentCount;
    }

    public int nextBillID() {
        return ++billCount;
    }

    public int nextReceiptID() {
        return ++receiptCount;
    }

    public int nextProductID() {
        return ++productCount;
    }

    public void resetProductCount(int count) {
        productCount = count;
    }

    public void linkServiceBill(int billID, int appointmentID) {
        serviceBillAppointmentIDs.put(billID, appointmentID);
    }

    public Integer getAppointmentIDForBill(int billID) {
        return serviceBillAppointmentIDs.get(billID);
    }

    public String getAppointmentStatus(int appointmentID) {
        for (int i = 0; i < appointmentModel.getRowCount(); i++) {
            if (Integer.parseInt(appointmentModel.getValueAt(i, 0).toString()) == appointmentID) {
                return appointmentModel.getValueAt(i, 6).toString();
            }
        }
        return null;
    }

    public boolean updateAppointmentStatus(int appointmentID, String status) {
        boolean found = false;

        for (int i = 0; i < appointmentModel.getRowCount(); i++) {
            if (Integer.parseInt(appointmentModel.getValueAt(i, 0).toString()) == appointmentID) {
                appointmentModel.setValueAt(status, i, 6);
                found = true;
                break;
            }
        }

        if (found) {
            for (int i = 0; i < billModel.getRowCount(); i++) {
                if (billModel.getValueAt(i, 2).toString().equals("Service")) {
                    int billID = Integer.parseInt(billModel.getValueAt(i, 0).toString());
                    Integer linkedAppointmentID = serviceBillAppointmentIDs.get(billID);

                    if (linkedAppointmentID != null && linkedAppointmentID == appointmentID) {
                        billModel.setValueAt(status, i, 5);
                    }
                }
            }
        }

        return found;
    }
}
