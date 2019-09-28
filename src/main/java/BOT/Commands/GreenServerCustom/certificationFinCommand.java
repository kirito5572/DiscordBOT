package BOT.Commands.GreenServerCustom;
import BOT.Objects.ICommand;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class certificationFinCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        String joined = String.join("", args);
        if(event.getGuild().getId().equals("600010501266866186")) {
            Role role;
            try {
                role = event.getGuild().getRolesByName("인증완료", true).get(0);
            } catch (Exception e) {
                channel.sendMessage("인증완료 역할이 없습니다. 관리자에게 문의 바랍니다.").queue();
                return;
            }
            channel.sendMessage("유저 인증 확인중...").queue();
            String private_Key;
            try {
                private_Key = SHA256(event.getAuthor().getId());
            } catch (NoSuchAlgorithmException e) {
                channel.sendMessage("인증키 확인을 위한 비교 인증키 생성에 실패했습니다.").queue();
                return;
            }
            String final_Key = "#UI#{" + event.getAuthor().getName() + "/" + event.getAuthor().getId() + "} #TK#" + private_Key;
            channel.sendMessage("스팀으로 부터 정보 받아오는중...").queue();
            try {
                WebUtils.ins.scrapeWebPage(joined).async((document) -> {
                    String a = document.getElementsByTag("body").first().toString();
                    int b = a.indexOf("<div class=\"profile_summary\">");
                    a = a.substring(b + 31);
                    int c = a.indexOf("</div>");
                    a = a.substring(11, c - 12);

                    if (a.equals(final_Key)) {
                        WebUtils.ins.scrapeWebPage("https://steamid.io/lookup/" + joined).async((document1 -> {
                            String a1;
                            try {
                                a1 = document1.getElementsByTag("body").first().toString();
                                int b1 = a1.indexOf("<a href=\"https://steamid.io/lookup/");
                                a1 = a1.substring(b1 + 35);
                                int c1 = a1.indexOf("\">");
                                a1 = a1.substring(0, c1);
                                System.out.println(a1);
                            } catch (Exception e) {
                                channel.sendMessage("스팀 프로필이 비공개이거나, 스팀 프로필이 제대로 설정되어 있지 않습니다. \n" +
                                        "봇이 스팀 프로필을 불러오는데 실패하였습니다.").queue();

                                return;
                            }
                            try {
                                filesave(event.getAuthor().getName(), event.getAuthor().getId(), a1);
                            } catch (IOException e) {
                                channel.sendMessage("정보를 데이터베이스에 저장하다가 오류가 발생했습니다.").queue();
                                e.printStackTrace();
                                return;
                            }
                            channel.sendMessage(event.getMember().getAsMention() + ", 스팀 인증에 성공하셨습니다. ").queue();
                            event.getGuild().getController().addSingleRoleToMember(event.getMember(), role).complete();
                        }));
                    } else {
                        channel.sendMessage("인증키가 잘못되었습니다.").queue();
                    }
                });
            } catch (Exception e) {
                channel.sendMessage("스팀 링크가 잘못되었습니다.").queue();
            }
        } else {
            channel.sendMessage("이 명령어는 이 서버에서 지원하지 않는 명령어입니다.").queue();
        }
    }

    @Override
    public String getHelp() {
        return "스팀과 디스코드서버간의 인증 시스템입니다.";
    }

    @Override
    public String getInvoke() {
        return "인증확인";
    }

    @Override
    public String getSmallHelp() {
        return "serverCustom";
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
    private void filesave(String name, String ID, String SteamID) throws IOException{
        String message = "Discord ID = " + ID + "\n" +
                "Discord name = " + name;

        File file = new File("C:\\디스코드 서버 인증기록\\" + SteamID + ".txt");
        FileWriter writer;


        writer = new FileWriter(file, true);
        writer.write(message);
        writer.flush();

        if(writer != null) writer.close();

    }
}
