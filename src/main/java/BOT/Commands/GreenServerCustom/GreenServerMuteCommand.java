package BOT.Commands.GreenServerCustom;

import BOT.App;
import BOT.Objects.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;
import java.util.stream.Collectors;

public class GreenServerMuteCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if(event.getGuild().getId().equals("600010501266866186")) {
            if(args.isEmpty()) {
                event.getChannel().sendMessage("인수 부족 '" + App.getPREFIX() + "help" +
                        getInvoke() + "'").queue();
                return;
            }
            String users;
            String time;
            try {
                users = args.get(0);
            } catch (Exception e) {
                channel.sendMessage("유저 명이 없습니다.").queue();

                return;
            }
            try {
                time = args.get(1);
            } catch (Exception e) {
                channel.sendMessage("시간이 없습니다.").queue();

                return;
            }

            List<User> foundUsers = FinderUtil.findUsers(users, event.getGuild().getJDA());

            if(foundUsers.isEmpty()) {
                List<Member> foundMember = FinderUtil.findMembers(users, event.getGuild());
                if(foundMember.isEmpty()) {
                    event.getChannel().sendMessage("'" + users + "' 라는 유저는 없습니다.").queue();
                    return;
                }

                foundUsers = foundMember.stream().map(Member::getUser).collect(Collectors.toList());
            }
            User user = foundUsers.get(0);
            Member member = event.getGuild().getMember(user);
            Role role;
            try {
                role = event.getGuild().getRolesByName("채팅 금지", true).get(0);
            } catch (Exception e) {
                channel.sendMessage("채팅 금지 역할을 먼저 생성해주세요.").queue();

                return;
            }
            EmbedBuilder builder = EmbedUtils.defaultEmbed()
                    .setTitle("채팅 금지 제재")
                    .addField("유저명", user.getName(), false)
                    .addField("멘션명", member.getAsMention(), false)
                    .addField("삭제되는 역할", member.getRoles().toString(), false)
                    .addField("기한",time,false);
            event.getGuild().getTextChannelById("609781460785692672").sendMessage(builder.build()).queue();


            event.getGuild().getController().removeRolesFromMember(member, member.getRoles()).complete();

            event.getGuild().getController().addSingleRoleToMember(member, role).queue();
        } else {
            channel.sendMessage("이 명령어는 이 서버에서 지원하지 않습니다.").complete();
        }
    }

    @Override
    public String getHelp() {
        return "채팅 금지를 먹입니다.";
    }

    @Override
    public String getInvoke() {
        return "채팅금지";
    }

    @Override
    public String getSmallHelp() {
        return "채팅 금지를 먹입니다.";
    }
}
