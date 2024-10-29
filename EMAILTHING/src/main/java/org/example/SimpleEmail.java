package org.example;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleEmail {
    /* Outgoing Mail (SMTP) Server
     * requires TLS or SSL: smtp.gmail.com(SSL)
     * Use Authentication: Yes
     * Port for SSL: 465 */

    public static void main(String[] args) {
        final String fromEmail = "mumuffxiv@gmail.com"; // EMAIL DE SALIDA
        final String password = "rtdx zqyp dpqn kobi "; // CONTRASEÑA DEL EMAIL DE SALIDA
        final String toEmail = ""; // EMAIL DESTINATARIO

        System.out.println("Configurando datos conexión SSL");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP DE GMAIL EN ESTE CASO
        props.put("mail.smtp.socketFactory.port", "465"); // PUERTO SSL
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); // SSL Factory Class
        props.put("mail.smtp.auth", "true"); // ACTIVAR SMTP AUTHENTICATION
        props.put("mail.smtp.port", "465"); // SMTP PORT

        // Crear sesión con autenticación
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        System.out.println("Sesión creada");

        // Llamar al método para enviar email
        EmailUtil.sendEmail(session, toEmail, "ASUNTO", "MENSAJE/CUERPO");
    }

    static class EmailUtil {
        /**
         * Enviar un email usando la sesión que nos da
         *
         * @param session Sesión del email
         * @param toEmail El recipiente del correo electrónico
         * @param subject El destinatario del email
         * @param body El cuerpo del email
         */
        public static void sendEmail(Session session, String toEmail, String subject, String body) {
            try {
                MimeMessage msg = new MimeMessage(session);
                // Configurar cabeceras
                msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                msg.addHeader("format", "flowed");
                msg.addHeader("Content-Transfer-Encoding", "8-bit");
                msg.setFrom(new InternetAddress("no_reply_DOSA@DAM.com", "NO BIZUM NO RESPONDER")); // Ejemplo de dato
                msg.setReplyTo(InternetAddress.parse("no_reply_DOSA@DAM.com", false));
                msg.setSubject(subject, "UTF-8");
                msg.setText(body, "UTF-8");
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

                System.out.println("MENSAJE CREADO");
                Transport.send(msg);
                System.out.println("EMAIL ENVIADO"); // Si no da error
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}