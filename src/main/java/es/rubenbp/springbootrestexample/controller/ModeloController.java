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
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

        modeloDAO= new ModeloDAO();
        connectHibernate();

    }

    @GET
    @Path("/get_all_modelos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Modelo> getAllModelos()
    {

        entityManager.getTransaction().begin();
        List<Modelo> listaModelo = null;
        //return modeloRepository.findAll();

        try {
            listaModelo = entityManager.createQuery(" from Modelo ", Modelo.class).getResultList();
        }catch (Exception e)
        {
            System.out.println(e);

        }

        entityManager.getTransaction().commit();

        return  listaModelo;

    }
    @GET
    @Path("/get_all_modelos_animados")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Modelo> getAllModelosAnimados()
    {

        entityManager.getTransaction().begin();
        List<Modelo> listaModelo = null;
        //return modeloRepository.findAll();

        try {
            listaModelo = entityManager.createQuery(" from Modelo where tipo='animado' ", Modelo.class).getResultList();
        }catch (Exception e)
        {
            System.out.println(e);

        }

        entityManager.getTransaction().commit();

        return  listaModelo;

    }
    @GET
    @Path("/get_all_modelos_noanimados")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Modelo> getAllModelosNoAnimados()
    {

        entityManager.getTransaction().begin();
        List<Modelo> listaModelo = null;
        //return modeloRepository.findAll();

        try {
            listaModelo = entityManager.createQuery(" from Modelo where tipo='noanimado' ", Modelo.class).getResultList();
        }catch (Exception e)
        {
            System.out.println(e);

        }

        entityManager.getTransaction().commit();

        return  listaModelo;

    }

    @GET
    @Path("/get_data")
    //@Produces(MediaType.TEXT_PLAIN)
    public byte[] getModeloData(@QueryParam("id")int id) {


        Query query= entityManager.createQuery(" select data from Modelo  where id=:param1");
        query.setParameter("param1",id);

        byte[] data= (byte[]) query.getSingleResult();

         //byte[] data= (byte[]) entityManager.createQuery(" select data from Modelo  where id=:param1").getSingleResult();
        //modeloDAO.byteToFile(data,"src/Assets/archivo.zip");
        return data;

    }

    @POST
    @Path("/post_modelo")
    @Produces(MediaType.APPLICATION_JSON)
    //debo tipar que el post consume este standar que se corresponde con el etiquetado @FormParam
    //en el header cuando hago el post debe poner tmb este "application/x-www-form-urlencoded"
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //@Produces(MediaType.TEXT_PLAIN)
    public void postModeloToDatabase(@FormParam("nombre")String nombre,@FormParam("tipo")String tipo,@FormParam("extension")String extension)
    {

        Modelo modelo= new Modelo();


        File file= new File("src/Assets/LEGO_Man.zip");

        //File fileUnzip = new File("src/Assets/LEGO_Man.zip");
        //File fileFolderUnzip= new File("src/Assets");

        //modeloDAO.unZipIt(fileUnzip,fileFolderUnzip);

        modelo.setData( modeloDAO.fileToByte(file));
        modelo.setNombre(nombre);
        modelo.setTipo(tipo);
        modelo.setTamaño(file.length()+"");

        modelo.setExtension(extension);

        entityManager.getTransaction().begin();

        //modelo.setNombre(nombre);
        entityManager.persist(modelo);
        entityManager.getTransaction().commit();


    }

    public  void connectHibernate()
    {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();


    }


}


