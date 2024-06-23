package br.com.project.api.controle;

import org.springframework.web.bind.annotation.RestController;

import br.com.project.api.modelo.Cliente;
import br.com.project.api.modelo.Pessoa;
import br.com.project.api.repositorio.Repositorio;
import br.com.project.api.servico.Servico;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class Controle {

    @Autowired
    private Repositorio acao;

    @Autowired
    private Servico servico;

    @GetMapping("")
    public String mensagem(){
        return "Hello World";
    }

    @GetMapping("/boasvindas/{nome}")
    public String boasVindas(@PathVariable String nome){
        return "Seja bem vindo "+nome+"?";
    }

    @PostMapping("/api")
    public ResponseEntity<?> cadastrar(@RequestBody Pessoa p){
        return servico.cadastrar(p);
    }

    @GetMapping("/api")
    public ResponseEntity<?> selecionar(){
        return servico.selecionar();
    }
    @GetMapping("/api/{codigo}")
    public ResponseEntity<?> selecionarPeloCodigo(@PathVariable int codigo){
        return servico.selecionarPeloCodigo(codigo);
    }
    @PutMapping("/api")
    public ResponseEntity<?> editar(@RequestBody Pessoa pessoa){
        return servico.editar(pessoa);
    }
    @DeleteMapping("/api/{codigo}")
    public ResponseEntity<?> remover(@PathVariable int codigo){
        return servico.remover(codigo);
    }

    @GetMapping("/api/contador")
    public long contador(){
        return acao.count();
    }

    @GetMapping("/api/idadeMaiorIgual")
    public List<Pessoa> idadeMaiorIgual(){
        return acao.idadeMaiorIgual(40);
    }

    @GetMapping("/api/somaIdades")
    public int somaIdades(){
        return acao.somaIdades();
    }

    @GetMapping("/api/ordenarNomes")
    public List<Pessoa> ordenarNomes(){
        return acao.findByOrderByNomeDesc();
    }

    @GetMapping("/api/ordenarNomes2")
    public List<Pessoa> ordenarNomes2(){
        return acao.findByNomeOrderByIdadeDesc("Paulo");
    }

    @GetMapping("/api/nomeContem")
    public List<Pessoa> nomeContem(){
    return acao.findByNomeContaining("E");
    }

    @GetMapping("/api/iniciaCom")
    public List<Pessoa> iniciaCom(){
        return acao.findByNomeStartsWith("a");
    }

    @GetMapping("/api/terminaCom")
    public List<Pessoa> teminaCom(){
        return acao.findByNomeEndsWith("a");
    }

    @GetMapping("/status")
    public ResponseEntity<?> status(){
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/cliente")
    public void cliente(@Valid @RequestBody Cliente cliente){
        
    }


    @GetMapping("/populaBanco")
    public String populaDados(){
        servico.populaDados();
        return "Dados cargados com sucesso";
    }
        
}
