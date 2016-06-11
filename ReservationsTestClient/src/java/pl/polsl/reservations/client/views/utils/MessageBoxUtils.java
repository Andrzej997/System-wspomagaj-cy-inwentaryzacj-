package pl.polsl.reservations.client.views.utils;

import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import pl.polsl.reservations.client.ClientContext;
import pl.polsl.reservations.client.Lookup;
import pl.polsl.reservations.dto.PrivilegeRequestDTO;
import pl.polsl.reservations.dto.UserDTO;
import pl.polsl.reservations.ejb.remote.UserFacade;
import pl.polsl.reservations.ejb.remote.UserManagementFacade;

/**
 *
 * @author matis
 */
public class MessageBoxUtils {

    private static final UserManagementFacade userManagementFacade = Lookup.getUserManagementFacade();
    private static final UserFacade userFacade = Lookup.getUserFacade();
    
    public static void createPrivilegeError (JComponent parent){
        if(ClientContext.getInstance().isGuest() || ClientContext.getInstance().isStandardUser()){
            createPrivilegeErrorPane(parent);
        } else {
            createPrivilegeRequestErrorPane(parent);
        }
    }

    public static void createPrivilegeErrorPane(JComponent parent) {
        String message = "You don't have access to such funcionality!!!\n";
        String title = "Error";
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void createPrivilegeRequestErrorPane(JComponent parent) {
        if (userFacade.isRequestingHigherPrivilegeLevel()) {
            String message = "You don't have access to such functionality!!!\n"
                    + "Now you are waiting for chief acceptation";
            String title = "Warning";
            JOptionPane.showMessageDialog(parent, message, title,
                    JOptionPane.WARNING_MESSAGE);
        } else {
            String message = "You don't have access to such functionality!!!\n"
                    + "You can request for access to your boss, but \n"
                    + "you have to wait for acceptation";
            String title = "Warning";
            Object[] options = {"REQUEST PRIVILEGE", "ABORT"};
            int optionIndex = JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            String text = (String) options[optionIndex];
            if (!text.equals("ABORT")) {
                userFacade.requestHigherPrivilegeLevel("Every reason is a good reason");
                createHigherLevelRequestMessage(parent);
            }
        }
    }

    public static void createHigherLevelRequestMessage(JComponent parent) {
        JOptionPane.showMessageDialog(parent, "Your request is registered.\n "
                + "Now you can only wait for acceptation", "Request registered",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void createAvaliableRequestsMessage(JComponent parent) {
        if (ClientContext.getInstance().canAcceptRequests()) {
            List<PrivilegeRequestDTO> operationableRequests = userFacade.getOperationableRequests();
            if(operationableRequests == null){
                return;
            }
            for (PrivilegeRequestDTO privilegeRequest : operationableRequests) {
                Long userID = privilegeRequest.getUserID();
                UserDTO userDetails = userManagementFacade.getUserDetails(userID.intValue());
                List<UserDTO> underlings = userManagementFacade.getUnderlings(ClientContext.getInstance().getCurrentUserId().intValue());
                if (underlings.contains(userDetails)) {
                    String message = "User " + userDetails.getName() + " " + userDetails.getSurname() + "\n";
                    message += "requested for your privilege level!";
                    String title = "Warning";
                    Object[] options = {"ACCEPT", "DECLINE"};
                    int optionIndex = JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    String text = (String) options[optionIndex];
                    if (text.equals("ACCEPT")) {
                        userManagementFacade.acceptPrivilegeRequest(privilegeRequest);
                    } else {
                        userManagementFacade.declinePrivilegeRequest(privilegeRequest);
                    }
                }
            }
        }
    }

}
