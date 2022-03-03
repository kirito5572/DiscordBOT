package com.kirito5572.commands.main.gamecommand;

import com.kirito5572.objects.main.EventPackage;
import com.kirito5572.objects.main.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class OddOrEvenCommand implements ICommand {
    private final Random random = new Random();
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        new Thread(() -> {
            event.getChannel().sendMessage("홀수 또는 짝수를 선택해주세요").queue();
            boolean even = random.nextBoolean();
            try {
                int i;
                for (i = 0; i < 21; i++) {
                    Thread.sleep(500);
                    try {
                        String id = event.getChannel().getLatestMessageId();
                        String message = event.getChannel().retrieveMessageById(id).complete().getContentRaw();
                        String[] result = new String[] {
                                "a", "b", "c"
                        };
                        if(message.equals("홀수")) {
                            result[2] = "ok";
                            if(even) {
                                result[0] = "홀수";
                                result[1] = "성공";
                            } else {
                                result[0] = "짝수";
                                result[1] = "실패";
                            }
                            EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                                    .setTitle("홀/짝")
                                    .addField("결과", result[1], false)
                                    .addField("추첨 결과", result[0], false)
                                    .setFooter("과도한 도박은 정신건강에 해롭습니다");
                            event.getChannel().sendMessageEmbeds(builder.build()).queue();
                            break;
                        } else if (message.equals("짝수")) {
                            result[2] = "ok";
                            if(even) {
                                result[0] = "홀수";
                                result[1] = "실패";
                            } else {
                                result[0] = "짝수";
                                result[1] = "성공";
                            }
                            EmbedBuilder builder = EmbedUtils.getDefaultEmbed()
                                    .setTitle("홀/짝")
                                    .addField("결과", result[1], false)
                                    .addField("추첨 결과", result[0], false)
                                    .setFooter("과도한 도박은 정신건강에 해롭습니다");
                            event.getChannel().sendMessageEmbeds(builder.build()).queue();
                            break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(i == 20) {
                    event.getChannel().sendMessage("10초 동안 응답이 없어 명령어 사용이 취소되었습니다.").queue();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @NotNull
    @Override
    public String getHelp() {
        return "홀짝 게임 입니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "홀짝";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "game";
    }
}
