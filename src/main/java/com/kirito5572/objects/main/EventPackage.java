//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.objects.main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public class EventPackage {
    final TextChannel textChannel;
    final Member member;
    final Message message;

    public EventPackage(TextChannel textChannel, Member member, Message message) {
        this.textChannel = textChannel;
        this.member = member;
        this.message = message;
    }

    public TextChannel getTextChannel() {
        return this.textChannel;
    }

    public Member getMember() {
        return this.member;
    }

    public Message getMessage() {
        return this.message;
    }

    public JDA getJDA() {
        return this.textChannel.getJDA();
    }

    public MessageChannel getChannel() {
        return this.textChannel;
    }

    public User getAuthor() {
        return this.member.getUser();
    }

    public Guild getGuild() {
        return this.textChannel.getGuild();
    }
}
