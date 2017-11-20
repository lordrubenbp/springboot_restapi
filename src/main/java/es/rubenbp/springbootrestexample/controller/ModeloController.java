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
import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Component
@Path("/rest_service")
public class ModeloController {

     private EntityManager entityManager;
     private ModeloDAO modeloDAO;

   //private ModeloRepository modeloRepository;


    @Autowired
    public ModeloController()
    {

        connectHibernate();
        modeloDAO= new ModeloDAO();
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

    @GET
    @Path("/get_file")
    @Produces("application/json")
    public String getFile() {

        File file= new File("src/Assets/LEGO_Man.zip");
        if(file.exists())
        {
           return "existe";
        }else
        {
            return "pues no";
        }
    }

    @POST
    @Path("/post_modelo")
    @Produces("application/json")
    public void addModelo()
    {

        // TODO 1 Hacer una segunda tabla donde solo aparezacan las claves y el byte[] de datos para no sobrecargar la lista de objetos

        Modelo modelo= new Modelo();

        File file= new File("src/Assets/LEGO_Man.zip");

        File fileUnzip = new File("src/Assets/LEGO_Man.zip");
        File fileFolderUnzip= new File("src/Assets");

        modeloDAO.unZipIt(fileUnzip,fileFolderUnzip);

        modelo.setData( modeloDAO.fileToByte(file));
        modelo.setNombre(file.getName());
        entityManager.getTransaction().begin();

        //modelo.setNombre(nombre);
        entityManager.persist(modelo);
        entityManager.getTransaction().commit();


    }

    public void connectHibernate()
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();

    }

}


