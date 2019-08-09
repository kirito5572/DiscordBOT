package BOT.Commands.GreenServerCustom;

import BOT.App;
import BOT.Objects.ICommand;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;

import java.io.File;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class certificationCommand implements ICommand {
    private URL certification_Img_URL1 = getClass().getClassLoader().getResource("1.png");
    private File file1 = new File(certification_Img_URL1.getPath());
    private URL certification_Img_URL2 = getClass().getClassLoader().getResource("2.png");
    private File file2 = new File(certification_Img_URL2.getFile());
    private URL certification_Img_URL3 = getClass().getClassLoader().getResource("3.png");
    private File file3 = new File(certification_Img_URL3.getFile());
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        if(event.getGuild().getId().equals("600010501266866186")) {
            try {
                event.getMember().getRoles().contains(event.getGuild().getRolesByName("인증완료", true).get(0));
            } catch (Exception e) {
                Role role = event.getGuild().getController().createRole()
                        .setName("인증완료")
                        .complete();
            }
            if(!event.getMember().getRoles().contains(event.getGuild().getRolesByName("인증완료",true).get(0))) {
                String joined = "";
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
                            channel1.sendMessage("당신의 인증키는 \n #UI#{" + event.getAuthor().getName() + "/" + event.getAuthor().getId() + "} #TK#" + private_key + "\n입니다.").queue();
                            channel1.sendMessage("인증 받는법:").queue();
                            channel1.sendMessage("1. 본인의 스팀프로필에 접속합니다.").queue();
                            channel1.sendFile(file1).queue();
                            channel1.sendMessage("2. 본인의 프로필을 편집하여 요약탭에 인증키를 적습니다.").queue();
                            channel1.sendFile(file2).queue();
                            channel1.sendMessage("3. 프로필을 저장한후 `" + App.getPREFIX() + "확인 ` [스팀 프로필]을 입력하여. 인증 절차를 진행해주세요.\n" +
                                    "예시: `" + App.getPREFIX() + "확인 https://steamcommunity.com/id/kirito5572`").queue();
                            channel1.sendMessage("모든 인증과정을 걸치셨으면, 프로필은 다시 원래대로 설정하셔도 됩니다.").queue();
                        });
                    } catch (ErrorResponseException e) {
                        channel.sendMessage("인증키 전송에 실패했습니다. 제가 당신에게 메세지를 보낼수 없습니다.").queue();
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
        return "본인 인증키 생성";
    }

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
}