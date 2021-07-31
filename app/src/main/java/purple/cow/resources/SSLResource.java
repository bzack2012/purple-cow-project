package purple.cow.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.security.cert.X509Certificate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Enumeration;
import java.util.Objects;

@Path("/purple-cow")
public class SSLResource {

    public static final ZonedDateTime CURRENT_DATE_TIME = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
    public static final String VALID_CERT_MESSAGE = "Your certificate has not expired.";
    public static final String INVALID_CERT_MESSAGE = "Your certificate has expired. Please Renew.";
    public static final String CORRUPTED_CERT_MESSAGE = "Your certificate does not have a valid date or is corrupted." +
            "Please obtain a new certificate.";

    @Path("/purplecowTest") @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String purpleCowTest(@Context HttpServletRequest context) {
        Enumeration<String> headerNames = context.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
        }
        return "servlet";
    }

    @Path("/validateCertDate") @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String validateCertificateDate(@Context HttpServletRequest context) {
        X509Certificate certs[] =
                (X509Certificate[])context.getAttribute("javax.servlet.request.X509Certificate");
        X509Certificate certificate = null;
        if (certs == null)
             certificate = certs[0];

        if (Objects.nonNull(certificate)) {
            ZonedDateTime certificateDate = certificate.getNotAfter().toInstant().atZone(ZoneId.of("UTC"));
            return certificateDate.isAfter(CURRENT_DATE_TIME) ? INVALID_CERT_MESSAGE : VALID_CERT_MESSAGE;
        }
        else
            return CORRUPTED_CERT_MESSAGE;

    }
}
