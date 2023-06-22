package com.wissen.tresata.web.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLSession;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


@Configuration
public class FeignSSLConfiguration {

    /**
     * Method to disable SSL Validation.
     *
     * @return true/false
     * @throws Exception
     */
    @Bean
    public Boolean disableSSLValidation() throws Exception {
        final SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[] {
            new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        }, null);

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        return true;
    }

    /**
     * Interceptor for bearer token.
     *
     * @return RequestInterceptor
     */
    RequestInterceptor bearerTokenRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                requestTemplate.header("Authorization", String.format(
                        "Bearer %s", "eyJraWQiOiJsZzh1S3QwVkZ2ZVE2aSttYzRrMXo5OEkzeDBtS0dxWU5jZDNJTHUxSXBzPSIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJjYmRkN2U3Mi00MzIwLTQzNTMtYjUzMy1iNjgxOTBiZmYyMDQiLCJjb2duaXRvOmdyb3VwcyI6WyJ0aGVvbmUiXSwiZW1haWxfdmVyaWZpZWQiOnRydWUsImlzcyI6Imh0dHBzOlwvXC9jb2duaXRvLWlkcC51cy1lYXN0LTEuYW1hem9uYXdzLmNvbVwvdXMtZWFzdC0xX1EzSUk0bVVrZiIsImN1c3RvbTpncm91cCI6InRoZW9uZSIsImNvZ25pdG86dXNlcm5hbWUiOiJzaHViaGFtIiwiYXVkIjoiNXY2Nmc2b2tlOTBlbmJtb2pnNmZzZzI5OTkiLCJldmVudF9pZCI6IjBjNjgzY2VhLTgzYTItNGNkNy04MTI1LTUzNWZhNjdmOTM1MSIsInRva2VuX3VzZSI6ImlkIiwiYXV0aF90aW1lIjoxNjg2ODQ2MDI5LCJleHAiOjE2ODY4NDk2MjksImN1c3RvbTpyb2xlIjoidGVuYW50IiwiaWF0IjoxNjg2ODQ2MDI5LCJlbWFpbCI6InNodWJoYW0ucGF0ZWxAdHJlc2F0YS5jb20ifQ.YDzrJYnDkDtpOGzy0bZvUSx7JtVsmEdgnUBEu4DbBOjd_YhBBm9sHaVSsTdG-xaaf1nDc5swLagSBbvNxCoSjbGj5ArSNzCCBfY1nRUb01DRwOcNE1yNRICXvDV7atP5CkBRYa-2OcImAqk7OCO2mtGyyr7-3RO48JFpgvJ--69X82wlD8E5e4ZpE9KQDviDh9B2gWlYcWRqvhc8hUe030_Vz3oXXf4mTzpzVbcVEq8dbkNxyggJ4qHJrrZhtOXshbh_ZbD3gD4v72e-VSOMsaTQrnunI-5QSXo9IYmbQ2C6bUunmt_9762uV8O0QtzpfyEg3pqr9Be9mz_RANWMAA"
                ));
            }
        };
    }

    /**
     * Bean for Basic Auth Request.
     *
     * @return bean of BasicAuthRequest
     */
    @Bean
    public BasicAuthRequestInterceptor basicAuthenticationInterceptor() {
        return new BasicAuthRequestInterceptor("tresata", "Tresata_2023");
    }
}
