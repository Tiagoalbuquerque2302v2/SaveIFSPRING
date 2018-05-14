/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Anexo;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Tiago
 */

@Repository
public interface AnexoDAO extends PagingAndSortingRepository<Anexo, Integer> {
    
}
