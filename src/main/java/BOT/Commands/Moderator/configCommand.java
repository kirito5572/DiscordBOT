package BOT.Commands.Moderator;

import BOT.App;
import BOT.Listener.Listener;
import BOT.Objects.ICommand;
import BOT.Objects.SQL;
import BOT.Objects.config;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class configCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if(!Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
            if(!event.getAuthor().getId().equals(Listener.getID1())) {
                return;
            }
        }

        String guildId = event.getGuild().getId();
        String[] data = SQL.configDownLoad(guildId);
        System.out.println(Arrays.toString(data));
        EmbedBuilder builder = null;
        if (args.isEmpty()) {
            builder = EmbedUtils.defaultEmbed()
                    .setTitle("설정 도움말")
                    .addField("-stat", "서버의 설정 상태를 불러옵니다.", false)
                    .addField("-guildcolor", "색 커맨드를 설정합니다.(서버 전체)", false)
                    .addField("-guildcolorrole", "색 커맨드를 설정합니다.(특정 역할)", false)
                    .addField("-filter", "욕설 필터링을 설정합니다.", false)
                    .addField("-linkfilter", "링크 필터링을 설정합니다.", false)
                    .addField("-killfilter", "공개처형을 설정합니다.", false)
                    .addField("-lewdneko", "후방주의 컨텐츠를 설정합니다.", false)
                    .addField("-chatlog", "채팅 로그를 설정합니다.", false)
                    .addField("-channellog", "채널 설정 로그를 설정합니다.", false)
                    .addField("-notice", "공지를 받을 채널을 설정합니다.", false)
                    .setDescription(App.getPREFIX() + getInvoke() + " " + "옵션" + " 활성화/비활성화\n" +
                            "예: " + App.getPREFIX() + getInvoke() + " " + "-filter" + " 활성화");
        } else if(args.get(0).equals("-stat")) {
            builder = EmbedUtils.defaultEmbed()
                    .setTitle(event.getGuild().getName() + "서버의 설정")
                    .addField("색 커맨드", data[0].equals("0") ? "활성화" : "비활성화", false)
                    .addField("역할 색 커맨드", data[7].equals("0")? "있음" : "없음", false)
                    .addField("욕설 필터", data[1].equals("0") ? "활성화" : "비활성화", false)
                    .addField("링크 차단", data[2].equals("0") ? "활성화" : "비활성화", false)
                    .addField("공개 처형", data[3].equals("0") ? "활성화" : "비활성화", false)
                    .addField("후방주의네코 커맨드", data[4].equals("0") ? "활성화" : "비활성화", false)
                    .addField("채팅 로그", data[5].equals("1") ? "활성화" : "비활성화", false)
                    .addField("채널 설정 로그", data[6].equals("1") ? "활성화" : "비활성화", false)
                    .addField("공지 채널 설정", data[8].equals("0") ? data[9] :"없음", false);
        } else if(args.get(0).equals("-guildcolor")) {
            if(args.size() >= 2) {
                if (args.get(1).equals("활성화")) {
                    SQL.configSetup(guildId, SQL.color_guild, "0");
                } else if(args.get(1).equals("비활성화")) {
                    SQL.configSetup(guildId, SQL.color_guild, "1");
                } else {
                    event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                            "현재 입력한 옵션값: " + args.get(1)).queue();
                }
            }
        } else if(args.get(0).equals("-filter")) {
            if(args.size() >= 2) {
                if (args.get(1).equals("활성화")) {
                    SQL.configSetup(guildId, SQL.filter, "0");
                } else if(args.get(1).equals("비활성화")) {
                    SQL.configSetup(guildId, SQL.filter, "1");
                } else {
                    event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                            "현재 입력한 옵션값: " + args.get(1)).queue();
                }
            }
        } else if(args.get(0).equals("-linkfilter")) {
            if(args.size() >= 2) {
                if (args.get(1).equals("활성화")) {
                    SQL.configSetup(guildId, SQL.link_filter, "0");
                } else if(args.get(1).equals("비활성화")) {
                    SQL.configSetup(guildId, SQL.link_filter, "1");
                } else {
                    event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                            "현재 입력한 옵션값: " + args.get(1)).queue();
                }
            }
        } else if(args.get(0).equals("-killfilter")) {
            if(args.size() >= 2) {
                if (args.get(1).equals("활성화")) {
                    SQL.configSetup(guildId, SQL.kill_filter, "0");
                } else if(args.get(1).equals("비활성화")) {
                    SQL.configSetup(guildId, SQL.kill_filter, "1");
                } else {
                    event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                            "현재 입력한 옵션값: " + args.get(1)).queue();
                }
            }
        } else if(args.get(0).equals("-lewdneko")) {
            if(args.size() >= 2) {
                if (args.get(1).equals("활성화")) {
                    SQL.configSetup(guildId, SQL.lewdneko, "0");
                } else if(args.get(1).equals("비활성화")) {
                    SQL.configSetup(guildId, SQL.lewdneko, "1");
                } else {
                    event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                            "현재 입력한 옵션값: " + args.get(1)).queue();
                }
            }
        } else if(args.get(0).equals("-guildcolorrole")) {
            if(args.size() >= 2) {
                if(args.get(1).equals("비활성화")) {
                    SQL.configSetup(guildId, SQL.color_role, "1");
                } else {
                    if(args.size() >= 3) {
                        if(args.get(1).equals("추가")) {
                            SQL.configSetup(guildId, "0", args.get(2), true);
                        } else if(args.get(1).equals("삭제")) {
                            SQL.configSetup(guildId, "0", args.get(2), false);
                        }
                    }
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                data = SQL.configDownLoad_role(guildId);
                for (String roleId : data) {
                    Role role = event.getGuild().getRoleById(roleId);
                    if (role != null) {
                        stringBuilder.append(role.getName()).append("(").append(roleId).append(")").append("\n");
                    }
                }
                builder = EmbedUtils.defaultEmbed()
                        .setTitle("역할 색 지정 안내")
                        .setDescription("사용법:" + App.getPREFIX() + getInvoke() + " -guildcolorrole 비활성화/추가/삭제 <roleId>")
                        .addField("비활성화", "색 명령어 사용이 가능한 역할을 초기화 합니다.", false)
                        .addField("추가", "색 명령어 사용이 가능한 역할을 추가합니다.", false)
                        .addField("삭제", "색 명령어 사용이 가능한 역할을 제거합니다.", false)
                        .addField("현재 등록된 색 명령어 사용 가능한 역할", stringBuilder.toString(), false);
            }
        } else if(args.get(0).equals("-chatlog")) {
            if (args.size() >= 2) {
                if (args.get(1).equals("활성화")) {
                    SQL.configSetup(guildId, SQL.textLogging, "0");
                } else if (args.get(1).equals("비활성화")) {
                    SQL.configSetup(guildId, SQL.textLogging, "1");
                } else {
                    event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                            "현재 입력한 옵션값: " + args.get(1)).queue();
                }
            }
        } else if(args.get(0).equals("-channellog")) {
            if (args.size() >= 2) {
                if (args.get(1).equals("활성화")) {
                    SQL.configSetup(guildId, SQL.channelLogging, "0");
                } else if (args.get(1).equals("비활성화")) {
                    SQL.configSetup(guildId, SQL.channelLogging, "1");
                } else {
                    event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                            "현재 입력한 옵션값: " + args.get(1)).queue();
                }
            }
        } else if(args.get(0).equals("-notice")) {
            if (args.size() >= 2) {
                if (args.get(1).equals("활성화")) {
                    SQL.configSetup(guildId, "0", args.get(2));
                } else if (args.get(1).equals("비활성화")) {
                    SQL.configSetup(guildId, SQL.notice, "1");
                } else {
                    event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                            "현재 입력한 옵션값: " + args.get(1)).queue();
                }
            } else {
                String channelId = SQL.configDownLoad_notice(guildId);
                if(channelId == null) {
                    channelId = "비활성화";
                }
                builder = EmbedUtils.defaultEmbed()
                        .setTitle("공지사항 안내")
                        .setDescription("사용법:" + App.getPREFIX() + getInvoke() + " -notice 비활성화/활성화 <channelId>")
                        .addField("비활성화", "공지사항 수신을 비활성화 합니다.", false)
                        .addField("활성화", "공지사항 수신을 활성화 합니다.", false)
                        .addField("현재 등록된 채널 ID", channelId, false)
                        .setFooter("공지사항 채널은 한번 등록하면 삭제할 수 없습니다. 비활성화만 가능합니다.");
            }
        }
        if(builder != null) {
            event.getChannel().sendMessage(builder.build()).queue();
        }
        config.config_load();
    }

    @Override
    public String getHelp() {
        return "봇의 서버별 설정을 변경합니다.";
    }

    @Override
    public String getInvoke() {
        return "설정";
    }

    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
