package BOT.Listener;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class workHomeListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        Guild guild = event.getJDA().getGuildById("600010501266866186");
        assert guild != null;
        Role work = guild.getRoleById("622325436528984084");
        Role home = guild.getRoleById("622325520868311041");
        String[] status = new String[10];
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                if(Objects.requireNonNull(guild.getMemberById("368688044934561792")).getRoles().contains(work)) {
                    status[0] = "출근";
                } else if(Objects.requireNonNull(guild.getMemberById("368688044934561792")).getRoles().contains(home)) {
                    status[0] = "퇴근";
                }
                if(Objects.requireNonNull(guild.getMemberById("306321660145958913")).getRoles().contains(work)) {
                    status[1] = "출근";
                } else if(Objects.requireNonNull(guild.getMemberById("306321660145958913")).getRoles().contains(home)) {
                    status[1] = "퇴근";
                }
                if(Objects.requireNonNull(guild.getMemberById("456088053933539328")).getRoles().contains(work)) {
                    status[3] = "출근";
                } else if(Objects.requireNonNull(guild.getMemberById("456088053933539328")).getRoles().contains(home)) {
                    status[3] = "퇴근";
                }
                if(Objects.requireNonNull(guild.getMemberById("284508374924787713")).getRoles().contains(work)) {
                    status[4] = "출근";
                } else if(Objects.requireNonNull(guild.getMemberById("284508374924787713")).getRoles().contains(home)) {
                    status[4] = "퇴근";
                }
                if(Objects.requireNonNull(guild.getMemberById("342951769627688960")).getRoles().contains(work)) {
                    status[5] = "출근";
                } else if(Objects.requireNonNull(guild.getMemberById("342951769627688960")).getRoles().contains(home)) {
                    status[5] = "퇴근";
                }
                if(Objects.requireNonNull(guild.getMemberById("315746279730839552")).getRoles().contains(work)) {
                    status[6] = "출근";
                } else if(Objects.requireNonNull(guild.getMemberById("315746279730839552")).getRoles().contains(home)) {
                    status[6] = "퇴근";
                }
                if(Objects.requireNonNull(guild.getMemberById("405018851399565323")).getRoles().contains(work)) {
                    status[7] = "출근";
                } else if(Objects.requireNonNull(guild.getMemberById("405018851399565323")).getRoles().contains(home)) {
                    status[7] = "퇴근";
                }
                if(Objects.requireNonNull(guild.getMemberById("501414420556152852")).getRoles().contains(work)) {
                    status[8] = "출근";
                } else if(Objects.requireNonNull(guild.getMemberById("501414420556152852")).getRoles().contains(home)) {
                    status[8] = "퇴근";
                }
                if(Objects.requireNonNull(guild.getMemberById("278086240195182592")).getRoles().contains(work)) {
                    status[8] = "출근";
                } else if(Objects.requireNonNull(guild.getMemberById("278086240195182592")).getRoles().contains(home)) {
                    status[8] = "퇴근";
                }
                EmbedBuilder builder = EmbedUtils.defaultEmbed()
                        .setTitle("[출/퇴근 상태]")
                        .addField("[운영팀장] green1052", status[0], false)
                        .addField("[운영팀] Joshua", status[1], false)
                        .addField("[민원팀장] Internal Security Department", status[3], false)
                        .addField("[개발팀장] kirito5572", status[4], false)
                        .addField("[개발팀] 블루베어", status[5], false)
                        .addField("[개발팀] 도리닭 DoryDarg", status[7], false)
                        .addField("[관리팀장] Lava", status[6], false)
                        .addField("[관리팀] (백은) Silver", status[8], false)
                        .addField("[보안팀] 꽃스시", status[9], false)
                        .setFooter("30초 마다 새로 고침 됩니다.", "https://media.istockphoto.com/vectors/refresh-icon-element-of-web-icon-for-mobile-concept-and-web-apps-thin-vector-id1049047088");
                Objects.requireNonNull(guild.getTextChannelById("622076975258927125")).editMessageById("622647164606349322",builder.build()).queue();
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 30000);
    }
}
