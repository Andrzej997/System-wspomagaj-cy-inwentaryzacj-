package pl.polsl.reservations.ejb.remote;

import java.util.List;
import javax.ejb.Remote;
import pl.polsl.reservations.dto.DepartamentDTO;
import pl.polsl.reservations.dto.InstituteDTO;
import pl.polsl.reservations.dto.PrivilegeLevelDTO;
import pl.polsl.reservations.dto.PrivilegeRequestDTO;
import pl.polsl.reservations.dto.UserDTO;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 *
 * @version 1.0
 *
 * User Management facade remote interface to manage all users in application
 */
@Remote
public interface UserManagementFacade extends AbstractBusinessFacade {

    /**
     * Method to get user details by username
     *
     * @param userName String with username
     * @return user data
     */
    UserDTO getUserDetails(String userName);

    /**
     * Method to get user details by user id
     *
     * @param userId user id
     * @return user data
     */
    UserDTO getUserDetails(int userId);

    /**
     * Method to assign user to chief
     *
     * @param userName username
     * @param chiefName chief username
     * @return true if succesfully assigned
     */
    boolean assignUserToChief(String userName, String chiefName);

    /**
     * Method to assign user to room
     *
     * @param userName String with username
     * @param roomNumber int with room number
     * @return true if succesfully assigned
     */
    boolean assignUserToRoom(String userName, int roomNumber);

    /**
     * Method to assign user to departament
     *
     * @param userName String with username
     * @param departamentId Long with departament id
     * @return true if succesfully assigned
     */
    boolean assignUserToDepartament(String userName, Long departamentId);

    /**
     * Method to change users' privilege level
     *
     * @param userName String with username
     * @param privilegeLevel Long privilege level value to change
     * @return true if success
     */
    boolean changePrivilegeLevel(String userName, Long privilegeLevel);

    /**
     * Method to get all privilege level values
     *
     * @return list of privilege levels
     */
    List<PrivilegeLevelDTO> getAllPrivilegeLevels();

    /**
     * Method to get all departaments
     *
     * @return list of departamenst
     */
    List<DepartamentDTO> getAllDepartaments();

    /**
     * Method to get all institutes
     *
     * @return list of institutes
     */
    List<InstituteDTO> getAllInstitutes();

    /**
     * Method to get users' privilege level by username
     *
     * @param userName String with username
     * @return PrivilegeLevel object
     */
    PrivilegeLevelDTO getUsersPrivilegeLevel(String userName);

    /**
     * Method to get users' privilege level by user id
     *
     * @param userId int with user id
     * @return PrivilegeLevel object
     */
    PrivilegeLevelDTO getUsersPrivilegeLevel(int userId);

    /**
     * Method to get user underlings by username
     *
     * @param userName String with username
     * @return list of users which are underligns of given user
     */
    List<UserDTO> getUnderlings(String userName);

    /**
     * Method to get user underlings by user id
     *
     * @param userId int with user id
     * @return list of users which are underligns of given user
     */
    List<UserDTO> getUnderlings(int userId);

    /**
     * Method to chech user's existence
     *
     * @param user user data
     * @return true if user exists
     */
    boolean checkUserExistence(UserDTO user);

    /**
     * Method to register new user
     *
     * @param user new user data
     * @param password new passwod
     * @return true if succesfully registered
     */
    boolean registerUser(UserDTO user, String password);

    /**
     * Method to accept privilege requests
     *
     * @param privilegeRequestDTO Privilege Request to accept
     * @return true if success
     */
    public boolean acceptPrivilegeRequest(PrivilegeRequestDTO privilegeRequestDTO);

    /**
     * Method to decline privilege requests
     *
     * @param privilegeRequestDTO Privilege Request to decline
     * @return true if success
     */
    public boolean declinePrivilegeRequest(PrivilegeRequestDTO privilegeRequestDTO);

    /**
     * Method to remove user
     *
     * @param user user data
     * @return true if succesfully removed
     */
    public boolean removeUser(UserDTO user);

    /**
     * Method to get possible chiefs by user's privilege level
     *
     * @param privilegeLevel privilege level value
     * @return List of users
     */
    public List<UserDTO> getPossibleChiefs(int privilegeLevel);
}
