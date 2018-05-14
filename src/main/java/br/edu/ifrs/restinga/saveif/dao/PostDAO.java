/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.dao;

import br.edu.ifrs.restinga.saveif.modelo.Post;
import br.edu.ifrs.restinga.saveif.modelo.Topico;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDAO extends PagingAndSortingRepository<Post, Integer>{
    @Query(nativeQuery = true,
    value = "SELECT * FROM post p \n" +
                    "INNER JOIN topico_posts tp ON ( p.id = tp.posts_id) \n" +
                    "INNER JOIN topico t ON (tp.topico_id = t.id)\n" +
                    "INNER JOIN grupo_topicos gp ON ( t.id = gp.topicos_id)\n" +
                    "INNER JOIN grupo g ON (gp.grupo_id = g.id)\n" +
                    "WHERE \n" +
                    "g.id = :idgrupo AND\n" +
                    "t.nome = \"Geral\" "
            + "ORDER BY p.id DESC;"
    )
    public List<Post> findGeral(@Param("idgrupo") int id);
       
    
    @Query(nativeQuery = true,
    value = "SELECT * FROM post p \n" +
                    "INNER JOIN topico_posts tp ON ( p.id = tp.posts_id) \n" +
                    "INNER JOIN topico t ON (tp.topico_id = t.id)\n" +
                    "INNER JOIN grupo_topicos gp ON ( t.id = gp.topicos_id)\n" +
                    "INNER JOIN grupo g ON (gp.grupo_id = g.id)\n" +
                    "WHERE \n" +
                    "g.id = :idgrupo AND\n" +
                    "t.id = :idtopico "
            + "ORDER BY p.id DESC;"
    )
    public List<Post> findPorTopico(@Param("idgrupo") int idg, @Param("idtopico") int idt);
    
    @Query( "SELECT post FROM  Grupo grupo JOIN grupo.topicos topico JOIN topico.posts post "
            + "WHERE grupo.id = :idgrupo AND topico.nome='Geral' ORDER BY post.id DESC")
    public Page<Post> findGeral(@Param("idgrupo") int id, Pageable pageable);
    
    @Query( "SELECT post FROM  Grupo grupo JOIN grupo.topicos topico JOIN topico.posts post "
            + "WHERE grupo.id = :idgrupo AND topico.id = :idtopico ORDER BY post.id DESC")
    public List<Post> findPorTopico(@Param("idgrupo") int idg, @Param("idtopico") int idt, Pageable pageable);
    
    Post findById(int id);
}
