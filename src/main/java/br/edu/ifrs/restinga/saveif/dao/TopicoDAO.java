
package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Topico;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoDAO extends PagingAndSortingRepository<Topico, Integer>{
    @Query(nativeQuery = true, value = "SELECT * FROM topico t\n"+
            "INNER JOIN grupo_topicos gt ON (t.id = gt.topicos_id)\n"+
            "INNER JOIN grupo g ON (gt.grupo_id = g.id)\n"+
            "WHERE g.id = :idgrupo"
    )   
    public List<Topico> findTopico(@Param("idgrupo")int id);        // SQL nativo
        
        
    @Query( "SELECT topico FROM  Grupo grupo JOIN grupo.topicos topico WHERE grupo.id = :idgrupo ORDER BY topico.nome")  // Spring Query EM ORDEM ALFABETICA  
    public Page<Topico> findTopico(@Param("idgrupo")int id,  Pageable pageable);
    
    Topico findById(int id);
    
}
