package cn.goindog.amsm.system;

import com.google.gson.JsonObject;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;

import java.text.DecimalFormat;
import java.util.Properties;

public class SystemStatus {
    public static JsonObject getDeviceInfo() {
        Properties props = System.getProperties();
        String java_version = props.getProperty("java.version");
        String java_vendor = props.getProperty("java.vendor");
        String system_name = props.getProperty("os.name");
        String system_arch = props.getProperty("os.arch");
        String os_version = props.getProperty("os.version");

        JsonObject object = new JsonObject();
        object.addProperty("system_name", system_name);
        object.addProperty("system_arch", system_arch);
        object.addProperty("system_version", os_version);
        object.addProperty("java_version", java_version);
        object.addProperty("java_vendor", java_vendor);

        object.addProperty("manager_version", "1.0-SNAPSHOT");

        return object;
    }

    public static JsonObject getCPUStatus() {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        JsonObject object = new JsonObject();
        object.addProperty("total", processor.getLogicalProcessorCount());
        object.addProperty("acaliable", new DecimalFormat("#.##%").format(1.0-(idle * 1.0 / totalCpu)));
        return object;
    }

    public static JsonObject getMemoryStatus() {
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        long totalByte = memory.getTotal();
        long acaliableByte = memory.getAvailable();
        JsonObject obj = new JsonObject();
        obj.addProperty("total", formatByte(totalByte));
        obj.addProperty("acaliable", new DecimalFormat("#.##%").format((totalByte-acaliableByte)*1.0/totalByte));

        return obj;
    }

    public static String formatByte(long byteNumber){
        double FORMAT = 1024.0;
        double kbNumber = byteNumber/FORMAT;
        if(kbNumber<FORMAT){
            return new DecimalFormat("#.##KB").format(kbNumber);
        }
        double mbNumber = kbNumber/FORMAT;
        if(mbNumber<FORMAT){
            return new DecimalFormat("#.##MB").format(mbNumber);
        }
        double gbNumber = mbNumber/FORMAT;
        if(gbNumber<FORMAT){
            return new DecimalFormat("#.##GB").format(gbNumber);
        }
        double tbNumber = gbNumber/FORMAT;
        return new DecimalFormat("#.##TB").format(tbNumber);
    }
}
