package in.brainupgrade.sqs;

import in.brainupgrade.dynamodb.User;
import in.brainupgrade.dynamodb.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Component
@Slf4j
public class Consumer {

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @Autowired
    UserService userService;

    
    @SqsListener(value = "${cloud.aws.end-point.uri}",deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void processMessage(TestMessage message) {

        log.info("Message from user access SQS {}", message.getMessage());

        User user = new User();
        user.setEmail("wengjiancong1994@gmail.com");
        user.setFirstName("Kevin");
        user.setLastName("Weng");
        user.setAccessTime(new Date());
        user.setUrlAccessed(message.getMessage());

        userService.saveAccessLog(user);
        log.info("User access log saved");

    }
}