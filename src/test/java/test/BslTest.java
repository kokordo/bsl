package test;

import org.boilit.bsl.Engine;
import org.boilit.bsl.xio.FileResourceLoader;
import org.boilit.bsl.xtp.DefaultTextProcessor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boilit
 * @see
 */
public class BslTest {
    public static void main(String[] args) throws Exception {
        String file = "D:\\W04WorkSpace\\Maven001\\Bsl\\src\\test\\java\\test\\bsl.html";

        List<Stock> items = Stock.dummyItems();

        Engine engine = new Engine();
        engine.clearTemplateCache();
        engine.setInputEncoding(System.getProperty("file.encoding"));
        engine.setOutputEncoding("UTF-8");
        engine.setSpecifiedEncoder(true);
        engine.setUseTemplateCache(true);
        engine.setResourceLoader(new FileResourceLoader(engine.getInputEncoding()));
        engine.setTextProcessor(new DefaultTextProcessor());

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("date", new Date());
        model.put("items", items);
        engine.getTemplate(file).execute(model, System.out);
    }
}
