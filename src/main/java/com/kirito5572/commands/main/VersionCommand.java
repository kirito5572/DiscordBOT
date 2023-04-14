package com.kirito5572.commands.main;

import com.kirito5572.App;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import com.sun.management.OperatingSystemMXBean;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.List;

public class VersionCommand implements ICommand {
    private static final String version = "빌드 버젼 V 2.0.2";
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        int serverCount = 0;
        int userCount = 0;
        for (Guild guild : event.getJDA().getGuilds()) {
            serverCount++;
            userCount += guild.getMembers().size();
        }
        Date nowDate = new Date();
        long temp = nowDate.getTime() - App.getDate().getTime();
        Date upTime = new Date(temp);
        SystemInfo systemInfo = new SystemInfo();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
        CentralProcessor cpu = hardwareAbstractionLayer.getProcessor();
        CentralProcessor.ProcessorIdentifier cpuInfo = cpu.getProcessorIdentifier();

        String day, hour, min, sec;
        try {
            double uptime = (double)operatingSystem.getSystemUptime();
            day = String.valueOf((int)uptime / 60 / 60 / 24);
            hour = String.valueOf((int)uptime / 60 / 60 % 24);
            min = String.valueOf((int)uptime / 60 % 60);
            sec = String.valueOf((int)uptime % 60);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        EmbedBuilder embedBuilder = EmbedUtils.getDefaultEmbed()
                .setTitle("봇 정보")
                .addField("제작자", "**kirito5572#5572**", true)
                .addField("서버수", String.valueOf(serverCount), true)
                .addField("유저수", String.valueOf(userCount), true)
                .addField("업타임", String.format("%s일 %s시간 %s분 %s초", upTime.getTime()/86400000%100, upTime.getTime()/3600000%24, upTime.getTime()/60000%60, upTime.getTime()/1000%60), true)
                .addField("게이트웨이 핑", event.getJDA().getGatewayPing() + "ms", true)
                .addField("램 사용량", ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024) + "MB", true)
                .addField("CPU 쓰레드", String.valueOf(Runtime.getRuntime().availableProcessors()), true)
                .addField("CPU 사용량", "프로그램: " + String.format("%.2f", osBean.getProcessCpuLoad() * 100.0D) + "% / 전체: " + String.format("%.2f", osBean.getCpuLoad() * 100.0D) + "%", true)
                .addField("CPU 정보 ", cpuInfo.getName() + "@" + cpuInfo.getVendorFreq() / 1000000L + "MHz", true)
                .addField("OS 정보", operatingSystem.getManufacturer() + " " + operatingSystem.getVersionInfo().getVersion() + " Build" + operatingSystem.getVersionInfo().getBuildNumber(), true)
                .addField("OS 업타임",day + "일 " + hour + "시간 " + min + "분 " + sec + "초", true);

        event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();


    }

    @NotNull
    @Override
    public String getHelp() {
        return "봇 정보를 알려줍니다. \n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "`";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "봇정보";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }

    @NotNull
    public static String getVersion() {
        return version;
    }
}
