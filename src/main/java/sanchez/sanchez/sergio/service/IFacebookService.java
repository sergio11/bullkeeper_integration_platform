/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sanchez.sanchez.sergio.service;

import java.util.List;
import sanchez.sanchez.sergio.persistence.entity.CommentEntity;

/**
 *
 * @author sergio
 */
public interface IFacebookService {
    
    List<CommentEntity> getComments(String accessToken);
    
}
