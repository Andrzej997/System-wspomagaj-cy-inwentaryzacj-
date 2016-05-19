package pl.polsl.reservationsdatabasebeanremote.database.common;

/**
 * Created by Krzysztof Stręk on 2016-05-19.
 */
public enum PrivilegeEnum {
    CHANGE_PASSWORD("P_CHANGE_PASSWORD");

    final String key;

    PrivilegeEnum(String key) {
        this.key = key;
    }

    public static PrivilegeEnum getPrivilege(String key) throws Exception {
        for (PrivilegeEnum e : values()) {
            if (e.key.equals(key)) {
                return e;
            }
        }
        throw new Exception("No such privilege");
    };
}
