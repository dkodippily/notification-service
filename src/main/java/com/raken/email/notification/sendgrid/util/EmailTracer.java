package com.raken.email.notification.sendgrid.util;

import com.raken.email.dto.EmailDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dayan Kodippily
 */


public class EmailTracer {

    private EmailTracer(){}

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailTracer.class);

    public static void filterAndTrace(EmailDto emailDto, String domain) {

        List<String> emailList = Stream.of(emailDto.getToAddresses(), emailDto.getCcAddresses(), emailDto.getBcAddresses())
                .filter(list -> list != null)
                .flatMap(List::stream)
                .filter(s -> s != null)
                .map(Optional::ofNullable)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        trace(emailList, domain);

        emailDto.setToAddresses(filter(emailDto.getToAddresses(), domain));
        emailDto.setCcAddresses(filter(emailDto.getCcAddresses(), domain));
        emailDto.setBcAddresses(filter(emailDto.getBcAddresses(), domain));
    }

    private static void trace(List<String> emailList, String domain) {
        LOGGER.info("tracing black listed domains");
        LOGGER.info("========================================");
        emailList.stream().filter(a -> a.contains(domain)).collect(Collectors.toList()).forEach(
                a -> LOGGER.info(a));
        LOGGER.info("========================================");
    }

    private static List<String> filter(List<String> emailList, String domain) {

        return Stream.of(emailList).filter(list -> list != null)
                .flatMap(List::stream)
                .filter(a -> !a.contains(domain))
                .map(Optional::ofNullable)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }
}
