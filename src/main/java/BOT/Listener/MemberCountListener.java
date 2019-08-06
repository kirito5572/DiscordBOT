package BOT.Listener;

import BOT.Objects.CommandManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.core.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.role.RoleCreateEvent;
import net.dv8tion.jda.core.events.role.RoleDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemberCountListener extends ListenerAdapter {
    private final CommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
    public MemberCountListener(CommandManager manager) {
        this.manager = manager;
    }

    VoiceChannel channel1;
    VoiceChannel channel2;
    VoiceChannel channel3;
    VoiceChannel channel4;
    VoiceChannel channel5;

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild, jda);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild, jda);
    }

    @Override
    public void onTextChannelCreate(TextChannelCreateEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild, jda);
    }

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild, jda);
    }

    @Override
    public void onRoleCreate(RoleCreateEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild, jda);
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild, jda);
    }

    private void count(Guild guild, JDA jda) {
        String categoryName = "\uD83D\uDCCB서버 상태\uD83D\uDCCB";
        Category category;
        try {
            category = guild.getCategoriesByName(categoryName,true).get(0);
        } catch (Exception e) {
            return;
        }

        for(int i = 0; i < category.getChannels().size(); i++) {
            category.getChannels().get(i).delete().complete();
        }

        String memberCountName = "멤버 숫자";
        category.createVoiceChannel(memberCountName + " : " + jda.getUsers().size()).complete();
        channel1 = category.getVoiceChannels().get(0);
        int numOfBot = 0;
        int numOfUser = 0;
        for (int i = 0; i < jda.getUsers().size(); i++) {
            if (jda.getUsers().get(i).isBot()) {
                numOfBot++;
            } else {
                numOfUser++;
            }
        }

        String botCountName = "봇 숫자";
        category.createVoiceChannel(botCountName + " : " + numOfBot).complete();
        channel2 = category.getVoiceChannels().get(1);
        String userCountName = "유저 숫자";
        category.createVoiceChannel(userCountName + " : " + numOfUser).complete();
        channel3 = category.getVoiceChannels().get(2);
        String channelCountName = "채널 숫자";
        category.createVoiceChannel(channelCountName + " : " + (guild.getChannels().size() + 2)).complete();
        channel4 = category.getVoiceChannels().get(3);
        String roleCountName = "역할 숫자";
        category.createVoiceChannel(roleCountName + " : " + guild.getRoles().size()).complete();
        channel5 = category.getVoiceChannels().get(4);
    }
}
