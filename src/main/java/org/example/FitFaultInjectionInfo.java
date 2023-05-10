package org.example;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class FitFaultInjectionInfo {
    private static FitFaultInjectionInfo instance;
    private String name;
    private long time;
    private int type;

    public static FitFaultInjectionInfo getFitFaultInjectionInfo(){
        if (instance == null) {
            instance = new FitFaultInjectionInfo();
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
