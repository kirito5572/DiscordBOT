package BOT.Listener;

import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
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

public class MemberCountListener extends ListenerAdapter {


    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onTextChannelCreate(TextChannelCreateEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onTextChannelDelete(TextChannelDeleteEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onVoiceChannelCreate(VoiceChannelCreateEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onVoiceChannelDelete(VoiceChannelDeleteEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onRoleCreate(RoleCreateEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    @Override
    public void onRoleDelete(RoleDeleteEvent event) {
        Guild guild = event.getGuild();

        count(guild);
    }

    private void count(Guild guild) {
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
            category.getChannels().get(0).getManager().setName(memberCountName + " : " + guild.getMembers().size()).complete();
            String botCountName = "\uD83C\uDF3F봇 수";
            category.getChannels().get(1).getManager().setName(botCountName + " : " + numOfBot).complete();
            String userCountName = "\uD83C\uDF3F유저 수";
            category.getChannels().get(2).getManager().setName(userCountName + " : " + numOfUser).complete();
            String channelCountName = "\uD83C\uDF3F채널 수";
            category.getChannels().get(3).getManager().setName(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).complete();
            String roleCountName = "\uD83C\uDF3F역할 갯수";
            category.getChannels().get(4).getManager().setName(roleCountName + " : " + guild.getRoles().size()).complete();
        } else {
            String memberCountName = "총 멤버 수";
            category.getChannels().get(0).getManager().setName(memberCountName + " : " + guild.getMembers().size()).complete();
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
}
