package zeiterfassung.models;

import javax.xml.bind.annotation.*;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.NONE)
public abstract class BaseModel {



    @XmlElement(name = "parent")
    @XmlIDREF
    private BaseModel parent;


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

}
