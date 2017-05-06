package ResponseTimeBenchmarking;

import java.util.ArrayList;

public class Benchmark extends Thread {
    
    private GUI gui;
    private int run, test, delay;
    private String url;
    
    private ArrayList<Long> meanArr;
    private ResultArray result;
    
    Benchmark(GUI gui) {
        this.gui = gui;
        
        this.run = Integer.parseInt(gui.inRun.getText());
        this.test = Integer.parseInt(gui.inTest.getText());
        this.delay = Integer.parseInt(gui.inDelay.getText());
        this.url = gui.inURL.getText();
        
        this.meanArr = new ArrayList<Long>();
        this.result = new ResultArray();
    }
    
    @Override
    public void run() {
        doBenchmark();
    }
    
    public void doBenchmark() {
        long mean;
        long meanAll = 0;
        Connection cn[] = new Connection[run * test];
        
        try {
            for(int i = 0; i < run; i++) {
                this.gui.textOut.append("Round "+ (i+1) +"\n");
                for(int j = 0; j < test; j++) {
                    cn[j] = new Connection(j+1, i+1, result, url, this.gui);
                    cn[j].start();
                }
                for(int j = 0; j < test; j++) {
                    cn[j].join();
                }
                
                mean = result.calculateMean();
                meanArr.add(mean);
                this.gui.textOut.append("\nMean time from round "+ (i+1) +" : "+ mean +" ms\n\n");
                
                if( (i + 1) == run ) {
                    this.gui.textOut.append("========== SLEEP SKIPPED ==========\n");
                } else {
                    this.gui.textOut.append("========== SLEEP FOR "+delay+" SECONDS ==========\n\n");
                    Thread.sleep(delay * 1000);
                }
            }
        } catch(InterruptedException ex) {
            this.gui.labelErrText.setText(ex.getMessage());
            this.gui.dialogOut.setVisible(true);
        }
        
        this.gui.textOut.append("\nSummary:\n");
        for(int i = 0; i < meanArr.size(); i++) {
            meanAll += meanArr.get(i);
            this.gui.textOut.append("Mean from all runs "+ (i+1) +" : "+meanArr.get(i)+" ms\n");
        }
        this.gui.textOut.append("Mean from all runs : "+ (meanAll / meanArr.size()) + " ms from "+meanAll+" ms / "+meanArr.size()+"\n");
    }
    
}
