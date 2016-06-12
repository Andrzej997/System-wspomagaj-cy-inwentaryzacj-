package pl.polsl.reservations.dto;

import java.io.Serializable;

/**
 *
 * @author Wojtek
 */
public class InstituteDTO implements Serializable {

    private static final long serialVersionUID = -4052463672452317142L;

    private Long id;
    
    private String name;

    private Long chefId;

    public InstituteDTO(){
    }

    public InstituteDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    public Long getChefId() {
        return chefId;
    }

    public void setChefId(Long chefId) {
        this.chefId = chefId;
    }
}
