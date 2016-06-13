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

    public static void createPrivilegeError(JComponent parent) {
        if (ClientContext.getInstance().isGuest() || ClientContext.getInstance().isStandardUser()) {
            createPrivilegeErrorPane(parent);
        } else {
            createPrivilegeRequestErrorPane(parent);
        }
    }

    public static void createPrivilegeErrorPane(JComponent parent) {
        String message = "Access denied";
        String title = "Error";
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void createPrivilegeRequestErrorPane(JComponent parent) {
        if (userFacade.isRequestingHigherPrivilegeLevel()) {
            String message = "Access denied \n"
                    + "Now you are waiting for chief acceptation";
            String title = "Warning";
            JOptionPane.showMessageDialog(parent, message, title,
                    JOptionPane.WARNING_MESSAGE);
        } else {
            String message = "Access denied \n"
                    + "You can request for access to your boss, but \n"
                    + "you have to wait for acceptation";
            String title = "Warning";
            Object[] options = {"REQUEST PRIVILEGE", "ABORT"};
            int optionIndex = JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            String text = null;
            try {
                text = (String) options[optionIndex];
            } catch (ArrayIndexOutOfBoundsException ex) {
                return;
            }
            if (text != null && !text.equals("ABORT")) {
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
            if (operationableRequests == null) {
                return;
            }
            for (PrivilegeRequestDTO privilegeRequest : operationableRequests) {
                Long userID = privilegeRequest.getUserID();
                UserDTO userDetails = userManagementFacade.getUserDetails(userID.intValue());
                List<UserDTO> underlings = userManagementFacade.getUnderlings(ClientContext.getInstance().getCurrentUserId().intValue());
                for (UserDTO user : underlings) {
                    if (user.getId().equals(userDetails.getId())) {
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

    public static Boolean userAlreadyAssignedWarrning(JComponent parent, Integer roomNumber) {
        String message = "Selected user is already assigned to room " + roomNumber.toString();
        String title = "Warning";
        Object[] options = {"ACCEPT", "ABORT"};
        int optionIndex = JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        return optionIndex == 0;
    }

    public static Boolean roomAlreadyAssignedWarrning(JComponent parent, String departament) {
        String message = "Selected room is already assigned to another departament: " + departament;
        String title = "Warning";
        Object[] options = {"ACCEPT", "ABORT"};
        int optionIndex = JOptionPane.showOptionDialog(parent, message, title, JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        return optionIndex == 0;
    }
}
