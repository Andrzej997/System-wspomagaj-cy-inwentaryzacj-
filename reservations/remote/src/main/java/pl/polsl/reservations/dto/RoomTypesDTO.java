package pl.polsl.reservations.dto;

import java.io.Serializable;

/**
 *
 * @author matis
 */
public class RoomTypesDTO implements Serializable{

    private static final long serialVersionUID = 1773308257625843597L;
    
    private Integer id;
    private String shortDescription;
    private String longDescription;
    
    public RoomTypesDTO(){
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @param shortDescription the shortDescription to set
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * @return the longDescription
     */
    public String getLongDescription() {
        return longDescription;
    }

    /**
     * @param longDescription the longDescription to set
     */
    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
    
    
}
