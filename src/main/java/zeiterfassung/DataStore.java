package zeiterfassung;

import zeiterfassung.models.TimeRegistrationRoot;

public class DataStore {
    private TimeRegistrationRoot root;

    public DataStore() {
        // TODO: autoload?
    }

    public void load() {
        // TODO: load data from xml
        // root = ...
    }

    public void unload() {
        // TODO: save data to xml
        root = null;
    }

    public TimeRegistrationRoot getRoot() {
        return root;
    }
}
