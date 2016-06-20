package pl.polsl.reservations.ejb.local;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import pl.polsl.reservations.privileges.PrivilegeRequest;

/**
 *
 * @author wojcdeb448
 * @version 1.0
 *
 * Class represents privilege level request queue
 */
public class PrivilegeLevelRequestsQueueImpl implements PrivilegeLevelRequestsQueue, Serializable {

    private static final long serialVersionUID = 4380284070224771302L;

    /**
     * request queue
     */
    private final List<PrivilegeRequest> currentPrivilegeLevelsQueue;
    
    /**
     * Singleton
     */
    private static final PrivilegeLevelRequestsQueueImpl instance = new PrivilegeLevelRequestsQueueImpl();

    private PrivilegeLevelRequestsQueueImpl() {
        this.currentPrivilegeLevelsQueue = new ArrayList<>();
    }

    public static PrivilegeLevelRequestsQueueImpl getInstance() {
        return instance;
    }

    /**
     * Adds privilege request to queue
     *
     * @param privilegeRequest PrivilegeRequest object
     * @return true if success
     */
    @Override
    public boolean addRequest(PrivilegeRequest privilegeRequest) {
        for (PrivilegeRequest pr : currentPrivilegeLevelsQueue) {
            if (Objects.equals(pr.getPrivilegeLevel(), privilegeRequest.getPrivilegeLevel())
                    && Objects.equals(pr.getUserID(), privilegeRequest.getUserID())) {
                return false;
            }
        }
        currentPrivilegeLevelsQueue.add(privilegeRequest);
        return true;
    }

    /**
     * Method to remove privilege request from queue
     *
     * @param privilegeRequest PrivilegeRequest object
     * @return true if success
     */
    @Override
    public boolean removeRequest(PrivilegeRequest privilegeRequest) {
        for (PrivilegeRequest pr : currentPrivilegeLevelsQueue) {
            if (Objects.equals(pr.getPrivilegeLevel(), privilegeRequest.getPrivilegeLevel())
                    && Objects.equals(pr.getUserID(), privilegeRequest.getUserID())) {
                currentPrivilegeLevelsQueue.remove(pr);
                return true;
            }
        }
        return false;
    }

    /**
     * Method returns list of requests for privilege level
     *
     * @param maxLevel selected privilege level
     * @return List of privilege requests
     */
    @Override
    public List<PrivilegeRequest> getOperationableRequests(Long maxLevel) {
        List<PrivilegeRequest> requests = new ArrayList<>();
        for (PrivilegeRequest pr : currentPrivilegeLevelsQueue) {
            if (pr.getPrivilegeLevel() >= maxLevel) {
                requests.add(pr);
            }
        }
        if (requests.isEmpty()) {
            return null;
        } else {
            return requests;
        }
    }

    /**
     * Method to check if request is already in queue
     *
     * @param privilegeRequest PrivilegeRequest object
     * @return true if success
     */
    @Override
    public boolean findRequest(PrivilegeRequest privilegeRequest) {
        for (PrivilegeRequest pr : currentPrivilegeLevelsQueue) {
            if (Objects.equals(pr.getPrivilegeLevel(), privilegeRequest.getPrivilegeLevel())
                    && Objects.equals(pr.getUserID(), privilegeRequest.getUserID())) {
                return true;
            }
        }
        return false;
    }

}
