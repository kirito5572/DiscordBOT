package BOT.Commands.Moderator;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class MemberCount implements ICommand {
    private String categoryName = "\uD83D\uDCCB서버 상태\uD83D\uDCCB";
    private String memberCountName = "멤버 숫자";
    private String botCountName = "봇 숫자";
    private String userCountName = "유저 숫자";
    private String channelCountName = "채널 숫자";
    private String roleCountName = "역할 숫자";
    private Category category;
    private VoiceChannel memberCount;
    private VoiceChannel botCount;
    private VoiceChannel userCount;
    private VoiceChannel channelCount;
    private VoiceChannel roleCount;
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            event.getChannel().sendMessage("이 명령어를 사용할 권한이 없습니다.").queue();

            return;
        }
        if(!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL)) {
            event.getChannel().sendMessage("봇이 이 명령어를 사용 할 권한이 없습니다.").queue();

            return;
        }
        String joined = String.join(" ",args);
        Guild guild = event.getGuild();
        JDA jda = event.getJDA();
        if(joined.equals("시작")) {
            try {
                category = guild.getCategoriesByName(categoryName, true).get(0);

                event.getChannel().sendMessage("이미 멤버 카운팅이 시작되었습니다. \n" +
                        App.getPREFIX() + getInvoke() + " 새로고침").queue();

                return;
            } catch (Exception ignored) {

            }

            guild.getController().createCategory(categoryName)
                    .setPosition(0)
                    .complete();
            category = guild.getCategoriesByName(categoryName, true).get(0);
            category.createVoiceChannel(memberCountName + " : " + guild.getMembers().size()).complete();

            int numOfBot = 0;
            int numOfUser = 0;
            for (int i = 0; i < guild.getMembers().size(); i++) {
                if (guild.getMembers().get(i).getUser().isBot()) {
                    numOfBot++;
                } else {
                    numOfUser++;
                }
            }

            category.createVoiceChannel(botCountName + " : " + numOfBot).complete();
            category.createVoiceChannel(userCountName + " : " + numOfUser).complete();
            category.createVoiceChannel(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).complete();
            category.createVoiceChannel(roleCountName + " : " + guild.getRoles().size()).complete();

            event.getChannel().sendMessage("멤버 카운팅이 시작되었습니다.").queue();
        } else if(joined.equals("종료")) {
            try {
                category = guild.getCategoriesByName(categoryName, true).get(0);
                for(int i = 0; i < category.getChannels().size(); i++) {
                    category.getChannels().get(i).delete().complete();
                }
                category.delete().complete();
                event.getChannel().sendMessage("멤버 카운팅이 종료되었습니다..").queue();
            } catch (Exception e) {
                event.getChannel().sendMessage("멤버 카운팅을 하고 있지 않습니다.").queue();
            }
        } else if(joined.equals("새로고침")) {
            count(guild);
            event.getChannel().sendMessage("새로고침이 완료되었습니다.").queue();
        } else {
            event.getChannel().sendMessage("그런 인수는 없습니다.\n" +
                    App.getPREFIX() + getInvoke() + " [시작 / 종료 / 새로고침]").queue();
        }
    }

    @Override
    public String getHelp() {
        return "멤버 카운터 [시작/정지/새로고침]";
    }

    @Override
    public String getInvoke() {
        return "멤버카운팅";
    }

    @Override
    public String getSmallHelp() {
        return "서버 멤버 카운팅";
    }
    private void count(Guild guild) {
        try {
            category = guild.getCategoriesByName(categoryName,true).get(0);
        } catch (Exception e) {
            return;
        }


        String memberCountName = "멤버 숫자";
        category.getChannels().get(0).getManager().setName(memberCountName + " : " + guild.getMembers().size()).complete();

        int numOfBot = 0;
        int numOfUser = 0;
        for (int i = 0; i < guild.getMembers().size(); i++) {
            if (guild.getMembers().get(i).getUser().isBot()) {
                numOfBot++;
            } else {
                numOfUser++;
            }
        }

        category.getChannels().get(1).getManager().setName(botCountName + " : " + numOfBot).complete();
        category.getChannels().get(2).getManager().setName(userCountName + " : " + numOfUser).complete();
        category.getChannels().get(3).getManager().setName(channelCountName + " : " + (guild.getChannels().size() - guild.getCategories().size())).complete();
        category.getChannels().get(4).getManager().setName(roleCountName + " : " + guild.getRoles().size()).complete();
    }
}
