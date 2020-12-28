package BOT.Commands;

import BOT.App;
import BOT.Objects.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class UserInfoCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {
        User user;
        Member member;
        Guild guilda = null;
        if(args.isEmpty()) {
            user = Objects.requireNonNull(event.getMember()).getUser();
            member = event.getMember();
            guilda = event.getGuild();
        } else {
            String joined = String.join(" ", args);
            try {
                boolean bypass = false;
                List<Member> foundMember = null;
                List<Guild> guilds = event.getJDA().getGuilds();
                for (Guild guild : guilds) {
                    if(!bypass) {
                        foundMember = FinderUtil.findMembers(joined, guild);
                        if (!foundMember.isEmpty()) {
                            bypass = true;
                            guilda = guild;
                        }
                    }
                }
                if(foundMember == null) {
                    event.getChannel().sendMessage("'" + joined + "' 라는 유저는 없습니다.").queue();
                    return;
                }
                if(foundMember.isEmpty()) {
                    event.getChannel().sendMessage("'" + joined + "' 라는 유저는 없습니다.").queue();
                    return;
                }

                user = foundMember.get(0).getUser();
                member = foundMember.get(0);

            } catch (Exception e) {
                event.getChannel().sendMessage("해당 유저를 봇이 찾을수 없거나, 인수가 잘못 입력되었습니다.").queue();

                return;
            }
        }
        StringBuilder serverRole = new StringBuilder();
        if(event.getGuild().getId().equals(guilda.getId())) {
            List<Role> role = member.getRoles();
            for (Role value : role) {
                serverRole.append(value.getAsMention()).append("\n");
            }
        } else {
            List<Role> role = member.getRoles();
            for (Role value : role) {
                serverRole.append(value.getName()).append("\n");
            }
        }
        MessageEmbed embed = EmbedUtils.getDefaultEmbed()
                .setColor(member.getColor())
                .setThumbnail(user.getEffectiveAvatarUrl())
                .addField("유저이름#번호", String.format("%#s", user), false)
                .addField("서버 표시 이름", member.getEffectiveName(), false)
                .addField("유저 ID + 언급 멘션", String.format("%s (%s)", user.getId(), member.getAsMention()), false)
                .addField("디스코드 가입 일자", user.getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault())), false)
                .addField("서버 초대 일자", member.getTimeJoined().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault())), false)
                .addField("서버 부여 역할", serverRole.toString(), false)
                .addField("온라인 상태", member.getOnlineStatus().name().toLowerCase().replaceAll("_", " "), false)
                .addField("봇 여부", user.isBot() ? "예" : "아니요", false)
                .addField("검색 된 서버", guilda.getName(), false)
                .build();

        event.getChannel().sendMessage(embed).queue();

    }

    @NotNull
    @Override
    public String getHelp() {
        return "유저정보 알기! \n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " [유저 이름/@유저/유저 id] `";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "유저정보";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "other";
    }
}
