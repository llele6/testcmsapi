package demo;
import javax.net.ssl.SSLContext;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class testcmsapi {

    public static void main(String[] args) {
        test();
    }

    public static HttpEntity test(){
        //   SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
        //       public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        //           return true;
        //       }
        //   }).build();
//
        //   SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
        //           sslcontext,
        //           new String[] { "NO_OP" },
        //           null,
        //           SSLConnectionSocketFactory.getDefaultHostnameVerifier()
        //   );
        //   CloseableHttpClient httpclient = HttpClients.custom()
        //           .setSSLSocketFactory(sslsf)
        //           .build();



        //try {

        CloseableHttpClient httpclient = (CloseableHttpClient) getClient();
        String cmsapi = "https://api.adidas-group.com/page/?publication_path=/reebok_enGB&url=/dot-com/blog/index.html";

        System.out.println("Executing request " + cmsapi);

        CloseableHttpResponse response;
        HttpGet httpGet = new HttpGet(cmsapi);
        //httpGet.setHeader("glass", "true");

        try {
            response = httpclient.execute(httpGet);
        } catch (IOException ex) {
            return null;
        }

        return response.getEntity();
        //  try {
        //          HttpEntity entity = response.getEntity();
        //          System.out.println("----------------------------------------");
        //          System.out.println(response.getStatusLine());
        //          System.out.println(EntityUtils.toString(entity));
        //      } finally {
        //          response.close();
        //      }

    }

    private static HttpClient getClient() {
        final SSLConnectionSocketFactory sslsf;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault(),
                    NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        final Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register( "http", new PlainConnectionSocketFactory())
                .register("https", sslsf)
                .build();

        final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(100);

        HttpClientBuilder customClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setConnectionManager(cm);

        String customAgent;

        //customClient.setProxy(new HttpHost(Proxy_IP, Proxy_PORT, null));
        return customClient.build();
    }


}

