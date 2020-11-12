package yr.ppmtool.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import yr.ppmtool.domain.Project;

import java.util.*;


@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
//    @Override
//    public Iterable<Project> findAllById(Iterable<Long> iterable) {
//        return null;
//    }
    Project findByProjectIdentifier(String projectId);

    @Override
    Iterable<Project> findAll();

}
