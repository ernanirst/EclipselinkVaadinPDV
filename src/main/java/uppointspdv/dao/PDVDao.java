/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uppointspdv.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import uppointspdv.model.PDV;

/**
 *
 * @author ernanirst
 * use entity manager
 */
public class PDVDao {
private final EntityManager entityManager;

    public PDVDao(String persistenceUnitName) {
        this.entityManager = Persistence.createEntityManagerFactory(persistenceUnitName).createEntityManager();
    }
    
    // -------------------------------- CREATE -------------------------------   
    public void createPDV(PDV pdv) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(pdv);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
    
    // -------------------------------- READ ALL -------------------------------
    public List<PDV> getAllPDVs(){
        return entityManager.createNamedQuery("PDV.findAll", PDV.class).getResultList();
    }

    // -------------------------------- READ BY ID -------------------------------
    public PDV getPDVById(int id){
        return entityManager.find(PDV.class, new Long(id));
    }
        
    // -------------------------------- UPDATE ---------------------------------
    public void updatePDV(PDV pdv){
        entityManager.getTransaction().begin();
        try {
            entityManager.merge(pdv);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
    
    // -------------------------------- DELETE BY ID -------------------------------
    public void deletePDVById(int id){
        entityManager.getTransaction().begin();
        try {
            PDV pdv = entityManager.find(PDV.class, new Long(id));
            if(pdv == null){
                entityManager.getTransaction().rollback();
            }else{
                entityManager.remove(pdv);
                entityManager.getTransaction().commit();
            }
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
}
