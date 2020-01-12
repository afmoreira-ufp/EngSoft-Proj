package edu.ufp.esof.projecto.services;

import edu.ufp.esof.projecto.models.Aluno;
import edu.ufp.esof.projecto.models.Componente;
import edu.ufp.esof.projecto.models.MomentoRealizado;
import edu.ufp.esof.projecto.repositories.AlunoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class AlunoService {

    private AlunoRepo alunoRepo;
    private MomentoRealizadoService momentoRealizadoService;
    // Falta o Filtro do serviço e no constructor

    @Autowired
    public AlunoService(AlunoRepo alunoRepo, MomentoRealizadoService momentoRealizadoService) {
        this.momentoRealizadoService = momentoRealizadoService;
        this.alunoRepo = alunoRepo;
    }

    public Set<Aluno> findAll() {
        Set<Aluno> alunos=new HashSet<>();
        for(Aluno a:this.alunoRepo.findAll()){
            alunos.add(a);
        }
        return alunos;
    }

    public Optional<Aluno> findByNumber(String id) {
        Optional<Aluno> optionalAluno = Optional.empty();
        for(Aluno a:this.alunoRepo.findAll()){
            if (a.getCode().compareTo(id) == 0){
                optionalAluno = Optional.of(a);
                break;
            }
        }
        return optionalAluno;
    }

    public Optional<Aluno> createAluno(Aluno aluno) {
        Optional<Aluno> optionalAluno=this.alunoRepo.findByCode(aluno.getCode());
        if(optionalAluno.isPresent()){
            return Optional.empty();
        }
        Aluno createdAluno=this.alunoRepo.save(aluno);
        return Optional.of(createdAluno);
    }

    public Optional<Aluno> updateAluno(String code, Aluno aluno){
        Optional<Aluno> optionalAluno=this.alunoRepo.findByCode(code);
        if(optionalAluno.isPresent()){
            alunoRepo.save(aluno);
            return optionalAluno;
        }
        return Optional.empty();
    }

    public Boolean deleteAluno(String code){
        Optional<Aluno> optionalAluno=this.alunoRepo.findByCode(code);
        if(optionalAluno.isPresent()){
            /*for (MomentoRealizado mr:optionalAluno.get().getMomentos()) {
                momentoRealizadoService.deleteMomentoRealizado(mr.getId());
            }
            alunoRepo.delete(optionalAluno.get());*/
            delete(optionalAluno.get());
            return true;
        }
        return false;
    }

    public void deleteAll(){
        for (Aluno a:this.alunoRepo.findAll()) {
            delete(a);
        }
    }

    public void delete(Aluno a){
        for (Componente c : a.getComponentes()) {
            c.getAlunos().remove(a);
            a.getComponentes().remove(c);
        }
        for (MomentoRealizado mr : a.getMomentos()) {
            momentoRealizadoService.delete(mr);
        }
        alunoRepo.delete(a);
    }
}
