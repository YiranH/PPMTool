package yr.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import yr.ppmtool.domain.Backlog;
import yr.ppmtool.domain.Project;
import yr.ppmtool.domain.ProjectTask;
import yr.ppmtool.exceptions.ProjectNotFoundException;
import yr.ppmtool.repositories.BacklogRepository;
import yr.ppmtool.repositories.ProjectRepository;
import yr.ppmtool.repositories.ProjectTaskRepository;

import java.util.List;

@Service
public class ProjectTaskService {
    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
        // Exception: project not found; should have error message - ProjectNotFound: "Project not found"

        // all project tasks to be added to a specific project, project != null,
        // backlog exists
        // reuse code to make sure user can only access their own projects
        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
        // set the backlog to project task
        projectTask.setBacklog(backlog);
        // we want our project sequence to be like: projectIdentifier-taskIdentifier
        Integer BacklogSequence = backlog.getPTSequence();
        // if we delete id-2, the next will still be id-3
        // update the backlog sequence
        BacklogSequence++; // want to start from 1
        backlog.setPTSequence(BacklogSequence);
        // add sequence to project task
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        // set initial priority when priority is null: low medium high
        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }
        // initial status when status is null
        if (projectTask.getStatus() == null || projectTask.getStatus() == "") {
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask> findBacklogById(String id, String username) {
        projectService.findProjectByIdentifier(id, username);

        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }

    public ProjectTask findProjectTaskByProjectSequence(String backlog_id, String projectTask_id, String username) {
        //make sure we are searching on the right backlog
        projectService.findProjectByIdentifier(backlog_id, username);
        // make sure our task exists
        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(projectTask_id);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Task with ID '" + projectTask_id + "' does not exist");
        }
        // make sure our task belongs to the project
        if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
            throw new ProjectNotFoundException("Project Task '" + projectTask_id + "' does not exist " +
                    "in Project: '" + backlog_id + "'");
        }

        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String projectTask_id, String username) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, projectTask_id, username);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }
    // Update project task
    // find existing project task
    // replace it with updated task
    // save update


    public void deleteProjectTaskByProjectSequence(String backlog_id, String projectTask_id, String username) {
        ProjectTask projectTask = findProjectTaskByProjectSequence(backlog_id, projectTask_id, username);
        projectTaskRepository.delete(projectTask);
    }
}
