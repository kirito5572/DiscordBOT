package BOT.Listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.util.Objects;

public class nekoDiscordMemberListener extends ListenerAdapter {
    private final Logger logger = LoggerFactory.getLogger(nekoDiscordMemberListener.class);
    private static String Chating1;

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        Guild guild = event.getGuild();
        if(guild.getId().equals("439780696999985172")) {
            Role role = guild.getRoleById("625549604674600970");  //휴식자
            Role role2 = guild.getRoleById("619556312627544105");  //사무직
            Member member = event.getMember();
            if (event.getMessageId().equals(Chating1)) {
                assert member != null;
                if (member.getRoles().contains(role2)) {
                    Objects.requireNonNull(guild.getTextChannelById("616452604506931230")).removeReactionById(event.getMessageId(), event.getReactionEmote().getEmote(), member.getUser()).complete();
                    member.getUser().openPrivateChannel().complete().sendMessage("휴식자 역할을 부여 받기전 관리직 역할을 부여 받아야 합니다.\n" +
                            "!join으로 역할을 부여 받으시기 바랍니다.").complete();
                } else {
                    assert role != null;
                    guild.addRoleToMember(member, role).complete();
                }
            }
        } else if (guild.getId().equals("617222347425972234")) {
            Role role = null;
            Member member = null;
            switch (event.getMessageId()) {
                case "661969267729629194":
                    role = guild.getRoleById("658290356692975616");
                    member = event.getMember();
                    assert member != null;
                    assert role != null;
                    guild.addRoleToMember(member, role).complete();
                    break;

                case "668590253786791943":
                    role = guild.getRoleById("666828960503693338");
                    member = event.getMember();
                    assert member != null;
                    assert role != null;
                    guild.addRoleToMember(member, role).complete();
                    break;
            }
            if(member != null) {
                member.getUser().openPrivateChannel().complete().sendMessage(role.getName() + " 역할 부여!").queue();
            }
        }
    }
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        Guild guild = event.getGuild();
        if(guild.getId().equals("439780696999985172")) {
            Role role = guild.getRoleById("625549604674600970");  //휴식자
            Member member = event.getMember();
            if (event.getMessageId().equals(Chating1)) {
                assert member != null;
                assert role != null;
                guild.removeRoleFromMember(member, role).complete();
            }
        } else if(guild.getId().equals("617222347425972234")) {
            Role role = null;
            Member member = null;
            switch (event.getMessageId()) {
                case "661969267729629194":
                    role = guild.getRoleById("658290356692975616");
                    member = event.getMember();
                    assert member != null;
                    assert role != null;
                    guild.removeRoleFromMember(member, role).complete();
                    break;

                case "668590253786791943":
                    role = guild.getRoleById("666828960503693338");
                    member = event.getMember();
                    assert member != null;
                    assert role != null;
                    guild.removeRoleFromMember(member, role).complete();
                    break;
            }
            if(member != null) {
                member.getUser().openPrivateChannel().complete().sendMessage(role.getName() + " 역할 제거!").queue();
            }
        }
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        StringBuilder Chating_temp = new StringBuilder();
        try {
            File file = new File("C:\\디스코드봇 파일들\\네코디코 리스너\\Chating1.txt");
            FileReader fileReader = new FileReader(file);
            int singalCh;
            while ((singalCh = fileReader.read()) != -1) {
                Chating_temp.append((char) singalCh);
            }
        } catch (Exception e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
        Chating1 = Chating_temp.toString();
    }
}
