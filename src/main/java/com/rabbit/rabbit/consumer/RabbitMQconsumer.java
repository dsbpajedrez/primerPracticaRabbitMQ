package com.rabbit.rabbit.consumer;

import com.rabbit.rabbit.notification.data.Notification;
import com.rabbit.rabbit.serializer.JSONMapper;
import com.rabbit.rabbit.serializer.JSONMapperImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RabbitMQconsumer {
    public static final String EVENTS_QUEUE = "events.queue";
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQconsumer.class);
    private final JSONMapper mapper = new JSONMapperImpl();
    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitListener(queues = EVENTS_QUEUE)
    public void recievedMessage(String message) {
        Notification notification = Notification.from(message);
        LOGGER.info(String.valueOf(notification));

        if(notification.getType().equals("co.com.sofka.model.paciente.events.CitaAgregada")){
            Pattern CorreoNotificar = Pattern.compile("\"email\":\"(\\S+?)\"");
            Matcher matcherCorreo = CorreoNotificar.matcher(notification.getBody());
            matcherCorreo.find();
            String Correo = matcherCorreo.group(1);

            Pattern CuerpoNotificar = Pattern.compile("\"horaReserva\":\"(\\S+?)\"");
            Matcher matcherCuerpo = CuerpoNotificar.matcher(notification.getBody());
            matcherCuerpo.find();
            String Cuerpo = matcherCuerpo.group(1);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(Correo);
        mailMessage.setSubject("Cita confirmada");
            mailMessage.setText("Codial saludo, se le informa que su cita ha sido agendada  a las "+Cuerpo+
                    " el dia: "+notification.getInstant());
            javaMailSender.send(mailMessage);
        }

    }
}
