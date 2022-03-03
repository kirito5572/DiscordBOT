package com.kirito5572.commands.main

import com.kirito5572.App;
import com.kirito5572.objects.main.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class UnusedColorCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        TextChannel channel = event.getChannel();
        if(Objects.requireNonNull(event.getMember()).hasPermission(Permission.MANAGE_ROLES)) {
            List<Member> members = event.getGuild().getMembers();
            channel.sendMessage(event.getMember().getAsMention() + ", 안 쓰는 색 역할 삭제를 시작합니다.").queue();

            List<Role> foundRole = FinderUtil.findRoles("#", event.getGuild());
            if(foundRole.isEmpty()) {
                channel.sendMessage(event.getMember().getAsMention() + ", 삭제 할 역할이 없습니다.").queue();
                return;
            }
            for (Role value : foundRole) {
                boolean role_delete = true;
                for (Member member : members) {
                    if (member.getRoles().contains(value)) {
                        role_delete = false;
                    }
                }
                if (role_delete) {
                    channel.sendMessage(value.getAsMention() + " 역할이 삭제되었습니다.").complete();
                    value.delete().complete();
                    System.out.println(value.getAsMention());
                }
            }
            channel.sendMessage(event.getMember().getAsMention() + " 색 역할 삭제가 완료되었습니다.").queue();
        } else {
            channel.sendMessage("권한이 없습니다.").queue();
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "사용하지 않는 색 역할을 정리합니다." +
                "사용법: `" + App.getPREFIX() + getInvoke() + "`";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "색정리";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
