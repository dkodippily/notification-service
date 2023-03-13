package com.rk.email.util;

import com.rk.email.dto.EmailDto;
import com.rk.email.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Dayan Kodippily
 */


public class InputValidator {

    private InputValidator(){}

    private static final Logger LOGGER = LoggerFactory.getLogger(InputValidator.class);

    private static final String INVALID_ADDRESS = "Invalid Address";
    private static final String INVALID_ADDRESS_EMPTY = "Empty or null receiver address";
    private static final String INVALID_SUBJECT = "Invalid Subject";
    private static final String INVALID_CONTENT = "Invalid Content";

    private static final String EMAIL_REGX = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)+$";


    public static void validate(EmailDto emailDto) throws BadRequestException {

        if(CollectionUtils.isEmpty(emailDto.getToAddresses())){
            LOGGER.error("Empty or null receiver address");
            throw new BadRequestException(INVALID_ADDRESS_EMPTY);
        }

        if (StringUtils.isBlank(emailDto.getContent())) {
            LOGGER.error("Empty body - {}", emailDto.getToAddresses());
            throw new BadRequestException(INVALID_CONTENT);
        }

        if (StringUtils.isBlank(emailDto.getSubject())) {
            LOGGER.error("Empty subject - {}", emailDto.getToAddresses());
            throw new BadRequestException(INVALID_SUBJECT);
        }

        List<String> emailList = Stream.of(emailDto.getToAddresses(), emailDto.getCcAddresses(), emailDto.getBcAddresses())
                .filter(list -> list != null)
                .flatMap(List::stream)
                .filter(s -> s != null)
                .map(Optional::ofNullable)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        for (String email : emailList) {
            if (!StringUtils.isNotEmpty(email) && email.matches(EMAIL_REGX)) {
                throw new BadRequestException(INVALID_ADDRESS);
            }
        }
    }

}
