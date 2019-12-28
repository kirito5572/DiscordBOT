package BOT.Listener;

import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class workHomeListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Guild guild = event.getJDA().getGuildById("600010501266866186");
        assert guild != null;
        Role work = guild.getRoleById("622325436528984084");
        Role home = guild.getRoleById("622325520868311041");
        String[] discordID = new String[] {
                "368688044934561792", "306321660145958913", "284508374924787713", "315746279730839552", "209239034721992704",
                "405018851399565323", "456088053933539328", "501414420556152852", "492832169715040276"
        };
        String[] status = new String[discordID.length];
        String[] nickname = new String[discordID.length];
        TimerTask job = new TimerTask() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < discordID.length; i++) {
                        if (Objects.requireNonNull(guild.getMemberById(discordID[i])).getRoles().contains(work)) {
                            status[i] = "출근";
                        } else if (Objects.requireNonNull(guild.getMemberById(discordID[i])).getRoles().contains(home)) {
                            status[i] = "퇴근";
                        } else {
                            status[i] = "알수 없음";
                        }
                        nickname[i] = Objects.requireNonNull(guild.getMemberById(discordID[i])).getEffectiveName();
                    }
                    EmbedBuilder builder = EmbedUtils.defaultEmbed()
                            .setTitle("[O5 Council]")
                            .addField(nickname[0], status[0], false)
                            .addField(nickname[1], status[1], false);
                    EmbedBuilder builder1 = EmbedUtils.defaultEmbed()
                            .setTitle("[MTF Commander]")
                            .addField(nickname[2], status[2], false)
                            .addField(nickname[3], status[3], false)
                            .addField(nickname[4], status[4], false);
                    EmbedBuilder builder2 = EmbedUtils.defaultEmbed()
                            .setTitle("[MTF Scientist]");
                    EmbedBuilder builder3 = EmbedUtils.defaultEmbed()
                            .setTitle("[MTF Lieutenant]")
                            .addField(nickname[5], status[5], false)
                            .addField(nickname[6], status[6], false)
                            .addField(nickname[7], status[7], false);
                    EmbedBuilder builder4 = EmbedUtils.defaultEmbed()
                            .setTitle("[MTF Cadet]")
                            .addField(nickname[8], status[8], false)
                            .setFooter("30초 마다 새로 고침 됩니다.", "https://media.istockphoto.com/vectors/refresh-icon-element-of-web-icon-for-mobile-concept-and-web-apps-thin-vector-id1049047088");
                    Objects.requireNonNull(guild.getTextChannelById("622076975258927125")).editMessageById("657901600358137856", builder.build()).queue();
                    Objects.requireNonNull(guild.getTextChannelById("622076975258927125")).editMessageById("657901620679540746", builder1.build()).queue();
                    Objects.requireNonNull(guild.getTextChannelById("622076975258927125")).editMessageById("657901624316002364", builder2.build()).queue();
                    Objects.requireNonNull(guild.getTextChannelById("622076975258927125")).editMessageById("657901627432501258", builder3.build()).queue();
                    Objects.requireNonNull(guild.getTextChannelById("622076975258927125")).editMessageById("657901634747367425", builder4.build()).queue();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Timer jobScheduler = new Timer();
        jobScheduler.scheduleAtFixedRate(job, 1000, 30000);
    }
}
