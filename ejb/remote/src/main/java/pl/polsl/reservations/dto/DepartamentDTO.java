/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.dto;

import java.io.Serializable;
import pl.polsl.reservationsdatabasebeanremote.database.Departaments;

/**
 *
 * @author Wojtek
 */
public class DepartamentDTO implements Serializable {

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
    
    public DepartamentDTO(Departaments d) {
        this.id = d.getId();
        this.name = d.getDepratamentName();
        this.institute = new InstituteDTO(d.getInstituteId());
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
