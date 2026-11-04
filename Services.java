import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalTime;
import java.time.ZoneId;

public class Services {
    private DefaultTableModel appointmentModel = new DefaultTableModel();
    private DefaultTableModel productModel = new DefaultTableModel();
    private DefaultTableModel cartModel = new DefaultTableModel();
    private DefaultTableModel billModel = new DefaultTableModel();
    private DefaultTableModel transModel = new DefaultTableModel();

    private int appointmentCount = 0;
    private int billCount = 0;
    private int receiptCount = 0;

    private JComboBox<String> appointmentServiceCMB;
    private JTextField appointmentVehicleTXT;
    private JTable table1;
    private JTextField appointmentTimeTXT;
    private JSpinner appointmentDateSPN;
    private JButton appointmentSubmitAppBT;
    private JComboBox<String> appointmentProblemCMB;
    private JSpinner appointmentTimeSPN;

    private JButton approveEstimateButton;
    private JButton declineEstimateButton;
    private JButton cancelEstimateButton;
    private JButton refreshButton;

    private JSpinner productQuantitySPN;
    private JButton productAddToCartBT;
    private JTable table2;
    private JTable table3;
    private JTextField productSearchTXT;
    private JTextField productTotalTXT;
    private JButton productRemoveBT;
    private JButton productConfirmProdOrderBT;

    private JTextField billingOutstandingBalanceTXT;
    private JTable billingMyBillsTable;
    private JComboBox<String> billingPaymentMethodCMB;
    private JTable billingTransacRecordTable;
    private JButton billingViewRecieptBT;
    private JButton billingReviewBillBT;

    private JTabbedPane servicesPanel;
    private JButton billingLogOutBT;
    private JScrollPane appointmentTable;
    private JScrollPane productProductTable;
    private JScrollPane productCartTable;

    private AutomotivePasswords sharedCred;
    private Timer philippinesTimeTimer;
    private int lastSyncedMinutes = -1;

    public Services() {
        this(new AutomotivePasswords());
    }

    public Services(AutomotivePasswords sharedCred) {

        this.sharedCred = sharedCred;

        appointmentModel.addColumn("ID");
        appointmentModel.addColumn("Service");
        appointmentModel.addColumn("Vehicle");
        appointmentModel.addColumn("Details");
        appointmentModel.addColumn("Schedule");
        appointmentModel.addColumn("Status");
        appointmentModel.addColumn("Estimate");
        appointmentModel.addColumn("Staff");

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
        billModel.addColumn("Type");
        billModel.addColumn("Description");
        billModel.addColumn("Amount");
        billModel.addColumn("Status");
        billModel.addColumn("Date");

        transModel.addColumn("Receipt #");
        transModel.addColumn("Bill #");
        transModel.addColumn("Amount");
        transModel.addColumn("Method");
        transModel.addColumn("Date");

        table1.setModel(appointmentModel);
        table2.setModel(productModel);
        table3.setModel(cartModel);
        billingMyBillsTable.setModel(billModel);
        billingTransacRecordTable.setModel(transModel);

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
        }

        SpinnerDateModel dateModel = new SpinnerDateModel(
                new Date(), null, null, Calendar.DAY_OF_MONTH
        );

        appointmentDateSPN.setModel(dateModel);
        appointmentDateSPN.setEditor(
                new JSpinner.DateEditor(appointmentDateSPN, "dd/MM/yyyy")
        );

        productQuantitySPN.setModel(new SpinnerNumberModel(1, 1, 100, 1));

        productTotalTXT.setText("₱0.00");
        productTotalTXT.setEditable(false);

        billingOutstandingBalanceTXT.setText("₱0.00");
        billingOutstandingBalanceTXT.setEditable(false);

        LocalTime initialTime = LocalTime.now(ZoneId.of("Asia/Manila"));

        lastSyncedMinutes = initialTime.getHour() * 60 + initialTime.getMinute();

