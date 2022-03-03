//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.kirito5572.objects.main;

import java.util.List;
import javax.annotation.Nonnull;

public interface ICommand {
    void handle(List<String> var1, @Nonnull EventPackage var2);

    String getHelp();

    String getInvoke();

    String getSmallHelp();
}
