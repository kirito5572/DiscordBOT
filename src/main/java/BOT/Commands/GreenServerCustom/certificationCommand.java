package BOT.Commands.GreenServerCustom;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class certificationCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(certificationCommand.class);
    private InputStream certification_Img_is1 = getClass().getClassLoader().getResourceAsStream("1.png");
    private InputStream certification_Img_is2 = getClass().getClassLoader().getResourceAsStream("2.png");
    private InputStream certification_Img_is3 = getClass().getClassLoader().getResourceAsStream("3.png");
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if(event.getGuild().getId().equals("600010501266866186")) {
            try {
                boolean role = Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRolesByName("인증완료", true).get(0));
                if(!role) {
                    event.getGuild().createRole()
                            .setName("인증완료")
                            .complete();
                }
            } catch (Exception e) {
                event.getGuild().createRole()
                        .setName("인증완료")
                        .complete();
            }
            if(!Objects.requireNonNull(event.getMember()).getRoles().contains(event.getGuild().getRolesByName("인증완료",true).get(0))) {
                String joined;
                String private_key ,Key;
                joined = String.join(" ", args);
                if (joined.equals("")) {
                    channel.sendMessage("인증키 생성중....").complete();
                    try {
                        Key = SHA256(event.getAuthor().getId());
                    } catch (NoSuchAlgorithmException e) {
                        channel.sendMessage("인증키 생성에 실패했습니다.").queue();
                        return;
                    }
                    private_key = Key;
                    channel.sendMessage("인증키 생성 완료. DM확인 부탁드립니다.").queue();
                    try {
                        event.getMember().getUser().openPrivateChannel().queue((channel1) -> {
                            File file1;
                            try {
                                file1 = convertInputStreamToFile(certification_Img_is1);
                            } catch (IOException e) {
                                e.printStackTrace();
                                StackTraceElement[] eStackTrace = e.getStackTrace();
                                StringBuilder a = new StringBuilder();
                                for (StackTraceElement stackTraceElement : eStackTrace) {
                                    a.append(stackTraceElement).append("\n");
                                }
                                logger.warn(a.toString());

                                return;
                            }
                            channel1.sendMessage("당신의 인증키는 \n #UI#{" + event.getAuthor().getName() + "/" + event.getAuthor().getId() + "} #TK#" + private_key + "\n입니다.\n" +
                                    "인증 받는법:\n" +
                                    "1. 본인의 스팀프로필에 접속합니다.").addFile(file1).queue();
                            File file2;
                            try {
                                file2 = convertInputStreamToFile(certification_Img_is2);
                            } catch (IOException e) {
                                e.printStackTrace();
                                StackTraceElement[] eStackTrace = e.getStackTrace();
                                StringBuilder a = new StringBuilder();
                                for (StackTraceElement stackTraceElement : eStackTrace) {
                                    a.append(stackTraceElement).append("\n");
                                }
                                logger.warn(a.toString());

                                return;
                            }
                            channel1.sendMessage("2. 본인의 프로필을 편집하여 요약탭에 인증키를 적습니다.").addFile(file2).queue();
                            channel1.sendMessage("3. 프로필을 저장한후 `" + App.getPREFIX() + "확인 ` [스팀 프로필]을 입력하여. 인증 절차를 진행해주세요.\n" +
                                    "예시: `" + App.getPREFIX() + getInvoke() + "확인 https://steamcommunity.com/id/kirito5572`\n" +
                                    "모든 인증과정을 거치셨으면, 프로필은 다시 원래대로 설정하셔도 됩니다.").queue();
                        });
                    } catch (ErrorResponseException e) {
                        channel.sendMessage("인증키 전송에 실패했습니다. 제가 당신에게 메세지를 보낼수 없습니다.").queue();
                        File file3;
                        try {
                            file3 = convertInputStreamToFile(certification_Img_is3);
                        } catch (IOException e1) {
                            e.printStackTrace();
                            StackTraceElement[] eStackTrace = e.getStackTrace();
                            StringBuilder a = new StringBuilder();
                            for (StackTraceElement stackTraceElement : eStackTrace) {
                                a.append(stackTraceElement).append("\n");
                            }
                            logger.warn(a.toString());

                            return;
                        }
                        channel.sendMessage(event.getMember().getAsMention() + "디스코드 설정을 확인해주세요.").addFile(file3).queue();
                    }
                }
            } else {
                channel.sendMessage("이미 당신은 인증된 회원이므로 이 명령어를 사용하지 않아도 됩니다.").complete();
            }
        } else {
            channel.sendMessage("이 명령어는 이 서버에서 지원하지 않습니다.").complete();
        }
    }

    @Override
    public String getHelp() {
        return "서버 접근 인증을 위한 인증키를 생성합니다.";
    }

    @Override
    public String getInvoke() {
        return "인증";
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";    }

    private String SHA256(String msg) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes());
        byte[] bytes =md.digest();

        StringBuilder builder = new StringBuilder();
        for (byte b: bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
    private File convertInputStreamToFile(InputStream is) throws IOException {
        File file = File.createTempFile("C://temp", ".png");

        OutputStream outputStream = new FileOutputStream(file);

        IOUtils.copy(is, outputStream);

        outputStream.close();

        return file;
    }
}
