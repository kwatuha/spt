package org.spt.properties;

/**
 * Created by ALFAYO on 7/23/2017.
 */
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Batch {

    public static void run() {

        String[] springConfig = {"spring/batch/jobs/job-contact-jobs.xml"};

        ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("testJob");

        try {

            JobParameters param = new JobParametersBuilder().addString("age", "20").toJobParameters();

            JobExecution execution = jobLauncher.run(job, param);
            System.out.println("Exit Status : " + execution.getStatus());
            System.out.println("Exit Status : " + execution.getAllFailureExceptions());

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Done");

    }
}
