package BOT.Commands;

import BOT.App;
import BOT.Objects.ICommand;
import com.jagrosh.jdautilities.commons.utils.FinderUtil;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.HierarchyException;

import java.util.List;
import java.util.Random;

public class ColorCommand implements ICommand {
    private final Random random = new Random();
    private boolean flag;
    private boolean change_flag;
    private boolean delete;
    private static final String[][] color = {
            {"red", "빨강", "16711680", "(#FF0000)"},         //0
            {"orange", "주황", "16753920", "(#FFA500)"},      //1
            {"yellow", "노랑", "16770560", "(FFE600)"},       //2
            {"gold", "황금", "16766720", "(#FFD700)"},        //3
            {"light green", "연두", "9109386", "(#8AFF8A)"},  //4
            {"green", "녹색", "7259754", "(#6EC66A)"},        //5
            {"light blue", "하늘", "4638975", "(#46C8FF)"},   //6
            {"blue","파랑", "4934655", "(#4B4BFF)"},          //7
            {"purple","보라", "11141290", "(#AA00AA)"},       //8
            {"wood","갈색", "10320982", "(#9D7C56)"},         //9
            {"pink","분홍", "15401193", "(#EB00E9)"},         //10
            {"sliver","은색", "12632256", "(#C0C0C0)"},       //11
            {"gray","회색", "8421504", "(#808080)"},          //12
            {"black","검정", "1", "(#000001)"},               //13
            {"white","하양", "16777215", "(#FFFFFF)"},       //14
            {"delete","삭제", "", "(색 삭제)"},                //15
            {"random","랜덤", "", "(색 랜덤)"}                 //16
    };

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        new Thread(() -> {
            delete = false;
            flag = false;
            setChange_flag(false);
            String joined = String.join(" ", args);
            int joined_int = 0;
            TextChannel channel = event.getChannel();
            for(int i = 0; i < 15; i++) {
                if (joined.equals(color[i][0]) || joined.equals(color[i][1])) {
                    joined_int = Integer.parseInt(color[i][2]);
                    flag = true;
                }
            }
            if(joined.equals(color[15][0]) || joined.equals(color[15][1])) {
                delete = true;
                flag = true;
            } else if(joined.equals(color[16][0]) || joined.equals(color[16][1])) {
                while(true) {
                    int temp = random.nextInt();
                    if ((temp > 0) && (temp < 16777216)) {
                        joined_int = temp;
                        flag = true;
                        break;
                    }
                }
            }
            if(!flag) {
                try {
                    if(joined.substring(0,1).equals("#")) {
                        joined_int = Integer.parseInt(joined.substring(1,7), 16);
                        if(joined_int > 16777215) {
                            channel.sendMessage("#FFFFFF보다 작게 입력해야 합니다.\n" +
                                    "현재 입력값 : #" + joined).queue();

                            return;
                        }
                    } else {
                        joined_int = Integer.parseInt(joined, 10);
                        if(joined_int > 16777215) {
                            channel.sendMessage("16777215보다 작은 숫자로 입력해야 합니다.\n" +
                                    "현재 입력값 : " + joined_int).queue();

                            return;
                        }
                    }
                } catch (Exception e) {
                    channel.sendMessage("지정된 색깔 또는 숫자를 제대로 입력해주세요.").queue();

                    return;
                }
            }
            Role[] set_Color = new Role[]{
                    event.getGuild().getRoleById("539470397846978575"),
                    event.getGuild().getRoleById("515745144616517652"),
                    event.getGuild().getRoleById("515745033257746434"),
                    event.getGuild().getRoleById("515744971597545472"),
                    event.getGuild().getRoleById("515744849966792719"),
                    event.getGuild().getRoleById("517842991667216395"),
                    //위는 CGC서버 아래는 Green 서버
                    event.getGuild().getRoleById("600011020836143115"),     //O5평의회
                    event.getGuild().getRoleById("600012814467465236"),     //디스코드담당
                    event.getGuild().getRoleById("600012855407935520"),     //시스템담당
                    event.getGuild().getRoleById("600012769596670011"),     //민원담당
                    event.getGuild().getRoleById("600012069559074822"),     //기지이사관
                    event.getGuild().getRoleById("600012538817806346"),     //후원자
                    event.getGuild().getRoleById("600021907374342156"),     //니트로 부스팅 지원자
                    event.getGuild().getRoleById("618750638699839498")      //정기후원자
            };
            List<Role> role_List = event.getMember().getRoles();
            for (Role value : set_Color) {
                if (role_List.contains(value)) {
                    setChange_flag(true);
                }
            }
            if(event.getGuild().getId().equals("617222347425972234")) {
                setChange_flag(true);
            }
            if(event.getGuild().getId().equals("617757206929997895")) {
                setChange_flag(true);
            }
            if (isChange_flag() || delete) {
                StringBuilder temp = null;
                if(!delete) {
                    temp = new StringBuilder(Integer.toHexString(joined_int));
                    for (; temp.length() <= 5; ) {
                        temp.insert(0, "0");
                    }
                }
                channel.sendMessage(event.getMember().getAsMention() + ", 기존 색을 검색중입니다....").queue();

                List<Role> roleList = FinderUtil.findRoles("#", event.getGuild());
                for (Role role : roleList) {
                    if (event.getMember().getRoles().contains(role)) {
                        try {
                            event.getGuild().getController().removeRolesFromMember(event.getMember(), role).queue();
                        } catch (Exception e) {
                            e.printStackTrace();
                            channel.sendMessage(event.getMember().getAsMention() + ", 기존 색을 제거하다가 오류가 발생했습니다.").complete();
                            break;
                        }
                        channel.sendMessage(event.getMember().getAsMention() + ", 기존 색 " + role.getName() + " 이 제거되었습니다.\n").queue();

                        break;
                    }
                }
                if(delete) {

                    return;
                }

                assert temp != null;
                if (event.getGuild().getRolesByName("#" + temp.toString(), true).toString().equals("[]")) {
                    Role rolea = event.getGuild().getController().createRole()
                            .setColor(joined_int)
                            .setName("#" + temp.toString())
                            .complete();
                    int i = 0;
                    while(true) {
                        try {
                            i++;
                            event.getGuild().getController().modifyRolePositions().selectPosition(rolea).moveUp(i).queue();
                        } catch (Exception e) {
                            break;
                        }
                    }
                }
                Role final_Role = null;
                try {
                    for (int j = 0; j < 250; j++) {
                        Role for_role = event.getGuild().getRolesByName("#" + temp.toString(), true).get(j);
                        if (!event.getMember().getRoles().contains(for_role)) {
                            final_Role = event.getGuild().getRolesByName("#" + temp.toString(), true).get(j);
                        }
                    }
                } catch (Exception ignored) {

                }
                try {
                    event.getGuild().getController().addRolesToMember(event.getMember(), final_Role).queue();

                    channel.sendMessage(event.getMember().getAsMention() + ", #" + temp.toString() + "로 색깔이 지정되었습니다.").queue();
                } catch (HierarchyException e1) {
                channel.sendMessage("Error: 봇 권한 < 역할 권한").queue();
                } catch (Exception e) {
                    channel.sendMessage(event.getMember().getAsMention() + ", 색 지정중 오류가 발생했습니다. 다시 명령어를 입력해주세요.").queue();
                    if (final_Role == null) {
                        channel.sendMessage("Error: role == null").queue();
                    }
                }
            } else {
                channel.sendMessage(event.getMember().getAsMention() + ", 당신은 명령어를 쓸 수 있는 역할이 아닙니다.").queue();
            }
        }).start();
    }

    @Override
    public String getHelp() {
        return ("닉네임의 색깔을 변경합니다.\n" +
                "사용법: `" + App.getPREFIX() + getInvoke() + "[RGB코드(#hex/숫자)/색깔명]`" +
                "색깔명: `" + App.getPREFIX() + getInvoke() + "정보`");
    }

    @Override
    public String getInvoke() {
        return "색";
    }

    public static String[][] getColor() {
        return color;
    }

    @Override
    public String getSmallHelp() {
        return "닉네임 색을 적용합니다.";
    }

    private boolean isChange_flag() {
        return change_flag;
    }

    private void setChange_flag(boolean change_flag) {
        this.change_flag = change_flag;
    }
}
