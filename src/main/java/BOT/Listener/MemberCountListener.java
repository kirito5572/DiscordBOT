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
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
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


    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    @Override
    public void onTextChannelCreate(TextChannelCreateEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    @Override
    public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    @Override
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    @Override
    public void onRoleCreate(RoleCreateEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild);
    }

    private void count(Guild guild) {
        String categoryName = "\uD83D\uDCCB서버 상태\uD83D\uDCCB";
        Category category;
        try {
            category = guild.getCategoriesByName(categoryName,true).get(0);
        } catch (Exception e) {
            return;
        }


        String memberCountName = "총 멤버 수";
        category.getChannels().get(0).getManager().setName(memberCountName + " : " + guild.getMembers().size()).complete();

        int numOfBot = 0;
        int numOfUser = 0;
        for (int i = 0; i < guild.getMembers().size(); i++) {
            if (guild.getMembers().get(i).getUser().isBot()) {
                numOfBot++;
            } else {
                numOfUser++;
            }
        }
        String botCountName = "봇 수";
        category.getChannels().get(1).getManager().setName(botCountName + " : " + numOfBot).complete();
        String userCountName = "유저 수";
        category.getChannels().get(2).getManager().setName(userCountName + " : " + numOfUser).complete();
        String channelCountName = "채널 수";
        category.getChannels().get(3).getManager().setName(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).complete();
        String roleCountName = "역할 갯수";
        category.getChannels().get(4).getManager().setName(roleCountName + " : " + guild.getRoles().size()).complete();
    }
}
