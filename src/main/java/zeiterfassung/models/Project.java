package zeiterfassung.models;

import java.lang.IllegalArgumentException;
import java.time.Duration;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Project extends SubProject {
    List<Role> roleList = new ArrayList<>();
    List<SubProject> subProjectList = new ArrayList<>();


    public void getRoles(Listable<Role> roles){
        roles.getList(roleList);
    }

    public void getSubProjects(Listable<SubProject> subProjects){
        subProjects.getList(subProjectList);
    }

    /**
     * @throws IllegalArgumentException
     */
    public void addSubProject(SubProject newSubProject) {
        subProjectList.add(newSubProject);
    }

    public boolean removeSubProject(SubProject subProject) {
        return subProjectList.remove(subProject);
    }


    public boolean hasSubProject(SubProject subProject) {
        return subProjectList.contains(subProject);
    }

    public void addRole(Role newRole) {
        roleList.add(newRole);
    }

    public boolean hasRole(Role role) {

        return roleList.contains(role);
    }



    @Override
    public Duration getDuration(LocalDateTime start, LocalDateTime stop) {
        Duration duration = super.getDuration(start, stop);
        for (SubProject sp : subProjectList) {
            duration = duration.plus(sp.getDuration(start, stop));
        }
        return duration;
    }

    @Override
    public double getCosts(LocalDateTime start, LocalDateTime stop) {
        double costs = super.getCosts(start, stop);
        for (SubProject sp : subProjectList) {
            costs += sp.getCosts(start, stop);
        }
        return costs;
    }
}
