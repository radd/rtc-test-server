package io.github.radd.mgrtestserver.controller;

import io.github.radd.mgrtestserver.util.Stat;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@RestController
@RequestMapping("/monitor")
public class MonitorController {


    @Autowired
    MetricsEndpoint metricsEndpoint;

    boolean isRunning = false;
    String type;
    int testID;
    ArrayList<Stat> stats = new ArrayList<>();
    long lastTimestamp;

    @PostMapping("/start")
    public String start(@RequestBody String payload) {
        JSONObject obj = new JSONObject(payload);
        type = obj.getString("type");
        testID = obj.getInt("testID");

        stats.clear();
        isRunning = obj.getBoolean("start");

        return "ok";
    }

    @Scheduled(fixedDelay = 1000)
    public void stats() {
        if(!isRunning)
            return;

        Stat stat = new Stat();
        stat.setTimestamp(System.currentTimeMillis());

        stat.setCPU(metricsEndpoint.metric("process.cpu.usage", null).getMeasurements().get(0).getValue()*100);
        //System.out.println("CPU: " + metricsEndpoint.metric("process.cpu.usage", null).getMeasurements().get(0).getValue()*100);

        stat.setMemory(metricsEndpoint.metric("jvm.memory.committed", null).getMeasurements().get(0).getValue()/1000000);
        //System.out.println("Memory " + metricsEndpoint.metric("jvm.memory.committed", null).getMeasurements().get(0).getValue()/1000000);

        stats.add(stat);
    }

    @GetMapping("/stop")
    public String stop() throws IOException {
        if(!isRunning || stats.size()==0)
            return "ok";

        isRunning=false;
        File file = new File("C:\\Users\\woles\\Projects\\Node\\mgr-test-client\\test2\\"+ type + "\\monitor\\test_"+ testID + ".log");
        file.getParentFile().mkdir();
        if(!file.exists())
            file.createNewFile();

        FileWriter fileWriter = new FileWriter(file);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        printWriter.println("CPU %  Memory MB   span ms");

        lastTimestamp = stats.get(0).getTimestamp();
        stats.forEach(stat -> {
            printWriter.printf("%f  %f  %d\r\n", stat.getCPU(), stat.getMemory(), (stat.getTimestamp()-lastTimestamp));
            lastTimestamp = stat.getTimestamp();
        });

        printWriter.close();

        stats.clear();

        return "ok";
    }

}
