package org.spt.service;
import org.springframework.batch.item.ItemProcessor;
import org.spt.model.Contact;
import org.spt.model.Report;

/**
 * Created by ALFAYO on 7/21/2017.
 */
public class EmailQueueProcessor implements ItemProcessor<Contact, Contact>  {

    @Override
    public Contact process(Contact item) throws Exception {
        return item;
    }


}