package br.com.gramalho.certification_nlw.modules.certifications.useCases;

import br.com.gramalho.certification_nlw.modules.students.controllers.entities.CertificationStudentEntity;
import br.com.gramalho.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Top10RankingUseCase {

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    public List<CertificationStudentEntity> execute() {
        var result = this.certificationStudentRepository.findTop10ByOrderByGradeDesc();
        return result;
    }

}
