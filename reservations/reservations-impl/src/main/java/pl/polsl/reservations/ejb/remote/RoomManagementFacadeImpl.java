package pl.polsl.reservations.ejb.remote;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.interceptor.Interceptors;
import pl.polsl.reservations.annotations.RequiredPrivilege;
import pl.polsl.reservations.builder.DTOBuilder;
import pl.polsl.reservations.dto.*;
import pl.polsl.reservations.ejb.dao.*;
import pl.polsl.reservations.ejb.local.UserContext;
import pl.polsl.reservations.entities.*;
import pl.polsl.reservations.interceptors.PrivilegeInterceptor;
import pl.polsl.reservations.logger.LoggerImpl;
import pl.polsl.reservations.privileges.PrivilegeEnum;

/**
 * Created by Krzysztof Stręk on 2016-05-09.
 */
@Stateful(mappedName = "RoomManagementFacade")
@StatefulTimeout(value = 30)
@Interceptors({LoggerImpl.class, PrivilegeInterceptor.class})
public class RoomManagementFacadeImpl extends AbstractBusinessFacadeImpl implements RoomManagementFacade {

    @EJB
    RoomDao roomsDAO;

    @EJB
    UsersDao userDAO;

    @EJB
    DepartamentsDao departmentDAO;

    @EJB
    InstitutesDao institutesDAO;

    @EJB
    EquipmentStateDao equipmentStateDAO;

    @EJB
    EquipmentTypeDao equipmentTypeDAO;

    @EJB
    WorkersDao workersDAO;

    @EJB
    EquipmentDao equipmentDAO;

    @EJB
    RoomTypesDao roomTypeDAO;

    public RoomManagementFacadeImpl() {
        super();
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_OWN)
    public void removeEquipmentType(int typeId) {
        EquipmentType type = equipmentTypeDAO.find(typeId);
        equipmentTypeDAO.remove(type);
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_OWN)
    public Boolean addEquipmentState(String definition) {
        EqupmentState state = new EqupmentState();
        state.setStateDefinition(definition);
        equipmentStateDAO.create(state);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_OWN)
    public void removeEquipmentState(int stateId) {
        EqupmentState state = equipmentStateDAO.find(stateId);
        equipmentStateDAO.remove(state);
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_OWN)
    public Boolean addEquipmentType(String shortDescription, String longDescription) {
        EquipmentType type = new EquipmentType();
        type.setShortDescription(shortDescription);
        type.setLongDescription(longDescription);
        equipmentTypeDAO.create(type);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ASSIGN_KEEPER)
    public void assignKeeperToRoom(int roomId, int workerId) {
        Room room = roomsDAO.find(roomId);
        room.setKeeper(workersDAO.find(workerId));
        roomsDAO.edit(room);
    }

