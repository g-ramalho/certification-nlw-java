package br.com.gramalho.certification_nlw.modules.students.repositories;

import br.com.gramalho.certification_nlw.modules.students.controllers.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    public Optional<StudentEntity> findByEmail(String email);
}
