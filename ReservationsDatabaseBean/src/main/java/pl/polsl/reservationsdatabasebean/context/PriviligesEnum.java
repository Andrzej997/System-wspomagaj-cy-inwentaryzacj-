package pl.polsl.reservationsdatabasebean.context;

/**
 *
 * @author matis
 */
public enum PriviligesEnum {
    ADMIN(1, "admin") {
        @Override
        public IPrivilige getPrivilige() {
            return new AdminPrivilige();
        }
    },
    //dodac inne poziomy uprawnien
    STANDARDUSER(6, "user"){
        @Override
        public IPrivilige getPrivilige(){
            return new StandardUserPrivilige();
        }
    },
    TESTER(7, "tester") {
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
