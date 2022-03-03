package com.kirito5572.commands.main;

import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;

import javax.annotation.Nonnull;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

public class ServerInfoCommand implements ICommand {
    @Override
    public void handle(List<String> args, @Nonnull EventPackage event) {
        EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                .setTitle(event.getGuild().getName() + " 서버의 유저정보")
                .setDescription("한국 리전이 한국에서만 활성화가 되어 봇 API에 해당 정보가 없어, 리전 정보에 한국이 나오지 않습니다.")
                .addField("서버 이름", event.getGuild().getName(), false)
                .addField("서버 소유자", Objects.requireNonNull(event.getGuild().getOwner()).getAsMention(), false)
                .addField("서버 생성일", event.getGuild().getTimeCreated().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneId.systemDefault())), false)
                .addField("서버 인원수", String.valueOf(event.getGuild().getMembers().size()), false)
                .addField("채팅 채널수", String.valueOf(event.getGuild().getTextChannels().size()), false)
                .addField("보이스 채널수", String.valueOf(event.getGuild().getTextChannels().size()), false)
                .addField("서버 역할수", String.valueOf(event.getGuild().getRoles().size()), false)
                .addField("서버 부스터 수", String.valueOf(event.getGuild().getBoostCount()), false)
                .addField("서버 이모지수", String.valueOf(event.getGuild().getEmotes().size()), false)
                .addField("서버 보안 단계", event.getGuild().getRequiredMFALevel().name(), false);

        event.getChannel().sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "현재 있는 서버의 정보를 불러옵니다.";
    }

    @Override
    public String getInvoke() {
        return "서버정보";
    }

    @Override
    public String getSmallHelp() {
        return "other";
    }
}
