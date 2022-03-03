package BOT.Commands.Moderator;

import com.kirito5572.listener.main.Listener;
import com.kirito5572.objects.main.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;

public class botipAddressCommand implements ICommand {
    @Override
    public void handle(@NotNull List<String> args, @NotNull EventPackage event) {
        String ip;
        if(!Objects.requireNonNull(event.getMember()).getUser().getId().equals(Listener.getID1())) {
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

    @NotNull
    @Override
    public String getHelp() {
        return "봇이 구동되고 있는 컴퓨터의 IP를 알아냅니다.";
    }

    @NotNull
    @Override
    public String getInvoke() {
        return "내아이피";
    }

    @NotNull
    @Override
    public String getSmallHelp() {
        return "moderator";
    }
}
