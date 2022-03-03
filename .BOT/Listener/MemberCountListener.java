package BOT.Listener;

import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent;
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.events.role.RoleDeleteEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class MemberCountListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onTextChannelDelete(@NotNull TextChannelDeleteEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onVoiceChannelCreate(@NotNull VoiceChannelCreateEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onVoiceChannelDelete(@NotNull VoiceChannelDeleteEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onRoleDelete(@NotNull RoleDeleteEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    public static void count(@NotNull Guild guild) {
        String categoryName = "\uD83D\uDCCB:서버 상태";
        String categoryName1 = "\uD83D\uDCCB서버 상태\uD83D\uDCCB";
        Category category;
        try {
            category = guild.getCategoriesByName(categoryName,true).get(0);
        } catch (Exception e) {
            try {
                category = guild.getCategoriesByName(categoryName1,true).get(0);
            } catch (Exception e1) {
                return;
            }
        }



        int numOfBot = 0;
        int numOfUser = 0;
        for (int i = 0; i < guild.getMembers().size(); i++) {
            if (guild.getMembers().get(i).getUser().isBot()) {
                numOfBot++;
            } else {
                numOfUser++;
            }
        }
        if(guild.getId().equals("609985979167670272")) {
            String memberCountName = "\uD83C\uDF3F총 멤버 수";
            if(!category.getChannels().get(0).getName().equals(memberCountName + " : " + guild.getMembers().size())) {
                category.getChannels().get(0).getManager().setName(memberCountName + " : " + guild.getMembers().size()).complete();
            }
            String botCountName = "\uD83C\uDF3F봇 수";
            if(!category.getChannels().get(1).getName().equals(botCountName + " : " + numOfBot)) {
                category.getChannels().get(1).getManager().setName(botCountName + " : " + numOfBot).complete();
            }
            String userCountName = "\uD83C\uDF3F유저 수";
            if(!category.getChannels().get(2).getName().equals(userCountName + " : " + numOfUser)) {
                category.getChannels().get(2).getManager().setName(userCountName + " : " + numOfUser).complete();
            }
            String channelCountName = "\uD83C\uDF3F채널 수";
            if(!category.getChannels().get(3).getName().equals(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size()))) {
                category.getChannels().get(3).getManager().setName(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).complete();
            }
            String roleCountName = "\uD83C\uDF3F역할 갯수";
            if(!category.getChannels().get(4).getName().equals(roleCountName + " : " + guild.getRoles().size())) {
                category.getChannels().get(4).getManager().setName(roleCountName + " : " + guild.getRoles().size()).complete();
            }
        } else {
            String memberCountName = "총 멤버 수";
            if(!category.getChannels().get(0).getName().equals(memberCountName + " : " + guild.getMembers().size())) {
                category.getChannels().get(0).getManager().setName(memberCountName + " : " + guild.getMembers().size()).complete();
            }
            String botCountName = "봇 수";
            if(!category.getChannels().get(1).getName().equals(botCountName + " : " + numOfBot)) {
                category.getChannels().get(1).getManager().setName(botCountName + " : " + numOfBot).complete();
            }
            String userCountName = "유저 수";
            if(!category.getChannels().get(2).getName().equals(userCountName + " : " + numOfUser)) {
                category.getChannels().get(2).getManager().setName(userCountName + " : " + numOfUser).complete();
            }
            String channelCountName = "채널 수";
            if(!category.getChannels().get(3).getName().equals(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size()))) {
                category.getChannels().get(3).getManager().setName(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).complete();
            }
            String roleCountName = "역할 갯수";
            if(!category.getChannels().get(4).getName().equals(roleCountName + " : " + guild.getRoles().size())) {
                category.getChannels().get(4).getManager().setName(roleCountName + " : " + guild.getRoles().size()).complete();
            }
        }
    }
}
