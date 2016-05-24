package pl.polsl.reservations.dto;

import java.io.Serializable;

/**
 *
 * @author Wojtek
 */
public class DepartamentDTO implements Serializable {

    private static final long serialVersionUID = 3348704551067543455L;

    private Long id;

    private String name;
    
    private InstituteDTO institute;

    public DepartamentDTO() {
    }

    public DepartamentDTO(Long id, String name, InstituteDTO institute) {
        this.id = id;
        this.name = name;
        this.institute = institute;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InstituteDTO getInstitute() {
        return institute;
    }

    public void setInstitute(InstituteDTO institute) {
        this.institute = institute;
    }
    

}
