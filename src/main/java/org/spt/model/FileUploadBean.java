package org.spt.model;

/**
 * Created by ALFAYO on 7/6/2017.
 */
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUploadBean {

    private CommonsMultipartFile file;

    public CommonsMultipartFile getFile() {
        return file;
    }

    public void setFile(CommonsMultipartFile file) {
        this.file = file;
    }
}