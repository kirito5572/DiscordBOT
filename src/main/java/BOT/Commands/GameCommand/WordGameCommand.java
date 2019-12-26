package BOT.Commands.GameCommand;

import BOT.App;
import BOT.Objects.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WordGameCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {
        List<Member> members = new ArrayList<>();
        members.add(event.getMember());
        String startingWord = args.get(args.size() - 1);
        args = args.subList(0, args.size() - 2);
        for(String a : args) {
            List<Member> member = FinderUtil.findMembers(a, event.getGuild());
            members.add(member.get(0));
        }
        String ID = event.getChannel().sendMessage("끝말 잇기 게임 시작중.....").complete().getId();
        //독립 쓰레드 실행
        event.getChannel().editMessageById(ID, "끝말 잇기 게임 초기화중.....").complete();
        //기본 데이터값 입력
        TextChannel textChannel = event.getChannel();
        event.getChannel().sendMessage("끝말 잇기 게임을 시작합니다.\n" +
                "시작 단어: " + startingWord +
                "\n 참여 인원: " + members.size()).queue();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "끝말 잇기!\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + " <유저1> <유저2> <유저3> ... <시작 단어>\n" +
                "유저명은 닉네임/DiscordID/멘션 이여야 합니다.\n" +
                "본인은 유저명에 입력하지 않아도 됩니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "끝말잇기";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "game";
    }
}
