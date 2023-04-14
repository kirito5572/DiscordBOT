//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.objects.main;

import com.kirito5572.App;
import com.kirito5572.commands.main.AirInformationCommand;
import com.kirito5572.commands.main.AirLocalInformationCommand;
import com.kirito5572.commands.main.BETACommand;
import com.kirito5572.commands.main.CallCommand;
import com.kirito5572.commands.main.CatCommand;
import com.kirito5572.commands.main.ColorCommand;
import com.kirito5572.commands.main.ColorInfoCommand;
import com.kirito5572.commands.main.HelpCommand;
import com.kirito5572.commands.main.PPCommand;
import com.kirito5572.commands.main.PingCommand;
import com.kirito5572.commands.main.SayCommand;
import com.kirito5572.commands.main.ServerInfoCommand;
import com.kirito5572.commands.main.TESTCommand;
import com.kirito5572.commands.main.UnusedColorCommand;
import com.kirito5572.commands.main.UserInfoCommand;
import com.kirito5572.commands.main.VersionCommand;
import com.kirito5572.commands.main.WeatherCommand;
import com.kirito5572.commands.main.inviteCommand;
import com.kirito5572.commands.main.lewdnekoCommand;
import com.kirito5572.commands.main.naverCommand;
import com.kirito5572.commands.main.upTimeCommand;
import com.kirito5572.commands.main.gamecommand.DICECommand;
import com.kirito5572.commands.main.gamecommand.OddOrEvenCommand;
import com.kirito5572.commands.main.gamecommand.RockPaperScissorsCommand;
import com.kirito5572.commands.main.gamecommand.russianRouletteCommand;
import com.kirito5572.commands.main.moderator.BanCommand;
import com.kirito5572.commands.main.moderator.ClearCommand;
import com.kirito5572.commands.main.moderator.KickCommand;
import com.kirito5572.commands.main.moderator.MemberCount;
import com.kirito5572.commands.main.moderator.MessagePinCommand;
import com.kirito5572.commands.main.moderator.MuteCommand;
import com.kirito5572.commands.main.moderator.OwnerChattingToServer;
import com.kirito5572.commands.main.moderator.SlashSetupCommand;
import com.kirito5572.commands.main.moderator.UnbanCommand;
import com.kirito5572.commands.main.moderator.UnmuteCommand;
import com.kirito5572.commands.main.moderator.botIpAddressCommand;
import com.kirito5572.commands.main.moderator.configCommand;
import com.kirito5572.commands.main.moderator.giveRoleCommand;
import com.kirito5572.commands.main.moderator.pollCommand;
import com.kirito5572.commands.main.moderator.publicExecutionCommand;
import com.kirito5572.commands.main.ONIGIRIServerCustom.ONIGIRICommand;
import com.kirito5572.commands.main.owneronlycommand.BotOwnerNoticeCommand;
import com.kirito5572.commands.main.owneronlycommand.evalCommand;
import com.kirito5572.commands.main.owneronlycommand.getGuildsInformationCommand;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.IMentionable;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageActivity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.MessageReference;
import net.dv8tion.jda.api.entities.MessageSticker;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.ComponentLayout;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.AuditableRestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.requests.restaction.pagination.ReactionPaginationAction;
import org.apache.commons.collections4.Bag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandManager {
    private final Logger logger = LoggerFactory.getLogger(CommandManager.class);
    private final Map<String, ICommand> commands = new HashMap<>();

    public CommandManager() {
        this.addCommand(new HelpCommand(this));
        this.addCommand(new PingCommand());
        this.addCommand(new UserInfoCommand());
        this.addCommand(new ColorInfoCommand());
        this.addCommand(new ColorCommand());
        this.addCommand(new ClearCommand());
        this.addCommand(new giveRoleCommand());
        this.addCommand(new SayCommand());
        this.addCommand(new UnusedColorCommand());
        this.addCommand(new VersionCommand());
        this.addCommand(new SlashSetupCommand());
        this.addCommand(new upTimeCommand());
        this.addCommand(new AirInformationCommand());
        this.addCommand(new AirLocalInformationCommand());
        this.addCommand(new CatCommand());
        this.addCommand(new KickCommand());
        this.addCommand(new TESTCommand());
        this.addCommand(new BanCommand());
        this.addCommand(new UnbanCommand());
        this.addCommand(new MemberCount());
        this.addCommand(new publicExecutionCommand());
        this.addCommand(new ONIGIRICommand());
        this.addCommand(new lewdnekoCommand());
        this.addCommand(new WeatherCommand());
        this.addCommand(new DICECommand());
        this.addCommand(new botIpAddressCommand());
        this.addCommand(new pollCommand());
        this.addCommand(new BotOwnerNoticeCommand());
        this.addCommand(new RockPaperScissorsCommand());
        this.addCommand(new russianRouletteCommand());
        this.addCommand(new CallCommand());
        this.addCommand(new OddOrEvenCommand());
        this.addCommand(new BETACommand());
        this.addCommand(new OwnerChattingToServer());
        this.addCommand(new configCommand());
        this.addCommand(new inviteCommand());
        this.addCommand(new getGuildsInformationCommand());
        this.addCommand(new evalCommand());
        this.addCommand(new MessagePinCommand());
        this.addCommand(new PPCommand());
        this.addCommand(new naverCommand());
        this.addCommand(new ServerInfoCommand());
        this.addCommand(new MuteCommand());
        this.addCommand(new UnmuteCommand());
    }

    private void addCommand(@NotNull ICommand command) {
        if (!this.commands.containsKey(command.getInvoke())) {
            this.commands.put(command.getInvoke(), command);
        }

        this.sleep();
    }

    private void sleep() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException var8) {
            StackTraceElement[] eStackTrace = var8.getStackTrace();
            StringBuilder a = new StringBuilder();
            for (StackTraceElement stackTraceElement : eStackTrace) {
                a.append(stackTraceElement).append("\n");
            }

            this.logger.warn(a.toString());
        }

    }

    @NotNull
    public Collection<ICommand> getCommands() {
        return this.commands.values();
    }

    public ICommand getCommand(String name) {
        return this.commands.get(name);
    }

    public void handleCommand(@NotNull GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        String[] split = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote(App.getPREFIX()), "").split("\\s+");
        String invoke = split[0].toLowerCase();
        EventPackage eventPackage = new EventPackage(event.getChannel(), event.getMember(), event.getMessage());
        if (this.commands.containsKey(invoke)) {
            List<String> args = Arrays.asList(split).subList(1, split.length);
            channel.sendTyping().queue();
            this.commands.get(invoke).handle(args, eventPackage);
        }

    }

    public void handleCommand(@NotNull final SlashCommandEvent event) {
        String[] split = event.getCommandString().replaceFirst("(?i)" + Pattern.quote("/"), "").split("\\s+");
        String invoke = split[0].toLowerCase();
        if (this.commands.containsKey(invoke)) {
            List<String> args = Arrays.asList(split).subList(1, split.length);
            Message message = new Message() {
                @Nullable
                public MessageReference getMessageReference() {
                    return null;
                }

                @NotNull
                public List<User> getMentionedUsers() {
                    return null;
                }

                @NotNull
                public Bag<User> getMentionedUsersBag() {
                    return null;
                }

                @NotNull
                public List<TextChannel> getMentionedChannels() {
                    return null;
                }

                @NotNull
                public Bag<TextChannel> getMentionedChannelsBag() {
                    return null;
                }

                @NotNull
                public List<Role> getMentionedRoles() {
                    return null;
                }

                @NotNull
                public Bag<Role> getMentionedRolesBag() {
                    return null;
                }

                @NotNull
                public List<Member> getMentionedMembers(@NotNull Guild guild) {
                    return null;
                }

                @NotNull
                public List<Member> getMentionedMembers() {
                    return null;
                }

                @NotNull
                public List<IMentionable> getMentions(@NotNull MentionType... types) {
                    return null;
                }

                public boolean isMentioned(@NotNull IMentionable mentionable, @NotNull MentionType... types) {
                    return false;
                }

                public boolean mentionsEveryone() {
                    return false;
                }

                public boolean isEdited() {
                    return false;
                }

                @Nullable
                public OffsetDateTime getTimeEdited() {
                    return null;
                }

                @NotNull
                public User getAuthor() {
                    return event.getUser();
                }

                @Nullable
                public Member getMember() {
                    return event.getMember();
                }

                @NotNull
                public String getJumpUrl() {
                    return null;
                }

                @NotNull
                public String getContentDisplay() {
                    return event.getCommandString();
                }

                @NotNull
                public String getContentRaw() {
                    return event.getCommandString();
                }

                @NotNull
                public String getContentStripped() {
                    return event.getCommandString();
                }

                @NotNull
                public List<String> getInvites() {
                    return null;
                }

                @Nullable
                public String getNonce() {
                    return null;
                }

                public boolean isFromType(@NotNull ChannelType type) {
                    return type == ChannelType.TEXT;
                }

                @NotNull
                public ChannelType getChannelType() {
                    return event.getChannelType();
                }

                public boolean isWebhookMessage() {
                    return false;
                }

                @NotNull
                public MessageChannel getChannel() {
                    return event.getChannel();
                }

                @NotNull
                public PrivateChannel getPrivateChannel() {
                    return null;
                }

                @NotNull
                public TextChannel getTextChannel() {
                    return event.getTextChannel();
                }

                @Nullable
                public Category getCategory() {
                    return null;
                }

                @NotNull
                public Guild getGuild() {
                    return Objects.requireNonNull(event.getGuild());
                }

                @NotNull
                public List<Attachment> getAttachments() {
                    return null;
                }

                @NotNull
                public List<MessageEmbed> getEmbeds() {
                    return null;
                }

                @NotNull
                public List<ActionRow> getActionRows() {
                    return null;
                }

                @NotNull
                public List<Emote> getEmotes() {
                    return null;
                }

                @NotNull
                public Bag<Emote> getEmotesBag() {
                    return null;
                }

                @NotNull
                public List<MessageReaction> getReactions() {
                    return null;
                }

                @NotNull
                public List<MessageSticker> getStickers() {
                    return null;
                }

                public boolean isTTS() {
                    return false;
                }

                @Nullable
                public MessageActivity getActivity() {
                    return null;
                }

                @NotNull
                public MessageAction editMessage(@NotNull CharSequence newContent) {
                    return null;
                }

                @NotNull
                public MessageAction editMessageEmbeds(@NotNull Collection<? extends MessageEmbed> embeds) {
                    return null;
                }

                @NotNull
                public MessageAction editMessageComponents(@NotNull Collection<? extends ComponentLayout> components) {
                    return null;
                }

                @NotNull
                public MessageAction editMessageFormat(@NotNull String format, @NotNull Object... args) {
                    return null;
                }

                @NotNull
                public MessageAction editMessage(@NotNull Message newContent) {
                    return null;
                }

                @NotNull
                public AuditableRestAction<Void> delete() {
                    return null;
                }

                @NotNull
                public JDA getJDA() {
                    return event.getJDA();
                }

                public boolean isPinned() {
                    return false;
                }

                @NotNull
                public RestAction<Void> pin() {
                    return null;
                }

                @NotNull
                public RestAction<Void> unpin() {
                    return null;
                }

                @NotNull
                public RestAction<Void> addReaction(@NotNull Emote emote) {
                    return null;
                }

                @NotNull
                public RestAction<Void> addReaction(@NotNull String unicode) {
                    return null;
                }

                @NotNull
                public RestAction<Void> clearReactions() {
                    return null;
                }

                @NotNull
                public RestAction<Void> clearReactions(@NotNull String unicode) {
                    return null;
                }

                @NotNull
                public RestAction<Void> clearReactions(@NotNull Emote emote) {
                    return null;
                }

                @NotNull
                public RestAction<Void> removeReaction(@NotNull Emote emote) {
                    return null;
                }

                @NotNull
                public RestAction<Void> removeReaction(@NotNull Emote emote, @NotNull User user) {
                    return null;
                }

                @NotNull
                public RestAction<Void> removeReaction(@NotNull String unicode) {
                    return null;
                }

                @NotNull
                public RestAction<Void> removeReaction(@NotNull String unicode, @NotNull User user) {
                    return null;
                }

                @NotNull
                public ReactionPaginationAction retrieveReactionUsers(@NotNull Emote emote) {
                    return null;
                }

                @NotNull
                public ReactionPaginationAction retrieveReactionUsers(@NotNull String unicode) {
                    return null;
                }

                @Nullable
                public ReactionEmote getReactionByUnicode(@NotNull String unicode) {
                    return null;
                }

                @Nullable
                public ReactionEmote getReactionById(@NotNull String id) {
                    return null;
                }

                @Nullable
                public ReactionEmote getReactionById(long id) {
                    return null;
                }

                @NotNull
                public AuditableRestAction<Void> suppressEmbeds(boolean suppressed) {
                    return null;
                }

                @NotNull
                public RestAction<Message> crosspost() {
                    return null;
                }

                public boolean isSuppressedEmbeds() {
                    return false;
                }

                @NotNull
                public EnumSet<MessageFlag> getFlags() {
                    return null;
                }

                public long getFlagsRaw() {
                    return 0L;
                }

                public boolean isEphemeral() {
                    return false;
                }

                @NotNull
                public MessageType getType() {
                    return null;
                }

                @Nullable
                public Interaction getInteraction() {
                    return null;
                }

                public void formatTo(Formatter formatter, int flags, int width, int precision) {
                }

                public long getIdLong() {
                    return 0L;
                }
            };
            EventPackage eventPackage = new EventPackage(event.getTextChannel(), event.getMember(), message);
            event.reply("").queue(interactionHook -> interactionHook.deleteOriginal().queueAfter(10L, TimeUnit.SECONDS));
            this.commands.get(invoke).handle(args, eventPackage);
        }

    }
}
