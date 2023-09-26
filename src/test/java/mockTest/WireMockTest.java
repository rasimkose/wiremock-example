package mockTest;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Rule;
import org.junit.Test;

public class WireMockTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089));

  @Test
  public void ticket_test() throws IOException {
    configureStub();

    OkHttpClient client = new OkHttpClient().newBuilder()
        .build();
    Request request = new Request.Builder()
        .url("http://localhost:8089/message-api/1")
        .method("GET", null)
        .build();
    Response response = client.newCall(request).execute();

    assert response.body() != null;
    assertEquals("welcome", response.body().string());
  }

  private void configureStub() {
    configureFor("localhost", 8089);
    stubFor(get(urlEqualTo("/message-api/1")).willReturn(aResponse().withBody("welcome")));
  }
}
