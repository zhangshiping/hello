package com.company.project.authz.domain;

import com.company.project.infrastructure.tools.DateUtils;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by eayon on 2017-07-14.
 */
public abstract class AbstractDomain implements Serializable {

    private static final long serialVersionUID = 7787898374385773471L;

    protected String guid;

    protected Date createTime = DateUtils.now();

    public AbstractDomain(){

    }

    public String guid(){
        return guid;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractDomain> T guid(String guid){
        this.guid = guid;
        return (T) this;
    }


    public Date createTime() {
        return createTime;
    }

    @SuppressWarnings("unchecked")
    public <T extends AbstractDomain> T createTime(Date createTime) {
        this.createTime = createTime;
        return (T) this;
    }


    @Override
    public String toString() {
        return "{" +
                "createTime=" + createTime +
                ", guid='" + guid + '\'' +
                '}';
    }
}
