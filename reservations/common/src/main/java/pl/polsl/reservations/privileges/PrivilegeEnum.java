package pl.polsl.reservations.privileges;

/**
 * Created by Krzysztof Strek on 2016-05-19.
 *
 * @version 1.0
 *
 * Enum representing user privileges to dedicated actions
 */
public enum PrivilegeEnum {
    //uprawnienia według tabelki ról w dokumentacji
    //_OWN onacza własne rezerwacje/sale itp, _WORKER sale/reserwacje podwładnych
    ADD_RESERVATION("P_ADD_RESERVATION"),
    MODIFY_RESERVATION_OWN("P_MODIFY_RESERVATION_OWN"), //usuwanie to też modyfikacja
    MODIFY_RESERVATION_WORKER("P_MODIFY_RESERVATION_WORKER"),
    ROOMS_LOOKUP("P_ROOMS_LOOKUP"),
    SCHEDULE_LOOKUP("P_SCHEDULE_LOOKUP"),
    EQUIPMENT_MANAGEMENT_OWN("P_EQUIPMENT_MANAGEMENT_OWN"),
    EQUIPMENT_MANAGEMENT_WORKER("P_EQUIPMENT_MANAGEMENT_WORKER"),
    ASSIGN_KEEPER("P_ASSIGN_KEEPER"),//przypisywanie opiekuna do sali
    REPORT_GENERATION("P_REPORT_GENERATION"),
    GRANT_PRIVILEGES_DEPARTMENT("P_GRANT_PRIVILEGES_DEPARTMENT"),//zmiana uprawnień w ramach zakładu
    GRANT_PRIVILEGES_INSTITUTE("P_GRANT_PRIVILEGES_INSTITUTE"),
    MANAGE_TECH_CHEF_SUBORDINATES("P_MANAGE_TECH_CHEF_SUBORDINATES"),//edycja/dodawanie podwładnych kierownika technicznego
    MANAGE_INSTITUTE_WORKERS("P_MANAGE_INSTITUTE_WORKERS"),
    ADMIN_ACTIONS("P_ADMIN_ACTIONS"); //wszystkie uprawnienia które ma tylko admin

    final String key;

    PrivilegeEnum(String key) {
        this.key = key;
    }

    /**
     * Method returns enum value by key
     *
     * @param key String with privilege name
     * @return PrivilegeEnum value
     * @throws Exception when there is no such privilege
     */
    public static PrivilegeEnum getPrivilege(String key) throws Exception {
        for (PrivilegeEnum e : values()) {
            if (e.key.equals(key)) {
                return e;
            }
        }
        throw new Exception("No enum for privilege " + key);
    }
}
