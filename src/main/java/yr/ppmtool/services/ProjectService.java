package yr.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yr.ppmtool.domain.Backlog;
import yr.ppmtool.domain.Project;
import yr.ppmtool.domain.User;
import yr.ppmtool.exceptions.ProjectIdException;
import yr.ppmtool.exceptions.ProjectNotFoundException;
import yr.ppmtool.repositories.BacklogRepository;
import yr.ppmtool.repositories.ProjectRepository;
import yr.ppmtool.repositories.UserRepository;

import java.security.Principal;
import java.util.List;

// talks to the repository, rather than having all the logic on the controller layer
@Service
public class ProjectService {
//    inject the project repository so that we can have access that allow us to interfere with backend
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String username) {
        if (project.getId() != null) {
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
            if (existingProject != null && !existingProject.getProjectLeader().equals(username)) {
                throw new ProjectNotFoundException("Project not found in your account");
            } else if (existingProject == null) { // when updating you cannot change the id
                throw new ProjectNotFoundException("Project with ID '" + project.getProjectIdentifier() +
                        "' cannot be updated because it does not exist");
            }
        }
//        Logic
        try {
            User user = userRepository.findByUsername(username);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            String projectId = project.getProjectIdentifier().toUpperCase();
            project.setProjectIdentifier(projectId);
            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(projectId);
            }
            if (project.getId() != null) {
                project.setBacklog(backlogRepository.findByProjectIdentifier(projectId));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project ID '" + project.getProjectIdentifier().toUpperCase() + "' already exists");
        }

    }

    public Project findProjectByIdentifier(String projectId, String username) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project ID '" + projectId + "' does not exist");
        }

        if (!project.getProjectLeader().equals(username)) {
            throw new ProjectNotFoundException("Project not found in your account");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(String username) {
        return projectRepository.findAllByProjectLeader(username);
    }

    public void deleteProjectByIdentifier(String projectId, String username) {
        projectRepository.delete(findProjectByIdentifier(projectId, username));
    }

}
