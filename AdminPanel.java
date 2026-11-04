import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanel {
    private JTabbedPane tabbedPane1;
    private JButton btnLogOut;
    private JTable tblClientAppointmentMastList;
    private JButton btnConfirmApp;
    private JButton btnCancelAppointment;
    private JButton btnInspectEstimate;
    private JTable tblProdMasterList;
    private JScrollPane scrollPane;
    private JComboBox cmbCashPayment;
    private JButton btnRecordPayment;
    private JButton btnRefreshBillHistory;
    private JTable tblAdminBillHistory;
    private JScrollPane tblBillingRecords;
    private JTable tblTransactionRecords;
    private JButton btnViewReceipt;
    private JPanel adminPanel;
    private JTextField txtProdName;
    private JTextField txtProdCat;
    private JTextField txtProdPrice;
    private JTextField txtProdStock;
    private JButton btnProdAdd;
    private JButton btnProdUpdate;
    private JButton btnProdDelete;
    private JButton btnProdClear;

    public JPanel getAdminPanel(){return adminPanel;}
    public static DefaultTableModel clientAppointmentListTable = new DefaultTableModel();
    public static DefaultTableModel productMasterlistTable = new DefaultTableModel();
    public static DefaultTableModel transactionRecordsTable = new DefaultTableModel();
    public static DefaultTableModel billingRecordsTable = new DefaultTableModel();

    String prodName, prodCategory;
    float prodPrice;
    int prodStock, prodID=0;

    public AdminPanel(AutomotivePasswords clientInfo){
        clientAppointmentListTable.addColumn("ID");
        clientAppointmentListTable.addColumn("Client");
        clientAppointmentListTable.addColumn("Service");
        clientAppointmentListTable.addColumn("Vehicle");
        clientAppointmentListTable.addColumn("Details");
        clientAppointmentListTable.addColumn("Schedule");
        clientAppointmentListTable.addColumn("Status");
        clientAppointmentListTable.addColumn("Estimate");

        tblClientAppointmentMastList.setModel(clientAppointmentListTable);

        productMasterlistTable.addColumn("ID");
        productMasterlistTable.addColumn("Name");
        productMasterlistTable.addColumn("Category");
        productMasterlistTable.addColumn("Price");
        productMasterlistTable.addColumn("Stock");

        tblProdMasterList.setModel(productMasterlistTable);

        transactionRecordsTable.addColumn("Receipt#");
        transactionRecordsTable.addColumn("Bill#");
        transactionRecordsTable.addColumn("Client");
        transactionRecordsTable.addColumn("Amount");
        transactionRecordsTable.addColumn("Method");
        transactionRecordsTable.addColumn("Date");

        tblTransactionRecords.setModel(transactionRecordsTable);

        billingRecordsTable.addColumn("Bill#");
        billingRecordsTable.addColumn("Client");
        billingRecordsTable.addColumn("Type");
        billingRecordsTable.addColumn("Description");
        billingRecordsTable.addColumn("Amount");
        billingRecordsTable.addColumn("Status");
        billingRecordsTable.addColumn("Date");

        tblAdminBillHistory.setModel(billingRecordsTable);

        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    JFrame loginFrame = new JFrame("Login");
                    AutomotiveServiceLogin loginPanel = new AutomotiveServiceLogin(clientInfo);

                    JPanel panel = loginPanel.getMainPanel();
                    if (panel==null) {
                        JOptionPane.showMessageDialog(null, "Error: getMainPanel() returned null. Check AutomotiveServiceLogin.java!", "Null Panel Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    loginFrame.setContentPane(panel);
                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loginFrame.pack();
                    loginFrame.setLocationRelativeTo(null);
                    loginFrame.setVisible(true);

                    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(adminPanel);
                    if (currentFrame != null) {
                        currentFrame.dispose();
                    }
                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        btnProdAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    prodName = txtProdName.getText().trim();
                    prodCategory = txtProdCat.getText().trim();
                    for(int rowSearch=0; rowSearch<productMasterlistTable.getRowCount(); rowSearch++){
                        if(prodName.equals(productMasterlistTable.getValueAt(rowSearch, 1))){
                            JOptionPane.showMessageDialog(null, "Product Already Exists!", "ERROR", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                    }
                    if(prodName.isEmpty()||prodCategory.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Invalid Input!", "ERROR", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    prodPrice = Float.parseFloat(txtProdPrice.getText().trim());
                    prodStock = Integer.parseInt(txtProdStock.getText().trim());

                    prodID++;
                    Object[] prodList = {prodID, prodName, prodCategory, prodPrice, prodStock};
                    productMasterlistTable.addRow(prodList);
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Input!", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnProdUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int selectedRow = tblProdMasterList.getSelectedRow();

                    if(selectedRow==-1){
                        JOptionPane.showMessageDialog(null, "Please Select an item to Update", "ERROR", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    prodName = txtProdName.getText().trim();
                    prodCategory = txtProdCat.getText().trim();
                    prodPrice = Float.parseFloat(txtProdPrice.getText().trim());
                    prodStock = Integer.parseInt(txtProdStock.getText().trim());

                    if(prodName.isEmpty()||prodCategory.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Invalid Input!", "ERROR", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    productMasterlistTable.setValueAt(prodName, selectedRow, 1);
                    productMasterlistTable.setValueAt(prodCategory, selectedRow, 2);
                    productMasterlistTable.setValueAt(prodPrice, selectedRow, 3);
                    productMasterlistTable.setValueAt(prodStock, selectedRow, 4);

                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Input!", "ERROR", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        btnProdDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblProdMasterList.getSelectedRow();

                if(selectedRow==-1){
                    JOptionPane.showMessageDialog(null, "Please Select an item to delete", "ERROR", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int row = tblProdMasterList.convertRowIndexToModel(selectedRow);
                productMasterlistTable.removeRow(row);

                for(int reID=0; reID<productMasterlistTable.getRowCount(); reID++){
                    productMasterlistTable.setValueAt(reID+1, reID, 0);
                }

                prodID=productMasterlistTable.getRowCount();
            }
        });
    }
}
