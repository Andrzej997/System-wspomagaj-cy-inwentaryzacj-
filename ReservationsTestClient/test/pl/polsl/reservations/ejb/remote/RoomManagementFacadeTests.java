package pl.polsl.reservations.ejb.remote;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.polsl.reservations.dto.EquipmentStateDTO;

/**
 *
 * @author matis
 */
public class RoomManagementFacadeTests {

    private RoomManagementFacade roomManagementFacade;

    private UserFacade userFacade;
    
    @Before
    public void setUp() throws Exception {
        roomManagementFacade = (RoomManagementFacade) Lookup.getRemote("RoomManagementFacade");
        userFacade.login("tester", "tester");
    }

    @After
    public void tearDown() throws Exception {
        userFacade.logout();
        Lookup.removeUserCertificate();
    }
    
    @Test
    public void test1(){
        List<EquipmentStateDTO> equipmentStates = roomManagementFacade.getEquipmentStates();
        int expected = 4;
        Assert.assertEquals(equipmentStates.size(), expected);
    }
    
}
