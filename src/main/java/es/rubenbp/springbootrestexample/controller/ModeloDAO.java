package es.rubenbp.springbootrestexample.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ModeloDAO {

    public ModeloDAO(){}

    public void unZipIt(File zipFile, File outputFolder){

        byte[] buffer = new byte[1024];

        try{


            if(!outputFolder.exists()){
                outputFolder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while(ze!=null){

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                //System.out.println("file unzip : "+ newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            //System.out.println("Done");

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    public byte[] fileToByte(File file)
    {

        byte[] fileByte= new byte[(int)file.length()];

        FileInputStream fileInputStream= null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileByte);
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileByte;
    }

    public File byteToFile(byte[] fileByte, String path)
    {

        File file= new File(path);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileByte);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }


}
