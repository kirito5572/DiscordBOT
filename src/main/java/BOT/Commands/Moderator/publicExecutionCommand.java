package BOT.Commands.Moderator;

import BOT.App;
import BOT.Listener.Listener;
import BOT.Objects.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class publicExecutionCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(publicExecutionCommand.class);
    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();

        String joined = String.join(" ", args);
        if(!Objects.requireNonNull(event.getMember()).getUser().getId().equals(Listener.getID1()) || !event.getMember().getUser().getId().equals(Listener.getID2())) {
            if (!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                if(event.getGuild().getId().equals("600010501266866186")) {
                    if (!event.getMember().getRoles().contains(event.getGuild().getRoleById("600012069559074822"))) {
                        channel.sendMessage(event.getMember().getAsMention() + ", 당신은 이 명령어를 사용할 권한이 없습니다.").queue();

                        return;
                    }
                } else {
                    channel.sendMessage(event.getMember().getAsMention() + ", 당신은 이 명령어를 사용할 권한이 없습니다.").queue();

                    return;
                }
            }
        }
        String userName;
        String time;
        try {
            userName = args.get(0);
        } catch (Exception e) {
            channel.sendMessage("유저명란이 비었습니다.").queue();

            return;
        }

        List<User> foundUsers = FinderUtil.findUsers(userName, event.getGuild().getJDA());

        if(foundUsers.isEmpty()) {
            List<Member> foundMember = FinderUtil.findMembers(joined, event.getGuild());
            if(foundMember.isEmpty()) {
                event.getChannel().sendMessage("'" + joined + "' 라는 유저는 없습니다.").queue();
                return;
            }

            foundUsers = foundMember.stream().map(Member::getUser).collect(Collectors.toList());
        }
        User user = foundUsers.get(0);
        Member member = event.getGuild().getMember(user);
        Role role;
        try {
            role = event.getGuild().getRolesByName("공개 처형", true).get(0);
        } catch (Exception e1) {
            try {
                role = event.getGuild().createRole()
                        .setName("공개 처형")
                        .setColor(Color.RED)
                        .setPermissions(0L)
                        .complete();
            } catch (Exception e) {

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
                channel.sendMessage("공개 처형 역할 생성중 에러가 발생했습니다.").queue();

                return;
            }
            channel.sendMessage("공개 처형 역할이 없어 새로 생성했습니다.").queue();

        }
        assert member != null;
        if(member.getRoles().contains(role)) {
            event.getGuild().removeRoleFromMember(member, role).complete();

            event.getChannel().sendMessage(user.getName() + "을/를 공개 처형 대상자에서 해제 했습니다.").queue();
            EmbedBuilder builder = EmbedUtils.defaultEmbed()
                    .setColor(Color.GREEN)
                    .setTitle("공개 처형자 해제")
                    .addField("대상자", member.getAsMention(), true)
                    .addField("지정 담당자", event.getMember().getAsMention(), true);
            switch (event.getGuild().getId()) {
                case "600010501266866186":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("600015587544006679")).sendMessage(builder.build()).queue();
                    Objects.requireNonNull(event.getGuild().getTextChannelById("609781460785692672")).sendMessage(builder.build()).queue();
                    break;
                case "617222347425972234":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("617244045780975637")).sendMessage(builder.build()).queue();
                    break;
                case "617757206929997895":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("617760924714926113")).sendMessage(builder.build()).queue();
                    break;
                case "607390893804093442":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("620091943522664466")).sendMessage(builder.build()).queue();
                    break;
                case "607390203086372866":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("620560178424578061")).sendMessage(builder.build()).queue();
                    break;
                default:
                    channel.sendMessage(builder.build()).queue();
                    break;
            }
        } else {
            event.getGuild().addRoleToMember(member, role).complete();

            event.getChannel().sendMessage(member.getEffectiveName() + "을/를 공개 처형 대상자로 지정 했습니다.").queue();
            EmbedBuilder builder = EmbedUtils.defaultEmbed()
                    .setColor(Color.RED)
                    .setTitle("공개 처형자 지정")
                    .addField("대상자", member.getAsMention(), true)
                    .addField("지정 담당자", event.getMember().getAsMention(), true);

            switch (event.getGuild().getId()) {
                case "600010501266866186":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("600015587544006679")).sendMessage(builder.build()).queue();
                    Objects.requireNonNull(event.getGuild().getTextChannelById("609781460785692672")).sendMessage(builder.build()).queue();
                    break;
                case "617222347425972234":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("617244045780975637")).sendMessage(builder.build()).queue();
                    break;
                case "617757206929997895":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("617760924714926113")).sendMessage(builder.build()).queue();
                    break;
                case "607390893804093442":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("620091943522664466")).sendMessage(builder.build()).queue();
                    break;
                case "607390203086372866":
                    Objects.requireNonNull(event.getGuild().getTextChannelById("620560178424578061")).sendMessage(builder.build()).queue();
                    break;
                default:
                    channel.sendMessage(builder.build()).queue();
                    break;
            }
        }
        event.getMessage().delete().queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "유저를 공개처형 설정/해제 합니다 \n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " <유저명/유저ID/@멘션> `\n" +
                "공개처형 역할이 있는 사람에게 명령어를 재차 사용할 경우 공개처형이 해제됩니다. ";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "공개처형";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
