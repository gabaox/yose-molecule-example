package yose;

import com.objogate.wl.UnsynchronizedProber;
import com.objogate.wl.web.AsyncWebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import yose.pages.HomePage;

import java.io.IOException;

public class YoseDriver {
    private final int port;
    private Yose yose;
    private AsyncWebDriver browser;

    public YoseDriver(int port) {
        this.port = port;
    }

    private AsyncWebDriver browser() {
        if (browser == null) {
            browser = new AsyncWebDriver(new UnsynchronizedProber(), new FirefoxDriver());
        }
        return browser;
    }

    public void start() throws IOException {
        yose = Yose.launch(String.valueOf(port));
    }

    public void stop() throws IOException {
        if (yose != null) yose.stop();
        if (browser != null) browser.quit();
    }

    public HomePage home() {
        browser().navigate().to(url("/"));
        return new HomePage(browser);
    }

    private String url(String path) {
        return yose.uri().resolve(path).toASCIIString();
    }
}