        appointmentTimeSPN.setModel(new AbstractSpinnerModel() {

            private int currentMinutes = initialTime.getHour() * 60 + initialTime.getMinute();

            @Override
            public Object getValue() {
                int hour = currentMinutes / 60;
                int minute = currentMinutes % 60;

                return String.format("%02d:%02d", hour, minute);
            }

            @Override
            public void setValue(Object value) {
                if (value instanceof String) {

                    String[] timeParts = value.toString().split(":");

                    if (timeParts.length == 2) {

                        int hour = Integer.parseInt(timeParts[0]);
                        int minute = Integer.parseInt(timeParts[1]);

                        if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {

                            currentMinutes = (hour * 60) + minute;

                            fireStateChanged();
                        }
                    }
                }
            }

            @Override
            public Object getNextValue() {
                int nextMinutes = (currentMinutes + 5) % 1440;

                int hour = nextMinutes / 60;
                int minute = nextMinutes % 60;

                return String.format("%02d:%02d", hour, minute);
            }

            @Override
            public Object getPreviousValue() {
                int previousMinutes = (currentMinutes - 5 + 1440) % 1440;

                int hour = previousMinutes / 60;
                int minute = previousMinutes % 60;

                return String.format("%02d:%02d", hour, minute);
            }
        });

        appointmentTimeSPN.setEditor(new JSpinner.DefaultEditor(appointmentTimeSPN));
        ((JSpinner.DefaultEditor) appointmentTimeSPN.getEditor()).getTextField().setEditable(false);

        philippinesTimeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                LocalTime currentTime = LocalTime.now(ZoneId.of("Asia/Manila"));

                int currentMinutes = currentTime.getHour() * 60 + currentTime.getMinute();

