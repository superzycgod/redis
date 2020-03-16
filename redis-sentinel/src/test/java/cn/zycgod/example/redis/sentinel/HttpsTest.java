package cn.zycgod.example.redis.sentinel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.http.ssl.PrivateKeyDetails;
import org.apache.http.ssl.PrivateKeyStrategy;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.junit.jupiter.api.Test;

/**
 * JDK原生SSL/TLS的双向认证请求代码
 * <p>
 * 
 * 私钥和证书均为openssl工具生成的pem格式
 * 
 * @author zhangyanchao
 *
 */
public class HttpsTest {

	@Test
	public void test() throws Exception {

		URL url = new URL("https://192.168.177.128:2376/info");
		HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();

		SSLContext sslContext = SSLContexts.custom()
				// 加载信任的服务端证书（server.pem），用于客户端验证服务端合法性
				// keytool -import -alias "docker01 server cert" -file server.pem -keystore
				// my.truststore
				.loadTrustMaterial(new File("F:\\VM\\machines\\machines\\docker01\\my.truststore"),
						"docker".toCharArray(), new TrustStrategy() {
							@Override
							public boolean isTrusted(X509Certificate[] chain, String authType)
									throws CertificateException {
								return true;
							}
						})
				// 加载需要发送给服务端的客户端证书，用于服务端验证客户端合法性
				// pem -> keystore
				.loadKeyMaterial(new File("F:\\VM\\machines\\machines\\docker01\\out.keystore"), "docker".toCharArray(),
						"docker".toCharArray(), new PrivateKeyStrategy() {
							@Override
							public String chooseAlias(Map<String, PrivateKeyDetails> aliases, Socket socket) {
								// 证书别名
								return "dockerclient";
							}
						})
				.build();

		httpsConn.setSSLSocketFactory(sslContext.getSocketFactory());

		httpsConn.setHostnameVerifier(new HostnameVerifier() {
			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		});

		httpsConn.setRequestMethod("GET");
		httpsConn.setDoInput(true);
		httpsConn.setDoOutput(true);
		System.out.println(new String(getBytesFromStream(httpsConn.getInputStream()), "UTF-8"));

	}

	private static byte[] getBytesFromStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] kb = new byte[1024];
		int len;
		while ((len = is.read(kb)) != -1) {
			baos.write(kb, 0, len);
		}
		byte[] bytes = baos.toByteArray();
		baos.close();
		is.close();
		return bytes;
	}

}
