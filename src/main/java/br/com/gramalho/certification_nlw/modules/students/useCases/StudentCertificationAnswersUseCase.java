package br.com.gramalho.certification_nlw.modules.students.useCases;

import br.com.gramalho.certification_nlw.modules.questions.entities.QuestionEntity;
import br.com.gramalho.certification_nlw.modules.questions.repositories.QuestionRepository;
import br.com.gramalho.certification_nlw.modules.students.controllers.entities.AnswersCertificationsEntity;
import br.com.gramalho.certification_nlw.modules.students.controllers.entities.CertificationStudentEntity;
import br.com.gramalho.certification_nlw.modules.students.controllers.entities.StudentEntity;
import br.com.gramalho.certification_nlw.modules.students.dto.VerifyHasCertificationDTO;
import br.com.gramalho.certification_nlw.modules.students.repositories.CertificationStudentRepository;
import br.com.gramalho.certification_nlw.modules.students.dto.StudentCertificationAnswerDTO;
import br.com.gramalho.certification_nlw.modules.students.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class StudentCertificationAnswersUseCase {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CertificationStudentRepository certificationStudentRepository;

    @Autowired
    private VerifyIfHasCertificationUseCase verifyIfHasCertificationUseCase;

    public CertificationStudentEntity execute(StudentCertificationAnswerDTO dto) throws Exception {

        var hasCertification = this.verifyIfHasCertificationUseCase.execute(new VerifyHasCertificationDTO(dto.getEmail(), dto.getTechnology()));

        if (hasCertification) {
            throw new Exception("Voce ja tirou sua certificaçao!");
        }

        List <QuestionEntity> questionsEntity = questionRepository.findByTechnology(dto.getTechnology());
        List<AnswersCertificationsEntity> answersCertifications = new ArrayList<>();

        AtomicInteger correctAnswers = new AtomicInteger(0);

        dto.getQuestionAnswers()
                .stream().forEach(questionAnswer -> {
                    var question = questionsEntity.stream()
                            .filter(q -> q.getId().equals(questionAnswer.getQuestionID()))
                            .findFirst().get();

                    var findCorrectAlternative = question.getAlternatives().stream()
                            .filter(alternative -> alternative.isCorrect())
                            .findFirst().get();

                    if(findCorrectAlternative.getId().equals(questionAnswer.getAlternativeID())) {
                        questionAnswer.setCorrect(true);
                        correctAnswers.incrementAndGet();
                    }else {
                        questionAnswer.setCorrect(false);
                    }

                    var answerrsCertificationsEntity = AnswersCertificationsEntity.builder()
                            .answerID(questionAnswer.getAlternativeID())
                            .questionID(questionAnswer.getQuestionID())
                                    .isCorrect(questionAnswer.isCorrect())
                                            .build();

                    answersCertifications.add(answerrsCertificationsEntity);
                });

        var student = studentRepository.findByEmail(dto.getEmail());
        UUID studentID;
        if(student.isEmpty()) {
            var studentCreated = StudentEntity.builder().email(dto.getEmail()).build();
            studentCreated = studentRepository.save(studentCreated);
            studentID = studentCreated.getId();
        }else {
            studentID = student.get().getId();
        }

        CertificationStudentEntity certificationStudentEntity =
                CertificationStudentEntity.builder()
                        .technology(dto.getTechnology())
                        .studentID(studentID)
                        .grade(correctAnswers.get())
                        .build();

        var certificationStudentCreated = certificationStudentRepository.save(certificationStudentEntity);

        answersCertifications.stream().forEach(answerCertification -> {
            answerCertification.setCertificationID(certificationStudentEntity.getId());
            answerCertification.setCertificationStudentEntity(certificationStudentEntity);
        });

        certificationStudentEntity.setAnswersCertificationsEntities(answersCertifications);

        certificationStudentRepository.save(certificationStudentEntity);

        return certificationStudentCreated;
    }
}