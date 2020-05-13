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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class configCommand implements ICommand {
    public static class ConfigData {
        public boolean colorCommand;
        public boolean roleColorCommand;
        public boolean filter;
        public boolean linkFilter;
        public boolean executionCommand;
        public boolean lewdCommand;
        public boolean enableChatLog;
        public String chatLogChannelId;
        public boolean enableChannelLog;
        public String channelLogChannelId;
        public boolean enableMemberLog;
        public String memberLogChannelId;
        public boolean enableNotice;
        public String noticeChannelId;
        public boolean enableFilterLog;
        public String filterChannelId;
        public boolean enableBotChannel;
        public String botChannelId;
        public boolean enableCustomFilter;
        public List<String> customFilterList;
    }

    @Override
    public void handle(@NotNull List<String> args, @NotNull GuildMessageReceivedEvent event) {
        if (!Objects.requireNonNull(event.getMember()).hasPermission(Permission.ADMINISTRATOR)) {
            if (!event.getAuthor().getId().equals(Listener.getID1())) {
                return;
            }
        }
        String guildId = event.getGuild().getId();
        ConfigData configData = SQL.configDownLoad(guildId);
        EmbedBuilder builder = null;
        if (args.isEmpty()) {
            builder = EmbedUtils.defaultEmbed()
                    .setTitle("설정 도움말")
                    .addField("stat(-s)", "서버의 설정 상태를 불러옵니다.", false)
                    .addField("guildcolor(-g)", "색 커맨드를 설정합니다.(서버 전체)", false)
                    .addField("guildcolorrole(-gr)", "색 커맨드를 설정합니다.(특정 역할)", false)
                    .addField("filter(-f)", "욕설 필터링을 설정합니다.", false)
                    .addField("linkfilter(-lf)", "링크 필터링을 설정합니다.", false)
                    .addField("killfilter(-kf)", "공개처형을 설정합니다.", false)
                    .addField("lewdneko(-ln)", "후방주의 컨텐츠를 설정합니다.", false)
                    .addField("chatlog(-cl)", "채팅 관련 로그를 설정합니다.", false)
                    .addField("channellog(-chl)", "채널 관련 로그를 설정합니다.", false)
                    .addField("memberlog(-ml)", "멤버 관련 로그를 설정합니다.", false)
                    .addField("notice(-n)", "공지를 받을 채널을 설정합니다.", false)
                    .addField("filterlog(-fl)", "필터링 검출 항목/제재자 로그를 설정합니다.", false)
                    .addField("botchannel(-bc)", "봇 채널 로깅을 설정합니다.", false)
                    .addField("customfilter(-cf)", "서버별로 커스텀 필터를 설정합니다", false)
                    .setDescription(App.getPREFIX() + getInvoke() + " " + "옵션" + " 활성화/비활성화\n" +
                            "예: " + App.getPREFIX() + getInvoke() + " " + "-filter" + " 활성화");
        } else {
            switch (args.get(0)) {
                case "stat":
                case "-s":
                    builder = EmbedUtils.defaultEmbed()
                            .setTitle(event.getGuild().getName() + "서버의 설정")
                            .addField("색 커맨드", configData.colorCommand ? "활성화" : "비활성화", false)
                            .addField("역할 색 커맨드", configData.roleColorCommand ? "있음" : "없음", false)
                            .addField("욕설 필터", configData.filter ? "활성화" : "비활성화", false)
                            .addField("링크 차단", configData.linkFilter ? "활성화" : "비활성화", false)
                            .addField("공개 처형", configData.executionCommand ? "활성화" : "비활성화", false)
                            .addField("후방주의네코 커맨드", configData.lewdCommand ? "활성화" : "비활성화", false)
                            .addField("채팅 로그", configData.enableChatLog ? configData.channelLogChannelId : "비활성화", false)
                            .addField("채널 로그", configData.enableChannelLog ? configData.channelLogChannelId : "비활성화", false)
                            .addField("멤버 로그", configData.enableMemberLog ? configData.memberLogChannelId : "비활성화", false)
                            .addField("공지 채널 설정", configData.enableNotice ? configData.noticeChannelId : "없음", false)
                            .addField("필터링 / 제재 로그", configData.enableFilterLog ? configData.filterChannelId : "없음", false)
                            .addField("봇 채널", configData.enableBotChannel ? configData.botChannelId : "없음", false)
                            .addField("멤버 로그 채널", configData.enableMemberLog ? configData.memberLogChannelId : "없음", false)
                            .addField("커스텀 필터", configData.enableCustomFilter ? "활성화" : "비활성화", false);
                    break;
                case "guildcolor":
                case "-g":
                    if (args.size() >= 2) {
                        if (args.get(1).equals("활성화")) {
                            SQL.configSetup(guildId, SQL.color_guild, "0");
                        } else if (args.get(1).equals("비활성화")) {
                            SQL.configSetup(guildId, SQL.color_guild, "1");
                        } else {
                            event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                                    "현재 입력한 옵션값: " + args.get(1)).queue();
                        }
                    }
                    break;
                case "filter":
                case "-f":
                    if (args.size() >= 2) {
                        if (args.get(1).equals("활성화")) {
                            SQL.configSetup(guildId, SQL.filter, "0");
                        } else if (args.get(1).equals("비활성화")) {
                            SQL.configSetup(guildId, SQL.filter, "1");
                        } else {
                            event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                                    "현재 입력한 옵션값: " + args.get(1)).queue();
                        }
                    }
                    break;
                case "linkfilter":
                case "-lf":
                    if (args.size() >= 2) {
                        if (args.get(1).equals("활성화")) {
                            SQL.configSetup(guildId, SQL.link_filter, "0");
                        } else if (args.get(1).equals("비활성화")) {
                            SQL.configSetup(guildId, SQL.link_filter, "1");
                        } else {
                            event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                                    "현재 입력한 옵션값: " + args.get(1)).queue();
                        }
                    }
                    break;
                case "killfilter":
                case "-kf":
                    if (args.size() >= 2) {
                        if (args.get(1).equals("활성화")) {
                            SQL.configSetup(guildId, SQL.kill_filter, "0");
                        } else if (args.get(1).equals("비활성화")) {
                            SQL.configSetup(guildId, SQL.kill_filter, "1");
                        } else {
                            event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                                    "현재 입력한 옵션값: " + args.get(1)).queue();
                        }
                    }
                    break;
                case "lewdneko":
                case "-ln":
                    if (args.size() >= 2) {
                        if (args.get(1).equals("활성화")) {
                            SQL.configSetup(guildId, SQL.lewdneko, "0");
                        } else if (args.get(1).equals("비활성화")) {
                            SQL.configSetup(guildId, SQL.lewdneko, "1");
                        } else {
                            event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                                    "현재 입력한 옵션값: " + args.get(1)).queue();
                        }
                    }
                    break;
                case "guildcolorrole":
                case "-gr":
                    if (args.size() >= 2) {
                        if (args.get(1).equals("비활성화")) {
                            SQL.configSetup(guildId, SQL.color_role, "1");
                        } else {
                            if (args.size() >= 3) {
                                if (args.get(1).equals("추가")) {
                                    SQL.configSetup(guildId, args.get(2), true);
                                } else if (args.get(1).equals("삭제")) {
                                    SQL.configSetup(guildId, args.get(2), false);
                                }
                            }
                        }
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        String[] data = SQL.configDownLoad_array(guildId, SQL.color_role);
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
                    break;
                case "chatlog":
                case "-cl":
                    if (args.size() >= 2) {
                        switch (args.get(1)) {
                            case "활성화":
                                SQL.configSetup(guildId, SQL.textLogging, "0");
                                break;
                            case "비활성화":
                                SQL.configSetup(guildId, SQL.textLogging, "1");
                                break;
                            case "채널":
                                String channelId;
                                try {
                                    channelId = args.get(2);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    event.getChannel().sendMessage("채널 ID가 없습니다.").queue();
                                    return;
                                }
                                SQL.configSetup(guildId, "", channelId, SQL.textLogging);
                                break;
                            default:
                                event.getChannel().sendMessage("활성화/비활성화/채널 옵션을 입력해주세요 \n" +
                                        "현재 입력한 옵션값: " + args.get(1)).queue();
                                break;
                        }
                    }
                    break;
                case "channellog":
                case "-chl":
                    if (args.size() >= 2) {
                        switch (args.get(1)) {
                            case "활성화":
                                SQL.configSetup(guildId, SQL.channelLogging, "0");
                                break;
                            case "비활성화":
                                SQL.configSetup(guildId, SQL.channelLogging, "1");
                                break;
                            case "채널":
                                String channelId;
                                try {
                                    channelId = args.get(2);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    event.getChannel().sendMessage("채널 ID가 없습니다.").queue();
                                    return;
                                }
                                SQL.configSetup(guildId, "", channelId, SQL.channelLogging);
                                break;
                            default:
                                event.getChannel().sendMessage("활성화/비활성화/채널 옵션을 입력해주세요 \n" +
                                        "현재 입력한 옵션값: " + args.get(1)).queue();
                                break;
                        }
                    }
                    break;
                case "memberlog":
                case "-ml":
                    if (args.size() >= 2) {
                        switch (args.get(1)) {
                            case "활성화":
                                SQL.configSetup(guildId, SQL.memberLogging, "0");
                                break;
                            case "비활성화":
                                SQL.configSetup(guildId, SQL.memberLogging, "1");
                                break;
                            case "채널":
                                String channelId;
                                try {
                                    channelId = args.get(2);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    event.getChannel().sendMessage("채널 ID가 없습니다.").queue();
                                    return;
                                }
                                SQL.configSetup(guildId, "", channelId, SQL.memberLogging);
                                break;
                            default:
                                event.getChannel().sendMessage("활성화/비활성화/채널 옵션을 입력해주세요 \n" +
                                        "현재 입력한 옵션값: " + args.get(1)).queue();
                                break;
                        }
                    }
                    break;
                case "notice":
                case "-n":
                    if (args.size() >= 2) {
                        if (args.get(1).equals("활성화")) {
                            SQL.configSetup(guildId, "0", args.get(2), SQL.notice);
                        } else if (args.get(1).equals("비활성화")) {
                            SQL.configSetup(guildId, SQL.notice, "1");
                        } else {
                            event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                                    "현재 입력한 옵션값: " + args.get(1)).queue();
                        }
                    } else {
                        String channelId = SQL.configDownLoad(guildId, SQL.notice);
                        if (channelId == null) {
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
                    break;
                case "filterlog":
                case "-fl":
                    if (args.size() >= 2) {
                        if (args.get(1).equals("활성화")) {
                            SQL.configSetup(guildId, "0", args.get(2), SQL.filterlog);
                        } else if (args.get(1).equals("비활성화")) {
                            SQL.configSetup(guildId, SQL.filterlog, "1");
                        } else {
                            event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                                    "현재 입력한 옵션값: " + args.get(1)).queue();
                        }
                    } else {
                        String channelId = SQL.configDownLoad(guildId, SQL.filterlog);
                        if (channelId == null) {
                            channelId = "비활성화";
                        }
                        builder = EmbedUtils.defaultEmbed()
                                .setTitle("필터링 로그 안내")
                                .setDescription("사용법:" + App.getPREFIX() + getInvoke() + " -filterlog 비활성화/활성화 <channelId>")
                                .addField("비활성화", "필터링 로그를 비활성화 합니다.", false)
                                .addField("활성화", "필터링 로그를 활성화 합니다.", false)
                                .addField("현재 등록된 채널 ID", channelId, false)
                                .setFooter("필터링 로그 채널은 한번 등록하면 삭제할 수 없습니다. 비활성화만 가능합니다.");
                    }
                    break;
                case "botchannel":
                case "-bc":
                    if (args.size() >= 2) {
                        if (args.get(1).equals("활성화")) {
                            SQL.configSetup(guildId, "0", args.get(2), SQL.botchannel);
                        } else if (args.get(1).equals("비활성화")) {
                            SQL.configSetup(guildId, SQL.notice, "1");
                        } else {
                            event.getChannel().sendMessage("활성화 또는 비활성화 옵션을 입력해주세요 \n" +
                                    "현재 입력한 옵션값: " + args.get(1)).queue();
                        }
                    } else {
                        String channelId = SQL.configDownLoad(guildId, SQL.botchannel);
                        if (channelId == null) {
                            channelId = "비활성화";
                        }
                        builder = EmbedUtils.defaultEmbed()
                                .setTitle("봇채널 안내")
                                .setDescription("사용법:" + App.getPREFIX() + getInvoke() + " botchannel 비활성화/활성화 <channelId>")
                                .addField("비활성화", "봇채널 설정을 비활성화 합니다.", false)
                                .addField("활성화", "봇채널을 활성화 합니다.", false)
                                .addField("현재 등록된 채널 ID", channelId, false)
                                .setFooter("봇 채널은 한번 등록하면 삭제할 수 없습니다. 비활성화만 가능합니다.");
                    }
                    break;
                case "customfilter":
                case "-cf":
                    if (args.size() >= 2) {
                        switch (args.get(1)) {
                            case "활성화":
                                SQL.configSetup(guildId, "0", "", SQL.customFilter);
                                break;
                            case "비활성화":
                                SQL.configSetup(guildId, "1", "", SQL.customFilter);
                                break;
                            case "추가":
                                String[] a = SQL.configDownLoad_array(guildId, SQL.customFilter);
                                List<String> list = Arrays.asList(a);
                                list = new ArrayList<>(list);
                                list.remove("none");
                                for (int i = 2; i < args.size(); i++) {
                                    String da = args.get(i).replace(",", "");
                                    System.out.println(list.add(da));
                                }
                                if (list.isEmpty()) {
                                    event.getChannel().sendMessage("추가할 단어가 없었습니다.").queue();
                                    return;
                                }
                                list.remove("none");
                                SQL.configSetup(guildId, list);
                                break;
                            case "제거":
                                a = SQL.configDownLoad_array(guildId, SQL.customFilter);
                                list = Arrays.asList(a);
                                list = new ArrayList<>(list);
                                for (int i = 2; i < args.size(); i++) {
                                    list.remove(args.get(i).replace(",", ""));
                                }
                                if (list.isEmpty()) {
                                    event.getChannel().sendMessage("제거할 단어가 없었습니다.").queue();
                                    return;
                                }
                                list.remove("none");
                                SQL.configSetup(guildId, list);
                                break;
                            case "조회":
                                String[] data = SQL.configDownLoad_array(guildId, SQL.customFilter);
                                StringBuilder stringBuilder = new StringBuilder();
                                for (String c : data) {
                                    stringBuilder.append(c).append(", ");
                                }
                                builder = EmbedUtils.defaultEmbed()
                                        .setTitle("커스텀 필터링 단어 목록")
                                        .setDescription(stringBuilder.toString().substring(0, stringBuilder.toString().length() - 2));
                                break;
                            default:
                                builder = EmbedUtils.defaultEmbed()
                                        .setTitle("커스텀 필터링 안내")
                                        .setDescription("사용법: " + App.getPREFIX() + getInvoke() + " customfilter 활성화/비활성화\n" +
                                                App.getPREFIX() + getInvoke() + " customfilter 추가/삭제 단어")
                                        .addField("활성화", "커스텀 필터링을 활성화 합니다.", false)
                                        .addField("비활성화", "커스텀 필터링을 비활성화 합니다", false)
                                        .addField("조회", "커스텀 필터링 단어 목록을 조회합니다.", false)
                                        .addField("추가 <단어1 단어2 단어3...>", "커스텀 필터링 단어 목록을 추가합니다", false)
                                        .addField("삭제 <단어1 단어2 단어3...>", "커스텀 필터링 단어 목록을 삭제합니다", false);
                                break;
                        }
                    } else {
                        builder = EmbedUtils.defaultEmbed()
                                .setTitle("커스텀 필터링 안내")
                                .setDescription("사용법: " + App.getPREFIX() + getInvoke() + " customfilter 활성화/비활성화\n" +
                                        App.getPREFIX() + getInvoke() + " customfilter 추가/삭제 단어")
                                .addField("활성화", "커스텀 필터링을 활성화 합니다.", false)
                                .addField("비활성화", "커스텀 필터링을 비활성화 합니다", false)
                                .addField("추가 <단어1 단어2 단어3...>", "커스텀 필터링 단어 목록을 추가합니다", false)
                                .addField("삭제 <단어1 단어2 단어3...>", "커스텀 필터링 단어 목록을 삭제합니다", false);

                    }
                    break;
            }
        }
        if(builder != null) {
            event.getChannel().sendMessage(builder.build()).queue();
        } else {
            event.getChannel().sendMessage("설정이 완료되었습니다.").queue();
        }
        config.config_load();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "봇의 서버별 설정을 변경합니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "설정";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
