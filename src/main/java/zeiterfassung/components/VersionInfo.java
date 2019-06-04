package zeiterfassung.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * Provides static information about our project inside final static fields
 * and loads dynamic information from the resource path
 * The dynamic information is generated with gradle at the end of the assemble task
 */
public class VersionInfo {
    static final String VERSION_FILE_NAME = "/VERSION.txt";
    public static final String ABOUT = "Projekt Zeiterfassung im Modul Programmieren in Java";
    public static final String[] TEAM = {"Christiane", "Philipp", "Anton", "Jonas", "Nils"};
    private HashMap<String, Consumer<String>> parameterMap = new HashMap<>();
    private String commitHash;
    private String branchName;

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    private String creationTime;

    /**
     * loads information from the resource path
     */
    public  VersionInfo() {
        parameterMap.put("commitHash", (String s) -> commitHash = s);
        parameterMap.put("branchName", (String s) -> branchName = s);
        parameterMap.put("active", (String s) -> active = Boolean.valueOf(s));
        parameterMap.put("time", (String s) -> creationTime = s);

        commitHash = "undefined";
        branchName = "undefined";
        creationTime = "undefined";
        try {
            loadFromResourceFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * is Developing Information activated
     * @return
     */
    public boolean isActive() {
        return active;
    }
    private boolean active;
    /**
     * the git commit hash of the current commit
     * or "undefined" if not set
     * @return
     */
    public String getCommitHash() {
        return commitHash;
    }
    /**
     * the git branch name of the current commit
     * or "undefined" if not set
     * @return
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * formatted information about the program version (hash, branch name) if provided
     * or an empty String <""> if not {@link this.isActive()}
     * @return
     */
    public String developInfo() {
        String info = "";
        if(isActive()) {
            if(! getCommitHash().equals("undefined"))
                info += "hash: " + getCommitHash() + "\n";
            if(! getBranchName().equals("undefined"))
                info += "branch: " + getBranchName() + "\n";
            if(! getCreationTime().equals("undefined"))
                info += "created: " + getCreationTime() + "\n";
        }
        return info;
    }

    /**
     * comprehensive formatted information about the project
     * (about, developing team member, {@link this.developInfo()})
     * @return
     */
    public String info() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(developInfo() + "\n");
        sBuilder.append(VersionInfo.ABOUT + "\n");
        sBuilder.append("Team 1:");
        for(String member: TEAM) {
            sBuilder.append("\n    " + member);
        }
        return sBuilder.toString();
    }

    void loadFromResourceFile() throws IOException {
        URI uri = null;
        File file = null;
        BufferedReader reader = null;
        try {
            uri = getClass().getResource(VERSION_FILE_NAME).toURI();
            file = new File(uri);
            reader = new BufferedReader(new FileReader(file));
        } catch(Exception e) {
            e.printStackTrace();
            if(reader != null) reader.close();
            return;
        }
        String line = null;
        try {
            do {
                line = reader.readLine();
                if(line == null) break;
                String[] token = line.split("=");
                String t1, t2;
                if(token.length >= 2) {
                    t1 = token[0].trim();
                    t2 = token[1].trim();
                    Consumer<String> action = parameterMap.get(t1);
                    if(action != null) {
                        action.accept(t2);
                    }
                }
            } while(true);

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }


}
