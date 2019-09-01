package BOT.Commands.Moderator;

import BOT.App;
import BOT.Listener.Listener;
import BOT.Objects.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class publicExecutionCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();

        String joined = String.join(" ", args);
        if(!event.getMember().getUser().getId().equals(Listener.getID1()) || !event.getMember().getUser().getId().equals(Listener.getID2())) {
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
        try {
            time = args.get(1);
        } catch (Exception e) {
            channel.sendMessage("시간이 비었습니다. \n" +
                    "비었을 경우 수동 해제시까지 적용됩니다.").queue();

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
            e1.printStackTrace();
            try {
                role = event.getGuild().getController().createRole()
                        .setName("공개 처형")
                        .setColor(Color.RED)
                        .setPermissions(0L)
                        .complete();
            } catch (Exception e) {
                e.printStackTrace();
                channel.sendMessage("공개 처형 역할 생성중 에러가 발생했습니다.").queue();

                return;
            }
            channel.sendMessage("공개 처형 역할이 없어 새로 생성했습니다.").queue();

        }
        if(member.getRoles().contains(role)) {
            event.getGuild().getController().removeSingleRoleFromMember(member, role).complete();

            event.getChannel().sendMessage(user.getName() + "을/를 공개 처형 대상자에서 해제 했습니다.").queue();
            EmbedBuilder builder = EmbedUtils.defaultEmbed()
                    .setColor(Color.GREEN)
                    .setTitle("공개 처형자 해제")
                    .addField("대상자", member.getAsMention(), true)
                    .addField("지정 담당자", event.getMember().getAsMention(), true);
            switch (event.getGuild().getId()) {
                case "600010501266866186":
                    event.getGuild().getTextChannelById("600015587544006679").sendMessage(builder.build()).queue();
                    event.getGuild().getTextChannelById("609781460785692672").sendMessage(builder.build()).queue();
                    break;
                case "617222347425972234":
                    event.getGuild().getTextChannelById("617244045780975637").sendMessage(builder.build()).queue();
                    break;
                case "617757206929997895":
                    event.getGuild().getTextChannelById("617760924714926113").sendMessage(builder.build()).queue();
                    break;
                default:
                    channel.sendMessage(builder.build()).queue();
                    break;
            }
        } else {
            event.getGuild().getController().addSingleRoleToMember(member, role).complete();

            event.getChannel().sendMessage(user.getName() + "을/를 공개 처형 대상자로 지정 했습니다.").queue();
            EmbedBuilder builder = EmbedUtils.defaultEmbed()
                    .setColor(Color.RED)
                    .setTitle("공개 처형자 지정")
                    .addField("대상자", member.getAsMention(), true)
                    .addField("지정 담당자", event.getMember().getAsMention(), true);

            switch (event.getGuild().getId()) {
                case "600010501266866186":
                    event.getGuild().getTextChannelById("600015587544006679").sendMessage(builder.build()).queue();
                    event.getGuild().getTextChannelById("609781460785692672").sendMessage(builder.build()).queue();
                    break;
                case "617222347425972234":
                    event.getGuild().getTextChannelById("617244045780975637").sendMessage(builder.build()).queue();
                    break;
                case "617757206929997895":
                    event.getGuild().getTextChannelById("617760924714926113").sendMessage(builder.build()).queue();
                    break;
                default:
                    channel.sendMessage(builder.build()).queue();
                    break;
            }
        }
    }

    @Override
    public String getHelp() {
        return "유저를 공개처형 설정/해제 합니다 \n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " <유저명/유저ID/@멘션> `\n" +
                "공개처형 역할이 있는 사람에게 명령어를 재차 사용할 경우 공개처형이 해제됩니다. ";
    }

    @Override
    public String getInvoke() {
        return "공개처형";
    }

    @Override
    public String getSmallHelp() {
        return "유저 공개처형 시키기";
    }
}
