package zeiterfassung.models;

import java.lang.IllegalArgumentException;
import java.time.Duration;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

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
        if (subProjectList.add(newSubProject)) {
            setChanged();
            notifyObservers();
        }
    }

    public boolean removeSubProject(SubProject subProject) {
        if (subProjectList.remove(subProject)) {
            setChanged();
            notifyObservers();
            return true;
        }
        return false;
    }


    public boolean hasSubProject(SubProject subProject) {
        return subProjectList.contains(subProject);
    }

    public void addRole(Role newRole) {
        if (roleList.add(newRole)){
            setChanged();
            notifyObservers();
        }
    }

    public boolean deleteRole(Role role) {

        if (roleList.remove(role)){
            setChanged();
            notifyObservers();
            return true;
        }
        return false;
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
