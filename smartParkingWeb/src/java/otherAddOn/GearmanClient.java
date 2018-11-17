/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otherAddOn;

import org.gearman.Gearman;
import org.gearman.GearmanJobEvent;
import org.gearman.GearmanJobReturn;

/**
 *
 * @author linhph
 */
public class GearmanClient {
    
    public static boolean testGearmanClient() throws InterruptedException {
        Gearman gearman = Gearman.createGearman();
        final org.gearman.GearmanClient client = gearman.createGearmanClient();
        client.addServer(gearman.createGearmanServer("localhost",4730));
        byte data[] = "testIMG/plate.jpg".getBytes() ;
        GearmanJobReturn jobReturn = client.submitBackgroundJob("detect_plate",data);
        System.out.println(jobReturn.poll().getData());
        GearmanJobEvent event1 = jobReturn.poll();
        System.out.println(event1.getData());
        System.out.println(event1.getEventType());
        System.out.println(new String(event1.getData()));
        while (!jobReturn.isEOF()) {

            // Poll the next job event (blocking operation)
            GearmanJobEvent event = jobReturn.poll();
            System.out.println(event.getData());
            System.out.println(event.getEventType());
            switch (event.getEventType()) {

            // success
            case GEARMAN_JOB_SUCCESS: // Job completed successfully
                    // print the result
                    System.out.println(new String(event.getData()));
                    break;

            // failure
            case GEARMAN_SUBMIT_FAIL: // The job submit operation failed
            case GEARMAN_JOB_FAIL: // The job's execution failed
                    System.err.println(event.getEventType() + ": "
                                    + new String(event.getData()));
            default:
                System.out.println("Nothing!!!");
            }
            gearman.shutdown();

    }
        return false;
    }
    
    
    public static void main(String[] args) throws InterruptedException {
        testGearmanClient();
    }
    
}
