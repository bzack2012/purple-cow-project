package purple.cow.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.cert.X509Certificate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * Purple Cow API. Functionality includes testing the certificates of users to see if their certs have expired.
 */
@Path("/purple-cow")
public class SSLResource {

    public static final ZonedDateTime CURRENT_DATE_TIME = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
    public static final String VALID_CERT_MESSAGE = "Your certificate has not expired.";
    public static final String INVALID_CERT_MESSAGE = "Your certificate has expired. Please Renew.";
    public static final String CORRUPTED_CERT_MESSAGE = "Your certificate does not have a valid date or is corrupted." +
            "Please obtain a new certificate.";

    /**
     * Test Endpoint to validate purple-cow api is up and running.
     *
     * @param context
     * @return String
     */
    @GET
    @Path("/purpleCowTest")
    @Produces(MediaType.APPLICATION_JSON)
    public String purpleCowTest(@Context HttpServletRequest context) {
        return "Purple Cow Server is Operational";
    }

    /**
     * Resource for validating the whether a certificate is expired.
     * @param context
     * @return
     */
    @GET
    @Path("/validateCertDate")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validateCertificateDate(@Context HttpServletRequest context) {
        X509Certificate certs[] =
                (X509Certificate[])context.getAttribute("javax.servlet.request.X509Certificate");
        X509Certificate certificate = null;
        if (certs != null)
             certificate = certs[0];

        if (Objects.nonNull(certificate)) {
            ZonedDateTime certificateDate = certificate.getNotAfter().toInstant().atZone(ZoneId.of("UTC"));
            return certificateDate.isAfter(CURRENT_DATE_TIME) ? Response.status(400).entity(INVALID_CERT_MESSAGE).build()
                    : Response.status(200).entity(VALID_CERT_MESSAGE).build();
        }
        else
            return Response.status(400).entity(CORRUPTED_CERT_MESSAGE).build();
    }
}
