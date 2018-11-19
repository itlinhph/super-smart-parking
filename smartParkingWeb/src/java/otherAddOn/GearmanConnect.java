/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otherAddOn;

import org.gearman.Gearman;
import org.gearman.GearmanClient;
import org.gearman.GearmanJobEvent;
import org.gearman.GearmanJobReturn;
import org.gearman.GearmanServer;


/**
 *
 * @author linhph
 */
public class GearmanConnect {

    private static final String GM_HOST = "localhost";
    private static final int GM_PORT = 4730;
    private static final String GM_FUCNTION = "detect_plate";
    
    public static String getPlateByGearman(String image) {
            
        String plate = "";
        
        try {
            Gearman gearman = Gearman.createGearman();
            GearmanClient client = gearman.createGearmanClient();
            GearmanServer server = gearman.createGearmanServer(GM_HOST, GM_PORT);
            client.addServer(server);

            GearmanJobReturn jobReturn = client.submitJob(GM_FUCNTION, image.getBytes());
            while (!jobReturn.isEOF()) {
                GearmanJobEvent event = jobReturn.poll();
                switch (event.getEventType()) {
                case GEARMAN_JOB_SUCCESS: 
                    plate = new String(event.getData()) ;
                    break;
                    
                case GEARMAN_SUBMIT_FAIL: 
                    System.out.println("Job submit fail!");
                case GEARMAN_JOB_FAIL: 
                    System.err.println(":Job excute fail: " + new String(event.getData()));
                default:
                }
            }
//            client.shutdown();
            
        } catch (Exception e) {
            System.out.println("Exeption when get data from Gearman: "+ e.getMessage());
        }
        
        return plate;
    }
    
    public static void main(String[] args) {
        String plate = getPlateByGearman("testIMG/plate.jpg") ;
        System.out.println(plate);
    }
}
