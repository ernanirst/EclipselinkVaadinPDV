/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppointspdv.model;

import java.io.Serializable;
import javax.persistence.Column;
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
    @NamedQuery(name = "UserPDV.findAll", query="SELECT e FROM UserPDV e"),
    @NamedQuery(name = "UserPDV.findById", query="SELECT e FROM UserPDV e WHERE e.id = :id"),
    @NamedQuery(name = "UserPDV.findByUsername", query="SELECT e FROM UserPDV e WHERE e.username = :username"),
    @NamedQuery(name = "UserPDV.findByUserPDV", query="SELECT e FROM UserPDV e WHERE e.username = :username AND e.password = :password"),
})
public class UserPDV implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true, nullable=false) 
    private String username;
    @Column(nullable=false) 
    private String password;

    public UserPDV() {
    }

    public UserPDV(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (!(object instanceof UserPDV)) {
            return false;
        }
        UserPDV other = (UserPDV) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uppointspdv.model.Users[ id=" + id + " ]";
    }
    
}
