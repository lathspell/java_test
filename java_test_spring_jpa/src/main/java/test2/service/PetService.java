package test2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test2.model.Pet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class PetService {

    @PersistenceContext
    private EntityManager em;

    public Pet findPetById(long id) {
        return em.find(Pet.class, id);
    }

    public Pet addPet(Pet pet) {
        em.persist(pet);
        em.flush();
        em.refresh(pet);
        return pet;
    }

    public List<Pet> getAllPets() {
        return em.createQuery("SELECT p FROM Pet p ORDER BY p.id", Pet.class).getResultList();
    }
}
