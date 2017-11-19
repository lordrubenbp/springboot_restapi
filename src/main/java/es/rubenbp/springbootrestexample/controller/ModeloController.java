package es.rubenbp.springbootrestexample.controller;

import es.rubenbp.springbootrestexample.entity.Modelo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;



@Component
@Path("/rest_service")
public class ModeloController {

     private EntityManager entityManager;

   //private ModeloRepository modeloRepository;

    // TODO LO QUE HAY QUE REGISTRAR EN JERSEYCONFIG ES ESTA CLASE MONGOLO!!!
    @Autowired
    public ModeloController()
    {

        connectHibernate();
    }

    @GET
    @Path("/get_all_modelos")
    @Produces("application/json")
    public List<Modelo> findAllModelos()
    {

        connectHibernate();
        entityManager.getTransaction().begin();
        List<Modelo> listaModelo = null;
        //return modeloRepository.findAll();

        try {
            listaModelo = entityManager.createQuery("from Modelo ", Modelo.class).getResultList();
        }catch (Exception e)
        {
            System.out.println(e);

        }

        entityManager.getTransaction().commit();

        return  listaModelo;

    }

    @POST
    @Path("/post_modelo")
    @Produces("application/json")
    public void addModelo(@QueryParam("nombre") String nombre)
    {
        entityManager.getTransaction().begin();
        Modelo modelo= new Modelo();
        modelo.setNombre(nombre);
        entityManager.persist(modelo);
        entityManager.getTransaction().commit();


    }

    public void connectHibernate()
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();

    }

}
