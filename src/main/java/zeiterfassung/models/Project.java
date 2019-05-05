package zeiterfassung.models;

import java.lang.IllegalArgumentException;
import java.time.Duration;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Project extends SubProject {
    List<Role> roleList = new ArrayList<Role>();
    List<SubProject> subProjectList = new ArrayList<SubProject>();

    /**
     * @throws IllegalArgumentException
     */
    public void addSubProject(SubProject newSubProject) {
        if (hasSubProject(newSubProject.getName())) {
            String exceptionExplanation;
            exceptionExplanation = "Project with name "
                    + getName()
                    + " already has a SubProject named "
                    + newSubProject.getName();
            throw new IllegalArgumentException(exceptionExplanation);
        }
        subProjectList.add(newSubProject);
    }

    public SubProject getSubProject(String subProjectName) {
        for (SubProject sp : subProjectList) {
            if (sp.getName().equals(subProjectName)) {
                return sp;
            }
        }
        return null;
    }

    public boolean removeSubProject(String subProjectName) {
        SubProject subProject = getSubProject(subProjectName);
        if (subProject == null) return false;
        return subProjectList.remove(subProject);
    }

    public boolean hasSubProject(String subProjectName) {
        for (SubProject sb : subProjectList) {
            if (sb.getName().equals(subProjectName)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasSubProject(SubProject subProject) {
        return subProjectList.contains(subProject);
    }

    /**
     * @throws IllegalArgumentException
     */
    public void addRole(Role newRole) {
        if (hasRole(newRole.getName())) {
            String exceptionExplanation;
            exceptionExplanation = "Project with name "
                    + getName()
                    + " already has a Role named "
                    + newRole.getName();
            throw new IllegalArgumentException(exceptionExplanation);
        }
        roleList.add(newRole);
    }

    public boolean hasRole(String roleName) {
        for (Role role : roleList) {
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public Role getRole(String roleName) {
        for (Role role : roleList) {
            if (role.getName().equals(roleName)) {
                return role;
            }
        }
        return null;
    }

    @Override
    public Duration getDuration() {
        Duration duration = super.getDuration();
        for (SubProject sp : subProjectList) {
            duration.plus(sp.getDuration());
        }
        return duration;
    }

    @Override
    public Money getCosts() {
        BigDecimal costs = super.getCosts().getAmount();

        for (SubProject sp : subProjectList) {
            costs = costs.add(sp.getCosts().getAmount());
        }
        return new Money(costs);
    }
}