                if (currentMinutes != lastSyncedMinutes) {

                    lastSyncedMinutes = currentMinutes;

                    appointmentTimeSPN.setValue(String.format(
                            "%02d:%02d",
                            currentTime.getHour(),
                            currentTime.getMinute()
                    ));
                }
            }
        });

        philippinesTimeTimer.start();

        appointmentServiceCMB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedService = (String) appointmentServiceCMB.getSelectedItem();

                if (selectedService == null) {

                    appointmentProblemCMB.setModel(new DefaultComboBoxModel<String>());

                } else if (selectedService.equals("Repair Services")) {

                    RepairService repairService = new RepairService();

                    appointmentProblemCMB.setModel(
                            new DefaultComboBoxModel<String>(repairService.getServiceDetails())
                    );

                } else if (selectedService.equals("Installation Services")) {

                    InstallationService installationService = new InstallationService();

                    appointmentProblemCMB.setModel(
                            new DefaultComboBoxModel<String>(installationService.getServiceDetails())
                    );

                } else {

                    appointmentProblemCMB.setModel(new DefaultComboBoxModel<String>());
                }
            }
        });

        if (appointmentServiceCMB.getSelectedItem() != null) {

            if (appointmentServiceCMB.getSelectedItem().equals("Repair Services")) {

                RepairService repairService = new RepairService();

                appointmentProblemCMB.setModel(
                        new DefaultComboBoxModel<String>(repairService.getServiceDetails())
                );

            } else if (appointmentServiceCMB.getSelectedItem().equals("Installation Services")) {

                InstallationService installationService = new InstallationService();

                appointmentProblemCMB.setModel(
                        new DefaultComboBoxModel<String>(installationService.getServiceDetails())
                );
            }
        }

        appointmentSubmitAppBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String serviceName = (String) appointmentServiceCMB.getSelectedItem();
                String vehicle = appointmentVehicleTXT.getText().trim();
                String problem = (String) appointmentProblemCMB.getSelectedItem();
                String time = appointmentTimeSPN.getValue().toString();

                if (serviceName == null || problem == null || vehicle.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            servicesPanel,
                            "Please complete all appointment details.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                AutomotiveService service;

                if (serviceName.equals("Repair Services")) {

                    service = new RepairService();

                } else if (serviceName.equals("Installation Services")) {

                    service = new InstallationService();

                } else {

                    JOptionPane.showMessageDialog(
                            servicesPanel,
                            "Please select a valid service.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                AppointmentDetails appointment = new AppointmentDetails(service, vehicle, problem);

                if (!appointment.validateServiceRequest().equals("Valid Appointment")) {
                    JOptionPane.showMessageDialog(
                            servicesPanel,
                            appointment.validateServiceRequest(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                String date = new SimpleDateFormat("dd/MM/yyyy").format(
                        (Date) appointmentDateSPN.getValue()
                );

                String schedule = date + " " + time;
                double estimate = appointment.getEstimatedPrice();

                appointmentCount++;

                appointmentModel.addRow(new Object[]{
                        appointmentCount,
                        appointment.getServiceName(),
                        appointment.getVehicleInfo(),
                        appointment.getServiceDetail(),
                        schedule,
                        "Pending",
                        String.format("₱%.2f", estimate),
                        "-"
                });

                billCount++;

                billModel.addRow(new Object[]{
                        billCount,
                        "Service",
                        serviceName + " - " + problem + " (" + vehicle + ")",
                        String.format("₱%.2f", estimate),
                        "Unpaid",
                        new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date())
                });

                double balance = 0;

                for (int i = 0; i < billModel.getRowCount(); i++) {
                    if (billModel.getValueAt(i, 4).toString().equals("Unpaid")) {

                        String amount = billModel.getValueAt(i, 3).toString()
                                .replace("₱", "").replace(",", "");

                        balance += Double.parseDouble(amount);
                    }
                }

                billingOutstandingBalanceTXT.setText(String.format("₱%.2f", balance));

                JOptionPane.showMessageDialog(
                        servicesPanel,
                        "Appointment submitted successfully!"
                );
            }
        });

        productAddToCartBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table2.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(servicesPanel, "Select a product first.");
                    return;
                }

                selectedRow = table2.convertRowIndexToModel(selectedRow);

                int quantity = (Integer) productQuantitySPN.getValue();
                int stock = Integer.parseInt(productModel.getValueAt(selectedRow, 4).toString());

                if (quantity > stock) {
                    JOptionPane.showMessageDialog(servicesPanel, "Not enough stock available.");
                    return;
                }

                String id = productModel.getValueAt(selectedRow, 0).toString();
                String name = productModel.getValueAt(selectedRow, 1).toString();
                double price = Double.parseDouble(productModel.getValueAt(selectedRow, 3).toString());

                OrderCalculator calculator = new OrderCalculator();
                calculator.computeOrder(price, quantity);

                cartModel.addRow(new Object[]{
                        id,
                        name,
                        quantity,
                        String.format("%.2f", calculator.getTotalAmount())
                });

                productModel.setValueAt(stock - quantity, selectedRow, 4);

                double total = calculator.calculateCartTotal(cartModel);
                productTotalTXT.setText(String.format("₱%.2f", total));

                productQuantitySPN.setValue(1);
            }
        });

        productRemoveBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = table3.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(servicesPanel, "Select an item in the cart.");
                    return;
                }

                selectedRow = table3.convertRowIndexToModel(selectedRow);

                String id = cartModel.getValueAt(selectedRow, 0).toString();
                int quantity = Integer.parseInt(cartModel.getValueAt(selectedRow, 2).toString());

                for (int i = 0; i < productModel.getRowCount(); i++) {
                    if (productModel.getValueAt(i, 0).toString().equals(id)) {

                        int stock = Integer.parseInt(productModel.getValueAt(i, 4).toString());

                        productModel.setValueAt(stock + quantity, i, 4);
                        break;
                    }
                }

                cartModel.removeRow(selectedRow);

                OrderCalculator calculator = new OrderCalculator();

                productTotalTXT.setText(
                        String.format("₱%.2f", calculator.calculateCartTotal(cartModel))
                );
            }
        });

        productConfirmProdOrderBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (cartModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(servicesPanel, "Your cart is empty.");
                    return;
                }

                OrderCalculator calculator = new OrderCalculator();
                double total = calculator.calculateCartTotal(cartModel);

                billCount++;

                billModel.addRow(new Object[]{
                        billCount,
                        "Product",
                        "Product Order",
                        String.format("₱%.2f", total),
                        "Unpaid",
                        new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date())
                });

                cartModel.setRowCount(0);
                productTotalTXT.setText("₱0.00");

                double balance = 0;

                for (int i = 0; i < billModel.getRowCount(); i++) {
                    if (billModel.getValueAt(i, 4).toString().equals("Unpaid")) {

                        String amount = billModel.getValueAt(i, 3).toString()
                                .replace("₱", "").replace(",", "");

                        balance += Double.parseDouble(amount);
                    }
                }

                billingOutstandingBalanceTXT.setText(String.format("₱%.2f", balance));

                JOptionPane.showMessageDialog(servicesPanel, "Product order confirmed!");
            }
        });

        billingReviewBillBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = billingMyBillsTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(servicesPanel, "Select a bill first.");
                    return;
                }

                selectedRow = billingMyBillsTable.convertRowIndexToModel(selectedRow);

                if (billModel.getValueAt(selectedRow, 4).toString().equals("Paid")) {
                    JOptionPane.showMessageDialog(servicesPanel, "This bill has already been paid.");
                    return;
                }

                String billNumber = billModel.getValueAt(selectedRow, 0).toString();
                String amount = billModel.getValueAt(selectedRow, 3).toString();

                JComboBox<String> paymentMethodCMB = new JComboBox<>(
                        new String[]{"Cash", "Debit", "Credit"}
                );

                JTextField cardNameTXT = new JTextField();
                JTextField cardNumberTXT = new JTextField();

                JPanel paymentPanel = new JPanel(new java.awt.GridLayout(4, 2, 8, 8));

                paymentPanel.add(new JLabel("Payment Amount:"));
                paymentPanel.add(new JLabel(amount));

                paymentPanel.add(new JLabel("Payment Method:"));
                paymentPanel.add(paymentMethodCMB);

                paymentPanel.add(new JLabel("Cardholder Name:"));
                paymentPanel.add(cardNameTXT);

                paymentPanel.add(new JLabel("Card Number:"));
                paymentPanel.add(cardNumberTXT);

                paymentMethodCMB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String method = paymentMethodCMB.getSelectedItem().toString();

                        boolean cardPayment = method.equals("Debit") || method.equals("Credit");

                        cardNameTXT.setEnabled(cardPayment);
                        cardNumberTXT.setEnabled(cardPayment);

                        if (!cardPayment) {
                            cardNameTXT.setText("");
                            cardNumberTXT.setText("");
                        }
                    }
                });

                cardNameTXT.setEnabled(false);
                cardNumberTXT.setEnabled(false);

                int choice = JOptionPane.showConfirmDialog(
                        servicesPanel,
                        paymentPanel,
                        "Payment",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (choice == JOptionPane.OK_OPTION) {

                    OrderCalculator calculator = new OrderCalculator();
                    String selectedMethod = paymentMethodCMB.getSelectedItem().toString();

                    if (!calculator.validatePayment(
                            selectedMethod,
                            cardNameTXT.getText(),
                            cardNumberTXT.getText()
                    )) {

                        if (selectedMethod.equals("Debit") || selectedMethod.equals("Credit")) {

                            if (cardNameTXT.getText().trim().isEmpty()
                                    || cardNumberTXT.getText().trim().isEmpty()) {

                                JOptionPane.showMessageDialog(
                                        servicesPanel,
                                        "Please enter the cardholder name and card number."
                                );

                            } else {

                                JOptionPane.showMessageDialog(
                                        servicesPanel,
                                        "Please enter a valid card number."
                                );
                            }
                        }

                        return;
                    }

                    if (selectedMethod.equals("Debit")) {
                        selectedMethod = "Debit Card";

                    } else if (selectedMethod.equals("Credit")) {
                        selectedMethod = "Credit Card";
                    }

                    billingPaymentMethodCMB.setSelectedItem(selectedMethod);

                    billModel.setValueAt("Paid", selectedRow, 4);

                    receiptCount++;

                    transModel.addRow(new Object[]{
                            receiptCount,
                            billNumber,
                            amount,
                            selectedMethod,
                            new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(new Date())
                    });

                    double balance = 0;

                    for (int i = 0; i < billModel.getRowCount(); i++) {
                        if (billModel.getValueAt(i, 4).toString().equals("Unpaid")) {

                            String unpaidAmount = billModel.getValueAt(i, 3).toString()
                                    .replace("₱", "").replace(",", "");

                            balance += Double.parseDouble(unpaidAmount);
                        }
                    }

                    billingOutstandingBalanceTXT.setText(String.format("₱%.2f", balance));

                    JOptionPane.showMessageDialog(servicesPanel, "Payment successful!");
                }
            }
        });

        billingViewRecieptBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = billingTransacRecordTable.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(servicesPanel, "Select a transaction first.");
                    return;
                }

                selectedRow = billingTransacRecordTable.convertRowIndexToModel(selectedRow);

                JOptionPane.showMessageDialog(
                        servicesPanel,
                        "RECEIPT\n"
                                + "Receipt #: " + transModel.getValueAt(selectedRow, 0)
                                + "\nBill #: " + transModel.getValueAt(selectedRow, 1)
                                + "\nAmount: " + transModel.getValueAt(selectedRow, 2)
                                + "\nMethod: " + transModel.getValueAt(selectedRow, 3)
                                + "\nDate: " + transModel.getValueAt(selectedRow, 4),
                        "Receipt",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        approveEstimateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(servicesPanel, "Select an appointment first.");
                    return;
                }

                selectedRow = table1.convertRowIndexToModel(selectedRow);

                appointmentModel.setValueAt("Approved", selectedRow, 5);

                JOptionPane.showMessageDialog(servicesPanel, "Appointment approved.");
            }
        });

        declineEstimateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(servicesPanel, "Select an appointment first.");
                    return;
                }

                selectedRow = table1.convertRowIndexToModel(selectedRow);
                appointmentModel.setValueAt("Declined", selectedRow, 5);
                JOptionPane.showMessageDialog(servicesPanel, "Appointment declined.");
            }
        });

        cancelEstimateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(servicesPanel, "Select an appointment first.");
                    return;
                }

                selectedRow = table1.convertRowIndexToModel(selectedRow);
                appointmentModel.setValueAt("Cancelled", selectedRow, 5);
                JOptionPane.showMessageDialog(servicesPanel, "Appointment cancelled.");
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderCalculator calculator = new OrderCalculator();

                productTotalTXT.setText(String.format("₱%.2f", calculator.calculateCartTotal(cartModel)));

                double balance = 0;

                for (int i = 0; i < billModel.getRowCount(); i++) {
                    if (billModel.getValueAt(i, 4).toString().equals("Unpaid")) {
                        String amount = billModel.getValueAt(i, 3).toString().replace("₱", "").replace(",", "");
                        balance += Double.parseDouble(amount);
                    }
                }

                billingOutstandingBalanceTXT.setText(String.format("₱%.2f", balance));

                JOptionPane.showMessageDialog(servicesPanel, "System data refreshed.");
            }
        });

        productSearchTXT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = productSearchTXT.getText().trim().toLowerCase();

                if (search.isEmpty()) {
                    table2.clearSelection();
                    return;
                }

                for (int i = 0; i < productModel.getRowCount(); i++) {
                    String name = productModel.getValueAt(i, 1).toString().toLowerCase();
                    String category = productModel.getValueAt(i, 2).toString().toLowerCase();

                    if (name.contains(search) || category.contains(search)) {
                        int viewRow = table2.convertRowIndexToView(i);
                        table2.setRowSelectionInterval(viewRow, viewRow);
                        table2.scrollRectToVisible(table2.getCellRect(viewRow, 0, true));
                        return;
                    }
                }

                table2.clearSelection();

                JOptionPane.showMessageDialog(servicesPanel, "Product not found.");
            }
        });

        billingLogOutBT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(servicesPanel, "Are you sure you want to log out?", "Confirm Logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (choice == JOptionPane.YES_OPTION) {
                    if (philippinesTimeTimer != null) {
                        philippinesTimeTimer.stop();
                    }

                    JFrame servicesFrame = (JFrame) SwingUtilities.getWindowAncestor(servicesPanel);
                    AutomotiveServiceLogin login = new AutomotiveServiceLogin(sharedCred);
                    JFrame loginFrame = new JFrame("AutoCare Pro - Login");
                    loginFrame.setContentPane(login.getMainPanel());
                    loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loginFrame.pack();
                    loginFrame.setLocationRelativeTo(null);
                    loginFrame.setVisible(true);

                    if (servicesFrame != null) {
                        servicesFrame.dispose();
                    }
                }
            }
        });
    }

    public JTabbedPane getServicesPanel() {
        return servicesPanel;
    }
}