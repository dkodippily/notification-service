package com.raken.email.controller;

import com.raken.email.constant.ApplicationConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dayan Kodippily
 */

@RestController
@RequestMapping(value = ApplicationConstants.BASE_API_URL)
public abstract class NotificationController {
}
