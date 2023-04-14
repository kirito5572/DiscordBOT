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

public record EventPackage(TextChannel textChannel, Member member, Message message) {

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
