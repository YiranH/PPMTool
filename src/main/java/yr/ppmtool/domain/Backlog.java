package yr.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class Backlog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer PTSequence = 0;
    private String projectIdentifier;

    // One to one with project: each project has 1 backlog, the backlog can only belong to the project
//    lazy doesn't load the relationships unless it is explicitly requested.
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="project_id", nullable = false)
    @JsonIgnore // solve "could not write JSON: Infinite recursion" problem
//    how we are going to create a backlog
//    Design: every time there's a new project created it creates the backlog automatically for that project.
    private Project project;

    // One to many with project tasks
    // when delete something, delete all its children
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "backlog", orphanRemoval = true)
    private List<ProjectTask> projectTasks = new ArrayList<>();

    public Backlog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPTSequence() {
        return PTSequence;
    }

    public void setPTSequence(Integer PTSequence) {
        this.PTSequence = PTSequence;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }

    public void setProjectIdentifier(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Project getProject() {
        return project;
    }

    public List<ProjectTask> getProjectTasks() {
        return projectTasks;
    }

    public void setProjectTasks(List<ProjectTask> projectTasks) {
        this.projectTasks = projectTasks;
    }
}