    @Override
    public void assignRoomToDepartament(Long roomId, Long departmentId) {
        Room room = roomsDAO.find(roomId);
        Departaments departament = departmentDAO.find(departmentId);

        room.setDepartament(departament);
        departament.getRoomCollection().add(room);

        roomsDAO.edit(room);
        departmentDAO.edit(departament);
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ROOMS_LOOKUP)
    public UserDTO getRoomKeeper(int roomId) {
        Room room = roomsDAO.find(roomId);
        Workers w = room.getKeeper();
        Users u = userDAO.find(w.getId());
        return DTOBuilder.buildUserDTO(u, w);
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_OWN)
    public Boolean addEquipment(int roomId, String name, int quantity, short stateId, short typeId) throws UnauthorizedAccessException {
        Equipment newEquipment = new Equipment();
        newEquipment.setEquipmentName(name);
        newEquipment.setQuantity(quantity);
        newEquipment.setEquipmentState(equipmentStateDAO.find(stateId));
        newEquipment.setEquipmentType(equipmentTypeDAO.find(typeId));

        Room room = roomsDAO.find(roomId);
        UserContext userContext = getCurrentUserContext();
        Workers worker = userContext.getUser().getWorkers();

        Equipment old = null;
        List<Equipment> equipmentByRoomNumber = equipmentDAO.getEquipmentByRoomNumber(room.getRoomNumber());
        for (Equipment e : equipmentByRoomNumber) {
            if (e.getEquipmentName().equals(name) && e.getEquipmentState().getId() == stateId
                    && e.getEquipmentType().getId() == typeId) {
                old = e;
            }
        }
        if (old != null) {
            old.setQuantity(old.getQuantity() + quantity);
            equipmentDAO.merge(old);
            return true;
        }

        if (userContext.checkPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_WORKER)
                || room.getKeeper().getId().equals(worker.getId())) {
            newEquipment.setRoom(room);
            equipmentDAO.create(newEquipment);
        } else {
            throw new UnauthorizedAccessException("No access to room with ID: " + roomId);
        }
        return true;

    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_OWN)
    public void editEquipment(Long equipmentId, String name, int quantity, short stateId, short typeId) {
        Equipment equipment = equipmentDAO.find(equipmentId);
        equipment.setEquipmentName(name);
        equipment.setQuantity(quantity);

        EquipmentType type = equipmentTypeDAO.find(typeId);
        equipment.setEquipmentType(type);
        type.getEquipmentCollection().add(equipment);
        equipmentTypeDAO.edit(type);

        EqupmentState state = equipmentStateDAO.find(stateId);
        equipment.setEquipmentState(state);
        state.getEquipmentCollection().add(equipment);
        equipmentStateDAO.edit(state);

        equipmentDAO.edit(equipment);
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ROOMS_LOOKUP)
    public List<EquipmentDTO> getDepartmentEquipment(int departmentId) {
        List<Room> departmentRooms = departmentDAO.getRoomCollectionById((long) departmentId);
        List<EquipmentDTO> result = new ArrayList<>();

        for (Room room : departmentRooms) {
            List<Equipment> roomEquipment = room.getEquipmentCollection();
            for (Equipment e : roomEquipment) {
                result.add(DTOBuilder.buildEquipmentDTO(e));
            }
        }

        return result;
    }

    @Override
    public List<RoomDTO> getRoomsList() {
        List<Room> rooms = roomsDAO.findAll();
        List<RoomDTO> result = new ArrayList<>();

        for (Room r : rooms) {
            result.add(DTOBuilder.buildRoomDTO(r));
        }

        return result;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ROOMS_LOOKUP)
    public List<RoomDTO> getRoomsWithNumberOfSeatsHigherEqualThan(Number numberOfSeats) {
        List<Room> rooms = roomsDAO.getRoomWithNumOfSeatsHigherOrEqualThan(numberOfSeats);
        List<RoomDTO> result = new ArrayList<>();

        for (Room r : rooms) {
            result.add(DTOBuilder.buildRoomDTO(r));
        }

        return result;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ROOMS_LOOKUP)
    public List<EquipmentDTO> getRoomEquipment(int roomId) {

        List<Equipment> equpment = equipmentDAO.getEquipmentByRoomNumber(roomsDAO.find(roomId).getRoomNumber());
        List<EquipmentDTO> result = new ArrayList<>();

        for (Equipment e : equpment) {
            result.add(DTOBuilder.buildEquipmentDTO(e));
        }

        return result;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_OWN)
    public void moveEquipment(int equipmentId, int roomToId) throws UnauthorizedAccessException {
        UserContext userContext = getCurrentUserContext();

        Equipment e = equipmentDAO.find(equipmentId);
        Workers worker = userContext.getUser().getWorkers();
        Room roomFrom = e.getRoom();
        Room roomTo = roomsDAO.find(roomToId);

        if (userContext.checkPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_WORKER)
                || (roomFrom.getKeeper().getId().equals(worker.getId())
                && roomTo.getKeeper().getId().equals(worker.getId()))) {

            e.setRoom(roomsDAO.find(roomToId));
            equipmentDAO.edit(e);
        } else {
            throw new UnauthorizedAccessException("No access to room with ID: " + roomToId + " or " + roomFrom.getId());
        }
    }

    @Override
    public Boolean addRoom(int roomNumber, Long keeperId, Long departamentId, Long roomTypeId, int numberOfSeats) {
        if (roomsDAO.getRoomByNumber(roomNumber) != null) {
            return false;
        }
        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setKeeper(workersDAO.find(keeperId));
        room.setDepartament(departmentDAO.find(departamentId));
        room.setRoomType(roomTypeDAO.find(roomTypeId));
        room.setNumberOfSeats(numberOfSeats);
        roomsDAO.create(room);
        return true;
    }

    @Override
    public List<EquipmentStateDTO> getEquipmentStates() {
        List<EquipmentStateDTO> result = new ArrayList<>();
        for (EqupmentState es : equipmentStateDAO.findAll()) {
            result.add(DTOBuilder.buildEquipmentStateDTO(es));
        }
        return result;
    }

    @Override
    public List<RoomTypesDTO> getRoomTypes() {
        List<RoomTypes> findAll = roomTypeDAO.findAll();
        List<RoomTypesDTO> results = new ArrayList<>();
        for (RoomTypes roomTypes : findAll) {
            results.add(DTOBuilder.buildRoomTypesDTO(roomTypes));
        }
        return results;
    }

    @Override
    public List<EquipmentTypeDTO> getEquipmentTypes() {
        List<EquipmentTypeDTO> result = new ArrayList<>();
        for (EquipmentType et : equipmentTypeDAO.findAll()) {
            result.add(DTOBuilder.buildEquipmentTypeDTO(et));
        }
        return result;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.MANAGE_TECH_CHEF_SUBORDINATES)
    public void assignUserToRoom(int roomId, int workerId) {
        Room room = roomsDAO.find(roomId);

        Workers worker = workersDAO.find(workerId);

        worker.setRoom(room);

        workersDAO.edit(worker);
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_OWN)
    public void removeEquipment(int equipmentId) throws UnauthorizedAccessException {
        UserContext userContext = getCurrentUserContext();

        Equipment e = equipmentDAO.find(equipmentId);
        Workers worker = userContext.getUser().getWorkers();
        Room room = e.getRoom();

        if (userContext.checkPrivilege(PrivilegeEnum.EQUIPMENT_MANAGEMENT_WORKER)
                || room.getKeeper().getId().equals(worker.getId())) {

            Equipment eq = equipmentDAO.find(equipmentId);
            equipmentDAO.remove(eq);
        } else {
            throw new UnauthorizedAccessException("No access to room with ID: " + room.getId());
        }
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ROOMS_LOOKUP)
    public RoomDTO getRoom(int roomNumber) {
        Room room = roomsDAO.getRoomByNumber(roomNumber);
        if (room != null) {
            return DTOBuilder.buildRoomDTO(room);
        } else {
            return null;
        }
    }

    @Override
    public List<UserDTO> getRoomWorkers(Long roomId) {
        Room room = roomsDAO.find(roomId);
        List<Workers> workerses = room.getWorkerses();
        List<UserDTO> users = new ArrayList<>();
        for (Workers worker : workerses) {
            Users user = userDAO.find(worker.getId());
            if (user != null) {
                UserDTO userDTO = DTOBuilder.buildUserDTO(user, worker);
                users.add(userDTO);
            }
        }
        return users;
    }

    @Override
    public List<RoomDTO> getInstituteRooms(Long userId) {
        Users user = userDAO.find(userId);
        Workers workers = user.getWorkers();
        Departaments departament = workers.getDepartament();
        Institutes institute = departament.getInstitute();
        List<Departaments> departamentsCollection = institute.getDepartamentsCollection();
        List<RoomDTO> result = new ArrayList<>();
        for (Departaments departaments : departamentsCollection) {
            List<Room> roomCollection = departaments.getRoomCollection();
            for (Room room : roomCollection) {
                RoomDTO roomDTO = DTOBuilder.buildRoomDTO(room);
                result.add(roomDTO);
            }
        }
        return result;
    }

    @Override
    public List<RoomDTO> getDepartamentRooms(Long userId) {
        Users user = userDAO.find(userId);
        Workers workers = user.getWorkers();
        Departaments departament = workers.getDepartament();
        List<Room> roomCollection = departament.getRoomCollection();
        List<RoomDTO> result = new ArrayList<>();
        for (Room room : roomCollection) {
            result.add(DTOBuilder.buildRoomDTO(room));
        }
        return result;
    }

    @Override
    public List<RoomDTO> getDepartamentRoomsById(Long departamentId) {
        Departaments departaments = departmentDAO.find(departamentId);
        if (departaments == null) {
            return null;
        }
        List<Room> roomCollection = departaments.getRoomCollection();
        if (roomCollection == null || roomCollection.isEmpty()) {
            return null;
        }
        List<RoomDTO> result = new ArrayList<>();
        for (Room room : roomCollection) {
            result.add(DTOBuilder.buildRoomDTO(room));
        }
        return result;
    }

    @Override
    public Boolean certificateBean(String certificate) {
        Boolean certificateBean = super.certificateBean(certificate);
        roomsDAO.setUserContext(certificate);
        userDAO.setUserContext(certificate);
        departmentDAO.setUserContext(certificate);
        equipmentStateDAO.setUserContext(certificate);
        equipmentTypeDAO.setUserContext(certificate);
        workersDAO.setUserContext(certificate);
        equipmentDAO.setUserContext(certificate);
        roomTypeDAO.setUserContext(certificate);
        institutesDAO.setUserContext(certificate);
        return certificateBean;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean addRoomType(RoomTypesDTO roomTypesDTO) {
        RoomTypes roomType = new RoomTypes();
        roomType.setLongDescription(roomTypesDTO.getLongDescription());
        roomType.setShortDescription(roomTypesDTO.getShortDescription());
        roomTypeDAO.create(roomType);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean removeRoomType(RoomTypesDTO roomTypesDTO) {
        RoomTypes roomType = roomTypeDAO.find(roomTypesDTO.getId());
        if (roomType == null) {
            return false;
        }
        List<Room> rooms = roomType.getRoomCollection();
        for (Room room : rooms) {
            room.setRoomType(null);
            roomsDAO.edit(room);
        }
        roomTypeDAO.remove(roomType);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean editRoomType(RoomTypesDTO roomTypesDTO) {
        RoomTypes roomType = roomTypeDAO.find(roomTypesDTO.getId());
        if (roomType == null) {
            return false;
        }
        roomType.setLongDescription(roomTypesDTO.getLongDescription());
        roomType.setShortDescription(roomTypesDTO.getShortDescription());

        roomTypeDAO.edit(roomType);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean addDepartament(DepartamentDTO departamentDTO, Long chiefID, Long instituteID) {
        Departaments departament = new Departaments();
        departament.setDepratamentName(departamentDTO.getName());
        departament.setWorkersCollection(null);
        departament.setRoomCollection(null);
        Workers chief = workersDAO.find(chiefID);
        if (chief != null) {
            departament.setChief(chief);
        }

        Institutes institute = institutesDAO.find(instituteID);
        if (institute != null) {
            departament.setInstitute(institute);
        }

        departmentDAO.create(departament);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean removeDepartament(DepartamentDTO departamentDTO) {
        Departaments departament = departmentDAO.find(departamentDTO.getId());
        if (departament == null) {
            return false;
        }

        /*  List<Room> rooms = departament.getRoomCollection();
        if (rooms != null && !rooms.isEmpty()) {
            for (Room room : rooms) {
                room.setDepartament(null);
                roomsDAO.edit(room);
            }
        }

        List<Workers> workers = departament.getWorkersCollection();
        if (workers != null && !workers.isEmpty()) {
            for (Workers worker : workers) {
                worker.setDepartament(null);
                workersDAO.edit(worker);
            }
        }*/
        departmentDAO.remove(departament);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean editDepartament(DepartamentDTO departamentDTO, Long chiefID, Long instituteID) {
        Departaments departament = departmentDAO.find(departamentDTO.getId());
        if (departament == null) {
            return false;
        }
        departament.setDepratamentName(departamentDTO.getName());

        Workers chief = workersDAO.find(chiefID);
        if (chief != null) {
            departament.setChief(chief);
        }

        Institutes institute = institutesDAO.find(instituteID);
        if (institute != null) {
            departament.setInstitute(institute);
        }

        departmentDAO.edit(departament);

        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean addInstitute(InstituteDTO instituteDTO) {
        Institutes institute = new Institutes();

        Workers chief = workersDAO.find(instituteDTO.getChefId());
        if (chief != null) {
            institute.setChief(workersDAO.find(chief.getId()));
        }

        institute.setInstituteName(instituteDTO.getName());
        institutesDAO.create(institute);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean removeInstitute(Long id) {
        Institutes institute = institutesDAO.find(id);
        if (institute == null) {
            return false;
        }
        for (Departaments d : institute.getDepartamentsCollection()) {
            d.setInstitute(null);
            departmentDAO.edit(d);
        }

        institutesDAO.remove(institute);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public boolean editInstitute(InstituteDTO instituteDTO) {
        Institutes institute = institutesDAO.find(instituteDTO.getId());
        if (institute == null) {
            return false;
        }
        Workers chief = workersDAO.find(instituteDTO.getChefId());
        if (chief != null) {
            institute.setChief(workersDAO.find(chief.getId()));
        }
        institute.setChief(workersDAO.find(instituteDTO.getChefId()));
        institute.setInstituteName(instituteDTO.getName());
        institutesDAO.edit(institute);
        return true;
    }

    @Override
    @RequiredPrivilege(PrivilegeEnum.ADMIN_ACTIONS)
    public Long getDepartamentChief(Long departamentId) {
        Departaments departament = departmentDAO.find(departamentId);
        return departament.getChief().getId();
    }
}
