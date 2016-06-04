package pl.polsl.reservations.builder;

import pl.polsl.reservations.dto.*;
import pl.polsl.reservations.entities.*;

/**
 *
 * @author matis
 */
public class DTOBuilder {

    public static DepartamentDTO buildDepartamentDTO(Departaments departament) {
        DepartamentDTO departamentDTO = new DepartamentDTO();
        departamentDTO.setId(departament.getId());
        Institutes institute = departament.getInstitute();
        departamentDTO.setInstitute(buildInstituteDTO(institute));
        departamentDTO.setName(departament.getDepratamentName());
        return departamentDTO;
    }

    public static EquipmentDTO buildEquipmentDTO(Equipment equipment) {
        EquipmentDTO equipmentDTO = new EquipmentDTO();
        equipmentDTO.setId(equipment.getId());
        equipmentDTO.setName(equipment.getEquipmentName());
        equipmentDTO.setQuantity(equipment.getQuantity());
        equipmentDTO.setState(equipment.getEquipmentState().getStateDefinition());
        equipmentDTO.setType(equipment.getEquipmentType().getShortDescription());
        return equipmentDTO;
    }

    public static EquipmentStateDTO buildEquipmentStateDTO(EqupmentState equipmentState) {
        EquipmentStateDTO equipmentStateDTO = new EquipmentStateDTO();
        equipmentStateDTO.setDescription(equipmentState.getStateDefinition());
        equipmentStateDTO.setId(equipmentState.getId().longValue());
        return equipmentStateDTO;
    }

    public static EquipmentTypeDTO buildEquipmentTypeDTO(EquipmentType equipmentType) {
        EquipmentTypeDTO equipmentTypeDTO = new EquipmentTypeDTO();
        equipmentTypeDTO.setDescription(equipmentType.getShortDescription());
        equipmentTypeDTO.setId(equipmentType.getId().longValue());
        return equipmentTypeDTO;
    }

    public static InstituteDTO buildInstituteDTO(Institutes institute) {
        InstituteDTO instituteDTO = new InstituteDTO();
        instituteDTO.setId(institute.getId());
        instituteDTO.setName(institute.getInstituteName());
        return instituteDTO;
    }

    public static PrivilegeLevelDTO buildPrivilegeLevelDTO(PriviligeLevels priviligeLevel) {
        PrivilegeLevelDTO privilegeLevelDTO = new PrivilegeLevelDTO();
        privilegeLevelDTO.setDescription(priviligeLevel.getDescription());
        privilegeLevelDTO.setPrivilegeLevel(priviligeLevel.getPriviligeLevel());
        return privilegeLevelDTO;
    }

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

    public static RoomDTO buildRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setDepartment(room.getDepartament().getDepratamentName());
        roomDTO.setId(room.getId());
        roomDTO.setKeeper(room.getKeeper().getWorkerName());
        roomDTO.setNumber(room.getRoomNumber());
        roomDTO.setNumberOfSeats(room.getNumberOfSeats());
        roomDTO.setType(room.getRoomType().getShortDescription());
        return roomDTO;
    }
    
    public static UserDTO buildUserDTO(Users user, Workers worker){
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress(worker.getAdress());
        userDTO.setDepartment(worker.getDepartament().getDepratamentName());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setName(worker.getWorkerName());
        userDTO.setPesel(worker.getPesel());
        userDTO.setPhoneNumber(user.getPhoneNumber().toString());
        userDTO.setPrivilegeLevel(user.getPriviligeLevel().getPriviligeLevel());
        userDTO.setRoomNumber(worker.getRoom().getRoomNumber());
        userDTO.setSurname(worker.getSurname());
        userDTO.setUserName(user.getUsername());
        return userDTO;
    }

    public static ReservationTypeDTO buildReservationTypeDTO(ReservationTypes reservationType){
        ReservationTypeDTO reservationTypeDTO = new ReservationTypeDTO();
        reservationTypeDTO.setShortDescription(reservationType.getTypeShortDescription());
        reservationTypeDTO.setLongDescription(reservationType.getTypeLongDescription());
        reservationTypeDTO.setReservationColor(reservationType.getReservationColor());
        reservationTypeDTO.setId(reservationType.getId());
        return reservationTypeDTO;
    }

}
