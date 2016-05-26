package pl.polsl.reservations.privileges;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author matis
 */
public enum PrivilegeLevelEnum {

    ADMIN(1, "Admin", "ReservationAdminPU"),
    INSTITUTE_CHIEF(2, "Institute Chief", "ReservationIstitutePU"),
    DEPARTAMENT_CHIEF(3, "Departament Chief", "ReservationDepartamentPU"),
    TECHNICAL_CHIEF(4, "Technical Chief", "ReservationTechnicalChiefPU"),
    TECHNICAL_WORKER(5, "Technical Worker", "ReservationTechnicalWorkerPU"),
    STANDARD_USER(6, "Standard User", "ReservationStandardUserPU"),
    TESTER(7, "Tester", "ReservationTestsPU");

    private final Integer value;
    private final String name;
    private final EntityManagerFactory entityManagerFactory;

    PrivilegeLevelEnum(Integer value, String name, String entityManagerFactoryName) {
        this.value = value;
        this.name = name;
        this.entityManagerFactory = Persistence.createEntityManagerFactory(entityManagerFactoryName);
    }

    public Integer getValue() {
        return value;
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public String getName() {
        return name;
    }

    public static PrivilegeLevelEnum getPrivilegeLevel(int level) {
        switch (level) {
            case 1: return ADMIN;
            case 2: return INSTITUTE_CHIEF;
            case 3: return DEPARTAMENT_CHIEF;
            case 4: return TECHNICAL_CHIEF;
            case 5: return TECHNICAL_WORKER;
            case 6: return STANDARD_USER;
            case 7: return TESTER;
            default: return STANDARD_USER;
        }
    }
}
