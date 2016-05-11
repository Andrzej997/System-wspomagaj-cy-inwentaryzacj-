package pl.polsl.reservations.client;

import pl.polsl.reservationsdatabasebeanremote.database.controllers.*;

/**
 *
 * @author matis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lookup l = new Lookup();

//        UserFacadeRemote test = (UserFacadeRemote) l.getRemote("UserFacade");
//        int s = test.getUser();

        RoomManagementFacadeRemote test2 = (RoomManagementFacadeRemote) l.getRemote("RoomManagementFacade");


        List<Map<String, String>> s = test2.getRoomsList();
        int roomId = Integer.valueOf(s.get(0).get("number"));
        List<Map<String, String>> beforeUpdate = test2.getRoomEquipment(roomId);
        test2.addEquipment(roomId, "Projektor", 4, (short)1, (short)1);
        List<Map<String, String>> afterUpdate = test2.getRoomEquipment(roomId);


        UserManagementFacadeRemote test3 = (UserManagementFacadeRemote) l.getRemote("UserManagementFacade");

        int u = test3.testMethod();

        UsersFacadeRemote usersFacadeRemote = (UsersFacadeRemote) l.getRemote("UsersFacade");
        RoomFacadeRemote roomFacadeRemote = (RoomFacadeRemote) l.getRemote("RoomFacade");
        DepartamentsFacadeRemote departamentsFacadeRemote = (DepartamentsFacadeRemote) l.getRemote("DepartamentsFacade");
        EquipmentFacadeRemote equipmentFacadeRemote = (EquipmentFacadeRemote) l.getRemote("EquipmentFacade");
        EquipmentTypeFacadeRemote equipmentTypeFacadeRemote = (EquipmentTypeFacadeRemote) l.getRemote("EquipmentTypeFacade");
        EquipmentStateFacadeRemote equpmentStateFacadeRemote = (EquipmentStateFacadeRemote) l.getRemote("EquipmentStateFacade");
        InstitutesFacadeRemote institutesFacadeRemote = (InstitutesFacadeRemote) l.getRemote("InstitutesFacade");
        PriviligesFacadeRemote priviligesFacadeRemote = (PriviligesFacadeRemote) l.getRemote("PriviligesFacade");
        PriviligeLevelsFacadeRemote priviligeLevelsFacadeRemote = (PriviligeLevelsFacadeRemote) l.getRemote("PriviligeLevelsFacade");
        ReservationTypesFacadeRemote reservationTypesFacadeRemote = (ReservationTypesFacadeRemote) l.getRemote("ReservationTypesFacade");
        ReservationsFacadeRemote reservationsFacadeRemote = (ReservationsFacadeRemote) l.getRemote("ReservationsFacade");
        RoomTypesFacadeRemote roomTypesFacadeRemote = (RoomTypesFacadeRemote) l.getRemote("RoomTypesFacade");
        WorkersFacadeRemote workersFacadeRemote = (WorkersFacadeRemote) l.getRemote("WorkersFacade");

        usersFacadeRemote.setPriviligeLevel(1);
/*
        EqupmentState equpmentState = new EqupmentState();
        equpmentState.setStateDefinition("Uszkodzony");
        equpmentStateFacadeRemote.create(equpmentState);
        equpmentState = new EqupmentState();
        equpmentState.setStateDefinition("Sprawny");
        equpmentStateFacadeRemote.create(equpmentState);
        equpmentState = new EqupmentState();
        equpmentState.setStateDefinition("Zniszczony");
        equpmentStateFacadeRemote.create(equpmentState);
        equpmentState = new EqupmentState();
        equpmentState.setStateDefinition("W naprawie");
        equpmentStateFacadeRemote.create(equpmentState);

        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setShortDescription("Komputer");
        equipmentType.setLongDescription("Komputer osobisty klasy PC");
        equipmentTypeFacadeRemote.create(equipmentType);
        equipmentType = new EquipmentType();
        equipmentType.setShortDescription("Laptop");
        equipmentType.setLongDescription("Notebook");
        equipmentTypeFacadeRemote.create(equipmentType);
        equipmentType = new EquipmentType();
        equipmentType.setShortDescription("Projektor");
        equipmentType.setLongDescription("Projektor na\u015Bcienny");
        equipmentTypeFacadeRemote.create(equipmentType);
        equipmentType = new EquipmentType();
        equipmentType.setShortDescription("Tablica");
        equipmentType.setLongDescription("Bia\u0142a tablica markerowa");
        equipmentTypeFacadeRemote.create(equipmentType);
        equipmentType = new EquipmentType();
        equipmentType.setShortDescription("G\u0142o\u015Bniki");
        equipmentType.setLongDescription("Urz\u0105dzenie odtwarzaj\u0105ce audio");
        equipmentTypeFacadeRemote.create(equipmentType);
        equipmentType = new EquipmentType();
        equipmentType.setShortDescription("Szafa");
        equipmentType.setLongDescription("Szafa naro\u017Cnikowa dwudrzwiowa");
        equipmentTypeFacadeRemote.create(equipmentType);

        RoomTypes roomType = new RoomTypes();
        roomType.setShortDescription("aula");
        roomType.setLongDescription("sala wyk\u0142adowa");
        roomTypesFacadeRemote.create(roomType);
        roomType = new RoomTypes();
        roomType.setShortDescription("laboratorium");
        roomType.setLongDescription("sala laboratoryjna");
        roomTypesFacadeRemote.create(roomType);
        roomType = new RoomTypes();
        roomType.setShortDescription("sala \u0107wiczeniowa");
        roomType.setLongDescription("sala \u0107wiczeniowa");
        roomTypesFacadeRemote.create(roomType);
        roomType = new RoomTypes();
        roomType.setShortDescription("pok\u00F3j pracowniczy");
        roomType.setLongDescription("pok\u00F3j pracownika wydzia\u0142owego");
        roomTypesFacadeRemote.create(roomType);
        roomType.setShortDescription("sekretariat");
        roomType.setLongDescription("sekretariat zak\u0142adu");
        roomTypesFacadeRemote.create(roomType);

        ReservationTypes rt = new ReservationTypes();
        rt.setTypeShortDescription("Laboratorium");
        rt.setTypeLongDescription("Laboratorium Elektroniki Analogowiej");
        reservationTypesFacadeRemote.create(rt);
        rt = new ReservationTypes();
        rt.setTypeShortDescription("\u0106wiczenia");
        rt.setTypeLongDescription("\u0106wiczenia z matematyki stosowanej");
        reservationTypesFacadeRemote.create(rt);
        rt = new ReservationTypes();
        rt.setTypeShortDescription("Laboratorium");
        rt.setTypeLongDescription("Laboratorium Programowania komputer\u00F3w");
        reservationTypesFacadeRemote.create(rt);
        rt = new ReservationTypes();
        rt.setTypeShortDescription("Wyk\u0142ad EiM");
        rt.setTypeLongDescription("Wyk\u0142ad z Elektroniki i Miernictwa");
        reservationTypesFacadeRemote.create(rt);
        rt = new ReservationTypes();
        rt.setTypeShortDescription("Zebranie ZTI");
        rt.setTypeLongDescription("Zebranie zak\u0142adu teorii informatyki");
        reservationTypesFacadeRemote.create(rt);

        PriviligeLevels pl = new PriviligeLevels();
        pl.setDescription("Admin Priviliges");
        priviligeLevelsFacadeRemote.create(pl);
        pl = new PriviligeLevels();
        pl.setDescription("Institute Chief Priviliges");
        priviligeLevelsFacadeRemote.create(pl);
        pl = new PriviligeLevels();
        pl.setDescription("Departament Chief Priviliges");
        priviligeLevelsFacadeRemote.create(pl);
        pl = new PriviligeLevels();
        pl.setDescription("Technical Chief Priviliges");
        priviligeLevelsFacadeRemote.create(pl);
        pl = new PriviligeLevels();
        pl.setDescription("Technical Worker Priviliges");
        priviligeLevelsFacadeRemote.create(pl);
        pl = new PriviligeLevels();
        pl.setDescription("Standard User Priviliges");
        priviligeLevelsFacadeRemote.create(pl);

        Priviliges priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_PRIVILIGES");
        priviliges.setDescription("Select from priviliges");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_PRIVILIGES");
        priviliges.setDescription("Alter table priviliges");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_INTO_PRIVILIGES");
        priviliges.setDescription("Insert into priviliges");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_PRIVILIGES");
        priviliges.setDescription("update priviliges");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_PRIVILIGES");
        priviliges.setDescription("delete from priviliges");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_PRIVILIGES");
        priviliges.setDescription("create priviliges");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_PRIVILIGES");
        priviliges.setDescription("drop priviliges");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_PRIVILIGE_LEVELS");
        priviliges.setDescription("Select from privilige levels");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_PRIVILIGE_LEVELS");
        priviliges.setDescription("Alter table privilige levels");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_INTO_PRIVILIGE_LEVELS");
        priviliges.setDescription("Insert into privilige levels");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_PRIVILIGE_LEVELS");
        priviliges.setDescription("update privilige levels");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_PRIVILIGE_LEVELS");
        priviliges.setDescription("delete from privilige levels");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_PRIVILIGE_LEVELS");
        priviliges.setDescription("create privilige levels");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_PRIVILIGE_LEVELS");
        priviliges.setDescription("drop privilige levels");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_USERS");
        priviliges.setDescription("Select from users");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_USERS");
        priviliges.setDescription("Alter table users");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_USERS");
        priviliges.setDescription("Insert into users");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_USERS");
        priviliges.setDescription("update users");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_USERS");
        priviliges.setDescription("delete from users");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_USERS");
        priviliges.setDescription("create users");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_USERS");
        priviliges.setDescription("drop users");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_RESERVATION_TYPES");
        priviliges.setDescription("Select from reservation types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_RESERVATION_TYPES");
        priviliges.setDescription("Alter table reservation types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_RESERVATION_TYPES");
        priviliges.setDescription("Insert into reservation types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_RESERVATION_TYPES");
        priviliges.setDescription("update reservation types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_RESERVATION_TYPES");
        priviliges.setDescription("delete from reservation types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_RESERVATION_TYPES");
        priviliges.setDescription("create reservation types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_RESERVATION_TYPES");
        priviliges.setDescription("drop reservation types");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_RESERVATIONS");
        priviliges.setDescription("Select from reservations");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_RESERVATIONS");
        priviliges.setDescription("Alter table reservations");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_RESERVATIONS");
        priviliges.setDescription("Insert into reservations");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_RESERVATIONS");
        priviliges.setDescription("update reservations");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_RESERVATIONS");
        priviliges.setDescription("delete from reservations");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_RESERVATIONS");
        priviliges.setDescription("create reservations");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_RESERVATIONS");
        priviliges.setDescription("drop reservations");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_EQUIPMENT_TYPE");
        priviliges.setDescription("Select from equipment type");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_EQUIPMENT_TYPE");
        priviliges.setDescription("Alter table equipment type");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_EQUIPMENT_TYPE");
        priviliges.setDescription("Insert into equipment type");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_EQUIPMENT_TYPE");
        priviliges.setDescription("update equipment type");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_EQUIPMENT_TYPE");
        priviliges.setDescription("delete from equipment type");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_EQUIPMENT_TYPE");
        priviliges.setDescription("create equipment type");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_EQUIPMENT_TYPE");
        priviliges.setDescription("drop equipment type");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_EQUIPMENT");
        priviliges.setDescription("Select from equipment");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_EQUIPMENT");
        priviliges.setDescription("Alter table equipment");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_EQUIPMENT");
        priviliges.setDescription("Insert into equipment");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_EQUIPMENT");
        priviliges.setDescription("update equipment");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_EQUIPMENT");
        priviliges.setDescription("delete from equipment");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_EQUIPMENT");
        priviliges.setDescription("create equipment");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_EQUIPMENT");
        priviliges.setDescription("drop equipment");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_EQUIPMENT_STATE");
        priviliges.setDescription("Select from equipment state");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_EQUIPMENT_STATE");
        priviliges.setDescription("Alter table equipment state");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_EQUIPMENT_STATE");
        priviliges.setDescription("Insert into equipment state");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_EQUIPMENT_STATE");
        priviliges.setDescription("update equipment state");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_EQUIPMENT_STATE");
        priviliges.setDescription("delete from equipment state");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_EQUIPMENT_STATE");
        priviliges.setDescription("create equipment state");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_EQUIPMENT_STATE");
        priviliges.setDescription("drop equipment state");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_WORKERS");
        priviliges.setDescription("Select from workers");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_WORKERS");
        priviliges.setDescription("Alter table workers");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_WORKERS");
        priviliges.setDescription("Insert into workers");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_WORKERS");
        priviliges.setDescription("update workers");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_WORKERS");
        priviliges.setDescription("delete from workers");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_WORKERS");
        priviliges.setDescription("create workers");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_WORKERS");
        priviliges.setDescription("drop workers");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_ROOM");
        priviliges.setDescription("Select from room");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_ROOM");
        priviliges.setDescription("Alter table room");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_ROOM");
        priviliges.setDescription("Insert into room");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_ROOM");
        priviliges.setDescription("update room");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_ROOM");
        priviliges.setDescription("delete from room");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_ROOM");
        priviliges.setDescription("create room");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_ROOM");
        priviliges.setDescription("drop room");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_ROOM_SCHEDULE");
        priviliges.setDescription("Select from room schedule");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_ROOM_SCHEDULE");
        priviliges.setDescription("Alter table room schedule");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_ROOM_SCHEDULE");
        priviliges.setDescription("Insert into room schedule");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_ROOM_SCHEDULE");
        priviliges.setDescription("update room schedule");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_ROOM_SCHEDULE");
        priviliges.setDescription("delete from room schedule");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_ROOM_SCHEDULE");
        priviliges.setDescription("create room schedule");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_ROOM_SCHEDULE");
        priviliges.setDescription("drop room schedule");
        priviligesFacadeRemote.create(priviliges);

        priviliges = new Priviliges();
        priviliges.setPrivilegeName("SELECT_ROOM_TYPES");
        priviliges.setDescription("Select from room types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_ROOM_TYPES");
        priviliges.setDescription("Alter table room types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_ROOM_TYPES");
        priviliges.setDescription("Insert into room types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_ROOM_TYPES");
        priviliges.setDescription("update room types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_ROOM_TYPES");
        priviliges.setDescription("delete from room types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_ROOM_TYPES");
        priviliges.setDescription("create room types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_ROOM_TYPES");
        priviliges.setDescription("drop room types");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();

        priviliges.setPrivilegeName("SELECT_DEPARTAMENTS");
        priviliges.setDescription("Select from departaments");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_DEPARTAMENTS");
        priviliges.setDescription("Alter table departaments");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_DEPARTAMENTS");
        priviliges.setDescription("Insert into departaments");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_DEPARTAMENTS");
        priviliges.setDescription("update departaments");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_DEPARTAMENTS");
        priviliges.setDescription("delete from departaments");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_DEPARTAMENTS");
        priviliges.setDescription("create departaments");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_DEPARTAMENTS");
        priviliges.setDescription("drop departaments");
        priviligesFacadeRemote.create(priviliges);

        priviliges.setPrivilegeName("SELECT_INSTITUTES");
        priviliges.setDescription("Select from institutes");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("ALTER_INSTITUTES");
        priviliges.setDescription("Alter table institutes");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("INSERT_INSTITUTES");
        priviliges.setDescription("Insert into institutes");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("UPDATE_INSTITUTES");
        priviliges.setDescription("update institutes");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DELETE_INSTITUTES");
        priviliges.setDescription("delete from institutes");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("CREATE_INSTITUTES");
        priviliges.setDescription("create institutes");
        priviligesFacadeRemote.create(priviliges);
        priviliges = new Priviliges();
        priviliges.setPrivilegeName("DROP_INSTITUTES");
        priviliges.setDescription("drop institutes");
        priviligesFacadeRemote.create(priviliges);

        //Admin
        PriviligeLevels find = priviligeLevelsFacadeRemote.find(new Long(1));
        List<Priviliges> findAll = priviligesFacadeRemote.findAll();
        find.setPriviligesCollection(findAll);
        priviligeLevelsFacadeRemote.merge(find);
        List<PriviligeLevels> list = new ArrayList<>();
        list.add(find);
        findAll.stream().forEach((p) -> {
            p.setPriviligeLevelsCollection(list);
            priviligesFacadeRemote.merge(p);
        });

        //Standard User
        PriviligeLevels find2 = priviligeLevelsFacadeRemote.find(new Long(6));
        for (Priviliges p : findAll) {
            if (p.getPrivilegeName().equals("SELECT_ROOM")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find2)) {
                    priviligeLevelsCollection.add(find2);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find2.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find2.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find2);
            }
            if (p.getPrivilegeName().equals("SELECT_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find2)) {
                    priviligeLevelsCollection.add(find2);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find2.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find2.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find2);
            }

            if (p.getPrivilegeName().equals("SELECT_ROOM_SCHEDULE")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find2)) {
                    priviligeLevelsCollection.add(find2);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find2.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find2.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find2);
            }

            if (p.getPrivilegeName().equals("SELECT_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find2)) {
                    priviligeLevelsCollection.add(find2);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find2.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find2.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find2);
            }

            if (p.getPrivilegeName().equals("SELECT_ROOM_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find2)) {
                    priviligeLevelsCollection.add(find2);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find2.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find2.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find2);
            }

            if (p.getPrivilegeName().equals("SELECT_USERS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find2)) {
                    priviligeLevelsCollection.add(find2);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find2.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find2.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find2);
            }
        }

        //Technical worker
        PriviligeLevels find3 = priviligeLevelsFacadeRemote.find(new Long(5));
        for (Priviliges p : findAll) {
            if (p.getPrivilegeName().startsWith("SELECT")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }
            if (p.getPrivilegeName().endsWith("USERS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("ALTER_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("CREATE_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("INSERT_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("SELECT_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("UPDATE_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                priviligesCollection.add(p);
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("DELETE_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("ALTER_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("CREATE_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("INSERT_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("SELECT_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }

            if (p.getPrivilegeName().equals("UPDATE_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find3)) {
                    priviligeLevelsCollection.add(find3);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find3.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find3.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find3);
            }
        }

        //Technical Chief
        PriviligeLevels find4 = priviligeLevelsFacadeRemote.find(new Long(4));
        for (Priviliges p : findAll) {
            if (p.getPrivilegeName().startsWith("SELECT")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }
            if (p.getPrivilegeName().endsWith("USERS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().endsWith("EQUIPMENT")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().endsWith("EQUIPMENT_STATE") && !p.getPrivilegeName().startsWith("DELETE") && !p.getPrivilegeName().startsWith("DROP")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().endsWith("EQUIPMENT_TYPE") && !p.getPrivilegeName().startsWith("DELETE") && !p.getPrivilegeName().startsWith("DROP")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().endsWith("PRIVILIGES") && (p.getPrivilegeName().startsWith("SELECT") || p.getPrivilegeName().startsWith("UPDATE") || p.getPrivilegeName().startsWith("ALTER"))) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("ALTER_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("CREATE_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("INSERT_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("SELECT_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("UPDATE_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("DELETE_RESERVATIONS")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("ALTER_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("CREATE_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("INSERT_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("SELECT_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }

            if (p.getPrivilegeName().equals("UPDATE_RESERVATION_TYPES")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find4)) {
                    priviligeLevelsCollection.add(find4);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find4.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find4.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find4);
            }
        }

        //departament chief
        PriviligeLevels find5 = priviligeLevelsFacadeRemote.find(new Long(3));
        List<Priviliges> priviligesCollection1 = find4.getPriviligesCollection();
        find5.setPriviligesCollection(priviligesCollection1);
        priviligeLevelsFacadeRemote.merge(find5);
        for (Priviliges p : findAll) {
            if (p.getPrivilegeName().equals("DELETE_EQUIPMENT_TYPE")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find5)) {
                    priviligeLevelsCollection.add(find5);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find5.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find5.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find5);
            }
            if (p.getPrivilegeName().endsWith("ROOM")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find5)) {
                    priviligeLevelsCollection.add(find5);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find5.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find5.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find5);
            }

            if (p.getPrivilegeName().endsWith("ROOM_SCHEDULE")) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                priviligeLevelsCollection.add(find5);
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find5.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find5.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find5);
            }

            if ((p.getPrivilegeName().endsWith("DEPARTAMENTS") || p.getPrivilegeName().endsWith("PRIVILIGES")) && (p.getPrivilegeName().startsWith("SELECT") || p.getPrivilegeName().startsWith("UPDATE") || p.getPrivilegeName().startsWith("ALTER"))) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find5)) {
                    priviligeLevelsCollection.add(find5);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find5.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find5.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find5);
            }
        }

        //institute chief
        PriviligeLevels find6 = priviligeLevelsFacadeRemote.find(new Long(2));
        List<Priviliges> priviligesCollection2 = find5.getPriviligesCollection();
        find6.setPriviligesCollection(priviligesCollection2);
        priviligeLevelsFacadeRemote.merge(find6);
        for (Priviliges p : findAll) {
            if ((p.getPrivilegeName().endsWith("DEPARTAMENTS") || p.getPrivilegeName().endsWith("PRIVILIGES")) && (!p.getPrivilegeName().startsWith("CREATE") && !p.getPrivilegeName().startsWith("DROP"))) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find6)) {
                    priviligeLevelsCollection.add(find6);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find6.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find6.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find5);
            }
            if (p.getPrivilegeName().endsWith("INSTITUTES") && (p.getPrivilegeName().startsWith("SELECT") || p.getPrivilegeName().startsWith("UPDATE"))) {
                List<PriviligeLevels> priviligeLevelsCollection = p.getPriviligeLevelsCollection();
                if (!priviligeLevelsCollection.contains(find6)) {
                    priviligeLevelsCollection.add(find6);
                }
                p.setPriviligeLevelsCollection(priviligeLevelsCollection);
                List<Priviliges> priviligesCollection = find6.getPriviligesCollection();
                if (!priviligesCollection.contains(p)) {
                    priviligesCollection.add(p);
                }
                find6.setPriviligesCollection(priviligesCollection);
                priviligesFacadeRemote.merge(p);
                priviligeLevelsFacadeRemote.merge(find5);
            }
        }

        Departaments dep = new Departaments();
        dep.setDepratamentName("Zak\u0142ad mikrokontroler\u00F3w");
        departamentsFacadeRemote.create(dep);
        dep = new Departaments();
        dep.setDepratamentName("Zak\u0142ad oprogramowania");
        departamentsFacadeRemote.create(dep);
        dep = new Departaments();
        dep.setDepratamentName("Zak\u0142ad teorii informatyki");
        departamentsFacadeRemote.create(dep);

        dep = new Departaments();
        dep.setDepratamentName("Zak\u0142ad pomiar\u00F3w");
        departamentsFacadeRemote.create(dep);
        dep = new Departaments();
        dep.setDepratamentName("Zak\u0142ad system\u00F3w sterowania");
        departamentsFacadeRemote.create(dep);

        Workers w = new Workers();
        w.setAdress("Adres");
        w.setGrade("dr");
        w.setPesel("94090500157");
        w.setSurname("Sroka");
        w.setWorkerName("Andrzej");
        w.setDepartamentId(departamentsFacadeRemote.getReference(new Long(1)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adrr");
        w.setGrade("mgr");
        w.setPesel("1515151515");
        w.setSurname("Pstrokaty");
        w.setWorkerName("Grzegorz");
        w.setChiefId(workersFacadeRemote.find(new Long(1)));
        w.setDepartamentId(departamentsFacadeRemote.getReference(new Long(1)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500156");
        w.setSurname("Kania");
        w.setWorkerName("Jacek");
        w.setChiefId(workersFacadeRemote.find(new Long(1)));
        w.setDepartamentId(departamentsFacadeRemote.getReference(new Long(1)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500142");
        w.setSurname("Dziki");
        w.setWorkerName("Zbyszek");
        w.setChiefId(workersFacadeRemote.find(new Long(1)));
        w.setDepartamentId(departamentsFacadeRemote.getReference(new Long(1)));
        workersFacadeRemote.create(w);

        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("dr");
        w.setPesel("94090500144");
        w.setSurname("Sroka");
        w.setWorkerName("Andrzej");
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(2)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adrr");
        w.setGrade("mgr");
        w.setPesel("1515151545");
        w.setSurname("Pstrokaty");
        w.setWorkerName("Grzegorz");
        w.setChiefId(workersFacadeRemote.find(new Long(5)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(2)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500141");
        w.setSurname("Kania");
        w.setWorkerName("Jacek");
        w.setChiefId(workersFacadeRemote.find(new Long(5)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(2)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500178");
        w.setSurname("Dziki");
        w.setWorkerName("Zbyszek");
        w.setChiefId(workersFacadeRemote.find(new Long(5)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(2)));
        workersFacadeRemote.create(w);

        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("dr");
        w.setPesel("94090500179");
        w.setSurname("Sroka");
        w.setWorkerName("Andrzej");
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(2)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adrr");
        w.setGrade("mgr");
        w.setPesel("1515151514");
        w.setSurname("Pstrokaty");
        w.setWorkerName("Grzegorz");
        w.setChiefId(workersFacadeRemote.find(new Long(9)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(3)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500165");
        w.setSurname("Kania");
        w.setWorkerName("Jacek");
        w.setChiefId(workersFacadeRemote.find(new Long(9)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(3)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500166");
        w.setSurname("Dziki");
        w.setWorkerName("Zbyszek");
        w.setChiefId(workersFacadeRemote.find(new Long(9)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(3)));
        workersFacadeRemote.create(w);

        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("dr");
        w.setPesel("94090500168");
        w.setSurname("Sroka");
        w.setWorkerName("Andrzej");
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(4)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adrr");
        w.setGrade("mgr");
        w.setPesel("1515151577");
        w.setSurname("Pstrokaty");
        w.setWorkerName("Grzegorz");
        w.setChiefId(workersFacadeRemote.find(new Long(13)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(4)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500188");
        w.setSurname("Kania");
        w.setWorkerName("Jacek");
        w.setChiefId(workersFacadeRemote.find(new Long(13)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(4)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500889");
        w.setSurname("Dziki");
        w.setWorkerName("Zbyszek");
        w.setChiefId(workersFacadeRemote.find(new Long(13)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(4)));
        workersFacadeRemote.create(w);

        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("dr");
        w.setPesel("94090500189");
        w.setSurname("Sroka");
        w.setWorkerName("Andrzej");
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(5)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adrr");
        w.setGrade("mgr");
        w.setPesel("1515151777");
        w.setSurname("Pstrokaty");
        w.setWorkerName("Grzegorz");
        w.setChiefId(workersFacadeRemote.find(new Long(17)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(5)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500889");
        w.setSurname("Kania");
        w.setWorkerName("Jacek");
        w.setChiefId(workersFacadeRemote.find(new Long(17)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(5)));
        workersFacadeRemote.create(w);
        w = new Workers();
        w.setAdress("Adres");
        w.setGrade("mgr");
        w.setPesel("94090500785");
        w.setSurname("Dziki");
        w.setWorkerName("Zbyszek");
        w.setChiefId(workersFacadeRemote.find(new Long(17)));
        w.setDepartamentId(departamentsFacadeRemote.find(new Long(5)));
        workersFacadeRemote.create(w);*/

    }
}
