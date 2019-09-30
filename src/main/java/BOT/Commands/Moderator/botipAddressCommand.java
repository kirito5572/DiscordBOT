package BOT.Commands.Moderator;

import BOT.Listener.Listener;
import BOT.Objects.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

public class botipAddressCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        String ip;
        if(!event.getMember().getUser().getId().equals(Listener.getID1())) {
            return;

        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    ip = addr.getHostAddress();
                    event.getChannel().sendMessage(iface.getDisplayName() + " " + ip).queue();
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getHelp() {
        return "봇이 구동되고 있는 컴퓨터의 IP를 알아냅니다.";
    }

    @Override
    public String getInvoke() {
        return "내아이피";
    }

    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
