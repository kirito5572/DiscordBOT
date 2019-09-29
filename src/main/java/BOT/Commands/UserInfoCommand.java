package BOT.Commands;

import BOT.App;
import BOT.Constants;
import BOT.Objects.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class UserInfoCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        User user;
        Member member;
        if(args.isEmpty()) {
            user = event.getMember().getUser();
            member = event.getMember();
        } else {
            String joined = String.join(" ", args);
            List<User> foundUsers = FinderUtil.findUsers(joined, event.getGuild().getJDA());

            if (foundUsers.isEmpty()) {
                List<Member> foundMember = FinderUtil.findMembers(joined, event.getGuild());
                if (foundMember.isEmpty()) {
                    event.getChannel().sendMessage("'" + joined + "' 라는 유저는 없습니다.").queue();
                    return;
                }

                foundUsers = foundMember.stream().map(Member::getUser).collect(Collectors.toList());
            }
            user = foundUsers.get(0);
            member = event.getGuild().getMember(user);
        }

        MessageEmbed embed = EmbedUtils.defaultEmbed()
                .setColor(member.getColor())
                .setThumbnail(user.getEffectiveAvatarUrl().replaceFirst("gif", "png"))
                .addField("유저이름#번호", String.format("%#s", user), false)
                .addField("표시 이름", member.getEffectiveName(), false)
                .addField("유저 ID + 언급 멘션", String.format("%s (%s)", user.getId(), member.getAsMention()), false)
                .addField("가입 일자", user.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.systemDefault())), false)
                .addField("서버 초대 일자", member.getJoinDate().format(DateTimeFormatter.RFC_1123_DATE_TIME.withZone(ZoneId.systemDefault())), false)
                .addField("온라인 상태", member.getOnlineStatus().name().toLowerCase().replaceAll("_", " "), false)
                .addField("봇 여부", user.isBot() ? "Yes" : "No", false)
                .build();

        event.getChannel().sendMessage(embed).queue();

    }

    @Override
    public String getHelp() {
        return "유저정보 알기! \n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " [유저 이름/@유저/유저 id] `";
    }

    @Override
    public String getInvoke() {
        return "유저정보";
    }

    @Override
    public String getSmallHelp() {
        return "other";
    }
}
