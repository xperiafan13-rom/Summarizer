package docsum;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import docsum.deal.SummaryDeal;
import io.javalin.Javalin;

class Data {
    String img;
    int length;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}

public class Main {

    public static void main(String[] args) {

        Javalin app = Javalin.start(7000);

        app.options("/*",
                ctx -> {

                    String accessControlRequestHeaders = ctx
                            .header("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        ctx.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = ctx
                            .header("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        ctx.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    ctx.status(200);
                });

        app.before(ctx -> ctx.header("Access-Control-Allow-Origin", "*"));

        app.post("/summarize", ctx -> {
            Data b64 = JsonIterator.deserialize(ctx.body(), Data.class);
            SummaryDeal summary = new SummaryDeal();
            ctx.result(JsonStream.serialize(summary.Summary(b64.img, b64.length)));
        });
    }
}
