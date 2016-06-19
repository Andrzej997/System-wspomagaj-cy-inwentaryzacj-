package pl.polsl.reservations.ejb.local;

import java.util.List;
import pl.polsl.reservations.privileges.PrivilegeRequest;

/**
 *
 * @author wojcdeb448
 */
public interface PrivilegeLevelRequestsQueue {

    public boolean addRequest(PrivilegeRequest privilegeRequest);

    public boolean removeRequest(PrivilegeRequest privilegeRequest);

    public boolean findRequest(PrivilegeRequest privilegeRequest);

    public List<PrivilegeRequest> getOperationableRequests(Long maxLevel);
}
