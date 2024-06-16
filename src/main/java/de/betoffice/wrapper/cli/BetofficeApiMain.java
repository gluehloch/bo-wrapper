/*
 * ============================================================================
 * Project betoffice-wrapper Copyright (c) 2000-2024 by Andre Winkler. All
 * rights reserved.
 * ============================================================================
 * GNU GENERAL PUBLIC LICENSE TERMS AND CONDITIONS FOR COPYING, DISTRIBUTION AND
 * MODIFICATION
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */

package de.betoffice.wrapper.cli;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import de.betoffice.wrapper.api.BetofficeApi;
import de.betoffice.wrapper.api.GroupTypeRef;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = { "de.betoffice", "de.winkler.betoffice" })
public class BetofficeApiMain implements CommandLineRunner {

    @Autowired
    private BetofficeApi betofficeApi;

    @Autowired
    private EM2024Setup em2024setup;

    public static void main(String args[]) {
        SpringApplication.run(BetofficeApiMain.class, args);
    }

    @Override
    public void run(String... args) {
        ApiCommandLineParser parser = new ApiCommandLineParser();
        Optional<ApiCommandLineArguments> acla = parser.parse(args, System.out);

        acla.ifPresent(arguments -> {
            if (arguments.getCommand() != null) {
                switch (arguments.getCommand()) {
                    case HELP -> System.out.println("Help");
                    case TEST_DATABASE_CONNECTION -> {
                        doit(betofficeApi);
                    }
                    case EM_2024 -> {
                        em2024setup.setup();
                    }
                    default -> System.out.println("Help");
                }
            }
        });
    }

    private static void doit(BetofficeApi betofficeApi) {
        List<GroupTypeRef> result = betofficeApi.groupTypes().result();
        for (GroupTypeRef gtr : result) {
            System.out.println(gtr.groupType());
        }
    }

    public static final <T> T getBean(String beanId, Class<T> requiredType, ApplicationContext context) {
        return (T) context.getBean(beanId, requiredType);
    }

    public static final <T> T getBean(Class<T> requiredType, ApplicationContext context) {
        return context.getBean(requiredType);
    }

}
