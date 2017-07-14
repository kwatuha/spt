package org.spt.model;

/**
 * Created by ALFAYO on 7/6/2017.
 */
public class ExtJSFormResult {

    private boolean success;

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String toString(){
        return "{success:"+this.success+"}";
    }
}