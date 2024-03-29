package com.kirito5572.commands.main.moderator;

import com.kirito5572.App;
import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static com.kirito5572.listener.main.MemberCountListener.count;

public class MemberCount implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        if(!Objects.requireNonNull(event.member()).hasPermission(Permission.MANAGE_CHANNEL)) {
            event.getChannel().sendMessage("이 명령어를 사용할 권한이 없습니다.").queue();

            return;
        }
        if(!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)) {
            event.getChannel().sendMessage("봇이 이 명령어를 사용 할 권한이 없습니다.").queue();

            return;
        }
        String joined = String.join(" ",args);
        Guild guild = event.getGuild();
        String categoryName = "\uD83D\uDCCB서버 상태\uD83D\uDCCB";
        Category category;
        switch (joined) {
            case "시작" -> {
                try {
                    category = guild.getCategoriesByName(categoryName, true).get(0);

                    if (category != null) {
                        event.getChannel().sendMessage("이미 멤버 카운팅이 시작되었습니다. \n" +
                                App.getPREFIX() + getInvoke() + " 새로고침").queue();
                    }
                    return;
                } catch (Exception ignored) {

                }
                guild.createCategory(categoryName)
                        .setPosition(0)
                        .complete();
                category = guild.getCategoriesByName(categoryName, true).get(0);
                String memberCountName = "총 멤버 수";
                category.createVoiceChannel(memberCountName + " : " + guild.getMembers().size()).queue();
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
                category.createVoiceChannel(botCountName + " : " + numOfBot).queue();
                String userCountName = "유저 수";
                category.createVoiceChannel(userCountName + " : " + numOfUser).queue();
                String channelCountName = "채널 수";
                category.createVoiceChannel(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).queue();
                String roleCountName = "역할 갯수";
                category.createVoiceChannel(roleCountName + " : " + guild.getRoles().size()).queue();
                event.getChannel().sendMessage("멤버 카운팅이 시작되었습니다.").queue();
            }
            case "종료" -> {
                try {
                    category = guild.getCategoriesByName(categoryName, true).get(0);
                    for (int i = 0; i < category.getChannels().size(); i++) {
                        category.getChannels().get(i).delete().queue();
                    }
                    category.delete().queue();
                    event.getChannel().sendMessage("멤버 카운팅이 종료되었습니다..").queue();
                } catch (Exception e) {
                    event.getChannel().sendMessage("멤버 카운팅을 하고 있지 않습니다.").queue();
                }
            }
            case "새로고침" -> {
                count(guild);
                event.getChannel().sendMessage("새로고침이 완료되었습니다.").queue();
            }
            default -> event.getChannel().sendMessage("그런 인수는 없습니다.\n" +
                    App.getPREFIX() + getInvoke() + " [시작 / 종료 / 새로고침]").queue();
        }
    }

    @NotNull
    @Override
    public String getHelp() {
        return "멤버 카운터 [시작/정지/새로고침]";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "멤버카운팅";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
