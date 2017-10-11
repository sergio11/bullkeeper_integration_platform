/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.bisite.usal.bulltect.persistence.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author sergio
 */
@Document(collection = ImageEntity.COLLECTION_NAME)
public class ImageEntity {
   
    public final static String COLLECTION_NAME = "images_metadata";
    
    @Id
    private ObjectId id;
    
    @Field("name")
    private String name;
    
    @Field("ext")
    private String ext;
    
    public ImageEntity(){}

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
