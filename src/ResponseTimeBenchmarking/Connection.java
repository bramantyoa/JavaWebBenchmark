package ResponseTimeBenchmarking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import javax.swing.JTextArea;

public class Connection extends Thread {
    
    private final String url;
    private final int id;
    private final int run;
    private ResultArray result;
    private GUI gui;
    
    public Connection(int id, int run, ResultArray result, String url, GUI gui) {
        this.url = url;
        this.id = id;
        this.run = run;
        this.result = result;
        this.gui = gui;
    }
    
    @Override
    public void run() {
        openConnection(this.url);
    }
    
    public void openConnection(String url) {
        String inputLine;
        try {
            
            URL urlObj = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();
            httpCon.setRequestMethod("GET");
            
            long lStartTime = Instant.now().toEpochMilli();
            httpCon.connect();
            
            final int responseCode = httpCon.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(httpCon.getInputStream()));
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                    
            }
            in.close();
            long lEndTime = Instant.now().toEpochMilli();
            long output = lEndTime - lStartTime;
            result.add(output);
            
            this.gui.textOut.append("Thread "+id+" - Run "+run+" : Sending 'GET' Request To: "+url+" - Elapsed Time : "+output+" ms\n");
        
        } catch (Exception ex) {
            this.gui.labelErrText.setText(ex.getMessage());
            this.gui.dialogOut.setVisible(true);
        }
    }
}
