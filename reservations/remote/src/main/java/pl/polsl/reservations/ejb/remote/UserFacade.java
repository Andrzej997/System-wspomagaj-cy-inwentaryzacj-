package pl.polsl.reservations.ejb.remote;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.PrivilegeRequestDTO;
import pl.polsl.reservations.dto.UserDTO;

/**
 * Created by Krzysztof Stręk on 2016-05-07.
 * @version 1.0
 * 
 * User facade remote interface to manage current user actions
 */
@Remote
public interface UserFacade extends AbstractBusinessFacade {

    /**
     * Method to login user
     * 
     * @param nameOrEmail String with username or users' email
     * @param password String with password
     * @return true if login success
     */
    public boolean login(String nameOrEmail, String password);

    /**
     * Method to perform logout
     * @return true if succesfully logout
     */
    public boolean logout();

    /**
     * Method to get current user privilege level
     * @return PrivilegeLevelDTO object
     */
    public PrivilegeLevelDTO getUserPriviligeLevel();

    /**
     * Method to change current user password
     * @param oldPassword String with old password
     * @param newPassword String with new password
     * @return true if password succesfully changed
     */
    public boolean changePassword(String oldPassword, String newPassword);

    /**
     * Method to get current user details
     * @return UserDTO object
     */
    public UserDTO getUserDetails();

    /**
     * Method to change current user details
     * @param user newUser data to change
     * @return true if succesfully changed
     */
    public boolean changeUserDetails(UserDTO user);

    /**
     * Method to perform login as guest action, creates dummy user context
     * @return true if success
     */
    public boolean loginAsGuest();

    /**
     * Method to remove users certificate from bean
     * @param certifiate String with certificate to remove
     */
    public void removeCertificate(String certifiate);

    /**
     * Method to get Privilege level which current user can obtain
     * @return PrivilegeLevelDTO with obtainable level
     */
    public PrivilegeLevelDTO getObtainablePrivilegeLevel();

    /**
     * Method to request higher privilege level
     * @param reason String with request reason
     * @return true if success
     */
    public boolean requestHigherPrivilegeLevel(String reason);

    /**
     * Method to check if user is already requesting higher privilege level
     * @return true if user is requesting higher privilege level
     */
    public boolean isRequestingHigherPrivilegeLevel();


    /**
     * Method to get users with lower privilege level than current user
     * @return list of users
     */
    public List<UserDTO> getUsersWithLowerPrivilegeLevel();

    /**
     * Method to get list of higher privilege level requests from queue 
     * @return List of Privilege Requests
     */
    public List<PrivilegeRequestDTO> getOperationableRequests();

    /**
     * Method to change another user data
     * @param userDTO user data to change
     * @return true if success
     */
    public Boolean changeAnotherUserDetails(UserDTO userDTO);
}
