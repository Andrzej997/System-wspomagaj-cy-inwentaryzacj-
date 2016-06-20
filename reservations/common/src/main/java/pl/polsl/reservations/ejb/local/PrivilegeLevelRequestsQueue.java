package pl.polsl.reservations.ejb.local;

import java.util.List;
import pl.polsl.reservations.privileges.PrivilegeRequest;

/**
 *
 * @author wojcdeb448
 * @version 1.0
 *
 * Interface represents Privilege requests level queue
 */
public interface PrivilegeLevelRequestsQueue {

    /**
     * Adds privilege request to queue
     *
     * @param privilegeRequest PrivilegeRequest object
     * @return true if success
     */
    public boolean addRequest(PrivilegeRequest privilegeRequest);

    /**
     * Method to remove privilege request from queue
     *
     * @param privilegeRequest PrivilegeRequest object
     * @return true if success
     */
    public boolean removeRequest(PrivilegeRequest privilegeRequest);

    /**
     * Method to check if request is already in queue
     *
     * @param privilegeRequest PrivilegeRequest object
     * @return true if success
     */
    public boolean findRequest(PrivilegeRequest privilegeRequest);

    /**
     * Method returns list of requests for privilege level
     *
     * @param maxLevel selected privilege level
     * @return List of privilege requests
     */
    public List<PrivilegeRequest> getOperationableRequests(Long maxLevel);
}
