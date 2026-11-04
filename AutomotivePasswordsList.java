import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AutomotivePasswordsList {
    DefaultTableModel adminCredList = new DefaultTableModel();
    DefaultTableModel clientCredList = new DefaultTableModel();
    int userCheck;

    public AutomotivePasswordsList(){
        adminCredList.addColumn("Username");
        adminCredList.addColumn("Password");

        Object[] adminRow1 = {"admin", "admin123"};
        Object[] adminRow2 = {"Jervey", "bossRica"};
        Object[] adminRow3 = {"Blair", "ILoveYouMaam"};
        Object[] adminRow4 = {"Liam", "Uarai"};
        adminCredList.addRow(adminRow4);
        adminCredList.addRow(adminRow3);
        adminCredList.addRow(adminRow2);
        adminCredList.addRow(adminRow1);

        clientCredList.addColumn("Username");
        clientCredList.addColumn("Password");
        clientCredList.addColumn("Name");
        clientCredList.addColumn("Appointments");
        clientCredList.addColumn("Bills");
        clientCredList.addColumn("Transaction Records");

        Object[] clientRow = {"client", "client123", "Jane Doe", "No appointments", "No Bills", "No Transactions"};
        clientCredList.addRow(clientRow);
    }

    String checkAdminCred (String inputAdminUser, String inputAdminPass){
        for(int rowSearch=0; rowSearch< adminCredList.getRowCount(); rowSearch++){
            if(inputAdminUser.equals(adminCredList.getValueAt(rowSearch, 0)) && inputAdminPass.equals(adminCredList.getValueAt(rowSearch, 1))) {
                return "Valid";
            }
        }
        return "Invalid";
    }

    String checkClientCred (String inputClientUser, String inputClientPass){
        for(int rowSearch=0; rowSearch< clientCredList.getRowCount(); rowSearch++){
            if(inputClientUser.equals(clientCredList.getValueAt(rowSearch, 0)) && inputClientPass.equals(clientCredList.getValueAt(rowSearch, 1))) {
                return "Valid";
            }
        }
        return "Invalid";
    }

    int checkClientCred (String inputClientUser, String inputClientPass, String inputClientName){
        for(int rowSearch=0; rowSearch< adminCredList.getRowCount(); rowSearch++){
            if(!inputClientUser.equals(clientCredList.getValueAt(rowSearch, 0))){
                userCheck=0;
                Object[] clientRow = {inputClientUser, inputClientPass, inputClientName, "No Appointments"};
                clientCredList.addRow(clientRow);
            } else{
                JOptionPane.showMessageDialog(null, "USER ALREADY EXISTS", "ERROR", JOptionPane.WARNING_MESSAGE);
                userCheck=1;
                break;
            }
        } return userCheck;

    }


}
