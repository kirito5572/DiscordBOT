package BOT.Listener;

import BOT.App;
import BOT.Constants;
import BOT.Objects.CommandManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;

public class Listener extends ListenerAdapter {
    private final CommandManager manager;
    private final Logger logger = LoggerFactory.getLogger(Listener.class);
    private static String ID1;
    private static String ID2;
    public Listener(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void onReady(ReadyEvent event) {
        logger.info(String.format("로그인 성공: %#s", event.getJDA().getSelfUser()));
        System.out.println(String.format("로그인 성공: %#s", event.getJDA().getSelfUser()));
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        User author = event.getAuthor();
        Message message = event.getMessage();
        String content = message.getContentDisplay();

        if (event.isFromType(ChannelType.TEXT)) {

            Guild guild = event.getGuild();
            TextChannel textChannel = event.getTextChannel();

            logger.info(String.format("\n" +
                    "보낸사람: %#s\n" +
                    "친 내용: %s\n" +
                    "[서버: %s]  " +
                    "[채팅방: %s]", author, content, guild.getName(), textChannel.getName()));
        } else if (event.isFromType(ChannelType.PRIVATE)) {
            logger.info(String.format("[PRIV]<%#s>: %s", author, content));
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        StringBuilder IDreader = new StringBuilder();
        StringBuilder IDreader1 = new StringBuilder();
        try {
            File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\OWNER_ID.txt");
            File file1 = new File("C:\\DiscordServerBotSecrets\\rito-bot\\OWNER_ID1.txt");
            FileReader fileReader = new FileReader(file);
            FileReader fileReader1 = new FileReader(file1);
            int singalCh, singalCh1;
            while((singalCh = fileReader.read()) != -1) {
                IDreader.append((char) singalCh);
            }
            while((singalCh1 = fileReader1.read()) != -1) {
                IDreader1.append((char) singalCh1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ID1 = IDreader.toString();
        ID2 = IDreader1.toString();
        if (event.getMessage().getContentRaw().equalsIgnoreCase(App.getPREFIX() + "종료") &&
                (
                        (event.getAuthor().getIdLong() == Long.decode(ID1)) ||
                        (event.getAuthor().getIdLong() == Long.decode(ID2))
                )) {
            System.out.println(ID1 + ID2);
            shutdown(event.getJDA(), event);
            return;

        } else if(event.getMessage().getContentRaw().equalsIgnoreCase(App.getPREFIX() + "재시작") &&
                (
                        (event.getAuthor().getIdLong() == Long.decode(ID1)) ||
                                (event.getAuthor().getIdLong() == Long.decode(ID2))
                )) {
            restart(event.getJDA(), event);
            return;
        }
        if(event.getAuthor().isBot()) {
            return;

        }
        if(event.getMessage().isWebhookMessage()) {

            return;
        }
        if(event.getGuild().getId().equals("600010501266866186")) {
            if(!event.getChannel().getId().equals("600012818879741963")) {
                if(!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                    Role role = event.getGuild().getRoleById("600012069559074822");
                    if(!event.getMember().getRoles().contains(role)) {
                        if (event.getMessage().getContentRaw().startsWith(App.getPREFIX())) {
                            event.getChannel().sendMessage(event.getMember().getAsMention() + " , 명령어는 봇 명령어 채널에서 사용해주세요").queue();

                            message.delete().queue();

                            return;
                        }
                    }
                }
            }
        }
        if(event.getGuild().getId().equals("617222347425972234")) {
            if(!event.getChannel().getId().equals("617230917315854356")) {
                if(!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                    if (event.getMessage().getContentRaw().startsWith(Constants.PREFIX)) {
                        event.getChannel().sendMessage(event.getMember().getAsMention() + " , 명령어는 봇 명령어 채널에서 사용해주세요").queue();

                        message.delete().queue();

                        return;
                    }
                }
            }
        }
        if(event.getGuild().getId().equals("607390893804093442")) {
            if(!event.getChannel().getId().equals("620095220729511977")) {
                if(!event.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                    if (event.getMessage().getContentRaw().startsWith(Constants.PREFIX)) {
                        event.getChannel().sendMessage(event.getMember().getAsMention() + " , 명령어는 봇 명령어 채널에서 사용해주세요").queue();

                        message.delete().queue();

                        return;
                    }
                }
            }
        }

        if (event.getMessage().getContentRaw().startsWith(Constants.PREFIX)) {
            manager.handleCommand(event);
        }
    }
    private void shutdown(JDA jda, GuildMessageReceivedEvent event) {
        new Thread(() -> {
            event.getChannel().sendMessage("봇이 종료됩니다.").queue();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jda.shutdown();
            System.exit(0);
        }).start();
    }
    private void restart(JDA jda, GuildMessageReceivedEvent event) {
        new Thread(() -> {
            event.getMessage().delete().queue();
            event.getChannel().sendMessage("종료 하는중....").queue();
            if(App.isDEBUG_MODE() || App.isONLINE_DEBUG()) {
                event.getChannel().sendMessage("디버그 모드 재시작입니다...").queue();
            } else {
                if (event.getAuthor().getId().equals("284508374924787713")) {
                    event.getJDA().getGuildById("617222347425972234").getTextChannelById("617222347983683586").sendMessage(event.getJDA().getSelfUser().getAsMention() + " 업데이트틀 위해 1분간 사용이 불가능합니다.").queue();
                    event.getJDA().getGuildById("617757206929997895").getTextChannelById("617757206929997901").sendMessage(event.getJDA().getSelfUser().getAsMention() + " 업데이트틀 위해 1분간 사용이 불가능합니다.").queue();
                    event.getJDA().getGuildById("479625309788962816").getTextChannelById("479625309788962818").sendMessage(event.getJDA().getSelfUser().getAsMention() + " 업데이트틀 위해 1분간 사용이 불가능합니다.").queue();
                    event.getJDA().getGuildById("508913681279483913").getTextChannelById("539466073343524864").sendMessage(event.getJDA().getSelfUser().getAsMention() + " 업데이트틀 위해 1분간 사용이 불가능합니다.").queue();
                    event.getJDA().getGuildById("453817631603032065").getTextChannelById("574856464347430914").sendMessage(event.getJDA().getSelfUser().getAsMention() + " 업데이트틀 위해 1분간 사용이 불가능합니다.").queue();
                    event.getJDA().getGuildById("600010501266866186").getTextChannelById("620079355707785226").sendMessage(event.getJDA().getSelfUser().getAsMention() + " 업데이트틀 위해 1분간 사용이 불가능합니다.").queue();
                    event.getJDA().getGuildById("607390893804093442").getTextChannelById("620223554172092460").sendMessage(event.getJDA().getSelfUser().getAsMention() + " 업데이트틀 위해 1분간 사용이 불가능합니다.").queue();
                } else {
                    event.getJDA().getGuildById("600010501266866186").getTextChannelById("600010501266866188").sendMessage(event.getJDA().getSelfUser().getAsMention() + " 업데이트틀 위해 1분간 사용이 불가능합니다.").queue();
                }
            }
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                jda.shutdown();
                System.exit(0);
            }).start();
        }).start();
    }

    public static String getID1() {
        return ID1;
    }

    public static String getID2() {
        return ID2;
    }
}

