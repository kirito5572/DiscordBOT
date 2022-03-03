package BOT.Objects;

import com.kirito5572.App;
import BOT.Commands.*;
import BOT.Commands.GameCommand.*;
import BOT.Commands.Moderator.*;
import BOT.Commands.ONIGIRIServerCustom.ONIGIRICommand;
import BOT.Commands._OwnerOnlyCommand.evalCommand;
import BOT.Commands._OwnerOnlyCommand.BotOwnerNoticeCommand;
import BOT.Commands._OwnerOnlyCommand.getGuildsInforCommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {
    private final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager() {
        {
            addCommand(new HelpCommand(this));
            addCommand(new HelpCommand(this) {
                @NotNull
                @Override
                public String getInvoke() {
                    return "help";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Shows a list of all the commands.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " [command]`";
                }
            });
            addCommand(new PingCommand());
            addCommand(new PingCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "ping";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Pong!\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "`";
                }
            });
            addCommand(new UserInfoCommand());
            addCommand(new UserInfoCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "userinfo";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Displays information about a user.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " [user name/@user/user id]`";
                }
            });
            addCommand(new ColorInfoCommand());
            addCommand(new ColorInfoCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "colorinfo";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Print color that you can set in discord \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "`";
                }
            });
            addCommand(new ColorCommand());
            addCommand(new ColorCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "color";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Set discord nickname color \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <colorcode/#color/intcolor> `";
                }
            });

            addCommand(new ClearCommand());
            addCommand(new ClearCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "clear";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Clears the chat with the specified amount of messages.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <amount>`";
                }
            });
            addCommand(new giveroleCommand());
            addCommand(new giveroleCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "giverole";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "create a role and give role to member \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <role name> <username>`\n" +
                            "option: [#color], [^permission int] [$role position] [setHoisted]";
                }
            });
            addCommand(new SayCommand());
            addCommand(new SayCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "say";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "send message by bot(only text)\n " +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <Message>`";
                }
            });
            addCommand(new UnusedColorCommand());
            addCommand(new UnusedColorCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "unusedcolorrole";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Delete unusedcolorrole \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke();
                }
            });
        }
        //------------------------------------------------------------------//
        {
            addCommand(new VersionCommand());
            addCommand(new VersionCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "version";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "say bot's build version";
                }
            });
        }
        //------------------------------------------------------------------//
        {
            addCommand(new upTimeCommand());
            addCommand(new upTimeCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "upTime";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Bot's uptime";
                }
            });
            addCommand(new AirInforCommand());
            addCommand(new AirInforCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "airinfor";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "air information by airkorea\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <City Name> `";
                }
            });
            addCommand(new AirLocalInforCommand());
            addCommand(new AirLocalInforCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "airlocalinfor";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "local air information by airkorea\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <local name> `";
                }
            });

            addCommand(new CatCommand());
            addCommand(new CatCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "neko";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "neko is come here!";
                }
            });
            addCommand(new KickCommand());
            addCommand(new KickCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "kick";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Kicks a user off the server.\n" +
                            "Usage: `"  + App.getPREFIX() + getInvoke() + " <user> <reason>`";
                }
            });
            addCommand(new TESTCommand());
            addCommand(new TESTCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "TEST";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "TEST Command";
                }
            });
        }
        //------------------------------------------------------------------//
        {
            addCommand(new BanCommand());
            addCommand(new BanCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "ban";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Ban user from discord server\n" +
                            "Usage: " + App.getPREFIX() + getInvoke() + " <nickname/discord ID/@mention>";
                }
            });
            addCommand(new UnbanCommand());
            addCommand(new UnbanCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "unban";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Unban user from discord server\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " <nickname/discord ID/@mention>`";
                }
            });
            addCommand(new MemberCount());
            addCommand(new MemberCount() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "membercount";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Start/stop/reload server member counting\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "[시작/정지/새로고침]`";
                }
            });
            addCommand(new publicExecutionCommand());
            addCommand(new publicExecutionCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "publicexe";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "PublicExecution a user\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "<nickname/discord ID/@mention>";
                }
            });

            addCommand(new ONIGIRICommand());
            addCommand(new ONIGIRICommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "meme";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "meme command list";
                }
            });
            addCommand(new lewdnekoCommand());
            addCommand(new lewdnekoCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "lewd";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "lewd neko meme";
                }
            });
            addCommand(new WeatherCommand());
            addCommand(new WeatherCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "weather";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
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
                @NotNull
                @Override
                public String getInvoke() {
                    return "dice";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Rolls a dice.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " [sides] [dices]`";
                }
            });
            addCommand(new botipAddressCommand());
            addCommand(new botipAddressCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "myip";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "return bot's ip address";
                }
            });
            addCommand(new pollCommand());
            addCommand(new pollCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "poll";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "poll!.\n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + " [poll content]`";
                }
            });
            addCommand(new BotOwnerNoticeCommand());
            addCommand(new RockPaperScissorsCommand());
            addCommand(new RockPaperScissorsCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "rockpaperscissors";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Rock! Paper! Scissors! \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "<가위/바위/보>";
                }
            });
            addCommand(new russianRouletteCommand());
            addCommand(new russianRouletteCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "russianRoulette";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "RussianRoulette! \n" +
                            "Usage: `" + App.getPREFIX() + getInvoke() + "<reload/shot>";
                }
            });
            addCommand(new CallCommand());
            addCommand(new CallCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "call";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Call to bot maker!";
                }
            });
            addCommand(new OddOrEvenCommand());
            addCommand(new OddOrEvenCommand() {
                @NotNull
                @Override
                public String getInvoke() {
                    return "evenodd";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "even/odd game!";
                }
            });
            addCommand(new BETACommand());
            addCommand(new BETACommand(){
                @NotNull
                @Override
                public String getInvoke() {
                    return "beta";
                }

                @NotNull
                @Override
                public String getSmallHelp() {
                    return "";
                }

                @NotNull
                @Override
                public String getHelp() {
                    return "Invite beta bot!";
                }
            });
        }
        addCommand(new OwnerChattingToServer());
        addCommand(new configCommand());
        addCommand(new configCommand() {
            @NotNull
            @Override
            public String getInvoke() {
                return "config";
            }

            @NotNull
            @Override
            public String getSmallHelp() {
                return "";
            }

            @NotNull
            @Override
            public String getHelp() {
                return "config bot setting";
            }
        });
        addCommand(new inviteCommand());
        addCommand(new inviteCommand() {
            @NotNull
            @Override
            public String getInvoke() {
                return "invite";
            }

            @NotNull
            @Override
            public String getSmallHelp() {
                return "";
            }

            @NotNull
            @Override
            public String getHelp() {
                return "Invite bot link";
            }
        });

        addCommand(new getGuildsInforCommand());
        addCommand(new evalCommand());
        addCommand(new MessagePinCommand());
        addCommand(new MessagePinCommand() {
            @Override
            public String getInvoke() {
                return "pin";
            }

            @Override
            public String getSmallHelp() {
                return "";
            }

            @Override
            public String getHelp() {
                return "pin message in Channel";
            }
        });
        addCommand(new PPCommand());
        addCommand(new naverCommand());
        addCommand(new ServerInfoCommand());
        addCommand(new ServerInfoCommand() {
            @Override
            public String getInvoke() {
                return "serverInfo";
            }

            @Override
            public String getSmallHelp() {
                return "";
            }

            @Override
            public String getHelp() {
                return "Print your server's information";
            }
        });
        addCommand(new MuteCommand());
        addCommand(new MuteCommand() {
            @Override
            public @NotNull String getInvoke() {
                return "mute";
            }
        });
        addCommand(new UnmuteCommand());
        addCommand(new UnmuteCommand() {
            @Override
            public @NotNull String getInvoke() {
                return "mute";
            }
        });
    }

    private void addCommand(@NotNull ICommand command) {
        if(!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
        sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {

            StackTraceElement[] eStackTrace = e.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }
            logger.warn(a.toString());
        }
    }

    @NotNull
    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(String name) {
        return commands.get(name);
    }

    public void handleCommand(@NotNull GuildMessageReceivedEvent event) {
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
