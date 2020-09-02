package ac.ds.monitortest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class RsuMonitor {
    public static void main(String[] args) throws Exception {

        System.out.println("rsu monitoring started");
        System.out.printf("\n"); 

        TimerTask ctTimer = new TimerTask() {
            public void run() {
                try {
                    String[] cmd = {"/bin/sh", "-c", "docker inspect $(docker ps -q) | egrep -w 'CAR_NUM|CpuShares|Memory'" };
                    Process p_monitor = Runtime.getRuntime().exec(cmd);
                    p_monitor.waitFor();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(p_monitor.getInputStream()));
                    String line = null;
                    
                    while((line=bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                    System.out.printf("\n"); 
                                        
                    bufferedReader.close();

                    p_monitor.destroy();
                    
                } catch (Exception e) {
                    e.printStackTrace();                    
                }
        
            }
        };

        Timer timer = new Timer();
        timer.schedule(ctTimer, 0, 1000); // 1초마다 차량의 시간 체크.   
         
    }
}
