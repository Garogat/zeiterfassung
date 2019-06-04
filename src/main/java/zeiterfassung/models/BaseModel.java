package zeiterfassung.models;

import javax.xml.bind.annotation.*;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class BaseModel {

    @XmlAttribute(name = "id")
    @XmlID
    private String id;

    @XmlElement(name = "parent")
    @XmlIDREF
    private BaseModel parent;

    public BaseModel() {
        if (id == null || id.isEmpty()) {
            id = UUID.randomUUID().toString();
        }
    }

    public BaseModel getParent() {
        return parent;
    }

    public void setParent(BaseModel parent) {
        this.parent = parent;
    }

    public BaseModel getParentByType(Class type) {
        BaseModel tmpModel = getParent();
        while (tmpModel != null && !(type.isInstance(tmpModel))) {
            tmpModel = tmpModel.getParent();
        }
        return tmpModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
