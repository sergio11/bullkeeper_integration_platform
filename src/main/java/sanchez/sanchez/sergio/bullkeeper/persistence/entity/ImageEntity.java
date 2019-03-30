/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanchez.sanchez.sergio.bullkeeper.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Entity for File System strategy
 * @author sergio
 */
@Document(collection = ImageEntity.COLLECTION_NAME)
public class ImageEntity {
   
    public final static String COLLECTION_NAME = "images_metadata";
    
    /**
     * Id
     */
    @Id
    private ObjectId id;
    
    /**
     * Name
     */
    @Field("name")
    private String name;
    
    /**
     * Ext
     */
    @Field("ext")
    private String ext;
    
    public ImageEntity(){}

    /**
     * 
     * @param name
     * @param ext
     */
    @PersistenceConstructor
    public ImageEntity(String name, String ext) {
        this.name = name;
        this.ext = ext;
    }
    
    public String getIdentity() {
        return id.toString();
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
    
    public String getFileName() {
        return String.format("%s.%s", name, ext);
    }

    
}
