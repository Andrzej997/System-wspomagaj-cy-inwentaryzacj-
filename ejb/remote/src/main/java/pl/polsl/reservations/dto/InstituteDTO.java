/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.reservations.dto;

import java.io.Serializable;
import pl.polsl.reservationsdatabasebeanremote.database.Institutes;

/**
 *
 * @author Wojtek
 */
public class InstituteDTO implements Serializable {

    private Long id;
    
    private String name;
    
    public InstituteDTO(){
    }

    public InstituteDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public InstituteDTO(Institutes i) {
        this.id = i.getId();
        this.name = i.getInstituteName();
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
    
    
}
