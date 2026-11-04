public class AutomotivePasswords extends AutomotivePasswordsList{
    private String clientUsername, clientPass,clientName, adminUsername, adminPass;

    void setClientUsername(String inputClientUsername) {clientUsername = inputClientUsername;}
    void setClientPass(String inputClientPass) {clientPass = inputClientPass;}
    void setClientName(String inputClientName) {clientName = inputClientName;}

    void setAdminUsername(String inputAdminUsername) {adminUsername = inputAdminUsername;}
    void setAdminPass(String inputAdminPass) {adminPass = inputAdminPass;}

    String getClientUsername(){return clientUsername;}
    String getClientPass(){return clientPass;}
    String getClientName(){return clientName;}

    String getAdminUsername(){return adminUsername;}
    String getAdminPass(){return adminPass;}

}
