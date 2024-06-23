package br.com.project.api.servico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.project.api.modelo.Mensagem;
import br.com.project.api.modelo.Pessoa;
import br.com.project.api.repositorio.Repositorio;

@Service
public class Servico {

    @Autowired
    private Mensagem mensagem;

    @Autowired
    private Repositorio acao;

    // Método paa cadastrar pessoas
    public ResponseEntity<?> cadastrar(Pessoa pessoa){
        if(pessoa.getNome().equals("")){
            mensagem.setMensagem("O nome precisa ser preenchido!");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else if(pessoa.getIdade() < 0){
            mensagem.setMensagem("Informe uma idade válida.");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(acao.save(pessoa), HttpStatus.CREATED);

        }
    }

    //Método para selecionar pessoas
    public ResponseEntity<?> selecionar(){
        return new ResponseEntity<>(acao.findAll(), HttpStatus.OK);

    }

    //Metodo para selecionar pessoas através do códgo
    public ResponseEntity<?> selecionarPeloCodigo(int codigo){
        if(acao.countByCodigo(codigo) == 0){
            mensagem.setMensagem("Usuário não encontrado");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(acao.findByCodigo(codigo), HttpStatus.OK);
        }
    }

    // Metodo para editar os dados

    public ResponseEntity<?> editar(Pessoa pessoa){

        if(acao.countByCodigo(pessoa.getCodigo()) == 0 ){
            mensagem.setMensagem("O código informado não existe.");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND); 
        }else if(pessoa.getNome().equals("")){
            mensagem.setMensagem("É necessáio informar um nome");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else if(pessoa.getIdade() < 0){
            mensagem.setMensagem("Informe uma idade válida");
            return new ResponseEntity<>(mensagem, HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(acao.save(pessoa), HttpStatus.OK);
        }

    }

    //Metodo para remover registros

    public ResponseEntity<?> remover(int codigo){
        if(acao.countByCodigo(codigo) == 0){
            mensagem.setMensagem("O codigo informado não existe.");
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }else{
            Pessoa pessoa = acao.findByCodigo(codigo);
            acao.delete(pessoa);
            mensagem.setMensagem("Pessoa removida com sucesso.");
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        }

    }



    public void populaDados(){
        Random gerador = new Random();
        ArrayList<String> nomes = new ArrayList<String>(Arrays.asList("Paulo", "José", "Antonio", "Pedro", "Damião", "Enzo", "Gabriel", "Germano", "Fábio", "Jonas", "Raimundo", "Francisco"));
        ArrayList<Integer> idades = new ArrayList<Integer>(Arrays.asList(10, 18, 22, 55, 28, 20, 30, 50, 55, 70, 80, 85));
        for (int i = 0; i < nomes.size(); i++) {
            Pessoa p = new Pessoa();
            p.setNome(nomes.get(gerador.nextInt(12)));
            p.setIdade(idades.get(gerador.nextInt(12)));
            acao.save(p);
        }
    }


}
