/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppointspdv.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import uppointspdv.model.UserPDV;

/**
 *
 * @author ernanirst
 */
public class UserPDVDao {

    private final EntityManager entityManager;

    public UserPDVDao(String persistenceUnitName) {
        this.entityManager = Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
    }
    
    // -------------------------------- CREATE -------------------------------   
    public boolean createUser(UserPDV user) {
        UserPDV isThereUser = this.getUserPDVByUsername(user.getUsername());
        if(isThereUser == null){
            entityManager.getTransaction().begin();
            try {
                entityManager.persist(user);
                entityManager.getTransaction().commit();
                return true;
            } catch (Exception e) {
                entityManager.getTransaction().rollback();
            }
        }
        return false;            
    }
    
    // -------------------------------- READ BY USERNAME -------------------------------
    public UserPDV getUserPDVByUsername(String username){
        try{
            UserPDV user = entityManager.createNamedQuery("UserPDV.findByUsername", UserPDV.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return user;
        } catch (Exception e){
            // multiple users found or no user found
            return null;
        }
    }
    
    // -------------------------------- EVALUATE LOGIN -------------------------------
    public boolean login(UserPDV user){
        try{
            UserPDV checkUser = entityManager.createNamedQuery("UserPDV.findByUserPDV", UserPDV.class)
                    .setParameter("username", user.getUsername())
                    .setParameter("password", user.getPassword())
                    .getSingleResult();
            if(checkUser == null){
                return false;
            }else{
                return true;
            }
        } catch (Exception e){
            // multiple users found or no user found
            return false;
        }
    }
}
