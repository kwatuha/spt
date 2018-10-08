package org.spt.model;

/**
 * Created by ALFAYO on 7/22/2017.
 */
import org.springframework.batch.item.ItemProcessor;
public class CustomItemProcessor  implements ItemProcessor<Report, Report>  {

    @Override
    public Report process(Report item) throws Exception {

        System.out.println("Processing..." + item);
        return item;
    }


}
