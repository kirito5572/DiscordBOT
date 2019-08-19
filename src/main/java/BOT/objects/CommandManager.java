package BOT.Objects;

import BOT.App;
import BOT.Commands.*;
import BOT.Commands.GreenServerCustom.*;
import BOT.Commands.Moderator.*;
import BOT.Commands.Music.*;
import BOT.Commands.ONIGIRIServerCustom.ONIGIRICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager() {
        {
            addCommand(new HelpCommand(this));
            addCommand(new HelpCommand(this) {
                @Override
                public String getInvoke() {
                    return "help";
                }

                @Override
                public String getSmallHelp() {
                    return "";
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
            });
            addCommand(new GreenServerMuteCommand());
            addCommand(new GreenServerMuteCommand() {
                @Override
                public String getInvoke() {
                    return "mutechat";
                }

                @Override
                public String getSmallHelp() {
                    return "";
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
            });

        }
    }

    private void addCommand(ICommand command) {
        if(!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
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
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(App.getPREFIX()), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if(commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().queue();
            commands.get(invoke).handle(args, event);
        }
    }
}
