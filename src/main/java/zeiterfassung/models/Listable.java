package zeiterfassung.models;

import java.util.List;

/**
 * Callback for list iteration
 * @param <T> Type of the list entries
 */
public interface Listable<T> {
    void getList(List<T> list);
}
