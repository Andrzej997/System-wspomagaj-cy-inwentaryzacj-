package pl.polsl.reservationsdatabasebean.context;

/**
 * @author matis
 */
public enum PriviligesEnum {

    ADMIN(1, "Admin") {
        @Override
        public IPrivilige getPrivilige() {
            return new AdminPrivilige();
        }
    },
    INSTITUTE_CHIEF(2, "Institute Chief") {
        @Override
        public IPrivilige getPrivilige() {
            return new InstituteChiefPrivilige();
        }

    },
    DEPARTAMENT_CHIEF(3, "Departament Chief") {
        @Override
        public IPrivilige getPrivilige() {
            return new DepartamentChiefPrivilige();
        }

    },
    TECHNICAL_CHIEF(4, "Technical Chief") {
        @Override
        public IPrivilige getPrivilige() {
            return new TechnicalChiefPrivilige();
        }

    },
    TECHNICAL_WORKER(5, "Technical Worker") {
        @Override
        public IPrivilige getPrivilige() {
            return new TechnicalWorkerPrivilige();
        }

    },
    STANDARD_USER(6, "Standard User") {
        @Override
        public IPrivilige getPrivilige() {
            return new StandardUserPrivilige();
        }
    },
    TESTER(7, "Tester") {
        @Override
        public IPrivilige getPrivilige() {
            return new TesterPrivilige();
        }
    };

    private final Integer value;
    private final String name;

    private PriviligesEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public abstract IPrivilige getPrivilige();

}
