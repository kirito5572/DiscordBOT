package BOT.Objects;

import BOT.App;
import BOT.Commands.*;
import BOT.Commands.GreenServerCustom.*;
import BOT.Commands.Moderator.*;
import BOT.Commands.Music.*;
import BOT.Commands.ONIGIRIServerCustom.ONIGIRICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;
import java.util.regex.Pattern;

public class greenCommandManager {
    private final Map<String, ICommand> commands = new HashMap<>();

    public greenCommandManager() {
        {
            addCommand(new greenHelpCommand(this));
            addCommand(new greenHelpCommand(this) {
                @Override
                public String getInvoke() {
                    return "help";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Shows a list of all the commands.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " [command]`";
                }
            });
            addCommand(new PingCommand());
            addCommand(new PingCommand() {
                @Override
                public String getInvoke() {
                    return "ping";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Pong!\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "`";
                }
            });
            addCommand(new UserInfoCommand());
            addCommand(new UserInfoCommand() {
                @Override
                public String getInvoke() {
                    return "userinfo";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Displays information about a user.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " [user name/@user/user id]`";
                }
            });
            addCommand(new ColorInfoCommand());
            addCommand(new ColorInfoCommand() {
                @Override
                public String getInvoke() {
                    return "colorinfo";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Print color that you can set in discord \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "`";
                }
            });
            addCommand(new ColorCommand());
            addCommand(new ColorCommand() {
                @Override
                public String getInvoke() {
                    return "color";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Set discord nickname color \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <colorcode/#color/intcolor> `";
                }
            });

            addCommand(new ClearCommand());
            addCommand(new ClearCommand() {
                @Override
                public String getInvoke() {
                    return "clear";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Clears the chat with the specified amount of messages.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <amount>`";
                }
            });
            addCommand(new giveroleCommand());
            addCommand(new giveroleCommand() {
                @Override
                public String getInvoke() {
                    return "giverole";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "create a role and give role to member \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <role name> <username>`\n" +
                            "option: [#color], [^permission int] [$role position] [setHoisted]";
                }
            });
            addCommand(new SayCommand());
            addCommand(new SayCommand() {
                @Override
                public String getInvoke() {
                    return "say";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "send message by bot(only text)\n " +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <Message>`";
                }
            });
            addCommand(new UnusedColorCommand());
            addCommand(new UnusedColorCommand() {
                @Override
                public String getInvoke() {
                    return "unusedcolorrole";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Delete unusedcolorrole \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke();
                }
            });
            addCommand(new JoinCommand());
            addCommand(new JoinCommand() {
                @Override
                public String getInvoke() {
                    return "join";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Makes the bot join your channel";
                }
            });
        }
        //------------------------------------------------------------------//
        {
            addCommand(new QueueDelectCommand());
            addCommand(new QueueDelectCommand() {
                @Override
                public String getInvoke() {
                    return "queuedelect";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Clear Queqe";
                }
            });
            addCommand(new QueueDelectCommand() {
                @Override
                public String getInvoke() {
                    return "qd";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Clear Queue";
                }
            });
            addCommand(new QueueCommand());
            addCommand(new QueueCommand() {
                @Override
                public String getInvoke() {
                    return "queue";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Shows the current queue for the music player";
                }
            });
            addCommand(new StopClearCommand());
            addCommand(new StopClearCommand() {
                @Override
                public String getInvoke() {
                    return "stopclear";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Stop music and clear queue";
                }
            });
            addCommand(new StopClearCommand() {
                @Override
                public String getInvoke() {
                    return "sc";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Stop music and clear queue";
                }
            });
            addCommand(new PlayCommand());
            addCommand(new PlayCommand() {
                @Override
                public String getInvoke() {
                    return "play";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Plays a song\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <song url>`";
                }
            });
            addCommand(new PlayCommand() {
                @Override
                public String getInvoke() {
                    return "p";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Plays a song\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <song url>`";
                }
            });
            addCommand(new leaveCommand());
            addCommand(new leaveCommand() {
                @Override
                public String getInvoke() {
                    return "leave";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Makes the bot leave your channel";
                }
            });

            addCommand(new NowPlayingCommand());
            addCommand(new NowPlayingCommand() {
                @Override
                public String getInvoke() {
                    return "nowplaying";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Shows the currently playing track";
                }
            });
            addCommand(new NowPlayingCommand() {
                @Override
                public String getInvoke() {
                    return "np";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Shows the currently playing track";
                }
            });
            addCommand(new SkipCommand());
            addCommand(new SkipCommand() {
                @Override
                public String getInvoke() {
                    return "skip";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Skips the current song";
                }
            });
            addCommand(new StopCommand());
            addCommand(new StopCommand() {
                @Override
                public String getInvoke() {
                    return "stop";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Stops the music player";
                }
            });
            addCommand(new VolumeCommand());
            addCommand(new VolumeCommand() {
                @Override
                public String getInvoke() {
                    return "volume";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Set music player's volume\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <Volume> `";
                }
            });
            addCommand(new VolumeCommand() {
                @Override
                public String getInvoke() {
                    return "vol";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Set music player's volume\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <Volume> `";
                }
            });
            addCommand(new VersionCommand());
            addCommand(new VersionCommand() {
                @Override
                public String getInvoke() {
                    return "version";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "say bot's build version";
                }
            });
        }
        //------------------------------------------------------------------//
        {
            addCommand(new SearchCommand());
            addCommand(new SearchCommand() {
                @Override
                public String getInvoke() {
                    return "search";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Serach music from youtube\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <search word> `";
                }
            });
            addCommand(new upTimeCommand());
            addCommand(new upTimeCommand() {
                @Override
                public String getInvoke() {
                    return "upTime";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Bot's uptime";
                }
            });
            addCommand(new AirInforCommand());
            addCommand(new AirInforCommand() {
                @Override
                public String getInvoke() {
                    return "airinfor";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "air information by airkorea\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <City Name> `";
                }
            });
            addCommand(new AirkoreaListCommand());
            addCommand(new AirkoreaListCommand() {
                @Override
                public String getInvoke() {
                    return "airinforlist";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "local airinformation list by airkorea \n";
                }
            });
            addCommand(new AirLocalInforCommand());
            addCommand(new AirLocalInforCommand() {
                @Override
                public String getInvoke() {
                    return "airlocalinfor";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "local air information by airkorea\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <local name> `";
                }
            });

            addCommand(new CatCommand());
            addCommand(new CatCommand() {
                @Override
                public String getInvoke() {
                    return "neko";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "neko is come here!";
                }
            });
            addCommand(new KickCommand());
            addCommand(new KickCommand() {
                @Override
                public String getInvoke() {
                    return "kick";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Kicks a user off the server.\n" +
                            "Usage: `"  + App.getPREFIX() + getInvoke() + " <user> <reason>`";
                }
            });
            addCommand(new MuteCommand());
            addCommand(new MuteCommand() {
                @Override
                public String getInvoke() {
                    return "mute";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Mute user";
                }
            });
            addCommand(new certificationCommand());
            addCommand(new certificationCommand() {
                @Override
                public String getInvoke() {
                    return "cert";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "certification with discord and steam";
                }
            });
            addCommand(new TESTCommand());
            addCommand(new TESTCommand() {
                @Override
                public String getInvoke() {
                    return "TEST";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "TEST Command";
                }
            });
        }
        //------------------------------------------------------------------//
        {
            addCommand(new certificationFinCommand());
            addCommand(new certificationFinCommand() {
                @Override
                public String getInvoke() {
                    return "certfin";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Verify certification between Steam and Discord \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <Steam Profile/Steam ID/Steam Nickname> `";
                }
            });
            addCommand(new BanCommand());
            addCommand(new BanCommand() {
                @Override
                public String getInvoke() {
                    return "ban";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Ban user from discord server\n" +
                            "Usage: " + App.getPREFIX() + getInvoke() + " <nickname/discord ID/@mention>";
                }
            });
            addCommand(new UnbanCommand());
            addCommand(new UnbanCommand() {
                @Override
                public String getInvoke() {
                    return "unban";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Unban user from discord server\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <nickname/discord ID/@mention>`";
                }
            });
            addCommand(new MemberCount());
            addCommand(new MemberCount() {
                @Override
                public String getInvoke() {
                    return "membercount";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Start/stop/reload server member counting\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "[시작/정지/새로고침]`";
                }
            });
            addCommand(new publicExecutionCommand());
            addCommand(new publicExecutionCommand() {
                @Override
                public String getInvoke() {
                    return "publicexe";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "PublicExecution a user\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "<nickname/discord ID/@mention>";
                }
            });

            addCommand(new ONIGIRICommand());
            addCommand(new ONIGIRICommand() {
                @Override
                public String getInvoke() {
                    return "meme";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "meme command list";
                }
            });
            addCommand(new GreenServerMuteCommand());
            addCommand(new GreenServerMuteCommand() {
                @Override
                public String getInvoke() {
                    return "mutechat";
                }

                @Override
                public String getSmallHelp() {
                    return "Mute chating";
                }

                @Override
                public String getHelp() {
                    return "Mute chating";
                }
            });
            addCommand(new lewdnekoCommand());
            addCommand(new lewdnekoCommand() {
                @Override
                public String getInvoke() {
                    return "lewd";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "lewd neko meme";
                }
            });
            addCommand(new gameServerBanCommand());
            addCommand(new gameServerBanCommand() {
                @Override
                public String getInvoke() {
                    return "serverban";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "ServerCustomCommand \n" +
                            "Please use by `" + App.getPREFIX() + gameServerBanCommand.getCommand() + "`";
                }
            });
            addCommand(new WeatherCommand());
            addCommand(new WeatherCommand() {
                @Override
                public String getInvoke() {
                    return "weather";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Search weather information by City name\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <City name>`";
                }
            });
        }
        //------------------------------------------------------------------//
        {
            addCommand(new DICECommand());
            addCommand(new DICECommand() {
                @Override
                public String getInvoke() {
                    return "dice";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Rolls a dice.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " [sides] [dices]`";
                }
            });
            addCommand(new pollCommand());
            addCommand(new pollCommand() {
                @Override
                public String getInvoke() {
                    return "poll";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "poll!.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " [poll content]`";
                }
            });
            addCommand(new goWorkCommand());
            addCommand(new goWorkCommand() {
                @Override
                public String getInvoke() {
                    return "work";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Go to Work!\n";
                }
            });
            addCommand(new goHomeCommand());
            addCommand(new goHomeCommand() {
                @Override
                public String getInvoke() {
                    return "home";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Go to Home!\n";
                }
            });
            addCommand(new BotOwnerNoticeCommand());
            addCommand(new GreenNoticeCommand());
            addCommand(new PauseCommand());
            addCommand(new PauseCommand() {
                @Override
                public String getInvoke() {
                    return "pause";
                }

                @Override
                public String getSmallHelp() {
                    return "";
                }

                @Override
                public String getHelp() {
                    return "Pause music which is playing music by bot";
                }
            });
        }
    }

    private void addCommand(ICommand command) {
        if(!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
        sleep(10);
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(String name) {
        return commands.get(name);
    }

    public void handleCommand(GuildMessageReceivedEvent event) {
        final TextChannel channel = event.getChannel();
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(App.getPREFIX()), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if(commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            channel.sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
