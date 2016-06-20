package pl.polsl.reservations.builder;

import pl.polsl.reservations.dto.*;
import pl.polsl.reservations.entities.*;
import pl.polsl.reservations.privileges.PrivilegeRequest;

/**
 *
 * @author Mateusz Sojka
 * @version 1.0
 *
 * Class to build data transport objects from entities
 */
public class DTOBuilder {

    /**
     * Method to build departament data transport object
     *
     * @param departament entity
     * @return DepartamentDTO object
     */
    public static DepartamentDTO buildDepartamentDTO(Departaments departament) {
        DepartamentDTO departamentDTO = new DepartamentDTO();
        departamentDTO.setId(departament.getId());
        Institutes institute = departament.getInstitute();
        departamentDTO.setInstitute(buildInstituteDTO(institute));
        departamentDTO.setName(departament.getDepratamentName());
        return departamentDTO;
    }

    /**
     * Method to build equipment data transport object
     *
     * @param equipment entity
     * @return EquipmentDTO object
     */
    public static EquipmentDTO buildEquipmentDTO(Equipment equipment) {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.setId(equipment.getId());
        equipmentDTO.setName(equipment.getEquipmentName());
        equipmentDTO.setQuantity(equipment.getQuantity());
        equipmentDTO.setState(equipment.getEquipmentState().getStateDefinition());
        equipmentDTO.setType(equipment.getEquipmentType().getShortDescription());
        return equipmentDTO;
    }

    /**
     * Method to build equipment state transport object
     *
     * @param equipmentState entity
     * @return EquipmentStateDTO object
     */
    public static EquipmentStateDTO buildEquipmentStateDTO(EqupmentState equipmentState) {
        EquipmentStateDTO equipmentStateDTO = new EquipmentStateDTO();
        equipmentStateDTO.setDescription(equipmentState.getStateDefinition());
        equipmentStateDTO.setId(equipmentState.getId().longValue());
        return equipmentStateDTO;
    }

    /**
     * Method to build equipment type transport object
     *
     * @param equipmentType entity
     * @return EquipmentTypeDTO object
     */
    public static EquipmentTypeDTO buildEquipmentTypeDTO(EquipmentType equipmentType) {
        EquipmentTypeDTO equipmentTypeDTO = new EquipmentTypeDTO();
        equipmentTypeDTO.setDescription(equipmentType.getShortDescription());
        equipmentTypeDTO.setId(equipmentType.getId().longValue());
        return equipmentTypeDTO;
    }

    /**
     * Method to build institute transport object
     *
     * @param institute entity
     * @return InstituteDTO object
     */
    public static InstituteDTO buildInstituteDTO(Institutes institute) {
        InstituteDTO instituteDTO = new InstituteDTO();
        if (institute != null) {
            instituteDTO.setId(institute.getId());
            instituteDTO.setName(institute.getInstituteName());
            instituteDTO.setChefId(institute.getChief().getId());
        }
        return instituteDTO;
    }

    /**
     * Method to build privilege level transport object
     *
     * @param priviligeLevel entity
     * @return PrivilegeLevelDTO object
     */
    public static PrivilegeLevelDTO buildPrivilegeLevelDTO(PriviligeLevels priviligeLevel) {
        PrivilegeLevelDTO privilegeLevelDTO = new PrivilegeLevelDTO();
        privilegeLevelDTO.setDescription(priviligeLevel.getDescription());
        privilegeLevelDTO.setPrivilegeLevel(priviligeLevel.getPriviligeLevel());
        return privilegeLevelDTO;
    }

    /**
     * Method to build reservation transport object
     *
     * @param reservation entity
     * @return ReservationDTO object
     */
    public static ReservationDTO buildReservationDTO(Reservations reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setEndTime(reservation.getEndTime());
        reservationDTO.setId(reservation.getId());
        reservationDTO.setRoomNumber(reservation.getRoomSchedule().getRoom().getRoomNumber());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setType(reservation.getReservationType().getTypeShortDescription());
        reservationDTO.setUserId(reservation.getUser().getId());
        return reservationDTO;
    }

    /**
     * Method to build room transport object
     *
     * @param room entity
     * @return RoomDTO object
     */
    public static RoomDTO buildRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setDepartment(room.getDepartament().getDepratamentName());
        roomDTO.setId(room.getId());
        roomDTO.setKeeperId(room.getKeeper().getId());
        roomDTO.setNumber(room.getRoomNumber());
        roomDTO.setNumberOfSeats(room.getNumberOfSeats());
        roomDTO.setType(room.getRoomType().getShortDescription());
        return roomDTO;
    }

    /**
     * Method to build user transport object
     *
     * @param user entity
     * @param worker entity
     * @return UserDTO object
     */
    public static UserDTO buildUserDTO(Users user, Workers worker) {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress(worker.getAdress());
        userDTO.setDepartment(worker.getDepartament().getDepratamentName());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setName(worker.getWorkerName());
        userDTO.setPesel(worker.getPesel());
        userDTO.setPhoneNumber(user.getPhoneNumber().toString());
        userDTO.setPrivilegeLevel(user.getPriviligeLevel().getPriviligeLevel());
        if (worker.getRoom() != null) {
            userDTO.setRoomNumber(worker.getRoom().getRoomNumber());
        }
        userDTO.setSurname(worker.getSurname());
        userDTO.setUserName(user.getUsername());
        userDTO.setGrade(worker.getGrade());
        if (worker.getChief() != null) {
            userDTO.setChiefId(worker.getChief().getId());
        }
        return userDTO;
    }

    /**
     * Method to build reservation type transport object
     *
     * @param reservationType entity
     * @return ReservationTypeDTO object
     */
    public static ReservationTypeDTO buildReservationTypeDTO(ReservationTypes reservationType) {
        ReservationTypeDTO reservationTypeDTO = new ReservationTypeDTO();
        reservationTypeDTO.setShortDescription(reservationType.getTypeShortDescription());
        reservationTypeDTO.setLongDescription(reservationType.getTypeLongDescription());
        reservationTypeDTO.setReservationColor(reservationType.getReservationColor());
        reservationTypeDTO.setId(reservationType.getId());
        return reservationTypeDTO;
    }

    /**
     * Method to build privilege request transport object
     *
     * @param pr PrivilegeRequest object
     * @return PrivilegeRequestDTO object
     */
    public static PrivilegeRequestDTO buildPrivilegeRequestDTO(PrivilegeRequest pr) {
        return new PrivilegeRequestDTO(pr.getPrivilegeLevel(), pr.getUserID(), pr.getReason());
    }

    /**
     * Method to build room types transport object
     *
     * @param roomTypes entity
     * @return RoomTypesDTO object
     */
    public static RoomTypesDTO buildRoomTypesDTO(RoomTypes roomTypes) {
        RoomTypesDTO roomTypesDTO = new RoomTypesDTO();
        roomTypesDTO.setId(roomTypes.getRoomType().intValue());
        roomTypesDTO.setLongDescription(roomTypes.getLongDescription());
        roomTypesDTO.setShortDescription(roomTypes.getShortDescription());
        return roomTypesDTO;
    }
}
