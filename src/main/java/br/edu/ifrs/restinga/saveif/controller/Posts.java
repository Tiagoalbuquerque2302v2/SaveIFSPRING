/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifrs.restinga.saveif.controller;

import br.edu.ifrs.restinga.saveif.aut.UsuarioAut;
import br.edu.ifrs.restinga.saveif.dao.AnexoDAO;
import br.edu.ifrs.restinga.saveif.dao.GrupoDAO;
import br.edu.ifrs.restinga.saveif.dao.PostDAO;
import br.edu.ifrs.restinga.saveif.dao.TopicoDAO;
import br.edu.ifrs.restinga.saveif.modelo.Anexo;
import br.edu.ifrs.restinga.saveif.modelo.Grupo;
import br.edu.ifrs.restinga.saveif.modelo.Post;
import br.edu.ifrs.restinga.saveif.modelo.Topico;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/api")
public class Posts {

    @Autowired
    PostDAO postDAO;
    
    @Autowired
    TopicoDAO topicoDAO;
    
    @Autowired
    GrupoDAO grupoDAO;
    
    @Autowired
    AnexoDAO anexoDAO;

    @RequestMapping(path = "/grupos/{id}/geral", method = RequestMethod.GET)
    public Iterable<Post> listarGeral(@RequestParam(required = false, defaultValue = "0") int pagina, @PathVariable int id) {
        PageRequest pageRequest = new PageRequest(pagina, 20);
        
        return postDAO.findGeral(id);                           // TESTE SQL nativo
//        return postDAO.findGeral(id, pageRequest);            // TESTE Spring Query     
    }
    
    @RequestMapping(path = "/grupos/{id}/posts/{idt}", method = RequestMethod.GET)
    public Iterable<Post> listarPorTopico(@RequestParam(required = false, defaultValue = "0") int pagina, @PathVariable int id, @PathVariable int idt) {
        PageRequest pageRequest = new PageRequest(pagina, 20);
      
        return postDAO.findPorTopico(id, idt);                  // TESTE SQL nativo
//        return postDAO.findPorTopico(id, idt, pageRequest);   // TESTE Spring Query 
        
    }
    
    @RequestMapping(path = "/posts", method = RequestMethod.GET)
    public Iterable<Post> listar(@RequestParam(required = false, defaultValue = "0") int pagina) {
        PageRequest pageRequest = new PageRequest(pagina, 20);
        return postDAO.findAll(pageRequest);
    }
    
    @RequestMapping(path="/grupos/{idGrupo}/topicos/{idTopico}/posts", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Post inserir(@RequestBody Post post, @AuthenticationPrincipal UsuarioAut usuarioAut, @PathVariable int idGrupo, @PathVariable int idTopico) throws IOException
    {
        post.setId(0);
        post.setAutorPost(usuarioAut.getUsuario());
        
        Post postSalvo = postDAO.save(post);
        
        Grupo grupoAtual = grupoDAO.findById(idGrupo);
        
        Topico topicoAtual = topicoDAO.findById(idTopico);
        
        List<Topico> topicos = new ArrayList<>();
        topicos = grupoAtual.getTopicos();
        
        List<Post> posts = new ArrayList<>();
        posts = topicoAtual.getPosts();
        
        posts.add (postSalvo);
        
        topicoAtual.setPosts(posts);
        
        for (int i=0; i<=topicos.size(); i++){
            if(topicos.get(i).getId()==topicoAtual.getId()){
                topicos.set(i, topicoAtual);
                i=topicos.size();
            }
        }
        grupoAtual.setTopicos(topicos);
        
        topicoDAO.save(topicos.get(0));
        grupoDAO.save(grupoAtual);
                
        return postSalvo;
    }
    
    @RequestMapping(path = "/posts/{idPost}/anexo", method = RequestMethod.POST)
    public Anexo inserirArquivo(@PathVariable int idPost, @RequestParam("arquivo") MultipartFile arquivo) throws IOException {
            Anexo anexo = new Anexo();

            anexo.setId(0);
            anexo.setTipoAnexo(arquivo.getContentType());
            anexo.setNomeAnexo(arquivo.getOriginalFilename());
            anexo.setDocumentoAnexo(arquivo.getBytes());               
            
            Anexo anexoSalvo = anexoDAO.save(anexo);

            Post postAtual = postDAO.findById(idPost);
            
            postAtual.setAnexoPost(anexoSalvo);
            
            postDAO.save(postAtual);
            
            return anexoSalvo;    
    }

}
