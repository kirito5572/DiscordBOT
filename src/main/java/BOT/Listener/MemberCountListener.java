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

        count(guild, jda);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild, jda);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild, jda);
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
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
    public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();

        count(guild, jda);
    }

    @Override
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
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


        String memberCountName = "멤버 숫자";
        category.getChannels().get(0).getManager().setName(memberCountName + " : " + jda.getUsers().size()).complete();

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
        category.getChannels().get(1).getManager().setName(botCountName + " : " + numOfBot).complete();
        String userCountName = "유저 숫자";
        category.getChannels().get(2).getManager().setName(userCountName + " : " + numOfUser).complete();
        String channelCountName = "채널 숫자";
        category.getChannels().get(3).getManager().setName(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).complete();
        String roleCountName = "역할 숫자";
        category.getChannels().get(4).getManager().setName(roleCountName + " : " + guild.getRoles().size()).complete();
    }
}
