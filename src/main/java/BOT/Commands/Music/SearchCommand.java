package BOT.Commands.Music;

import BOT.App;
import BOT.Music.PlayerManager;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SearchCommand implements ICommand {
    private final Logger logger = LoggerFactory.getLogger(SearchCommand.class);
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        new Thread(() -> {
            AudioManager audioManager = event.getGuild().getAudioManager();
            TextChannel channel = event.getChannel();
            GuildVoiceState memberVoiceState = Objects.requireNonNull(event.getMember()).getVoiceState();
            assert memberVoiceState != null;
            VoiceChannel voiceChannel = memberVoiceState.getChannel();
            if(!audioManager.isConnected()) {
                Member selfMember = event.getGuild().getSelfMember();

                assert voiceChannel != null;
                if(!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
                    channel.sendMessageFormat("%s 보이스 채널에 들어올 권한이 없습니다.",voiceChannel).queue();
                    return;
                }
            }
            if(!memberVoiceState.inVoiceChannel()) {
                channel.sendMessage("먼저 보이스 채널에 들어오세요").queue();
                return;
            }

            StringBuilder youtube_Key = new StringBuilder();
            try {
                File file = new File("C:\\DiscordServerBotSecrets\\rito-bot\\YOUTUBE_DATA_API_KEY.txt");
                FileReader fileReader = new FileReader(file);
                int singalCh;
                while((singalCh = fileReader.read()) != -1) {
                    youtube_Key.append((char) singalCh);
                }
            } catch (Exception e) {

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            }
            try {
                String name = String.join("+", args);
                String apiurl = "https://www.googleapis.com/youtube/v3/search";
                apiurl += "?key=" + youtube_Key.toString();
                apiurl += "&part=snippet&type=video&maxResults=1&videoEmbeddable=true";
                apiurl += "&q=" + URLEncoder.encode(name, "UTF-8");

                URL url = new URL(apiurl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                JSONParser parser = new JSONParser();
                JSONObject youtube_api_return;

                youtube_api_return = (JSONObject) parser.parse(response.toString());
                JSONArray parse_main = (JSONArray) youtube_api_return.get("items");
                JSONObject parse_First;
                String temp_parse_main = parse_main.toString().substring(1,parse_main.toString().lastIndexOf("]"));

                parse_First = (JSONObject) parser.parse(temp_parse_main);

                JSONObject first_ID = (JSONObject) parse_First.get("id");
                JSONObject temp_first_Title = (JSONObject) parser.parse(parse_First.toString());
                temp_first_Title = (JSONObject) temp_first_Title.get("snippet");
                temp_first_Title = (JSONObject) parser.parse(temp_first_Title.toString());
                String first_Title = temp_first_Title.toString().substring(temp_first_Title.toString().indexOf("\",\"title\":\"") + 11,temp_first_Title.toString().indexOf("\",\"thumbnails\":{"));
                String first_url = first_ID.toString().substring(first_ID.toString().indexOf("\"videoId\":\"") + 11, first_ID.toString().indexOf("\"}"));

                Message message = channel.sendMessage( first_Title + "\n" + "https://youtu.be/" + first_url + "\n" +
                        "재생을 원하시면 :one: 아니면 :two:").complete();


                message.addReaction("1\u20E3").queue();
                message.addReaction("2\u20E3").queue();
                Thread.sleep(500);

                for(int i = 0; i < 11; i++) {
                    Thread.sleep(1000);
                    System.out.println(message.getReactions().get(0).getReactionEmote().getEmote());
                    if(message.getReactions().get(0).getCount() == 2) {
                        if(!audioManager.isConnected()) {
                            audioManager.openAudioConnection(voiceChannel);
                        }
                        PlayerManager manager = PlayerManager.getInstance();
                        message.delete().complete();
                        message = channel.sendMessage("노래가 추가되었습니다.").complete();
                        message.delete().queueAfter(5, TimeUnit.SECONDS);
                        manager.loadAndPlay(channel, "https://youtu.be/" + first_url);
                        return;
                    } else if(message.getReactions().get(1).getCount() == 2){
                        message.delete().complete();
                        message = event.getChannel().sendMessage("검색이 취소되었습니다.").complete();
                        message.delete().queueAfter(5, TimeUnit.SECONDS);
                        return;
                    }
                }
                message.delete().complete();
                message = channel.sendMessage("대기 시간이 초과되어 삭제되었습니다.").complete();
                message.delete().queueAfter(5,TimeUnit.SECONDS);

            } catch (UnsupportedEncodingException e) {
                Message message = channel.sendMessage("ErrorCode : 0x1402 UNSUPPORT ENCODING ERROR").complete();
                message.delete().queueAfter(7,TimeUnit.SECONDS);

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            } catch (ProtocolException e) {
                Message message = channel.sendMessage("ErrorCode : 0x9757 Protocol ERROR").complete();
                message.delete().queueAfter(7,TimeUnit.SECONDS);

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            } catch (MalformedURLException e) {
                Message message = channel.sendMessage("ErrorCode : 0x1576 URL ERROR").complete();
                message.delete().queueAfter(7,TimeUnit.SECONDS);

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            } catch (IOException e) {
                Message message = channel.sendMessage("ErrorCode : 0x3451 I/O ERROR").complete();
                message.delete().queueAfter(7,TimeUnit.SECONDS);

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            } catch (ParseException e) {
                Message message = channel.sendMessage("ErrorCode : 0x6712 PARSE ERROR").complete();
                message.delete().queueAfter(7,TimeUnit.SECONDS);

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            } catch (InterruptedException e) {
                Message message = channel.sendMessage("ErrorCode : 0x5734 THREAD ERROR").complete();
                message.delete().queueAfter(7,TimeUnit.SECONDS);

                StackTraceElement[] eStackTrace = e.getStackTrace();
                StringBuilder a = new StringBuilder();
                for (StackTraceElement stackTraceElement : eStackTrace) {
                    a.append(stackTraceElement).append("\n");
                }
                logger.warn(a.toString());
            }
        }).start();
    }

    @Override
    public String getHelp() {
        return "유튜브에서 노래를 검색합니다\n" +
                "사용법 : '" + App.getPREFIX() + getInvoke() + "'[검색할 노래]";
    }

    @Override
    public String getInvoke() {
        return "검색";
    }

    @Override
    public String getSmallHelp() {
        return "music";
    }
}
