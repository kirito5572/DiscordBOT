//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.listener.main;

import javax.annotation.Nonnull;
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

public class MemberCountListener extends ListenerAdapter {
    public MemberCountListener() {
    }

    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public void onTextChannelDelete(@NotNull TextChannelDeleteEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public void onVoiceChannelCreate(@NotNull VoiceChannelCreateEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public void onVoiceChannelDelete(@NotNull VoiceChannelDeleteEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public void onRoleDelete(@NotNull RoleDeleteEvent event) {
        Guild guild = event.getGuild();
        count(guild);
    }

    public static void count(@NotNull Guild guild) {
        String categoryName = "\ud83d\udccb:서버 상태";
        String categoryName1 = "\ud83d\udccb서버 상태\ud83d\udccb";

        Category category;
        try {
            category = guild.getCategoriesByName(categoryName, true).get(0);
        } catch (Exception var12) {
            try {
                category = guild.getCategoriesByName(categoryName1, true).get(0);
            } catch (Exception var11) {
                return;
            }
        }

        int numOfBot = 0;
        int numOfUser = 0;

        for(int i = 0; i < guild.getMembers().size(); ++i) {
            if (guild.getMembers().get(i).getUser().isBot()) {
                ++numOfBot;
            } else {
                ++numOfUser;
            }
        }

        String botCountName;
        String userCountName;
        String channelCountName;
        String roleCountName;
        String memberCountName;
        if (guild.getId().equals("609985979167670272")) {
            memberCountName = "\ud83c\udf3f총 멤버 수";
            if (!category.getChannels().get(0).getName().equals(memberCountName + " : " + guild.getMembers().size())) {
                category.getChannels().get(0).getManager().setName(memberCountName + " : " + guild.getMembers().size()).queue();
            }

            botCountName = "\ud83c\udf3f봇 수";
            if (!category.getChannels().get(1).getName().equals(botCountName + " : " + numOfBot)) {
                category.getChannels().get(1).getManager().setName(botCountName + " : " + numOfBot).queue();
            }

            userCountName = "\ud83c\udf3f유저 수";
            if (!category.getChannels().get(2).getName().equals(userCountName + " : " + numOfUser)) {
                category.getChannels().get(2).getManager().setName(userCountName + " : " + numOfUser).queue();
            }

            channelCountName = "\ud83c\udf3f채널 수";
            if (!category.getChannels().get(3).getName().equals(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size()))) {
                category.getChannels().get(3).getManager().setName(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).queue();
            }

            roleCountName = "\ud83c\udf3f역할 갯수";
            if (!category.getChannels().get(4).getName().equals(roleCountName + " : " + guild.getRoles().size())) {
                category.getChannels().get(4).getManager().setName(roleCountName + " : " + guild.getRoles().size()).queue();
            }
        } else {
            memberCountName = "총 멤버 수";
            if (!category.getChannels().get(0).getName().equals(memberCountName + " : " + guild.getMembers().size())) {
                category.getChannels().get(0).getManager().setName(memberCountName + " : " + guild.getMembers().size()).queue();
            }

            botCountName = "봇 수";
            if (!category.getChannels().get(1).getName().equals(botCountName + " : " + numOfBot)) {
                category.getChannels().get(1).getManager().setName(botCountName + " : " + numOfBot).queue();
            }

            userCountName = "유저 수";
            if (!category.getChannels().get(2).getName().equals(userCountName + " : " + numOfUser)) {
                category.getChannels().get(2).getManager().setName(userCountName + " : " + numOfUser).queue();
            }

            channelCountName = "채널 수";
            if (!category.getChannels().get(3).getName().equals(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size()))) {
                category.getChannels().get(3).getManager().setName(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).queue();
            }

            roleCountName = "역할 갯수";
            if (!category.getChannels().get(4).getName().equals(roleCountName + " : " + guild.getRoles().size())) {
                category.getChannels().get(4).getManager().setName(roleCountName + " : " + guild.getRoles().size()).queue();
            }
        }

    }
}
