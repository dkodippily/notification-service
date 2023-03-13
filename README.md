# Email Service API
This REST API allows sending an email to one or more recipients using Sendgrid.

# API Docs
* Default API specification - http://localhost:8282/v3/api-docs
* Swagger UI - http://localhost:8282/swagger-ui/index.html

# App Configurations
* apiKey: For security reasons this is read through a different file, secrets.yaml. spring:config:import: optional:secrets.yaml is used to mark this optional 
and secretes.yaml is not available to public.
* service: Service name for any future discovery servers to use.
* enrichApi: External api to get quote of the day.
* fromAddress: Sendgrid registered from address.
* filterEmailsByDomain: Toggle non blacklisted domain filtering.
* filterDomain: Domain to filer.

# Design decisions
* Returned 202 HTTP code with a custom header MAIL_QUEUE_ID - The reason is, in a real world scaling these messages will be sent to a Queue for Async processing,
this header will give the calling client a call back to check the progress of the email.
* Strategy pattern - For the Sendgrid external integration I used Strategy pattern, the reason is there may be multiple external notification services in a 
real application, Although Factory pattern is also a candidate Strategy will give you the benefit of dynamically switching between implementations.
* Externalize the APIKey - spring:config:import: optional:secrets.yaml is used to mark this optional and secretes.yaml is not available to public.
* Exception Translator - This is added to extract more customer friendly exception messages from the response and relay through the Exception advice.
* Spring profiles - Used profiles to mimic different config setups , as of now I use dev, for production or stage we can add accordingly.

# Improvements
* Increase the test coverage.
* Based on if this is part of a Microservice or Single service add appropriate security implementations like JWT, TLS/Certs, Edge/Network level, API gateway level security.
* Streamline logs and add more specific proper logging, so the tools like Splunk can be efficiently used.
* Integrate with a Queue / Async processing of messages.
* Multipart attachment support.
* Integrate with ChatGPT and Whisper APIs to do voice/translated automated emails.
* More proper validation.
* Support Multiple domains to be filtered.

# Start service - Default dev profile
```
java -jar target/notification-service-0.0.1-SNAPSHOT.jar

```
NOTE - For any other specific profile use below syntax.
```
java -jar -Dspring.profiles.active=xxx target/notification-service-0.0.1-SNAPSHOT.jar
```

# Sample request
NOTE : to minimize the client side errors all To,Cc,Bc Addresses are read as lists otherwise client may send the email lists with different delimiters.
```
{
    "subject" : "Test email from app",
    "content" : "Hello Dayan, Good reach out.",
    "toAddresses" : ["dkodippily@gmail.com", "1@redflag.com"],
    "ccAddresses" : ["2@redflag.com"]    
    
}
```

```
curl -X 'POST' \
  'http://localhost:8080/api/v1/emails' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json' \
  -d '{
    "subject" : "Test email from app",
    "content" : "Hello Dayan, Good reach out.",
    "toAddresses" : ["dkodippily@gmail.com", "1@redflag.com"],
    "ccAddresses" : ["2@redflag.com"]    
    
}'
```

# Author
Dayan Kodippily - dkodippily@gmail.com
