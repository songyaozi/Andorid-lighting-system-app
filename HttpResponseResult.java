package yao.myapplication;

/**
 * Created by yao on 11/14/15.
 */
public class HttpResponseResult {
    String show_http_result;
    public HttpResponseResult( String show_http_result) {
        this.show_http_result = show_http_result;

    }
    public String getResponseResult() {
        return show_http_result.toString();
    }
}
