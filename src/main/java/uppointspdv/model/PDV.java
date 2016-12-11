/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppointspdv.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author ernanirst
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PDV.findAll", query="SELECT e FROM PDV e"),
    @NamedQuery(name = "PDV.findById", query="SELECT e FROM PDV e WHERE e.id = :id"),
    @NamedQuery(name = "PDV.findByName", query="SELECT e FROM PDV e WHERE e.name = :name")
})
public class PDV implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String working_hours;

    public PDV() {
    }

    public PDV(String name, String phone, String address, String working_hours) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.working_hours = working_hours;
    }

    public PDV(Long id, String name, String phone, String address, String working_hours) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.working_hours = working_hours;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(String working_hours) {
        this.working_hours = working_hours;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PDV)) {
            return false;
        }
        PDV other = (PDV) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uppointspdv.model.PDV[ id=" + id + " ]";
    }
    
}
