public class AutomotivePasswords extends AutomotivePasswordsList {
    private String clientUsername, clientPass, clientName, adminUsername, adminPass;
    private final AutomotiveData automotiveData = new AutomotiveData();

    void setClientUsername(String inputClientUsername) { clientUsername = inputClientUsername; }
    void setClientPass(String inputClientPass) { clientPass = inputClientPass; }
    void setClientName(String inputClientName) { clientName = inputClientName; }

    void setAdminUsername(String inputAdminUsername) { adminUsername = inputAdminUsername; }
    void setAdminPass(String inputAdminPass) { adminPass = inputAdminPass; }

    String getClientUsername() { return clientUsername; }
    String getClientPass() { return clientPass; }
    String getClientName() {
        if (clientName != null && !clientName.trim().isEmpty()) {
            return clientName;
        }

        if (clientUsername != null) {
            for (int i = 0; i < clientCredList.getRowCount(); i++) {
                if (clientUsername.equals(clientCredList.getValueAt(i, 0).toString())) {
                    return clientCredList.getValueAt(i, 2).toString();
                }
            }
        }

        return clientName;
    }

    String getAdminUsername() { return adminUsername; }
    String getAdminPass() { return adminPass; }

    public AutomotiveData getAutomotiveData() {
        return automotiveData;
    }
}
